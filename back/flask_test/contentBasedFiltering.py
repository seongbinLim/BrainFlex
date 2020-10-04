# -*- coding: utf-8 -*-


from numpy.lib.function_base import average
import pandas as pd
import numpy as np
from S3Connection import s3, bucket
import warnings;warnings.filterwarnings('ignore')
import os
from random import *

def Content_recommend_False(userId):
    return Content_recommend(userId,False)

def Content_recommend_True(userId):
    return Content_recommend(userId,True)

def Content_recommend(userId,Boolean):
    file_name = "problem.csv"
    obj = s3.get_object(Bucket= bucket, Key= file_name) 
    problem_total = pd.read_csv(obj['Body']) # 'Body' is a key word

    # for local ...
    # problem_total = pd.read_csv(os.getcwd()+'/back/flask_test/problem/problem.csv')
    #problem_total = pd.read_csv('./problem/problem.csv',index_col=None,header=0)

    file_name = "userdetail/"+userId+".csv"
    obj = s3.get_object(Bucket= bucket, Key= file_name)
    target_user = pd.read_csv(obj['Body']) # 'Body' is a key word

    # for local ...
    # filepath = "userdetail/"+userId+".csv"
    # target_user = pd.read_csv(os.getcwd()+'/back/flask_test/userdetail/'+userId+'.csv')
    #target_user = pd.read_csv('./userdetail/'+userId+'.csv',index_col=None,header=0)

    target_list = target_user[target_user['정답여부']==Boolean]

    #problem_total = pd.concat([problem_total,target_user[target_user['정답여부']==False]])
    #problem_total.drop_duplicates()


    target_problems = np.array(target_list['문제번호'].tolist())
    length = len(target_problems)
    
    target_problems = target_problems[0:int(length/(length/2))]


    level_array = ['Bronze','Silver','Gold','Platinum','Diamond','Ruby','Not']
    recommend_Problem = pd.DataFrame(columns=['문제번호','난이도','제목','weighted_vote'])
    for i in range(0, len(target_problems)):
        problem = problem_total
        target_problem = target_problems[i]   
        problem_df = problem[['문제번호','난이도','분류','해결','평균시도']]
        problem_df = problem_df.dropna(axis=0)
        pd.set_option('max_colwidth',100)
        #각각의 장르와 그 장르의 id를 추출해 낼 수 있다
        problem_df[['분류','난이도']][:1]

        from sklearn.feature_extraction.text import CountVectorizer

        #CountVectorizer를 적용하기 위해 공백문자로 word 단위가 구분되는 문자열로 변환
        count_vect  = CountVectorizer(min_df = 0, ngram_range=(1,2))
        
        genre_mat = count_vect.fit_transform(problem_df['분류'].astype('U').values)
        
        from sklearn.metrics.pairwise import cosine_similarity

        genre_sim = cosine_similarity(genre_mat, genre_mat)
        
        genre_sim_sorted_ind = genre_sim.argsort()[:,::-1]
        

        #장르 유사도에 따라 영화를 추천하는 함수
        #함수명 : find_sim_movie()
        #movies_df DataFrame : 기반 데이터
        #genre_sim_sorted_ind : 레코드별 장르 코사인 유사도 인덱스
        #DataFrame : 고객이 선정한 추천 기준이 되는 영화제목, 추천할 영화 건수를 입력하면 추천 영화 정보를 가짐
        def find_sim_problem(df, sorted_ind, title_name, top_n = 10):
            
            title_problem = df[df['문제번호']== int(title_name)]
            
            title_index = title_problem.index.values
            similar_indexes = sorted_ind[title_index,:(top_n)]
            
            similar_indexes = similar_indexes.reshape(-1)
            
            return df.iloc[similar_indexes]

        similar_movies = find_sim_problem(problem_df, genre_sim_sorted_ind,target_problem, 40)
        similar_movies[['문제번호','평균시도','해결','난이도','분류']]

        problem_df[['문제번호','난이도','평균시도']].sort_values('평균시도',ascending=True)[:10]


        #난이도를 정제하여 난이도 수준을 맞춤]
        def level_set(df,target_problem):
            level_idx = df.index[df['문제번호']==int(target_problem)]
            level = level_array.index(df.loc[level_idx[0]]['난이도'].split(' ')[0])
            average_try = df.loc[level_idx[0]]['평균시도']
            print("average_Try : "+ str(average_try))
            df['level'],df['temp']= df['난이도'].str.split(' ',1).str
            for i, row in df.iterrows() :
                index = level_array.index(df.at[i,'level'])
                if(index == level): df.at[i,'평균시도'] = abs(df.at[i,'평균시도'] - average_try)
                else:
                    df.at[i,'평균시도'] = (-1)*abs(index - level) * abs(df.at[i,'평균시도'] - average_try)
        level_set(problem_df,target_problem)
        #try:
        #    level_set(problem_df,target_problem)
        #except:
        #        print("error!")
        C = problem_df['평균시도'].mean()
        m = problem_df['해결'].quantile(0.6)


        #원래 점수에 
        percentile = 0.6
        m = problem_df['해결'].quantile(0.6) # 최소 문제 푼 수
        C = problem_df['평균시도'].mean() #전체 문제 평균 평점

        def weighted_vote_average(record):
            v = record['해결'] #개별 문제 평점
            R = record['평균시도'] #개별 문제에 대한 평균평점

            return  (v/(v+m)) * R + (v/(v+m)) * C


        problem['weighted_vote'] = problem_df.apply(weighted_vote_average,axis=1)
    

        problem = problem.drop_duplicates(['문제번호'],keep='last')
        #새로 부여된 weighted_vote 평점이 높은 순으로 상위 10개 추출
        problem[['문제번호','제목','난이도','평균시도','weighted_vote','해결']].sort_values(
            'weighted_vote',ascending=False)[:10] 


        #이제 새롭게 정의된 평점 기준에 따라서 영화를 추천한다
        #장르 유사성이 높은 영화를 top_n의 2배수만큼 후보군으로 선정한 뒤에 weighted_vote 칼럼이 높은 순으로 top_n만큼 추출하는 방식으로
        #find_sim_movie()함수를 변경한다

        def find_sim_movie(df, sorted_ind, title_name, top_n =3):
            title_problem = df[df['문제번호'] == int(title_name)]
            title_index = title_problem.index.values
            #top_n의 2배에 해당하는 장르 유사성이 높은 인덱스 추출
            similar_indexes = sorted_ind[title_index, :(top_n*2)]
            similar_indexs = similar_indexes.reshape(-1)
            #기준 영화 인덱스는 제외
            similar_indexes = similar_indexes[similar_indexes!=title_index]
            #top_n의 2배에 해당하는 후보군에서 weighted_vote가 높은 순으로 top_n만큼 추출
            return df

        similar_problem = find_sim_movie(problem,genre_sim_sorted_ind,target_problem,10)
        similar_problem[['문제번호','난이도','제목','weighted_vote']]
        recommend_Problem = pd.concat([recommend_Problem,similar_problem])
        #else:
        #  print("왜 null 이라하지")
        #print("======================================================================")
        #print(recommend_Problem)
        #print("======================================================================")
        #problem[problem['문제번호'] == int(target_problem)]


    counting_result = recommend_Problem.sort_values(by='문제번호',ascending=False)

    recommend_Problem.drop_duplicates()
    problem_liist = np.array(recommend_Problem['문제번호'].tolist())
    x = np.arange(len(problem_liist))
    index_list= np.random.choice(x,3)
    #index_list = randrange(0,len(problem_liist),3)
    list = []
    list.append(int(problem_liist[index_list[0]]))
    list.append(int(problem_liist[index_list[1]]))
    list.append(int(problem_liist[index_list[2]]))
    return  list

#Data = Content_recommend("ans")
#print("!!!!!!!!!!!!!!!!DATA!!!!!!!!!!!!")
#print(Data)

#print(Contend_recommend_False("ans"))

#print(Contend_recommend_True("ans"))
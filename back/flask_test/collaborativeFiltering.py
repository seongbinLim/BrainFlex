import random
import glob
import pandas as pd
import numpy as np
from S3Connection import s3, bucket
from sklearn.metrics.pairwise import cosine_similarity
from sklearn.metrics import pairwise_distances

# 정답 여부 변환 기능을 수행합니다.
def func(row):
        if(row['정답여부'] == True):
            return 1.0
        elif(row['정답여부'] == False):
            return 0.5
        else:
            return 0

# 유사도가 높은 상위 30명을 찾습니다. 
def find_n_neighbours(df,n):
    order = np.argsort(df.values, axis=1)[:, :n]
    df = df.apply(lambda x: pd.Series(x.sort_values(ascending=False)
            .iloc[:n].index, 
            index=['top{}'.format(i) for i in range(1, n+1)]), axis=1)
    return df

# 요청받은 유저id 에 해당하는 추천문제를 반환합니다. 
def recommend(userId):

    # for local test only ... 
    # users = pd.read_csv('./goal.csv', index_col=None, header=0)
    # target = pd.read_csv('./userdetail/'+userId+'.csv', index_col=None, header=0)
    
    # 1000-2000등 정보를 가져옵니다.
    file_name = "goal.csv"
    obj = s3.get_object(Bucket= bucket, Key= file_name) 
    goal = pd.read_csv(obj['Body']) # 'Body' is a key word

    # 추천 대상자의 정보를 가져옵니다.
    file_name = "userdetail/"+userId+".csv"
    obj = s3.get_object(Bucket= bucket, Key= file_name)
    target = pd.read_csv(obj['Body']) # 'Body' is a key word

    # 추천 대상자의 데이터프레임을 goal 데이터프레임 형식에 맞춥니다 
    target['userID'] = userId
    target['정답여부'] = target.apply(func, axis=1)

    # 추천 대상자와 유저의 정보를 합칩니다. 
    users = pd.concat([goal, target])

    # 중복 제거를 위한 데이터프레임 ... 이부분은 추후에 test와 수정이 필요합니다
    check = pd.pivot_table(users,values='정답여부',index='userID',columns='문제번호')

    # 유사도 분석에 사용될 우수 유저의 데이터프레임 (Nan -> 0)
    final = pd.pivot_table(users,values='정답여부',index='userID',columns='문제번호')
    final = final.fillna(0)

    # 유사도 분석에 사용할 특정 유저의 데이터프레임 
    test = final[final.index == userId]

    # 타겟과 목표유저간 유사도 분석 
    b = cosine_similarity(test, final)
    similarity = pd.DataFrame(b,index=test.index)
    similarity.columns=final.index

    # 유사도 상위 30 계산 
    sim_user_30 = find_n_neighbours(similarity,30)

    # 유저별 시도한 문제 (정답, 실패 상관 없이) 리스트 생성 
    users = users.astype({'문제번호':str})
    problem_user = users.groupby(by = 'userID')['문제번호'].apply(lambda x:','.join(x))

    # 문제 추천
    Problem_solved_by_user = check.columns[check[check.index==userId].notna().any()].tolist()
    a = sim_user_30[sim_user_30.index==userId].values
    b = a.squeeze().tolist()
    d = problem_user[problem_user.index.isin(b)]
    l = ','.join(d.values)
    Problem_solved_by_similar_user = l.split(',')
    Problem_under_consideration = list(set(Problem_solved_by_similar_user)-set(list(map(str, Problem_solved_by_user))))
    Problem_under_consideration = list(map(int, Problem_under_consideration))

    # 추천 대상 문제 중 3문제 선택 
    ret = []
    maxNum = len(Problem_under_consideration)-1
    for i in range(3):
        ran_num = random.randint(0, maxNum)
        while ran_num in ret:
            ran_num = random.randint(0, maxNum)
        ret.append(ran_num)

    return ret

# test for collaborativeFiltering.py
# def main():

#     userId = input("Enter the user id to whom you want to recommend : ")
#     problems = recommend(userId)
#     print(problems)


# if __name__ == "__main__":
#     main()

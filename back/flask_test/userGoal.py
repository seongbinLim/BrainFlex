import pandas as pd

# 1000-2000등의 정보를 담은 데이터프레임을 생성하고 csv로 저장합니다.
# 유저의 문제추천은 이 1000-2000등 유저 데이터와의 유사도 분석을 통해 진행됩니다.
def create_goal():

    # 유저 목록에서 파일을 불러옵니다 
    # 이부분은 나중에 1000-2000등의 데이터를 저장하는 폴더로 path를 변경해야합니다 
    filenames = glob.glob(FILE_PATH+"/*.csv")

    # 각 유저의 문제 풀이 정보를 읽어와서 userID 컬럼을 추가하고 리스트에 넣습니다
    userList = []
    for filename in filenames:

        user = pd.read_csv(filename, index_col=None, header=0)
        user['userID'] = filename[filename.rfind('/')+1:-4]
        userList.append(user)

    # 리스트에 담긴 데이터프레임을 하나로 concat합니다.
    users = pd.concat(userList, ignore_index=True)

    # 정답 여부 (TRUE, FALSE) 를 (1, 0.5)로 변환합니다
    users['정답여부'] = users.apply(func, axis=1)

    # 1000등-2000등의 데이터를 저장합니다.
    users.to_csv('./goal.csv', index = False)

# #라이브러리 읽어 들이기
# from bs4 import BeautifulSoup
# import  urllib.request as req 
# from urllib.error import HTTPError,URLError
# import csv
# import pandas as pd
# import numpy as np

# # -*- coding:utf-8 -*-
# user = pd.read_csv('user.csv')


# for i in range(1000,2000):
#     users = user[user['등수']==i]
#     users.column
#     userlist = np.array(users['아이디'].tolist())




# def userdetail(userid):
#     url = "https://www.acmicpc.net/user/"+userid


#     f = open(userid+'.csv','w', encoding='utf-8-sig', newline='')
#     f.write("정답여부,문제번호,문제이름\n")
#     wr = csv.writer(f)
#     problemlist = []
#     try:
#         headers = {'User-Agent':'Chrome/66.0.3359.181'}
#         rew = req.Request(url,headers=headers)
#         html = req.urlopen(rew)    
#     except HTTPError as e:
#         err = e.read()
#         code = e.getcode()
#     source = html.read()
#     html.close()
#     soup = BeautifulSoup(source,"html.parser")
#     div1 = soup.select('.col-md-9')
#     div2 = div1[0].select('.panel-body')
#     div3 = div2[0].select('span')
#     for i in range(0, len(div3),2):
#         list = []
#         list.append("true")
#         print(div3[i].text)
#         list.append(div3[i].text)
#         list.append(div3[i+1].text)
#         wr.writerow(list)

#     div3 = div2[1].select('span')
#     for i in range(0, len(div3),2):
#         list = []
#         list.append("false")
#         print(div3[i].text)
#         list.append(div3[i].text)
#         list.append(div3[i+1].text)
#         wr.writerow(list)


#     f.close()
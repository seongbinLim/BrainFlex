#라이브러리 읽어 들이기
from bs4 import BeautifulSoup
import  urllib.request as req 
from urllib.error import HTTPError,URLError
import csv

# -*- coding:utf-8 -*-

f = open('user.csv','w', encoding='utf-8-sig', newline='')
f.write("아이디,사이트\n")
wr = csv.writer(f)
userlist = []
for user_page in range (1, 2202):
    url = "https://www.acmicpc.net/ranklist/"+str(user_page)
        
    try:
        headers = {'User-Agent':'Chrome/66.0.3359.181'}
        rew = req.Request(url,headers=headers)
        html = req.urlopen(rew)
        
    except HTTPError as e:
        err = e.read()
        code = e.getcode()
    source = html.read()
    html.close()
    soup = BeautifulSoup(source,"html.parser")
    div1 = soup.select('.table>tbody>tr')
    for i in range(0, len(div1)):
        list = []
        div2 = div1[i].select('td')
        user_profile = div2[1].select('a')[0]['href']
        user_id = user_profile.replace("/user/","")
        list.append(user_id)
        list.append(user_profile)    
        wr.writerow(list)
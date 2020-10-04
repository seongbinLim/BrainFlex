#라이브러리 읽어 들이기
from bs4 import BeautifulSoup
import  urllib.request as req 
from urllib.error import HTTPError,URLError
import csv

# -*- coding:utf-8 -*-

userid = "koosaga"
url = "https://www.acmicpc.net/user/"+userid


f = open(userid+'.csv','w', encoding='utf-8-sig', newline='')
f.write("정답여부, 문제 번호, 문제이름\n")
wr = csv.writer(f)
problemlist = []
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
div1 = soup.select('.col-md-9')
div2 = div1[0].select('.panel-body')
div3 = div2[0].select('span')
for i in range(0, len(div3),2):
    list = []
    list.append("true")
    print(div3[i].text)
    list.append(div3[i].text)
    list.append(div3[i+1].text)
    wr.writerow(list)

div3 = div2[1].select('span')
for i in range(0, len(div3),2):
    list = []
    list.append("false")
    print(div3[i].text)
    list.append(div3[i].text)
    list.append(div3[i+1].text)
    wr.writerow(list)


f.close()
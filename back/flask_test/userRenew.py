# -*- coding: utf-8 -*-


#라이브러리 읽어 들이기
from bs4 import BeautifulSoup
import  urllib.request as req 
from urllib.error import HTTPError,URLError
import csv
import pandas as pd
import numpy as np
from io import StringIO
import os
import json
from S3Connection import s3, bucket
from io import StringIO  # python3 (or BytesIO for python2)

FILE_PATH = "./userdetail/"
userId = ""
def userRenew(userid):
    truelist = []
    falselist = []
    url = "https://www.acmicpc.net/user/"+userid
    userId = userid
    i = 0
    #f = open(FILE_PATH+userid+'.csv','w', encoding='utf-8-sig', newline='')
    
    #f.write("정답여부,문제번호,문제이름\n")
    
    userdetail = pd.DataFrame(columns=['정답여부','문제번호','문제이름'])
    #wr = csv.writer(f)
    problemlist = []
    html=''
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
        list.append(div3[i].text)
        truelist.append(div3[i].text)
        list.append(div3[i+1].text)
        userdetail.loc[i] = list
        i = i +1

    div3 = div2[1].select('span')
    for i in range(0, len(div3), 2):
        list = []
        list.append("false")
        list.append(div3[i].text)
        falselist.append(div3[i].text)
        list.append(div3[i+1].text)
        userdetail.loc[i] = list
        i = i + 1

    # for loacal test 
    # userdetail.to_csv("./"+userId+".csv")

    csv_buffer = StringIO()
    userdetail.to_csv(csv_buffer)
    s3.put_object(Body=csv_buffer.getvalue(), Bucket=bucket, Key='userdetail/'+userId+'.csv')


    return truelist,falselist

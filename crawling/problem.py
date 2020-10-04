
#라이브러리 읽어 들이기
from bs4 import BeautifulSoup
import  urllib.request as req 
from urllib.error import HTTPError,URLError
import csv


# -*- coding:utf-8 -*-

tag = []
tag_url = []
for tag_page in range(1,7):
    url = "https://solved.ac/problems/tags?page="+str(tag_page)
        
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
    body = soup.select('body')
    div1 = body[0].select('.StickyTable__Row-akg1ak-2')
    for i in range(1, len(div1)):
        div_tag = div1[i].select('a')[0]
        print(div_tag['href'])
        tag_url.append(div_tag['href'])
        div2 = div1[i].select('a')
        tag.append(div2[0].text)
        print(div2[0].text)


#f = open("problem.csv", "w")
f = open('problem.csv','w', encoding='utf-8-sig', newline='')
f.write("문제번호,링크,난이도,분류,해결,평균시도\n")
wr = csv.writer(f)

for tag_num in range(0,len(tag)):
    url = "https://solved.ac"+tag_url[tag_num]
    print(tag[tag_num])
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
    body = soup.select('body')
    page = body[0].select('.Paginationstyles__PageIndicator-gesjo3-0')
    last_page = page[len(page)-1].text
    for page_num in range(1, (int)(last_page)+1):
        url = "https://solved.ac"+tag_url[tag_num] + "?sort=level&direction=asc&page="+ str(page_num)
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
        body = soup.select('body')
        div1 = body[0].select('.StickyTable__Row-akg1ak-2')
        for i in range(1, len(div1)): 
            
            list = []
            div2 = div1[i]
            div3 = div2.select('a')
            #번호
            div6 = div3[0].select('span')[0]
            list.append(div6.text)
            
            #링크
            div4 = div3[0]['href']
            list.append(div4)
            
            #난이도
            div5 = div3[0].select('img')[0]['alt']
            list.append(div5)
            
            #문제이름
            div7 = div3[1].select('span')[0]
            list.append(div7.text)
           
            #분류
            list.append(tag[tag_num])

            div8 = div2.select('.TabularFigures__TabularFiguresStyle-ved8ab-0')
            num = div8[0].text.replace(",","")
            list.append((int)(num)) #해결
            list.append((float)(div8[1].text)) #평균시도
    
  
            wr.writerow(list)


f.close()
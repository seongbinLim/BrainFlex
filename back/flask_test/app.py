from flask import Flask, request, jsonify
from collaborativeFiltering import *
# from contentBasedFiltering import *
from userRenew import *
import random
import sys
#sys.setrecursionlimit(10**7)

app = Flask(__name__)


@app.route('/userLogin', methods = ['POST'])
def userLogin():
    user = request.get_json()  # json 데이터를 받아옴
    return jsonify(user)  # 받아온 데이터를 다시 전송

# 추천 방식을 랜덤으로 고른 뒤 userId에 대한 추천문제 3문제의 문제번호가 담긴 리스트를 생성합니다.
@app.route('/recommend/<userId>')
def environments(userId):
    # menu = random.randint(0, 2)
    menu = 0

    print("recommend userId : " + userId)

    if menu == 0: #유저
        problems = recommend(userId)
    elif menu==1: #컨텐츠 false
        problems = Content_recommend_False(userId)
    else : #컨텐츠 true
        problems = Content_recommend_True(userId)
        
    return jsonify({"problems":problems})

# userId에 해당하는 유저정보를 갱신하여 S3에 csv로 저장하고 spring에는 true, false로 나눠 문제번호를 반환합니다.
@app.route('/userdetail/<userId>')
def userdetail(userId):
    print("userdetail userId : " + userId)
    truelist,falselist = userRenew(userId)
    return jsonify({"true": truelist, "false": falselist})

# flask test 
@app.route('/',methods=['GET']) 
def index(): 
    return "docker push test!"

if __name__ == "__main__":
    app.run(host='0.0.0.0')

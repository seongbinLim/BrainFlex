<template>
  <v-app id="app">
    <v-container fluid center>
      <div align="center">
        <h2 class="font-weight-black pb-6">코딩테스트 연습</h2>
          <v-card
            color="grey lighten-2"
            max-width="750"
            class="pa-3"
          >
            <div class="d-flex flex-no-wrap justify-space-between">
              
              <div>
                <v-card-title
                  class="headline"
                  v-text="title" 
                ></v-card-title>
                
                <v-card
                    class="ma-5"
                    width="400"
                >
                    <a :href="item.link" target="_blank">
                    <v-img src="../assets/bannerBackjoon.png"></v-img>
                    </a>
                </v-card>
              </div>
              


            <div class="d-flex justify-end align-center pa-4">
              <div>
                <v-btn class="ma-2" height="110" width="110" color="blue-grey darken-4" dark @click="timerBtn()">
                  <v-icon v-show="!start" size="80" dark>mdi-alarm</v-icon>
                  <font v-show="start" size="5px" color="white">{{resTimeData}}</font>
                </v-btn>
              </div>
              <div>
                <v-btn 
                  class="ma-2" 
                  height="110"
                  width="110"
                  dark 
                  v-bind:color="!isButtonDisabled ? 'blue-grey darken-4' : 'green'"
                  @click="successBtn()">
                  <v-icon size="80" dark>mdi-check</v-icon>
                </v-btn>
              </div>
            </div>
            </div>
          </v-card>
      </div>
    </v-container>
  </v-app>
</template>


<script>
export default {
    name: 'Detail',
    data: () => ({
      isButtonDisabled: false,
      start: false, 
      timeCounter: 3600,
      resTimeData: "60:00",
      submitted: false,
      item: [],
      title: '',
    }),
    mounted() {
      console.log("detail created");
      this.item = this.$route.params;
      console.log(this.item);
      this.title = this.item.number + ". " + this.item.title;
    }, 
    methods: {
      startTimer() {
        this.polling = setInterval(() => {
          this.timeCounter--;
          this.resTimeData = this.prettyTime();
          if(this.timeCounter <=0) clearInterval(this.polling);
        }, 1000);
      },
      prettyTime() {
        let time = this.timeCounter/60;
        let minutes = parseInt(time);
        let seconds = Math.round((time-minutes) * 60);
        return this.pad(minutes, 2) + ":" + this.pad(seconds, 2);
      },
      pad(n, width) {
        n = n+'';
        return n.length>=width ? n : new Array(width - n.length + 1).join('0') + n;
      },
      countdown() {
        if(this.timer >= 1) {
          this.timer--;
        } else {
          clearInterval(this.timer);
          this.timer = 0;
        }
      },
      stopTimer() {
        clearInterval(this.polling);
      },
      successBtn() {
        if(this.isButtonDisabled) {
          alert("이미 완료버튼을 눌렀습니다");
        }
        this.isButtonDisabled = true;
        this.stopTimer();
      },
      timerBtn() {
        if(!this.start) {
          this.start = true;
          this.startTimer();
        }
        else {
          this.start = false;
          this.stopTimer();
        }
      },
    },
}
</script>
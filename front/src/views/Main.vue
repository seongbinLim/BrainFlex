<template>
  <v-app id="app">
    <v-container fluid :grid-list-md="!$vuetify.breakpoint.xs">
      <p class="font-weight-black">오늘 가장 많은 회원이 푼 문제</p>
      <v-layout wrap row>
        <v-flex xs12 sm4 class="pb-2" v-for="i in getList" v-bind:key="i.title">
          <Card v-on:click.native="goToDetail(i)" v-bind:parent="i" />
        </v-flex>
      </v-layout>

      <br><br><br><br><br><br><br><br><br><br><br>
    </v-container>
  </v-app>
</template>

<script>
import Card from '@/components/Card.vue';
//import axios from 'axios';
import http from '@/util/http-common';
export default {
  name: 'Main',
  components: {
    Card,
  },
  data: () => ({
    list: [1,2,3],
    getList: [],
  }),
  created() {
    console.log("created");
    let url = '/api/brainflex/recommend/getproblem';
    const uid = this.$store.state.uid;
    if(uid != '') {
      //const uuid = uid.split("@");
      url = '/api/brainflex/recommend/getproblems/0.o'; //+ "uuid[0]";
    }
    http .get(url)
    .then(result => {
      console.log(result.data.data);
      this.getList = result.data.data;
      this.$store.state.cardList = result.data.data;
      console.log(this.getList[0].level);
    })
    .catch(err => {
      console.log(err);
    });    
  },
  methods: {
    goToDetail(cardData) {
      console.log("cardData: " + cardData.title);
      //this.$router.replace('/detail');
      this.$router.push({name: 'Detail', params: {
        title: cardData.title,
        number: cardData.number,
        avg_attempt: cardData.avg_attempt,
        level: cardData.level,
        link: cardData.link,
        solve: cardData.solve
        }
      });
    }
  },
};
</script>

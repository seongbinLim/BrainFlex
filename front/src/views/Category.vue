<template>
    <v-container fluid >
      <v-layout wrap row>
        
        <v-flex xs2>
          <Ffilter @updateFilter="updateFilter"></Ffilter>
        </v-flex>

        <v-flex class="pb-4">

          <div class="table-container">
            <div class="table-col" v-for="column in columns" v-bind:key="column.index">
              <div class="item-container" v-for="item in Object.keys(column)" v-bind:key="item">
                <Card v-on:click.native="goToDetail(column[item])" v-bind:parent="column[item]"/>
              </div>
            </div>
          </div>

          <div class="text-center">
            <v-container>
              <v-row justify="center">
                <v-col cols="8">
                  <v-container class="max-width">
                    <v-pagination
                      v-model="page"
                      class="my-4"
                      :length="pageTotal"
                      :total-visible="10"
                    ></v-pagination>
                  </v-container>
                </v-col>
              </v-row>
            </v-container>
          </div>

        </v-flex>
      </v-layout>
    </v-container>
</template>

<script>
import Card from '@/components/Card.vue';
import Ffilter from '@/components/Filter.vue';
//import axios from 'axios';
import http from '@/util/http-common';
export default {
  name: 'Category',
  components: {
    Card,
    Ffilter,
  },
  data: () => ({
    list: [1,2,3],
    getList: [],
    page: 1,
    pageTotal: 5,
    category: '수학',
    items: { 
          item1: { name: 'Item 1' }, 
          item2: { name: 'Item 2' }, 
          item3: { name: 'Item 3' },
          item4: { name: 'Item 4' },
          item5: { name: 'Item 5' },
          item6: { name: 'Item 6' },
          item7: { name: 'Item 7' },
          item8: { name: 'Item 8' },
          item9: { name: 'Item 9' },
          item10: { name: 'Item 10' },
        },
        cols: 2
  }),
  computed: {
    columns: function() {
      let columns = [];
      let mid = Math.ceil(Object.keys(this.items).length / this.cols);

      for (let col = 0; col < this.cols; col++) {
        columns.push(Object.entries(this.getList).slice(col * mid, col * mid + mid).map(entry => entry[1]));
      }
      return columns;
     }
  },


  watch: {
    page() {
      console.log("page change");
      const url = '/api/brainflex/problem/problemList/' + this.page + '/' + this.category
      http.get(url, {
      
      })
      .then(result => {
        console.log(result.data.data);
        this.getList = result.data.data;
        this.$store.state.cardList = result.data.data;
        this.pageTotal = result.data.lastpage;
        console.log(this.getList[0].level);
      })
      .catch(err => {
        console.log(err);
      });
    }
  },
  created() {
    console.log(`${this.API_URL}`);
    console.log("created");
    const url = '/api/brainflex/problem/problemList/' + this.page + '/' + this.category
    console.log(url);
    http.get(url, {
      
    })
    .then(result => {
      console.log(result.data.data);
      console.log(result.data.lastpage)
      this.getList = result.data.data;
      this.$store.state.cardList = result.data.data;
      this.pageTotal = result.data.lastpage;
      console.log(this.getList[0].level);
    })
    .catch(err => {
      console.log(err);
    });    
  },
  methods: {
    goToDetail(cardData) {
      console.log("cardData: " + cardData.title);
      this.$router.push({name: 'Detail', params: {
        title: cardData.title,
        number: cardData.number,
        avg_attempt: cardData.avg_attempt,
        level: cardData.level,
        link: cardData.link,
        solve: cardData.solve
        }
      });
    },
    changeCategory(message) {
      let dataTrim = '';
      if(message == 'DP') dataTrim = '다이나믹 프로그래밍';
      else if(message == 'BFS') dataTrim = '너비 우선 탐색'
      else if(message == 'DFS') dataTrim = '깊이 우선 탐색'
      else if(message == '완전탐색') dataTrim = '브루트포스 알고리즘'
      else if(message == '트리') dataTrim = '트리'
      else if(message == '백트래킹') dataTrim = '백트래킹'
      this.category = dataTrim;
    },
    updateFilter: function(message) { //자식 데이터 불러오기
      this.changeCategory(message);
      const url = '/api/brainflex/problem/problemList/' + 1 + '/' + this.category
      http.get(url)
      .then(result => {
        console.log(result.data.data);
        this.getList = result.data.data;
        this.$store.state.cardList = result.data.data;
        this.pageTotal = result.data.lastpage;
        console.log(this.getList[0].level);
      })
      .catch(err => {
        console.log(err);
      });
    }
  },
};
</script>

<style>
.table-container {
  display: flex;
  border: 1px;
}
.table-col {
  margin: 10px;
  border: 1px;
  flex-grow: 1;
  display: flex;
  flex-direction: column;
}
.item-container {
  border: 1px;
  padding: 5px;
  margin: 5px;
}
</style>
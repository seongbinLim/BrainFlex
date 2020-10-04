<!-- 리포트 컴포넌트 입니다 -->
<template>
  <v-main>
    <h2 class="font-weight-black text-center">
      {{ this.$store.state.uid }}님 맞춤 레포트
    </h2>
    <v-container>
      <!-- 맞은 문제 틀린문제 -->
      <v-expansion-panels v-model="panel" :readonly="readonly" multiple>
        <v-row justify="center">
          <v-col cols="5">
            <v-expansion-panel>
              <v-expansion-panel-header
                ><b
                  ><v-icon color="success">mdi-alert-circle</v-icon> 맞은
                  문제</b
                ></v-expansion-panel-header
              >
              <v-expansion-panel-content>
                <span
                  ><a
                    href="https://www.acmicpc.net/problem/1080"
                    class="green--text"
                    >1080
                  </a>
                </span>
                <span
                  ><a
                    href="https://www.acmicpc.net/problem/14302"
                    class="green--text"
                    >14302
                  </a>
                </span>
              </v-expansion-panel-content>
            </v-expansion-panel>
          </v-col>
          <v-col cols="5">
            <v-expansion-panel>
              <v-expansion-panel-header
                ><b
                  ><v-icon color="error">mdi-alert-circle</v-icon> 틀린 문제</b
                ></v-expansion-panel-header
              >
              <v-expansion-panel-content class="p-5">
                <span
                  ><a
                    href="https://www.acmicpc.net/problem/2020"
                    class="red--text"
                    >2020
                  </a>
                </span>
              </v-expansion-panel-content>
            </v-expansion-panel>
          </v-col>
        </v-row>
      </v-expansion-panels>
      <!-- 많이 푼 알고리즘 리포트 -->
      <v-row justify="center">
        <v-col cols="10">
          <v-card class="pa-4 mb-5">
            <div>
              <apexchart
                type="pie"
                height="350"
                :options="pieChartOptions"
                :series="pieSeries"
              ></apexchart>
            </div>
          </v-card>
        </v-col>
      </v-row>

      <!-- 취약점 분석 리포트 -->
      <v-row justify="center">
        <v-col cols="10">
          <v-card class="pa-4">
            <div>
              <apexchart
                type="radar"
                height="350"
                :options="chartOptions"
                :series="series"
              ></apexchart>
            </div>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </v-main>
</template>
<script>
export default {
  data: function() {
    return {
      panel: [0, 1],
      readonly: false,
      pieSeries: [25, 15, 44, 55, 41, 17],
      pieChartOptions: {
        chart: {
          width: '100%',
          type: 'pie',
        },
        labels: ['DP', 'BFS', 'DFS', '수학', '문자열', '시뮬레이션'],

        plotOptions: {
          pie: {
            dataLabels: {
              offset: -5,
            },
          },
        },
        title: {
          text: '어떤 알고리즘을 많이 풀었을까?',
        },
        dataLabels: {
          formatter(val, opts) {
            const name = opts.w.globals.labels[opts.seriesIndex];
            return [name, val.toFixed(1) + '%'];
          },
        },
        legend: {
          show: false,
        },
      },

      series: [
        {
          name: 'Series 1',
          data: [80, 50, 30, 40, 100, 20],
        },
        {
          name: 'Series 2',
          data: [20, 30, 40, 80, 20, 80],
        },
        {
          name: 'Series 3',
          data: [44, 76, 78, 13, 43, 10],
        },
      ],
      chartOptions: {
        chart: {
          height: 350,
          type: 'radar',
          dropShadow: {
            enabled: true,
            blur: 1,
            left: 1,
            top: 1,
          },
        },
        title: {
          text: '취약한 알고리즘 분석',
        },
        stroke: {
          width: 2,
        },
        fill: {
          opacity: 0.1,
        },
        markers: {
          size: 0,
        },
        xaxis: {
          categories: ['DP', 'BFS', 'DFS', '문자열', '시뮬레이션', '그래프'],
        },
      },
    };
  },
};
</script>
<style scoped>
a {
  text-decoration: none;
}
</style>

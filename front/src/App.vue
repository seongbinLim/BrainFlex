<template>
  <v-app>
    <v-app-bar flat app>
      <Header @showLogin="showLogin" @showJoin="showJoin"></Header>
    </v-app-bar>

    <!-- 이미지 들어갈 배너 자리 -->
    <v-img src="@/assets/banner.png" height="400"></v-img>

    <!-- 탭바 자리 -->
    <div>
      <v-tabs
        background-color="blue lighten-4"
        class="elevation-2"
        icons-and-text
      >
        <v-tab to="/">추천 프로그래밍 문제</v-tab>
        <v-tab to="/category">알고리즘 별 문제</v-tab>
        <v-tab to="/about">리포트</v-tab>
        <v-tab to="/">랭킹</v-tab>
      </v-tabs>
    </div>

    <v-overlay :value="this.$store.state.overlay">
      <v-btn @click="showLogin" color="blue darken-1">X</v-btn>
      <LoginForm></LoginForm>
    </v-overlay>

    <v-overlay :value="this.$store.state.JoinOverlay">
      <v-btn @click="showJoin" color="blue darken-1">X</v-btn>
      <JoinForm></JoinForm>
    </v-overlay>

    <v-main class="spacing-playground pa-16 grey lighten-5">
      <v-container fluid>
        <router-view class="grey lighten-5"></router-view>
      </v-container>
    </v-main>

    <Footer />
  </v-app>
</template>

<script>
import Header from '@/components/Header';
import Footer from '@/components/Footer';
import LoginForm from '@/components/LoginForm.vue';
import JoinForm from '@/components/JoinForm.vue';

export default {
  name: 'App',

  components: {
    Header,
    Footer,
    LoginForm,
    JoinForm,
  },

  data() {
    return {
      imgs: [
        {
          src: '@/assets/banner.png',
        },
      ],
    };
  },

  methods: {
    showLogin() {
      this.$store.commit('showLogin', this.$store.state.overlay);
    },
    showJoin() {
      this.$store.commit('showJoin', this.$store.state.JoinOverlay);
    },
  },
};
</script>

<!-- 로그인 폼 -->
<template>
  <v-card class="mx-auto" light width="500" elevation="10">
    <v-container>
      <v-row justify="center">
        <v-col cols="10">
          <v-card-text>
            <h1 class="mb-5 mt-5 text-center" style="color: #1E88E5">로그인</h1>
            <!--- 인풋 시작 -->
            <v-text-field
              outlined
              label="이메일"
              prepend-inner-icon="mdi-email"
              :rules="emailRules"
              v-model="user.email"
            ></v-text-field>
            <v-text-field
              outlined
              label="비밀번호"
              prepend-inner-icon="mdi-map-marker"
              :rules="passwordRules"
              type="password"
              v-model="user.password"
            ></v-text-field>
            <!-- 확인 누르면 메인페이지로 가는거 추가 구현 필요 -->
            <v-btn block dark color="blue darken-1" @click="login()"
              >확인</v-btn
            >
          </v-card-text>
        </v-col>
      </v-row>
      <div class="mb-5" style="font-size: 14px;">
        <v-row justify="center" class="text-center">
          <v-col cols="6">
            <p style="color: #1E88E5" @click="showJoin">
              <u>BrainFlex가 처음이신가요?</u>
            </p>
          </v-col>
        </v-row>
        <v-row justify="center" class="text-center">
          <v-col cols="6">
            <p style="color: #1E88E5" @click="showJoin">
              <u>비밀번호를 잊으셨나요?</u>
            </p>
          </v-col>
        </v-row>
      </div>
    </v-container>
  </v-card>
</template>

<script>
import { mapState } from 'vuex';

export default {
  data() {
    return {
      user: {
        email: '',
        password: '',
      },
    };
  },
  computed: {
    ...mapState(['emailRules', 'passwordRules']),
  },
  methods: {
    login() {
      const url = '/api/brainflex/user/login';
      this.$axios
        .post(url, {
          email: this.user.email,
          pw: this.user.password,
        })
        .then(res => {
          console.log(res);
          // 메인으로 가야함
          alert('로그인 성공!!!');
          this.$store.state.overlay = !this.$store.state.overlay;
          this.$store.state.uid = this.user.email;
          this.$store.state.uemail = this.user.email;
        })
        .catch(err => {
          console.log(err);
          alert('로그인 실패!!!!');
        });
    },
    showLogin() {
      this.$store.commit('showLogin', this.$store.state.overlay);
    },
    showJoin() {
      this.showLogin();
      this.$store.commit('showJoin', this.$store.state.overlay);
    },
  },
};
</script>

<style></style>

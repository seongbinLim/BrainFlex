<!-- 회원가입 폼 -->
<template>
  <v-card class="mx-auto" light width="500" elevation="10">
    <v-container>
      <v-row justify="center">
        <v-col cols="10">
          <v-card-text class="mb-5">
            <h1 class="mb-5 mt-5 text-center" style="color: #1E88E5">
              회원가입
            </h1>
            <!--- 인풋 시작 -->
            <!-- 이메일 인증 버튼 만들어야 함 -->
            <div class="mt-5">
              <v-text-field
                outlined
                label="아이디"
                name="id"
                prepend-inner-icon="mdi-face"
                :rules="[rules.required]"
                v-model="user.id"
                required
              >
              </v-text-field>
              <v-text-field
                outlined
                name="email"
                label="이메일"
                prepend-inner-icon="mdi-email"
                :rules="[rules.emailRules, rules.required]"
                :state="emailFlag"
                required
                v-model="user.email"
              >
                <template v-slot:append v-if="tmpFlag">
                  <v-chip color="primary" @click="sendEmail"> 인증</v-chip>
                </template>
                <template v-slot:append v-else>
                  <v-chip color="success"> 인증완료</v-chip>
                </template>
              </v-text-field>
              <!-- 이메일 인증번호창 -->
              <v-text-field
                outlined
                label="인증번호 입력"
                prepend-inner-icon="mdi-email"
                v-model="validateNum"
                :rules="[rules.required, rules.codeRules]"
                v-show="emailFlag"
                v-if="!checkEmail()"
                required
              >
              </v-text-field>
              <v-text-field
                outlined
                name="password"
                label="비밀번호"
                prepend-inner-icon="mdi-lock"
                :rules="[rules.required]"
                type="password"
                v-model="user.password"
                required
              ></v-text-field>
              <v-text-field
                outlined
                required
                name="password2"
                label="비밀번호 확인"
                prepend-inner-icon="mdi-lock"
                :rules="[rules.required, rules.passwordRules]"
                type="password"
                v-model="user.password2"
              ></v-text-field>
              <v-btn block dark color="blue darken-1" @click="submitForm()"
                >확인</v-btn
              >
            </div>
          </v-card-text>
        </v-col>
      </v-row>
    </v-container>
  </v-card>
</template>

<script>
export default {
  data() {
    return {
      user: {
        id: this.id,
        email: this.email,
        password: this.password,
        password2: this.password2,
        role: 'USER',
      },
      validateNum: '',
      emailCode: '',
      emailFlag: false,
      tmpFlag: true,
      rules: {
        required: value => !!value || '정보를 입력해주세요!',
        emailRules: v => /.+@.+\..+/.test(v) || '이메일 형식으로 작성해주세요.',
        passwordRules: v => v === this.user.password || '비밀번호가 다릅니다.',
        codeRules: v => v === this.emailCode || '인증코드를 다시 확인해주세요.',
      },
    };
  },
  computed: {},
  methods: {
    submitForm() {
      const url = '/api/brainflex/user/insert';
      this.$axios
        .post(url, {
          email: this.user.email,
          id: this.user.id,
          pw: this.user.password,
        })
        .then(res => {
          console.log(res);
          this.showLogin();
          this.$store.state.uid = this.user.id;
          this.$store.state.uemail = this.user.email;
          alert('회원가입 성공!!!');
        })
        .catch(err => {
          console.log(err);
        });
    },
    showLogin() {
      this.showJoin();
      this.$store.commit('showLogin', this.$store.state.overlay);
    },
    showJoin() {
      this.$store.commit('showJoin', this.$store.state.overlay);
    },
    sendEmail() {
      this.emailFlag = !this.emailFlag;
      console.log(this.emailFlag);

      const url = '/api/brainflex/mail/send/';
      this.$axios
        .get(url + this.user.email)
        .then(res => {
          console.log(res);
          this.emailCode = res.data.code;
          console.log(this.emailCode);
          return;
        })
        .catch(() => {
          alert('메일전송 실패!!!!!');
          return;
        });
    },
    checkEmail() {
      if (this.validateNum !== '' && this.emailCode !== '') {
        if (this.validateNum === this.emailCode) {
          this.tmpFlag = false;
          return true;
        }
      }
      return false;
    },
  },
};
</script>

<style>
.v-messages__message {
  line-height: 20px;
}
</style>

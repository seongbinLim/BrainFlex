<template>
  <v-app-bar color="grey lighten-4" dense flat fade-img-on-scroll>
    <v-toolbar-title>Brain Flex</v-toolbar-title>
    <v-spacer></v-spacer>
    <div v-if="$store.state.uid==''">
      <v-btn text @click="Login">
        로그인
      </v-btn>

      <v-btn text @click="Join">
        회원가입
      </v-btn>
    </div>
    <div v-else>
      <v-btn text> {{ this.$store.state.uid }} </v-btn>
      <v-menu bottom left>
        <template v-slot:activator="{ on, attrs }">
          <v-btn icon color="yellow" v-bind="attrs" v-on="on">
            <v-avatar>
              <v-icon dark>mdi-account-circle</v-icon>
              <!--<img src="https://cdn.vuetifyjs.com/images/john.jpg" alt="John">-->
            </v-avatar>
          </v-btn>
        </template>

        <v-list>
          <v-list-item v-for="(item, i) in items" :key="i">
            <v-list-item-title>{{ item.title }}</v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>
    </div>
  </v-app-bar>
</template>

<script>
export default {
  name: 'Header',
  components: {},
  data: () => ({
    items: [{ title: '로그아웃' }, { title: '마이페이지' }],
  }),
  methods: {
    Login() {
      this.$emit('showLogin');
    },
    Join() {
      this.$emit('showJoin');
    },
  },
  watch: {
    uid() {
      console.log("uid change");
    }
  },
};
</script>

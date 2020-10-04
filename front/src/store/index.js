import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        uemail: '',
        uid: '',
        overlay: false,
        JoinOverlay: false,
        user: 'user',
    },
    mutations: {
        showLogin: function(state, payload) {
            state.overlay = !state.overlay;
            payload;
        },
        showJoin: function(state, payload) {
            state.JoinOverlay = !state.JoinOverlay;
            payload;
        },
    },
    actions: {},
    modules: {},
});
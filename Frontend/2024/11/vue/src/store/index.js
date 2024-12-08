import Vue from 'vue'

import Vuex from 'vuex'
Vue.use(Vuex);

const actions = {
    
}

const mutations = {
    FUZHI01(state,value) {
        state.lengths=value
    },
    FUZHI02(state,value) {
        state.dataItem=value
    },
    FUZHI03(state,value) {
        state.errSum=value
    }
}

const state = {
    lengths: 0,
    dataItem: [],
    errSum: 0
}
export default new Vuex.Store({
    actions,
    mutations,
    state,
})
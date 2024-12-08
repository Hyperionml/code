import VueRouter from 'vue-router'
import Alldata from '../page/Alldata'
import Historydata from '../page/Historydata'
import Listdata from '../page/Listdata'
import DataLook from '../page/DataLook'

export default new VueRouter({
    routes: [
        {
            path: '/',
            component:DataLook
        },
        {
            path: '/historydata',
            component: Historydata,
        },
        {
            path: '/listdata',
            component: Listdata
        },
        {
            path: '/alldata',
            component: Alldata,
        },
    ]
})
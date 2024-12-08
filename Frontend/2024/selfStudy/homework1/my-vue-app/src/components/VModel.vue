<template>
    请输入姓名：<input type="text" v-model="username">
    <div>姓名是：{{ username }}</div>
</template>
<script setup>
import{ ref } from 'vue'
const username = ref('zhangsan')
</script>
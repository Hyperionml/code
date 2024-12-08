<template>
    <p><input type="text" v-bind:placeholder="username"></p>
    <p><input type="password" :placeholder="password"></p>
</template>

<script setup>
const username = '请输入用户名'
const password = '请输入密码'
</script>
<template>
  <div class="nav">
    <div class="header">
      <span>轴承误差检测系统</span>
    </div>
    <All />
  </div>
</template>

<script>
import All from "./component/All.vue";
import instance from "@/js/axios";
export default {
  name: "App",
  components: { All },
  created() {
    this.lunxun();
  },
  methods: {
    qingqiu() {
      instance
        .get("/gdupt/bearing/list")
        .then((response) => {
          this.$store.state.dataItem = response.data.reMsg;
        })
        .catch((error) => {
          console.log("有错", error);
        });
    },
    lunxun() {
      setInterval(() => {
        this.qingqiu();
      }, 1000);
    },
  },
};
</script>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

.nav {
  background-image: url("./images/bg.jpg");
  width: 100%;
  height: 591px;
  background-color: rgb(69, 71, 71);
  background-size: 100%;
}

.nav .header {
  width: 100%;
  text-align: center;
  line-height: 40px;
  border: 1px solid rgba(53, 190, 214, 0.2);
  background-image: url("./images/head_bg.png");
  background-size: 100% 115%;
}
.header span {
  display: inline-block;
  padding-right: 100px;
  color: azure;
}
</style>
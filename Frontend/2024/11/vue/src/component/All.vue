<template>
  <div class="zong">
    <div class="left-body">
      <div class="canlook">
        <router-link
          replace
          to="/"
          class="list-item"
          active-class="active"
          exact
        >数据可视化</router-link>
      </div>
      <div class="xinxi" @click="change01">
        <span>轴承信息</span>
        <i class="fas fa-angle-down"></i>
      </div>
      <div v-show="isShow">
        <div class="second">
          <router-link
            replace
            to="/historydata"
            class="list-item"
            active-class="active"
            >历史数据</router-link
          >
        </div>
        <div class="thrid">
          <router-link
            replace
            to="/listdata"
            class="list-item"
            active-class="active"
            >分页数据</router-link
          >
        </div>
        <div class="first">
          <router-link
            replace
            to="/alldata"
            class="list-item"
            active-class="active"
            >全表内容</router-link
          >
        </div>
      </div>
    </div>
    <div v-show="true" class="right-body">
      <transition name="slide-in">
        <router-view class="router-view"></router-view>
      </transition>
    </div>
  </div>
</template>

<script>
export default {
  name: "All",
  data() {
    return {
      dataItem: "",
      isShow: false,
      activeRouter: null,
      isShow02: false,
    };
  },
  methods: {
    handClick() {
      this.change();
      if (this.isShow) {
        this.qingqiu();
      }
    },
    change01() {
      this.isShow = !this.isShow;
    },
    // change02() {
    //   this.isShow02 = !this.isShow02;
    // },
  },
};
</script>

<style>
.zong {
  display: flex;
}
.zong .left-body {
  width: 10%;
  height: 550px;
  border: 1px solid rgba(53, 190, 214, 0.2);
  border-top: none;
}
.zong .right-body {
  width: 90%;
  height: 550px;
  border: 1px solid rgba(53, 190, 214, 0.2);
  border-top: none;
  border-right: none;
  position: relative;
  overflow: auto;
}
.router-view {
  position: absolute;
  width: 100%;
  /* transition: transform 0.5s; */
}
@keyframes slideIn {
  from {
    transform: translateX(20%);
  }
  to {
    transform: translateX(0px);
  }
}
.slide-in-enter-active {
  animation: slideIn linear 0.5s;
}
.xinxi,
.canlook {
  text-align: center;
  line-height: 30px;
  width: 100%;
  border: 1px solid rgb(51, 49, 49);
  color: azure;
}
.xinxi .list-item {
  color: black;
  text-decoration: none;
}
.xinxi i,
.canlook i {
  margin-left: 10px;
  font-size: 20px;
  color: rgb(51, 49, 49);
}

.first,
.second,
.thrid,
.canlook {
  text-align: center;
  border: 1px solid rgb(51, 49, 49);
}
.first .list-item,
.second .list-item,
.thrid .list-item,
.canlook .list-item {
  color: azure;
  text-decoration: none;
  font-size: 14px;
}

/* .list-item:hover {
  color: rgb(53, 190, 214);
} */
.list-item.active {
  color: rgb(53, 190, 214);
}
</style>
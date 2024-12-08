<template>
  <div class="sb">
    <div class="secondBox">
      <table v-show="istrue" class="output">
        <thead>
          <tr>
            <th class="tf">id</th>
            <th class="ts">名称</th>
            <th class="tt">当前状态</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(item, sid) in dataItem" :key="sid">
            <td class="tf">{{ item.sid }}</td>
            <td class="ts">{{ item.name }}</td>
            <td class="tt">{{ item.status }}</td>
          </tr>
        </tbody>
      </table>
      <!-- </div>
      </div> -->
      <div class="sc-buttom-box">
        <div class="sc-buttom-box-s">
          <span>共 {{ this.Allpages }} 页</span>
        </div>
        <div class="sc-buttom-box-f">
          <i class="fas fa-angle-left" @click="jian"></i>
          <span>第 {{ this.page }} 页</span>
          <i class="fas fa-angle-right" @click="jia"></i>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import instance from "@/js/axios";
export default {
  name: "Listdata",
  data() {
    return {
      dataItem: "",
      AllDataItem: this.$store.state.dataItem,
      Allpages: 1,
      page: 1,
      pageSize: 13,
      istrue: false,
    };
  },
  created() {
    this.startLunXun()
    // this.AllPage();
    this.qingqiu();
  },
  methods: {
    qingqiu() {
      instance
        .get(
          `/gdupt/bearing/page?page=${this.page}&pageSize=${this.pageSize}`
        )
        .then(
          (response) => {
            this.dataItem = response.data.reMsg;
            this.istrue = true;
          },
          (error) => {
            console.log("请求失败了", error.message);
          }
        );
    },
    jian() {
      if (this.pages != 1) {
        this.pages--;
        this.qingqiu();
      }
    },
    jia() {
      if (this.page < this.Allpages) {
        this.page++;
        this.qingqiu();
      }
    },
    AllPage() {
      let dataLength = this.AllDataItem.length;
      if (dataLength % this.pageSize != 0&&dataLength>this.pageSize) {
        this.Allpages++;
        dataLength = dataLength / this.pageSize;
      }
    },
    startLunXun(){
      setInterval(() => {
        this.AllPage()
      }, 1000);
    },
  },
};
</script>

<style>
.sb {
  position: relative;
  height: 100%;
}
.sb .secondBox {
  color: azure;
}
.sb .sc-buttom-box {
  position: absolute;
  width: 100%;
  height: 30px;
  border: 1px solid rgba(53, 190, 214, 0.2);
  bottom: 0;
  font-size: 10px;
}
.sb .sc-buttom-box span {
  vertical-align: middle;
}
.sc-buttom-box-f,
.sc-buttom-box-s {
  float: right;
  font-size: 15px;
}
.sc-buttom-box-s {
  margin: 2px 10px auto 10px;
}
.sc-buttom-box-f {
  display: flex;
}
.sc-buttom-box-f span {
  display: block;
  margin: 4px 0 0 0;
}
.sc-buttom-box-f i {
  margin: 5px 5px auto 5px;
  font-size: 20px;
}
/* .secondBox{
  overflow: hidden;
} */
</style>
<template>
  <div>
    <div class="thirdBox">
      <div v-show="istrue">
        <div>轴承总数:{{ dataItem.length }}</div>
        <div>出错数量:{{ errSum }}</div>
        <div>数据:</div>
      </div>
      <table v-show="istrue">
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
    </div>
  </div>
</template>

<script>
import instance from "@/js/axios";
import { mapMutations, mapState } from "vuex";

export default {
  name: "Alldata",
  data() {
    return {
      dataItem: this.$store.state.dataItem,
      errSum: this.$store.state.errSum,
      le: this.$store.state.lengths,
      istrue: false,
      lunXunData:null
    };
  },
  created() {
    // this.startTimer()
    this.startLunXun()
  },
  computed: {
    ...mapState(["lengths"]),
  },
  methods: {
    ...mapMutations(["FUZHI01", "FUZHI02", "FUZHI03"]),
    // qingqiu() {
      // instance
      //   .get("/api/gdupt/bearing/list")
      //   .then((response) => {
      //     console.log("成功", response.data.reMsg);
      //     this.dataItem1 = response.data.reMsg;
      //     console.log(this.dataItem1);
          
          // this.startTimer();
        // })
        // .catch((error) => {
        //   console.log("有错", error);
        // });
    // },
    startTimer() {
      // console.log('调用了')
      this.istrue = true;
      for (this.le; this.le < this.dataItem.length; this.le++) {
        this.turnData01(this.dataItem[this.le].sid);
        if (this.dataItem[this.le].status == "err") {
          this.turnData02(
            this.dataItem[this.le].sid,
            this.dataItem[this.le].status
          );
          this.judge()
        }
      }
      this.FUZHI01(this.le);
      this.FUZHI02(this.dataItem);
    },
    judge() {
      this.errSum++;
      this.FUZHI03(this.errSum);
    },
    turnData01(sid) {
      instance
        .put("/gdupt/history/add", sid)
        .then((response) => {
          console.log("成功");
        })
        .catch((error) => {
          console.log("有错", error);
        });
    },
    turnData02(sid, status) {
      instance
        .post("/gdupt/history/updateBy1", {
          sid: sid,
          errMsg: status,
        })
        .then((response) => {
          console.log("成功");
        })
        .catch((error) => {
          console.log("有错", error);
        });
    },
    startLunXun(){
      setInterval(() => {
        this.startTimer()
      }, 1000);
    },
    beforeDestroy() {
      this.istrue = false;
    },
  },
};
</script>

<style>
.thirdBox {
  color: rgb(255, 255, 255);
}
table {
  text-align: center;
  border-collapse: collapse;
  border: 1px solid rgba(53, 190, 214, 0.2);
  /* overflow: auto; */
}
thead {
  font-size: 14px;
}
th,
td {
  border: 1px solid rgba(53, 190, 214, 0.2);
}
.tf,
.ts,
.tt {
  width: 80px;
}
</style>
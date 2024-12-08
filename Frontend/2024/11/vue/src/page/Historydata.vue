<template>
  <div>
    <div class="firstBox">
      <div class="upContext">
        <div><input class="block" type="text" v-model="sidData" placeholder="请输入序号"/></div>
        <div><input type="date" class="block" v-model="value"></div>
        <div><button @click="qingqiu" class="block third">查询</button></div>
        <div>
          <button @click="reset" class="block third">
            <i class="fas fa-redo-alt"></i>
          </button>
        </div>
      </div>
      <div v-show="istrue">
        <br/>  
        <div v-if="dataItem != null">
          <table>
            <thead>
              <tr>
                <th class="tf">id</th>
                <th class="ts">错误数量</th>
                <th class="tt">时间</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td class="tf">{{ dataItem.sid }}</td>
                <td class="ts">{{ dataItem.errCount }}</td>
                <td class="tt">{{ dataItem.datetime }}</td>
              </tr>
            </tbody>
          </table>
          <div v-if="errMsgData != null" class="footData">
            <div class="footData-left">errMsg</div>
            <div class="footData-right">{{ qie }}</div>
          </div>
        </div>
        <div v-else>数据不存在</div>
      </div>
    </div>
  </div>
</template>

<script>
import instance from "@/js/axios";
export default {
  name: "Historydata",
  data() {
    return {
      dataItem: [],
      errMsgData: "",
      // newErrMsgData: [],
      sidData: 1,
      // timeData: "",
      value: "2024-11-01",
      istrue:false,
    }
  },
  created() {
    this.qingqiu();
  },
  methods: {
    formatDate(date) {
      var d = new Date(date),
          month = '' + (d.getMonth() + 1),
          day = '' + d.getDate(),
          year = d.getFullYear();

      if (month.length < 2) 
          month = '0' + month;
      if (day.length < 2) 
          day = '0' + day;

      return [year, month, day].join('-');
    },
    qingqiu() {
      const timeData= this.formatDate(this.value)
      instance
        .get(`/api/gdupt/history/get?sid=${this.sidData}&date=${timeData}`)
        .then(
          (response) => {
            console.log("成功", response);
            this.dataItem = response.data.reMsg;
            if(this.dataItem!=null){
              this.errMsgData = response.data.reMsg.errMsg;
            }
            this.istrue=true
          },
          (error) => {
            console.log("请求失败了", error.message);
          }
        );
    },
    reset(){
      this.sidData=1,
      this.value="2024-11-01",
      this.qingqiu()
    }
  },
  computed: {
    qie() {
      return this.errMsgData.substring(6);
    },
  },
};
</script>

<style>
.firstBox {
  color: azure;
}

.firstBox .buChange {
  padding: 0 5px 0 5px;
  font-size: 11px;
}
.upContext{
  display: flex;
}
.upContext .block{
  height: 30px;
  width: 200px;
  font-size: 15px;
}
.upContext .third{
  width: 50px;
}
table {
  width: 100%;
  text-align: center;
  border-collapse: collapse;
  border: 1px solid rgba(53, 190, 214, 0.2);
  /* border-left: none; */
}
thead {
  font-size: 20px;
}
th,
td {
  border: 1px solid rgba(53, 190, 214, 0.2);
  /* border-left: none; */
}
.tf,
.ts,
.tt {
  width: 100px;
  font-size: 20px;
  padding: 5px 0;
}
.footData{
  height: 100%;
  border: 1px solid rgba(53, 190, 214, 0.2);
  border-top: none;
  /* border-left: none; */
  font-size: 20px;
  /* margin: 0 0 0 5px; */
}
.footData-left{
  text-align: center;
  widows: 100%;
  border: 1px solid rgba(53, 190, 214, 0.2);
  border-top: none;
  /* border-left: none; */
  padding: 5px 0;
}
.footData-right{
  height: 386px;
  overflow: auto;
  padding: 0 0 0 5px;
}
</style>
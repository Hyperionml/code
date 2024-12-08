<template>
  <div>
    <!-- 图表容器 -->
    <div ref="main" style="width900px; height: 540px"></div>
    <!-- 报警信息容器 -->
    <div id="alarm" v-show="showAlarm">警告：振动值超出安全范围！</div>
    <!-- 错误信息容器 -->
    <div id="error" v-show="showError">{{ errorMessage }}</div>
  </div>
</template>

<script>
// 引入ECharts
import * as echarts from "echarts";

export default {
  name: "VibrationChart",

  data() {
    return {
      chart: null,
      alarmThreshold: 30,
      lastTimestamp: 0,
      showAlarm: false,
      showError: false,
      errorMessage: "",
    };
  },

  mounted() {
    this.initChart();
    this.fetchDataAndUpdateChart();
  },

  methods: {
    initChart() {
      // 初始化ECharts实例
      this.chart = echarts.init(this.$refs.main);
      // 设置ECharts图表配置
      const option = {
        title: {
          text: "振动信号图表",
        },
        tooltip: {
          trigger: "axis",
        },
        grid: {
          left: "60px", // 调整图表到左侧的距离为60像素
          right: "100px", // 调整图表到右侧的距离为60像素
          top: "60px", // 调整图表到顶部的距离为60像素
          bottom: "30px", // 调整图表到底部的距离为60像素
        },
        xAxis: {
          type: "time",
          name: "时间",
          splitLine: { show: false },
        },
        yAxis: {
          type: "value",
          name: "振动信号",
          axisLabel: {
            show: true,
            formatter: "{value}",
          },
          splitLine: { show: false },
          axisLine: { show: true },
          scale: true,
          min: -40,
          max: 40,
          alarmThreshold: 25,
          negativeAlarmThreshold: -25,
          interval: 5,
        },
        series: [
          {
            name: "振动信号",
            type: "line",
            showSymbol: false,
            // hoverAnimation: false,
            emphasis: {
              scale: true, // 使用 emphasis.scale 代替 hoverAnimation
            },
            data: [],
            markLine: {
              silent: true,
              data: [
                {
                  yAxis: 30,
                  lineStyle: { color: "red" },
                  label: {
                    position: "end",
                    formatter: "报警阈值: 30",
                  },
                },
                {
                  yAxis: -30,
                  lineStyle: { color: "red" },
                  label: {
                    position: "end",
                    formatter: "报警阈值: -30",
                  },
                },
              ],
            },
          },
        ],
      };
      // 设置图表配置
      this.chart.setOption(option);
    },

    fetchDataAndUpdateChart() {
      // 从API获取数据
      fetch()
        .then((response) => {
          if (!response.ok) {
            throw new Error("Network response was not ok");
          }
          return response.json();
        })
        .then((data) => {
          const timestamp = data.timestamp;
          const value = data.data.get(1);
          const seriesData = this.chart.getOption().series[0].data;

          if (timestamp > this.lastTimestamp) {
            seriesData.push([timestamp, value]);
            this.lastTimestamp = timestamp;
          }

          this.chart.setOption({
            series: [{ data: seriesData }],
          });

          const lastValue = seriesData[seriesData.length - 1][1];
          if (Math.abs(lastValue) > this.alarmThreshold) {
            this.showAlarm = true;
          } else {
            this.showAlarm = false;
          }
        })
        .catch((error) => {
          console.error("Error fetching data: ", error);
          this.showError = true;
          this.errorMessage = "数据加载失败，请稍后再试。";
        });
    },
  },
};
</script>

<style>
#alarm {
  color: red;
  display: none;
  margin-top: 10px;
}

#error {
  color: red;
  display: none;
  margin-top: 10px;
}
</style>
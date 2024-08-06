<template>
  <div class="dashboard">
    <div class="searchbar">
      <div class="icon-group">
        <div class="icon-item" @click="select('facebook')">
          <el-button
            :type="selected === 'facebook' ? 'primary' : 'default'"
            icon="el-icon-user-solid"
            circle
            :style="{ fontSize: '25px' }"
          />
          <div>Facebook</div>
        </div>
        <div class="icon-item" @click="select('twitter')">
          <el-button
            :type="selected === 'twitter' ? 'primary' : 'default'"
            icon="el-icon-platform-eleme"
            circle
            :style="{ fontSize: '25px' }"
          />
          <div>Twitter</div>
        </div>
        <div class="icon-item" @click="select('custom')">
          <el-button
            :type="selected === 'custom' ? 'primary' : 'default'"
            icon="el-icon-platform-eleme"
            circle
            :style="{ fontSize: '25px' }"
          />
          <div>Custom</div>
        </div>
        <div class="icon-item" @click="select('custom')">
          <el-button
            :type="selected === 'custom' ? 'primary' : 'default'"
            icon="el-icon-platform-eleme"
            circle
            :style="{ fontSize: '25px' }"
          />
          <div>Custom</div>
        </div>
        <div class="icon-item" @click="select('custom')">
          <el-button
            :type="selected === 'custom' ? 'primary' : 'default'"
            icon="el-icon-platform-eleme"
            circle
            :style="{ fontSize: '25px' }"
          />
          <div>Custom</div>
        </div>
      </div>
      <div class="date-selectors">
        <el-date-picker v-model="startDate" type="date" placeholder="开始日期" />
        <el-date-picker v-model="endDate" type="date" placeholder="结束日期" />
      </div>
    </div>

    <hr>

    <div class="radarChart">
      <div class="title-with-button">
        <h3 style="color:rgb(2, 157, 255); font-weight: bold; ">议题规模</h3>
      </div>
      <div id="radar-chart" style="width: 100%; height: 400px;" />
    </div>
    <hr>
    <div class="barChart">
      <div class="title-with-button">
        <h3 style="color:rgb(2, 157, 255); font-weight: bold; ">议题趋势与参考风险等级</h3>
      </div>
      <div id="bar-chart" style="width: 100%; height: 400px;" />
    </div>
    <hr>
    <div class="title-with-button">
      <h3 style="color:rgb(2, 157, 255); font-weight: bold; ">议题趋势与参考风险等级</h3>
    </div>
    <div class="positionAndSentiment">
      <div class="positionChart">
        <div id="position-chart" style="width: 100%; height: 400px;" />
      </div>
      <div class="sentimentChart">
        <div id="sentiment-chart" style="width: 100%; height: 400px;" />
      </div>
    </div>
  </div>
</template>

<script>
/* eslint-disable */
import * as echarts from 'echarts'
export default {
  name: 'Dashboard',
  data() {
    return {
      selected: null,
      startDate: '',
      endDate: ''
    }
  },
  mounted() {
    this.initRadarChart()
    this.initBarChart()
    this.initPositionChart()
    this.initSentimentChart()
  },
  methods: {
    select(icon) {
      this.selected = icon
    },
    initBarChart() {
      const colors = ['#5470C6', '#91CC75', '#EE6666']
      const barChartDom = document.getElementById('bar-chart')
      const barChart = echarts.init(barChartDom)
      barChart.showLoading()

      const option = {
        color: colors,
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross'
          }
        },
        grid: {
          right: '20%'
        },
        toolbox: {
          feature: {
            dataView: { show: true, readOnly: false },
            restore: { show: true },
            saveAsImage: { show: true }
          }
        },
        legend: {
          data: ['Evaporation', 'Precipitation', 'Temperature']
        },
        xAxis: [
          {
            type: 'category',
            axisTick: {
              alignWithLabel: true
            },
            data: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
          }
        ],
        yAxis: [
          {
            type: 'value',
            name: 'Evaporation',
            position: 'right',
            alignTicks: true,
            axisLine: {
              show: true,
              lineStyle: {
                color: colors[0]
              }
            },
            axisLabel: {
              formatter: '{value} ml'
            }
          },
          {
            type: 'value',
            name: 'Precipitation',
            position: 'right',
            alignTicks: true,
            offset: 80,
            axisLine: {
              show: true,
              lineStyle: {
                color: colors[1]
              }
            },
            axisLabel: {
              formatter: '{value} ml'
            }
          },
          {
            type: 'value',
            name: 'Temperature',
            position: 'left',
            alignTicks: true,
            axisLine: {
              show: true,
              lineStyle: {
                color: colors[2]
              }
            },
            axisLabel: {
              formatter: '{value} °C'
            }
          }
        ],
        series: [
          {
            name: 'Evaporation',
            type: 'bar',
            data: [2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3]
          },
          {
            name: 'Precipitation',
            type: 'bar',
            yAxisIndex: 1,
            data: [2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3]
          },
          {
            name: 'Temperature',
            type: 'line',
            yAxisIndex: 2,
            data: [2.0, 2.2, 3.3, 4.5, 6.3, 10.2, 20.3, 23.4, 23.0, 16.5, 12.0, 6.2]
          }
        ]
      }

      barChart.hideLoading()
      barChart.setOption(option)
    },
    //议题规模
    initRadarChart() {
      const radarChartDom = document.getElementById('radar-chart')
      const radarChart = echarts.init(radarChartDom)
      radarChart.showLoading()
      const radarOption = {
        tooltip: {
          trigger: 'item'
        },
        legend: {
          type: 'scroll',
          bottom: 10,
          data: Array.from({ length: 28 }, (_, i) => `${i + 2001}`)
        },
        visualMap: {
          top: 'middle',
          right: 10,
          color: ['red', 'yellow'],
          calculable: true
        },
        radar: {
          indicator: [
            { text: 'IE8-', max: 400 },
            { text: 'IE9+', max: 400 },
            { text: 'Safari', max: 400 },
            { text: 'Firefox', max: 400 },
            { text: 'Chrome', max: 400 }
          ]
        },
        series: Array.from({ length: 28 }, (_, i) => ({
          type: 'radar',
          symbol: 'none',
          lineStyle: {
            width: 1
          },
          emphasis: {
            areaStyle: {
              color: 'rgba(0,250,0,0.3)'
            }
          },
          data: [{
            value: [
              (40 - i) * 10,
              (38 - i) * 4 + 60,
              i * 5 + 10,
              i * 9,
              (i * i) / 2
            ],
            name: `${i + 2001}`
          }]
        }))
      }

      radarChart.hideLoading()
      radarChart.setOption(radarOption)
    },

    initPositionChart(){
      const positionChartDom = document.getElementById('position-chart')
      const positionChart = echarts.init(positionChartDom)
      positionChart.showLoading()
      const positionOption = {
        title: [
          {
            text: 'Tangential Polar Bar Label Position (middle)'
          }
        ],
        polar: {
          radius: [30, '80%']
        },
        angleAxis: {
          max: 4,
          startAngle: 75
        },
        radiusAxis: {
          type: 'category',
          data: ['a', 'b', 'c', 'd']
        },
        tooltip: {},
        series: {
          type: 'bar',
          data: [2, 1.2, 2.4, 3.6],
          coordinateSystem: 'polar',
          label: {
            show: true,
            position: 'middle',
            formatter: '{b}: {c}'
          }
        }
      }
      positionChart.hideLoading()
      positionChart.setOption(positionOption)
    },
    initSentimentChart(){
      const sentimentChartDom = document.getElementById('sentiment-chart')
      const sentimentChart = echarts.init(sentimentChartDom)
      sentimentChart.showLoading()
      const option = {
        angleAxis: {
          type: 'category',
          data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
        },
        radiusAxis: {},
        polar: {},
        series: [
          {
            type: 'bar',
            data: [1, 2, 3, 4, 3, 5, 1],
            coordinateSystem: 'polar',
            name: 'A',
            stack: 'a',
            emphasis: {
              focus: 'series'
            }
          },
          {
            type: 'bar',
            data: [2, 4, 6, 1, 3, 2, 1],
            coordinateSystem: 'polar',
            name: 'B',
            stack: 'a',
            emphasis: {
              focus: 'series'
            }
          },
          {
            type: 'bar',
            data: [1, 2, 3, 4, 1, 2, 5],
            coordinateSystem: 'polar',
            name: 'C',
            stack: 'a',
            emphasis: {
              focus: 'series'
            }
          }
        ],
        legend: {
          show: true,
          data: ['A', 'B', 'C']
        }
      }
      sentimentChart.hideLoading()
      sentimentChart.setOption(option)
    }
  }
}
</script>

<style scoped>
.dashboard {
  padding: 20px;
}

.icon-group {
  display: flex;
  justify-content: space-around;
  margin-bottom: 20px;
}

.icon-item {
  text-align: center;
}

.date-selectors {
  display: flex;
  justify-content: space-around;
  margin-bottom: 20px;
}

.radarChart,
.barChart,
.positionAndSentiment {
  margin-bottom: 20px;
}

.positionAndSentiment {
  display: flex;
  justify-content: space-between;
}

.positionChart,
.sentimentChart {
  flex: 1;
  margin: 10px;
}

.title-with-button {
  display: flex;
  align-items: center;
  justify-content: space-between;
}
</style>

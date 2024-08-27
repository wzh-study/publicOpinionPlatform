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
        </div>
        <div class="date-selectors">
          <el-date-picker v-model="startDate" type="date" placeholder="开始日期" />
          <el-date-picker v-model="endDate" type="date" placeholder="结束日期" />
        </div>
      </div>
  
      <hr>
      <div class="LesMiserables">
        <div class="title-with-button">
          <el-switch v-model="showEventTree" @change="toggleChart" />
          <h3 style="color:rgb(2, 157, 255); font-weight: bold; margin-left:10px">舆情事件树</h3>
        </div>
      </div>
      <div v-if="showEventTree">
        <div id="event-tree" style="width: 100%; height: 400px; margin: 0 auto;" />
        <div class="content-conclusion">
          [A] 舆情事件树
        </div>
      </div>
      <hr>
  
      <div class="title-with-button">
        <el-switch v-model="showSpreadingInfo" @change="toggleChart" />
        <h3 style="color:rgb(2, 157, 255); font-weight: bold; margin-left:10px">舆情事件分析——传播</h3>
      </div>
      <div v-if="showSpreadingInfo">
        <div class="centered-select">
          <el-select
            v-model="eventinfo_selected"
            placeholder="请选择"
            @change="fetchEventData"
            class="custom-select"
          >
            <el-option
              v-for="event in events"
              :key="event"
              :label="event"
              :value="event"
            />
          </el-select>
        </div>
        <div id="overview" style="width: 600px; height:600px; margin: 30px auto 0;" />
        <div class="content-conclusion">
          [B] 传播总览图
        </div>
  
        <div style="display: flex; justify-content: center; gap: 50px;">
          <div class="trendChartContent">
            <div id="trend-chart" style="width: 800px; height: 400px;" />
            <div class="content-conclusion">
            [C] 声量与新用户趋势
            </div>
          </div>
          <div class="pieChartContent">
            <div id="pie-chart" style="width: 400px; height: 400px;" />
            <div class="content-conclusion">
            [D.1] 传播链路长度分析
            </div>
          </div>
        </div>
  
        <div id="SpreadingChain" style="height:200px; width:100%"></div>
  
        <div class="content-conclusion">
          [D.2] 长传播链示意
        </div>
      </div>
      <hr>
  
      <div class="LesMiserables">
        <div class="title-with-button">
          <el-switch v-model="showUserInfo" @change="toggleChart" />
          <h3 style="color:rgb(2, 157, 255); font-weight: bold; margin-left:10px">舆情事件分析——关键用户</h3>
        </div>
      </div>
      <div v-if="showUserInfo">
        <div class="centered-select">
          <el-select
            v-model="eventinfo_selected"
            placeholder="请选择"
            @change="fetchEventData"
            class="custom-select"
          >
            <el-option
              v-for="event in events"
              :key="event"
              :label="event"
              :value="event"
            />
          </el-select>
        </div>
        <div class="content-conclusion">
          [E.1] 基于传播
        </div>
        <div class="content-conclusion">
          [E.2] 基于内容
        </div>
        <div class="content-conclusion">
          [E.3] 基于算法
        </div>
        <div id="spreading-chart" style="width:400px;height:400px;margin:30px auto" />
      </div>
      <hr>
  
      <div class="title-with-button">
        <el-switch v-model="showContentInfo" @change="toggleChart" />
        <h3 style="color:rgb(2, 157, 255); font-weight: bold; margin-left:10px">舆情事件分析——内容</h3>
      </div>
      <div v-if="showContentInfo">
        <div class="centered-select">
          <el-select
            v-model="eventinfo_selected"
            placeholder="请选择"
            @change="fetchEventData"
            class="custom-select"
          >
            <el-option
              v-for="event in events"
              :key="event"
              :label="event"
              :value="event"
            />
          </el-select>
        </div>
        <el-table :data="tableData" border style="width: 950px;margin:30px auto;" :row-class-name="getRowClassName">
          <el-table-column prop="ranking1" label="排名" width="50px" />
          <el-table-column prop="ranking2" label="排名*" width="50px" />
          <el-table-column prop="content" label="高频传播内容" width="810px" />
          <el-table-column prop="origin" label="源头" width="40px" />
        </el-table>
        <div class="content-conclusion">
          [F] 高频传播内容
        </div>
        <div class="cloudMap">
          [G.1] 高频词云图
        </div>
        <div class="frequentPhrase">
          [G.2] 高频词的共现
        </div>
        <div class="right-aligned-container">
          <table class="content-table">
            <tr>
              <td class="row-header">民主进步党</td>
              <td></td>
              <td></td>
              <td></td>
              <td></td>
              <td></td>
            </tr>
            <tr>
              <td class="row-header">中国国民党中常委</td>
              <td></td>
              <td></td>
              <td></td>
              <td></td>
              <td></td>
            </tr>
            <tr>
              <td class="row-header">ZZZ</td>
              <td>将相对长的内容放到自适应的宽单元格中</td>
              <td></td>
              <td></td>
              <td></td>
              <td></td>
            </tr>
            <tr>
              <td class="row-header">AAAA</td>
              <td class="colored-cell a1">a1</td>
              <td class="colored-cell a2">a2</td>
              <td class="colored-cell a3">a3</td>
              <td class="colored-cell a4">a4</td>
              <td class="colored-cell a5">a5</td>
            </tr>
            <tr>
              <td class="row-header">BBBB</td>
              <td></td>
              <td></td>
              <td></td>
              <td></td>
              <td></td>
            </tr>
          </table>
        </div>
  
        <div class="appointedPhrase">
          [G.3] 指定关注词的共现
        </div>
        <div class="right-aligned-container">
          <table class="content-table">
            <tr>
              <td class="row-header">中共</td>
              <td></td>
              <td></td>
              <td></td>
              <td></td>
              <td></td>
            </tr>
            <tr>
              <td class="row-header">中国</td>
              <td></td>
              <td></td>
              <td></td>
              <td></td>
              <td></td>
            </tr>
            <tr>
              <td class="row-header">武统</td>
              <td>将相对长的内容放到自适应的宽单元格中</td>
              <td></td>
              <td></td>
              <td></td>
              <td></td>
            </tr>
          </table>
        </div>
      </div>
      <hr>
  
  
      <div class="title-with-button">
        <el-switch v-model="showSentimentInfo" @change="toggleChart" />
        <h3 style="color:rgb(2, 157, 255); font-weight: bold; margin-left:10px">舆情事件分析——语义</h3>
      </div>
      <div v-if="showSentimentInfo">
        <div class="centered-select">
          <el-select
            v-model="eventinfo_selected"
            placeholder="请选择"
            @change="fetchEventData"
            class="custom-select"
          >
            <el-option
              v-for="event in events"
              :key="event"
              :label="event"
              :value="event"
            />
          </el-select>
        </div>
        <div class="topic">
          [H] 主要话题短语
        </div>
        <div class="opinion">
          [I] 主要观点
        </div>
        <div class="pieChartCombination">
          <div id="point-chart" style="width:600px;height:600px; margin-left: 20px;"></div>
          <div class="table-container">
            <table class="custom-table">
              <tr class="header-row">
                <td colspan="2">话题/观点与对应原文</td>
              </tr>
              <tr>
                <td class="second-row-left">观点-6</td>
                <td class="second-row-right">菲律宾人呼吁团结捍卫在西菲律宾海的主权</td>
              </tr>
              <tr>
                <td class="third-row-left">原文<br>（部分）</td>
                <td class="third-row-right">当我们在西菲律宾海（WPS）专属经济区的自己的水域和土地上受到欺凌和占领时，全体菲律宾人民必须为了祖国团结起来<br>---<br>当我们在西菲律宾海（WPS）专属经济区的自己的水域和土地上受到欺凌和占领时，全体菲律宾人民必须为了祖国团结起来<br>---<br>我们是菲律宾青年。我们停在了西菲律宾海<br>---<br>我们是菲律宾青年。我们停在了西菲律宾海<br>---<br>OCTA Research 的一项调查显示，七成菲律宾人认为，总统小费迪南德·马科斯的政府应该通过军事行动和外交手段维护该国在西菲律宾海 (WPS) 的领土权利。<br>---<br>OCTA Research 的一项调查显示，七成菲律宾人认为，总统小费迪南德·马科斯的政府应该通过军事行动和外交手段维护该国在西菲律宾海 (WPS) 的领土权利。<br>---<br>当我们在西菲律宾海（WPS）专属经济区的自己的水域和土地上受到欺凌和占领时，全体菲律宾人民必须为了祖国团结起来---我们是菲律宾青年。我们停在了西菲律宾海</td>
              </tr>
            </table>
          </div>
        </div>
        <div class="pointPieChartCombination">
          <div id="point-pie-chart" style="width:300px;height:300px; margin-left: 0" />
          <div class="pieChartTableContainer">
            <table class="pieChart-table">
              <tr class="header-row">
                <td colspan="3">〔极端愤怒〕情绪对应原文（部分）</td>
              </tr>
              <tr>
                <td class="second-row-left">民进党</td>
                <td class="second-row-middle">敌意</td>
                <td class="second-row-right">当我们在西菲律宾海（WPS）专属经济区的自己的水域和土地上受到欺凌和占领时，全体菲律宾人民必须为了祖国团结起来<br>---<br>我们是菲律宾青年。我们停在了西菲律宾海<br>---<br>我们是菲律宾青年。我们停在了西菲律宾海<br>---<br>OCTA Research 的一项调查显示，七成菲律宾人认为，总统小费迪南德·马科斯的政府应该通过军事行动和外交手段维护该国在西菲律宾海 (WPS) 的领土权利。<br>---<br>OCTA Research 的一项调查显示，七成菲律宾人认为，总统小费迪南德·马科斯的政府应该通过军事行动和外交手段维护该国在西菲律宾海 (WPS) 的领土权利。<br>---<br>我们是菲律宾青年。我们停在了西菲律宾海</td>
              </tr>
            </table>
          </div>
        </div>
        <div class="standpoint">
          [J] 针对XXX的立场
        </div>
        <div class="distribution">
          [K] 情绪分布
        </div>
        <div class="sentimentChartCombination">
          <div id="sentiment-chart" style="width:300px;height:300px;margin-left:0" />
          <div class="sentimentContainer">
            <table class="sentiment-table">
              <tr class="header-row">
                <td colspan="3">〔极端愤怒〕情绪对应原文（部分）</td>
              </tr>
              <tr>
                <td class="second-row-left">民进党</td>
                <td class="second-row-middle">敌意</td>
                <td class="second-row-right">当我们在西菲律宾海（WPS）专属经济区的自己的水域和土地上受到欺凌和占领时，全体菲律宾人民必须为了祖国团结起来<br>---<br>我们是菲律宾青年。我们停在了西菲律宾海<br>---<br>我们是菲律宾青年。我们停在了西菲律宾海<br>---<br>OCTA Research 的一项调查显示，七成菲律宾人认为，总统小费迪南德·马科斯的政府应该通过军事行动和外交手段维护该国在西菲律宾海 (WPS) 的领土权利。<br>---<br>OCTA Research 的一项调查显示，七成菲律宾人认为，总统小费迪南德·马科斯的政府应该通过军事行动和外交手段维护该国在西菲律宾海 (WPS) 的领土权利。<br>---<br>我们是菲律宾青年。我们停在了西菲律宾海</td>
              </tr>
            </table>
          </div>
        </div>
      </div>
      <hr>
  
  
        <div class="title-with-button">
          <el-switch v-model="showContrastInfo" @change="toggleChart" />
          <h3 style="color:rgb(2, 157, 255); font-weight: bold; margin-left:10px">舆情事件对比</h3>
        </div>
      <div v-if="showContrastInfo">
        <div id="contrast-chart" style="width:600px;height:400px; margin:0 auto" />
        <div class="content-conclusion">
          [L] 舆情事件间对比
        </div>
      </div>
      <hr>
  
  
      <div class="title-with-button">
        <el-switch v-model="showEvolutionInfo" @change="toggleChart" />
        <h3 style="color:rgb(2, 157, 255); font-weight: bold; margin-left:10px">舆情事件演化</h3>
      </div>
      <div v-if="showEvolutionInfo">
  
      </div>
  
    </div>
  </template>
  
  <script>
  /* eslint-disable */
  import * as echarts from 'echarts'
  import axios from 'axios'
  import localEventTree from '@/assets/eventPage/flare.json'
  import localPointChart from '@/assets/eventPage/les-miserables.json'
  import webkitDep from '@/assets/eventPage/webkit-dep.json'
  
  export default {
    data() {
      return {
        tableData: [
          {
            ranking1: '1',
            ranking2: '1',
            content: '加油！愿你平安喜乐，万事如意！在西菲律宾海！！！',
          },
          {
            ranking1: '2',
            ranking2: '2',
            content: '他们会这样做，然后却没有采取任何措施来保护在西菲律宾海受到骚扰的渔民💀；同意 BA KAYO，芭比粉丝？参议员们正在呼吁禁令即将上映的《芭比娃娃》电影中，有一段场景据称展示了中国对九段线的主权主张，这引起了争议。'
          },
          {
            ranking1: '3',
            ranking2: '3',
            content: '中国不仅在破坏西菲律宾海的生物多样性，还在破坏整个南海。多年来，我们我见过他们过度开采濒临灭绝的巨蛤，建造破坏珊瑚的岛屿，',
          },
          {
            ranking1: '4',
            ranking2: '4',
            content: '但是那些穿着“西菲律宾海”衬衫但在 2016-2022 年期间却一动不动的人，真是可笑？'
          },
          {
            ranking1: '5',
            ranking2: '5',
            content: '中国不仅非法宣称对西菲律宾海拥有主权，而且越南、马来西亚、印度尼西亚和东南亚其他地区的专属经济区！ 中国海岸警卫队在马来西亚的专属经济区内，一群马来西亚人在 Terumbu Sahap 附近捕鱼',
          },
        ],
        chartInstance: null,
        eventinfo_selected: '民进党执政不力',
        flareData: [],
        nodes:[],
        links:[],
        events:['民进党滥用公权力','民进党黑金政治','民进党执政不力'],
        showEventTree:true,
        showSpreadingInfo:true,
        showUserInfo:true,
        showContentInfo:true,
        showSentimentInfo:true,
        showContrastInfo:true,
        showEvolutionInfo:true
      }
    },
    mounted() {
      this.initOverview()
      this.initEventTree()
      this.initTrendChart()
      this.initPieChart()
      this.initPointChart()
      this.initPointPieChart()
      this.initSentimentChart()
      this.initContrastChart()
      this.initSpreadingChart()
    },
    methods: {
      getRowClassName({ row, rowIndex }) {
        // 如果是第一行（索引为 0），返回类名 'pink-row'
        if (rowIndex === 0) {
          return 'pink-row';
        }
        return '';
      },
      initOverview() {
        axios.get('http://localhost:8080/spreading-chart-data')
          .then(response => {
            // 使用后端返回的数据
            this.nodes = response.data.nodes;
            this.links = response.data.links;
            this.categories = response.data.categories;
          })
          .catch(error => {
            console.error('Error fetching spreading chart data from backend, using local data instead:', error);
            // 使用本地数据
            this.nodes = webkitDep.nodes;
            this.links = webkitDep.links;
            this.categories = webkitDep.categories;
          })
          .finally(() => {
            // 初始化图表
            this.chartInstance = echarts.init(document.getElementById('overview'));
            const option = {
              legend: {
                data: ['HTMLElement', 'WebGL', 'SVG', 'CSS', 'Other']
              },
              series: [
                {
                  type: 'graph',
                  layout: 'force',
                  animation: false,
                  label: {
                    position: 'right',
                    formatter: '{b}'
                  },
                  draggable: true,
                  data: this.nodes.map(function (node, idx) {
                    node.id = idx;
                    return node;
                  }),
                  categories: this.categories,
                  force: {
                    edgeLength: 1,
                    repulsion: 20,
                    gravity: 0.2
                  },
                  edges: this.links
                }
              ]
            };
            this.chartInstance.setOption(option);
          });
      },
      initSpreadingChart() {
        axios.get('http://localhost:8080/spreading-chart-data')
          .then(response => {
            // 使用后端返回的数据
            this.nodes = response.data.nodes;
            this.links = response.data.links;
            this.categories = response.data.categories;
          })
          .catch(error => {
            console.error('Error fetching spreading chart data from backend, using local data instead:', error);
            // 使用本地数据
            this.nodes = webkitDep.nodes;
            this.links = webkitDep.links;
            this.categories = webkitDep.categories;
          })
          .finally(() => {
            // 初始化图表
            this.chartInstance = echarts.init(document.getElementById('spreading-chart'));
            const option = {
              legend: {
                data: ['HTMLElement', 'WebGL', 'SVG', 'CSS', 'Other']
              },
              series: [
                {
                  type: 'graph',
                  layout: 'force',
                  animation: false,
                  label: {
                    position: 'right',
                    formatter: '{b}'
                  },
                  draggable: true,
                  data: this.nodes.map(function (node, idx) {
                    node.id = idx;
                    return node;
                  }),
                  categories: this.categories,
                  force: {
                    edgeLength: 1,
                    repulsion: 20,
                    gravity: 0.2
                  },
                  edges: this.links
                }
              ]
            };
            this.chartInstance.setOption(option);
          });
      },
  
      initTrendChart() {
        const trendChartDom = document.getElementById('trend-chart')
        const trendChart = echarts.init(trendChartDom)
        const colors = ['#5470C6', '#91CC75', '#EE6666'];
        trendChart.showLoading()
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
              // prettier-ignore
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
                show: false,
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
                show: false,
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
              name: '温度',
              position: 'left',
              alignTicks: true,
              axisLine: {
                show: false,
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
              data: [
                2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3
              ]
            },
            {
              name: 'Precipitation',
              type: 'bar',
              yAxisIndex: 1,
              data: [
                2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3
              ]
            },
            {
              name: 'Temperature',
              type: 'line',
              yAxisIndex: 2,
              data: [2.0, 2.2, 3.3, 4.5, 6.3, 10.2, 20.3, 23.4, 23.0, 16.5, 12.0, 6.2]
            }
          ]
        }
        trendChart.hideLoading()
        trendChart.setOption(option)
      },
      initPieChart() {
        const pieChartDom = document.getElementById('pie-chart')
        const pieChart = echarts.init(pieChartDom)
        pieChart.showLoading()
        const option = {
          title: {
            text: 'Referer of a Website',
            subtext: 'Fake Data',
            left: 'center'
          },
          tooltip: {
            trigger: 'item'
          },
          legend: {
            orient: 'vertical',
            left: 'left'
          },
          series: [
            {
              name: 'Access From',
              type: 'pie',
              radius: '50%',
              data: [
                { value: 1048, name: 'Search Engine' },
                { value: 735, name: 'Direct' },
                { value: 580, name: 'Email' },
                { value: 484, name: 'Union Ads' },
                { value: 300, name: 'Video Ads' }
              ],
              emphasis: {
                itemStyle: {
                  shadowBlur: 10,
                  shadowOffsetX: 0,
                  shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
              }
            }
          ]
        }
        pieChart.hideLoading()
        pieChart.setOption(option)
      },
      initEventTree() {
        const self = this;
        axios.get('https://localhost:8080/event-tree')
          .then(response => {
            self.flareData = response.data;
            addDepthInfo(self.flareData); // 为每个节点添加depth信息
          })
          .catch(error => {
            console.log('Error fetching event tree data from backend, using local data instead:', error);
            if (localEventTree) {
              self.flareData = localEventTree;
              addDepthInfo(self.flareData); // 为本地数据添加depth信息
            } else {
              console.log('Local event tree data is undefined.');
            }
          })
          .finally(() => {
            if (self.flareData) {
              const chartElement = document.getElementById('event-tree');
              chartElement.style.height = `${window.innerWidth * 0.5}px`;
  
              self.chartInstance = echarts.init(chartElement);
              const option = {
                tooltip: {
                  trigger: 'item',
                  triggerOn: 'mousemove'
                },
                series: [
                  {
                    type: 'tree',
                    data: [self.flareData],
                    left: '2%',
                    right: '2%',
                    top: '8%',
                    bottom: '20%',
                    symbol: 'emptyCircle',
                    orient: 'vertical',
                    expandAndCollapse: true,
                    label: {
                      position: 'top',
                      rotate: -90,
                      verticalAlign: 'middle',
                      align: 'right',
                      fontSize: 9,
                      formatter: function(params) {
                        if (params.data.depth === 0) { // 第一层
                          return '{firstLine|' + params.data.name + '}';
                        } else if (params.data.depth === 1) { // 第二层
                          return '{secondLine|' + params.data.name + '}';
                        }
                        return params.data.name;
                      },
                      rich: {
                        firstLine: {
                          color: '#ff0000', // 第一行文字颜色
                          fontWeight: 'bold', // 第一行文字加粗
                          fontSize: 12
                        },
                        secondLine: {
                          color: '#0000ff', // 第二行文字颜色
                          fontStyle: 'italic', // 第二行文字斜体
                          fontSize: 10
                        }
                      }
                    },
                    leaves: {
                      label: {
                        position: 'bottom',
                        rotate: -90,
                        verticalAlign: 'middle',
                        align: 'left'
                      }
                    },
                    animationDurationUpdate: 750
                  }
                ]
              };
              self.chartInstance.setOption(option);
  
              window.addEventListener('resize', () => {
                chartElement.style.height = `${window.innerWidth * 0.5}px`;
                self.chartInstance.resize();
              });
            } else {
              console.log('No data available to display the chart.');
              document.getElementById('event-tree').innerText = 'No data available';
            }
          });
      },
  
      initPointChart(){
        axios.get('http://localhost:8080/user-chart-data')
          .then(response => {
            // 使用后端返回的数据
            this.nodes = response.data.nodes
            this.links = response.data.links
          })
          .catch(error => {
            console.error('Error fetching user chart data from backend, using local data instead:', error)
            // 使用本地数据
            this.nodes = localPointChart.nodes
            this.links = localPointChart.links
          })
          .finally(() => {
            // 初始化图表
            this.chartInstance = echarts.init(document.getElementById('point-chart'))
            const option = {
              tooltip: {},
              legend: [
                {
                  data: localPointChart.categories.map(function (a) {
                    return a.name;
                  })
                }
              ],
              animationDurationUpdate: 1500,
              animationEasingUpdate: 'quinticInOut',
              series: [
                {
                  name: 'Les Miserables',
                  type: 'graph',
                  layout: 'circular',
                  circular: {
                    rotateLabel: true
                  },
                  data: localPointChart.nodes,
                  links: localPointChart.links,
                  categories: localPointChart.categories,
                  roam: true,
                  label: {
                    position: 'right',
                    formatter: '{b}'
                  },
                  lineStyle: {
                    color: 'source',
                    curveness: 0.3
                  }
                }
              ]
            }
            this.chartInstance.setOption(option)
          })
      },
      initPointPieChart() {
        const pieChartDom = document.getElementById('point-pie-chart')
        const pieChart = echarts.init(pieChartDom)
        pieChart.showLoading()
        const option = {
          tooltip: {
            trigger: 'item'
          },
          legend: {
            show:false
          },
          series: [
            {
              name: 'Access From',
              type: 'pie',
              radius: ['40%', '70%'],
              avoidLabelOverlap: false,
              itemStyle: {
                borderRadius: 10,
                borderColor: '#fff',
                borderWidth: 2
              },
              label: {
                show: false,
                position: 'center'
              },
              emphasis: {
                label: {
                  show: true,
                  fontSize: 20,
                  fontWeight: 'bold'
                }
              },
              labelLine: {
                show: false
              },
              data: [
                { value: 1048, name: 'Search Engine' },
                { value: 735, name: 'Direct' },
                { value: 580, name: 'Email' },
                { value: 484, name: 'Union Ads' },
                { value: 300, name: 'Video Ads' }
              ]
            }
          ]
        }
        pieChart.hideLoading()
        pieChart.setOption(option)
      },
      initSentimentChart(){
        const sentimentChartDom = document.getElementById('sentiment-chart')
        const sentimentChart = echarts.init(sentimentChartDom)
        sentimentChart.showLoading()
        const option = {
          angleAxis: {
            type: 'category',
            data: ['愤怒','厌恶','恐惧','悲伤','惊喜','蔑视','兴奋','自豪']
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
              name: '情绪',
              stack: 'a',
              emphasis: {
                focus: 'series'
              }
            },
            {
              type: 'bar',
              data: [1, 2, 3, 4, 1, 2, 5],
              coordinateSystem: 'polar',
              name: '极端情绪',
              stack: 'a',
              emphasis: {
                focus: 'series'
              }
            }
          ],
          legend: {
            show: true,
            data: ['A', '情绪', '极端情绪']
          }
        }
        sentimentChart.hideLoading()
        sentimentChart.setOption(option)
      },
      initContrastChart(){
        const contrastChartDom = document.getElementById('contrast-chart')
        const contrastChart = echarts.init(contrastChartDom)
        contrastChart.showLoading()
        const option = {
          title: {
            text: 'Stacked Area Chart',
            show:false
          },
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'cross',
              label: {
                backgroundColor: '#6a7985'
              }
            }
          },
          legend: {
            data: ['Email', 'Union Ads', 'Video Ads', 'Direct', 'Search Engine']
          },
          toolbox: {
            feature: {
              saveAsImage: {}
            }
          },
          grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
          },
          xAxis: [
            {
              type: 'category',
              boundaryGap: false,
              data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
            }
          ],
          yAxis: [
            {
              type: 'value'
            }
          ],
          series: [
            {
              name: 'Email',
              type: 'line',
              stack: 'Total',
              areaStyle: {},
              emphasis: {
                focus: 'series'
              },
              data: [120, 132, 101, 134, 90, 230, 210]
            },
            {
              name: 'Union Ads',
              type: 'line',
              stack: 'Total',
              areaStyle: {},
              emphasis: {
                focus: 'series'
              },
              data: [220, 182, 191, 234, 290, 330, 310]
            },
            {
              name: 'Video Ads',
              type: 'line',
              stack: 'Total',
              areaStyle: {},
              emphasis: {
                focus: 'series'
              },
              data: [150, 232, 201, 154, 190, 330, 410]
            },
            {
              name: 'Direct',
              type: 'line',
              stack: 'Total',
              areaStyle: {},
              emphasis: {
                focus: 'series'
              },
              data: [320, 332, 301, 334, 390, 330, 320]
            },
            {
              name: 'Search Engine',
              type: 'line',
              stack: 'Total',
              label: {
                show: true,
                position: 'top'
              },
              areaStyle: {},
              emphasis: {
                focus: 'series'
              },
              data: [820, 932, 901, 934, 1290, 1330, 1320]
            }
          ]
        }
        contrastChart.hideLoading()
        contrastChart.setOption(option)
      },
      toggleChart() {
        this.$nextTick(() => {
          if (this.showEventTree) this.initEventTree()
          if (this.showSpreadingInfo) {
            this.initOverview()
            this.initTrendChart()
            this.initPieChart()
          }
          if (this.showUserInfo) this.initSpreadingChart()
          if (this.showSentimentInfo) {
            this.initPointChart()
            this.initPointPieChart()
            this.initSentimentChart()
          }
          if (this.showContrastInfo) this.initContrastChart()
        })
      }
    }
  }
  </script>
  
  <style scoped>
  .dashboard {
    padding: 10px;
  }
  
  hr {
    background-color: #b3dbf1;
    height: 1px;
    border: none
  }
  
  .searchbar {
    margin-top: 20px;
    display: flex;
    justify-content: space-between;
    align-items: center;
  
  }
  
  .searchbar {
    border: none;
    padding: 0;
  }
  
  .icon-group {
    display: flex;
    gap: 50px;
    /* Add some space between icons */
  }
  
  .icon-item {
    display: flex;
    flex-direction: column;
    align-items: center;
    cursor: pointer;
  }
  
  .icon-item div {
    margin-top: 5px;
    /* Add some space between icon and text */
  }
  
  .date-selectors {
    display: flex;
    gap: 50px;
    /* Add some space between date pickers */
    justify-content: center;
    /* Center the date pickers */
  }
  
  .title-with-button {
    display: flex;
    align-items: center;
    margin-left: 10px;
  }
  
  .pink-row{
    background-color: pink;
  }
  .pieChartCombination {
    display: flex;
    align-items: flex-start; /* Align items to the start of the container */
    gap: 20px; /* Space between the chart and the table */
  }
  .pointPieChartCombination {
    display: flex;
    align-items: flex-start; /* Align items to the start of the container */
    gap: 60px; /* Space between the chart and the table */
    margin-top:30px;
  }
  .sentimentChartCombination {
    display: flex;
    align-items: flex-start; /* Align items to the start of the container */
    gap: 60px; /* Space between the chart and the table */
    margin-top:30px;
    margin-bottom:50px;
  }
  .pieChartContent{
    margin-top:10px;
  }
  
  .trendChartContent{
    margin-top:10px;
  }
  
  .content-conclusion {
    text-align: center;
    font-family: "Microsoft YaHei", serif;
    margin-bottom: 30px;
    font-size:20px;
    font-weight: bold;
    color: deepskyblue;
    margin-top: 10px;
  }
  .topic {
    font-family: "Microsoft YaHei", serif;
    margin:20px 0 10px 200px;
    font-size:20px;
    font-weight: bold;
    color: deepskyblue;
  }
  .opinion{
    font-family: "Microsoft YaHei", serif;
    margin:20px 0 10px 210px;
    font-size:20px;
    font-weight: bold;
    color: deepskyblue;
  }
  .standpoint{
    font-family: "Microsoft YaHei", serif;
    margin:0 0 30px 60px;
    font-size:20px;
    font-weight: bold;
    color: deepskyblue;
  }
  .distribution{
    font-family: "Microsoft YaHei", serif;
    margin:50px 0 10px 90px;
    font-size:20px;
    font-weight: bold;
    color: deepskyblue;
  }
  .cloudMap{
    font-family: "Microsoft YaHei", serif;
    margin:50px 0 10px 110px;
    font-size:20px;
    font-weight: bold;
    color: deepskyblue;
  }
  .frequentPhrase{
    font-family: "Microsoft YaHei", serif;
    text-align:center;
    margin:-30px 0 20px 180px;
    font-size:20px;
    font-weight: bold;
    color: deepskyblue;
  }
  .appointedPhrase{
    font-family: "Microsoft YaHei", serif;
    text-align:center;
    margin:30px 0 20px 220px;
    font-size:20px;
    font-weight: bold;
    color: deepskyblue;
  }
  .centered-select {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
  }
  
  .custom-select {
    width: 500px;
  }
  
  
  .light-ranking-content div {
    margin-top: 30px;
    font-size: 22px;
    color: lightgray;
    /* 标题颜色 */
    transform: skew(0, -10deg);
    /* 使文本校正回正 */
  }
  
  .content-table {
    width: 100%;
    border-collapse: collapse;
    text-align: center;
    table-layout: fixed; /* Ensures fixed cell width */
    margin-bottom:30px;
  }
  
  .content-table td {
    width: 120px; /* Fixed width for each cell */
    height: 60px; /* Fixed height for each cell */
    border: 1px solid lightgray;
    text-align: center; /* Center align the text horizontally */
    vertical-align: middle; /* Center align the text vertically */
    font-size: calc(8px + 0.3vw); /* Adjust font size based on content */
  }
  
  .row-header {
    background-color: #FFEEAA;
    width: 100px;
  }
  
  .colored-cell {
    color: white;
    font-weight: bold;
  }
  
  .right-aligned-container {
    max-width: 470px; /* Ensures it fits within the page */
    margin-left: auto;
    margin-right: 250px;
  }
  
  .a1 { background-color: #ff5722; }
  .a2 { background-color: #4caf50; }
  .a3 { background-color: #00c853; }
  .a4 { background-color: #00bcd4; }
  .a5 { background-color: #b2ebf2; }
  
  .table-container {
    width: 600px;
    margin: 0 auto; /* Center the table container horizontally */
  }
  .pieChartTableContainer {
    width: 880px;
    margin: 0 auto -20px;
  }
  .sentimentContainer {
    width: 880px;
    margin: -45px auto 0;
  }
  .custom-table {
    border-collapse: collapse;
    text-align: center;
    table-layout: fixed; /* Ensures fixed cell width */
  }
  .custom-table td {
    border: 1px solid lightgray;
    text-align: center;
    vertical-align: middle;
    font-size: calc(8px + 0.3vw); /* Adjust font size based on content */
    color:gray;
  }
  
  /* Header row styling */
  .custom-table .header-row {
    background-color: rgb(255, 240, 245); /* Light pink color for the first row */
    height:40px;
  }
  
  /* Specific widths and heights for cells in the second row */
  .custom-table .second-row-left {
    width: 110px !important;
    height: 40px !important;
  }
  
  .custom-table .second-row-right {
    width: 490px !important;
    height: 40px !important;
    text-align:left;
  }
  
  /* Specific widths and heights for cells in the third row */
  .custom-table .third-row-left {
    width: 110px !important;
    height: 500px !important;
  }
  
  .custom-table .third-row-right {
    width: 490px !important;
    height: 500px !important;
    text-align:left;
  }
  
  
  .pieChart-table {
    border-collapse: collapse;
    text-align: center;
    table-layout: fixed; /* Ensures fixed cell width */
  }
  .pieChart-table td {
    border: 1px solid lightgray;
    text-align: center;
    vertical-align: middle;
    font-size: calc(8px + 0.3vw); /* Adjust font size based on content */
    color:gray;
  }
  .pieChart-table .header-row {
    background-color: rgb(255, 240, 245); /* Light pink color for the first row */
    height:40px;
    width:1100px;
  }
  
  .pieChart-table .second-row-left {
    height:270px;
    width:80px;
  }
  .pieChart-table .second-row-middle {
    height:270px;
    width:80px;
  }
  .pieChart-table .second-row-right {
    height:270px;
    width:720px;
    text-align:left;
  }
  
  .sentiment-table {
    border-collapse: collapse;
    text-align: center;
    table-layout: fixed; /* Ensures fixed cell width */
  }
  .sentiment-table td {
    border: 1px solid lightgray;
    text-align: center;
    vertical-align: middle;
    font-size: calc(8px + 0.3vw); /* Adjust font size based on content */
    color:gray;
  }
  .sentiment-table .header-row {
    background-color: rgb(255, 240, 245); /* Light pink color for the first row */
    height:40px;
    width:1100px;
  }
  
  .sentiment-table .second-row-left {
    height:290px;
    width:80px;
  }
  .sentiment-table .second-row-middle {
    height:290px;
    width:80px;
  }
  .sentiment-table .second-row-right {
    height:290px;
    width:720px;
    text-align:left;
  }
  </style>
  
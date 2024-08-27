<template>
    <div class="GroupAndUser">
      <!-- -------------------------------------------------------------------------------------------------- -->
      <!-- CH0 头部 -->
      <!-- -------------------------------------------------------------------------------------------------- -->
      <div class="searchbar">
        <div class="icon-group">
          <div class="icon-item" @click="select('facebook')">
            <el-button
              :type="topicSelected === 'facebook' ? 'primary' : 'default'"
              icon="el-icon-user-solid"
              circle
              :style="{ fontSize: '25px' }"
            />
            <div>Facebook</div>
          </div>
          <div class="icon-item" @click="select('twitter')">
            <el-button
              :type="topicSelected === 'twitter' ? 'primary' : 'default'"
              icon="el-icon-platform-eleme"
              circle
              :style="{ fontSize: '25px' }"
            />
            <div>Twitter</div>
          </div>
          <div class="icon-item" @click="select('custom')">
            <el-button
              :type="topicSelected === 'custom' ? 'primary' : 'default'"
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
      <!-- -------------------------------------------------------------------------------------------------- -->
      <!-- CH1 敌意用户 -->
      <!-- -------------------------------------------------------------------------------------------------- -->
      <div class="HostilityUSer">
        <div class="title-with-button">
          <!-- el-switch的图标整体变大 -->
          <el-switch v-model="showCh1" style="transform: scale(1.25);" />
          <h2 style="color:rgb(2, 157, 255); font-weight: bold; margin-left:15px">敌意用户</h2>
        </div>
        <div v-show="showCh1" class="ch1">
          <!-- 分类标题 -->
          <el-row style="margin-top: 20px; width: 100%; display: flex; flex-wrap: wrap;">
            <div class="diyi-icon-group">
              <div class="icon-item" @click="select('综合')">
                <el-button
                  :type="topicSelected === '综合' ? 'primary' : 'default'"
                  icon="el-icon-star-on"
                  circle
                  :style="{ fontSize: '25px' }"
                />
                <div>综合</div>
              </div>
              <div class="icon-item" @click="select('台独')">
                <el-button
                  :type="topicSelected === '台独' ? 'primary' : 'default'"
                  icon="el-icon-star-off"
                  circle
                  :style="{ fontSize: '25px' }"
                />
                <div>台独╱统一•议题</div>
              </div>
              <div class="icon-item" @click="select('中国╱')">
                <el-button
                  :type="topicSelected === '中国╱' ? 'primary' : 'default'"
                  icon="el-icon-s-flag"
                  circle
                  :style="{ fontSize: '25px' }"
                />
                <div>中国╱中共•议题</div>
              </div>
              <div class="icon-item" @click="select('国家领袖')">
                <el-button
                  :type="topicSelected === '国家领袖' ? 'primary' : 'default'"
                  icon="el-icon-s-flag"
                  circle
                  :style="{ fontSize: '25px' }"
                />
                <div>国家领袖•议题</div>
              </div>
              <div class="icon-item" @click="select('台湾')">
                <el-button
                  :type="topicSelected === '台湾' ? 'primary' : 'default'"
                  icon="el-icon-s-flag"
                  circle
                  :style="{ fontSize: '25px' }"
                />
                <div>台湾•议题</div>
              </div>
              <div class="icon-item" @click="select('民进党')">
                <el-button
                  :type="topicSelected === '民进党' ? 'primary' : 'default'"
                  icon="el-icon-s-flag"
                  circle
                  :style="{ fontSize: '25px' }"
                />
                <div>民进党•议题</div>
              </div>
              <div class="icon-item" @click="select('赖清德')">
                <el-button
                  :type="topicSelected === '赖清德' ? 'primary' : 'default'"
                  icon="el-icon-s-flag"
                  circle
                  :style="{ fontSize: '25px' }"
                />
                <div>赖清德•议题</div>
              </div>
              <div class="icon-item" @click="select('港澳藏疆')">
                <el-button
                  :type="topicSelected === '港澳藏疆' ? 'primary' : 'default'"
                  icon="el-icon-star-on"
                  circle
                  :style="{ fontSize: '25px' }"
                />
                <div>港澳藏疆•议题</div>
              </div>
            </div>
          </el-row>
          <!-- 对应表格 -->
          <el-row style="margin-top: 0px; margin-left: 5%; width: 90%; display: flex; flex-wrap: wrap;">
            <el-table
              :data="hostileUsersList"
              border
              :header-cell-style="{
                'background-color': 'rgb(255, 240, 245)',
                color: 'rgb(105,105,105)',  
                'font-weight': 'normal', /* 取消加粗 */
                height: '20px'
              }"
              style="width: 100%;"
              class=" HostilityUSer-Table"
            >
              <el-table-column label="用户名" prop="username" :align="'center'" />
              <el-table-column label="头像" prop="avatar" :align="'center'">
                <template slot-scope="scope">
                  <img :src="scope.row.avatar" alt="空" style="width: 50px; height: 50px; object-fit: cover;">
                </template>
              </el-table-column>
              <el-table-column label="加权分" prop="bonus" :align="'center'" />
              <el-table-column label="恶意内容个数" prop="maliciousContentCount" :align="'center'" />
              <el-table-column label="被转人数" prop="retweetCount" :align="'center'" />
              <el-table-column label="被评人数" prop="reviewCount" :align="'center'" />
              <el-table-column label="被赞人数" prop="likeCount" :align="'center'" />
              <el-table-column label="恶意内容示意(部分)" prop="example">
                <template slot-scope="scope">
                  <span :style="{ color: 'gray' }">{{ scope.row.example }}</span>
                </template>
              </el-table-column>
            </el-table>
  
          </el-row>
          <div class="content-conclusion">[A] 敌意用户名册
          </div>
        </div>
      </div>
      <hr>
      <!-- -------------------------------------------------------------------------------------------------- -->
      <!-- CH2 敌意用户关联- -->
      <!-- -------------------------------------------------------------------------------------------------- -->
      <div class=" HostilityUSerAssociation">
        <div class="title-with-button">
          <el-switch v-model="showCh2" style="transform: scale(1.25);" />
          <h2 style="color:rgb(2, 157, 255); font-weight: bold; margin-left:15px">敌意用户关联</h2>
        </div>
  
        <div v-show="showCh2" class="charts-container">
  
          <div :style="{ width: '100%', display: 'flex' }">
            <!-- 图表1及标题容器 -->
            <div :style="{ display: 'flex', flexDirection: 'column', flex: '1' }">
              <div ref="attentionRelationsScatter" :style="{ height: '500px', width: '95%' }" />
              <div class="content-conclusion">[B.1] 关注关系
              </div>
            </div>
            <!-- 图表2及标题容器 -->
            <div :style="{ display: 'flex', flexDirection: 'column', flex: '1' }">
              <div ref="communicationRelationsScatter" :style="{ height: '500px', width: '95%' }" />
              <div class="content-conclusion">[B.2] 传播关系
              </div>
            </div>
          </div>
        </div>
      </div>
      <hr>
      <!-- -------------------------------------------------------------------------------------------------- -->
      <!-- CH3 敌意言论- -->
      <!-- -------------------------------------------------------------------------------------------------- -->
      <div class=" HostilitySpeech">
        <div class="title-with-button">
          <el-switch v-model="showCh3" style="transform: scale(1.25);" />
          <h2 style="color:rgb(2, 157, 255); font-weight: bold; margin-left:15px">敌意言论</h2>
        </div>
        <!-- 上面一行为搜索框 -->
        <div v-show="showCh3" class="dyllSearch">
          <!-- 上方行，包含两个搜索框 -->
          <el-row type="flex" justify="center" align="middle">
            <!-- 左侧搜索框  -->
            <el-col :span="3" :style="{ maxWidth: '33%' }">
              <el-input v-model="searchQueryLeft" placeholder="指定用户" suffix-icon="el-icon-search" />
            </el-col>
            <!-- 右侧搜索框 -->
            <el-col :span="5" :style="{ maxWidth: '66%', paddingLeft: '40px' }">
              <el-input v-model="searchQueryRight" placeholder="指定关键字" suffix-icon="el-icon-search" />
            </el-col>
          </el-row>
  
          <div class="charts-container">
            <div :style="{ width: '100%', display: 'flex' }">
              <!-- 图表1及标题容器 -->
              <div :style="{ display: 'flex', flexDirection: 'column', flex: '1' }">
                <div class="chart-or-content-div">
                  <div ref="scatterChart" :style="{ width: '100%', height: '800px', paddingLeft: '150px' }" />
                </div>
                <div class="content-conclusion" style="margin-top:-40px;padding-left:150px">[C.1] 敌意言论语义空间分布
                  <i
                    class="el-icon-question"
                    style="background-color: transparent; font-size: 20px; padding-left:10px;"
                  />
                </div>
              </div>
              <!-- 图表2及标题容器 -->
              <div
                :style="{
                  display: 'flex',
                  flexDirection: 'column',
                  flex: '1',
                  alignItems: 'stretch', // 添加这一行来确保子元素高度填满容器
                }"
              >
                <div class="chart-or-content-div" :style="{ marginTop: '60px' }">
                  <el-table
                    :data="hostileSpeechData"
                    border
                    style="width: 60%; max-height: 680px;"
                    :show-header="false"
                    class="custom-table"
                  >
                    <el-table-column prop="key" width="45" />
                    <el-table-column prop="value">
                      <template slot-scope="scope">
                        <span class="cell-with-space">{{ scope.row.value }}</span>
                      </template>
                    </el-table-column>
                  </el-table>
                </div>
                <div class="content-conclusion" style="width:60%">[C.2] 敌意言论
                </div>
              </div>
            </div>
          </div>
  
        </div>
      </div>
      <hr>
      <!-- -------------------------------------------------------------------------------------------------- -->
      <!-- CH4 群组发现- -->
      <!-- -------------------------------------------------------------------------------------------------- -->
      <div class="GroupDiscovery">
        <div class="title-with-button">
          <el-switch v-model="showCh4" style="transform: scale(1.25);" />
          <h2 style="color:rgb(2, 157, 255); font-weight: bold; margin-left:15px">群组发现</h2>
        </div>
        <div v-show="showCh4" class="ch4">
          <!-- 上半部分的标签选择器 -->
          <div style="text-align: center; ">
            <el-radio-group v-model="groupDiscoveryRadio" class="radio-group-custom">
              <el-radio-button
                v-for="(item, index) in groupDiscoveryRadioList"
                :key="index"
                :label="item"
                class="radio-button-text"
              />
            </el-radio-group>
          </div>
          <!-- 下半部分 -->
          <div class="charts-container" :style="{ height: '550px' }"> <!-- 添加高度到charts-container -->
            <div :style="{ width: '100%', display: 'flex', height: '100%' }"> <!-- 确保这个div填充charts-container的高度 -->
              <!-- 左边图表部分 -->
              <div :style="{ display: 'flex', flexDirection: 'column', flex: '1', height: '100%' }"> <!-- 设置高度为100% -->
                <div class="chart-or-content-div" :style="{ width: '100%', height: '100%', paddingLeft: '150px' }">
                  <div ref="radioChatter" :style="{ height: '100%', width: 'calc(100% - 150px)' }" />
                  <!-- 调整宽度以适应padding，并设置高度为100% -->
                </div>
              </div>
              <!-- 右边表格部分 -->
              <div
                :style="{
                  display: 'flex',
                  flexDirection: 'column',
                  flex: '1',
                  alignItems: 'stretch',
                  height: '100%', // 确保右侧内容也填充高度
                }"
              >
                <div class="chart-or-content-div" :style="{ marginTop: '0px', height: 'calc(100% - 60px)' }">
                  <!-- 减去marginTop的高度 -->
                  <el-table :data="displayData" style="width: 70%" :cell-style="initTableCellStyle">
                    <el-table-column v-for="(item, index) in displayData[0]" :key="index" align="center">
                      <template slot-scope="scope">
                        <span>{{ scope.row[index] }}</span>
                      </template>
                    </el-table-column>
                  </el-table>
                </div>
              </div>
            </div>
          </div>
          <div class="content-conclusion" style="width:100%">[D] 群组发现与统计特征
          </div>
        </div>
      </div>
      <hr>
      <!-- -------------------------------------------------------------------------------------------------- -->
      <!-- CH5 群组语义- -->
      <!-- -------------------------------------------------------------------------------------------------- -->
      <div class="GroupSemantics">
        <div class="title-with-button">
          <el-switch v-model="showCh5" style="transform: scale(1.25);" />
          <h2 style="color:rgb(2, 157, 255); font-weight: bold; margin-left:15px">群组语义</h2>
        </div>
  
        <div v-show="showCh5" class="container" style="display: flex;">
  
          <!-- 左侧部分 -->
          <div class="left-side" style="flex: 1;">
            <!-- 上半部分：两行div -->
            <div class="upper-part" style="display: flex; flex-direction: column;">
              <div class="row">
                <div class="content-conclusion" style="width: 120%;">[E] 主要话题短语</div>
              </div>
              <div class="row">
                <div class="content-conclusion" style="width: 117%;">[F] 主要观点</div>
              </div>
            </div>
            <!-- 下半部分：ECharts和弦图 -->
            <div ref="groupChordContainer" style="width: 100%; height: 400px;" />
          </div>
          <!-- 右侧部分 -->
          <div class="right-side" style="flex: 1; display: flex; flex-direction: column;">
            <!-- 自定义的表头div -->
            <div
              style="
                  background-color: rgb(255, 240, 245);
                  padding: 10px;
                  text-align: center;
                  width: 680px;
                  /* font-weight: bold; */
                  border-bottom: 1px solid #ddd;
              "
            >
              话题╱观点与对应原文
            </div>
  
            <!-- 不含表头的el-table -->
            <el-table :data="mainPointsAndOriginalText" stripe :show-header="false" style="width: 100%;color: gray">
              <el-table-column prop="point" width="80" />
              <el-table-column prop="text" width="600" />
            </el-table>
          </div>
        </div>
      </div>
      <hr>
      <!-- -------------------------------------------------------------------------------------------------- -->
      <!-- CH6 群组参与舆情   TODO：残次品，需要大修改- -->
      <!-- -------------------------------------------------------------------------------------------------- -->
      <div class="GroupPublicSentiment">
        <div class="title-with-button">
          <el-switch v-model="showCh6" style="transform: scale(1.25);" />
          <h2 style="color:rgb(2, 157, 255); font-weight: bold; margin-left:15px">群组参与舆情</h2>
        </div>
        <div
          v-show="showCh6"
          class="user-panel"
          style="display: flex; flex-direction: column; align-items: center; height: 100%;"
        >
          <!-- 头像区域 -->
          <div style="display: flex; justify-content: center; margin-bottom: 20px;">
            <el-avatar
              v-for="(user, index) in BigVImgUrlList"
              :key="index"
              :src="user"
              :size="65"
              :style="index !== BigVImgUrlList.length - 1 ? { 'margin-right': '10px' } : {}"
            />
          </div>
  
          <!-- 中间内容区域 -->
          <div class="middle-section" style="text-align: left; margin-bottom: 20px;">
            <div class="content-conclusion" style="text-align: left">[E] 群组大V（via出度）用户</div>
            <div class=" content-conclusion" style="text-align: left">[F] 群组参与舆情事件</div>
          </div>
  
          <!-- 底部区域（待定） -->
          <div class="custom-timeline">
            <div
              v-for="(item, index) in groupParticipationTimeline"
              :key="index"
              style="display: flex; align-items: center; position: relative; margin-bottom: 100px;"
            > <!-- 增大节点间隔 -->
              <!-- 时间轴连线（仅非首个节点显示） -->
              <div
                v-if="index !== 0"
                style="border-left: 10px solid rgb(0,157,255); height: 220px; position: absolute; left: 46%; transform: translateX(-70%);margin-bottom: 260px;"
              />
  
              <!-- 节点内容包裹 -->
              <div style="display: flex; align-items: center;">
                <!-- 左侧图片区域 -->
                <div style="display: flex; flex-wrap: wrap;">
                  <div
                    v-for="(img, imgIndex) in item.images"
                    :key="imgIndex"
                    style="position: relative; margin: 20px; margin-top: 50px;"
                  >
                    <img
                      :src="img.src"
                      :alt="img.title"
                      style="width: 80px; height: 80px; object-fit: cover; border-radius: 5px;"
                    >
                    <div
                      style="position: absolute; bottom: 85px; left: 0; width: 100%; text-align: center; color: gray; padding: 3px 0;  
                  display: -webkit-box; /* 将元素设为伸缩盒模型显示 */
                  -webkit-line-clamp: 2; /* 限制在一个块元素显示的文本的行数 */
                  -webkit-box-orient: vertical; /* 设置或检索伸缩盒对象的子元素的排列方式 */ 
                  overflow: hidden; /* 隐藏超出的内容 */
                  text-overflow: ellipsis;"
                    >
                      {{ img.title }}</div>
                  </div>
                </div>
  
                <!-- 中间日期圆圈 -->
                <div
                  style="width: 55px; height: 55px; border-radius: 50%; background-color: rgb(0,157,255); display: flex; justify-content: center; align-items: center; color: white; font-weight: bold;"
                >
                  {{ item.date }}</div>
  
                <!-- 右侧文本区域 -->
                <div style="display: flex; flex-direction: column;">
                  <div
                    v-for="(text, textIndex) in item.texts"
                    :key="textIndex"
                    style="margin-bottom: 15px; list-style-type: none; margin-left: 20px;color: gray;"
                  >
                    <i class="el-icon-circle-check" style="margin-right: 5px;" />{{ text }}
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <hr>
    </div>
  </template>
  
  <script>
  import axios from 'axios'
  import * as echarts from 'echarts'
  import GraphData from '@/assets/QunZu/hexian-miserables.json'
  import ForceData from '@/assets/QunZu/force-miserables.json'
  
  export default {
    name: 'GroupAnalysis',
    data() {
      return {
        // 全局变量
        chartInstance: null, // 大部分图表实例对象
        showCh1: true,  
        showCh2: true,  
        showCh3: true,  
        showCh4: true,  
        showCh5: true,  
        showCh6: true,  
        // ===============================
        // CH0 顶栏
        startDate: '',
        endDate: '',
        // ===============================
        // CH1 敌意用户
        topicSelected: null,
        hostileUsersList: [],
        // ===============================
        // Ch2 敌意用户间关联
        attentionRelations: {},
        communicationRelations: {},
        searchQueryLeft: '',
        searchQueryRight: '',
        // ===============================
        // Ch3 敌意言论
        hostileSpeechDistribution: [],
        hostileSpeechData: [],
        // ===============================
        // CH4 群组发现
        groupDiscoveryRadio: '基于社交关系的群组发现', // 初始化选中的标签
        groupDiscoveryRadioList: ['基于社交关系的群组发现', '基于（共）互动行为的群组发现', '基于相似语义的群组发现'],
        // 语义化的数据存储
        groupDiscoveryData: {},
        groupStatisticTableHeader: [],
        groupStatisticTable: [],
        // ===============================
        // CH5 群组语义
        groupChordData: {},
        mainPointsAndOriginalText: [],
        // ===============================
        // CH6 群组参与舆情
        BigVImgUrlList: [],
        groupParticipationTimeline: []
      }
    },
    computed: {
      displayData() {
        // 首先构造一个包含表头信息的数组
        const headerRow = [' '].concat(this.groupStatisticTableHeader);
        // 然后将groupStatisticTable数据整合到一个二维数组中，首行是表头信息
        const dataWithHeader = [headerRow];
        this.groupStatisticTable.forEach(metric => {
          dataWithHeader.push([metric.metric].concat(metric.values));
        });
  
        return dataWithHeader;
      }
    },
    watch: {
      // ===============================
      // CH4 群组发现
      groupDiscoveryRadio(newVal) {
        this.renderChartBasedOnSelection(newVal);
      }
    },
    methods: {
      // ------------------------------------------------------------
      // 图表数据请求
      initChordChart() {
        axios.get('http://localhost:8080/group-chord-data')
          .then(response => {
            // 使用后端返回的数据
            this.groupChordData = response.data
          })
          .catch(error => {
            console.error('Error fetching group chord data from backend, using local data instead:', error)
            // 使用本地数据
            this.groupChordData = GraphData
          })
          .finally(() => {
            // 群组语义----和弦关系图 
            this.chartInstance = echarts.init(this.$refs.groupChordContainer);
            const option = {
              title: {
                top: 'bottom',
                left: 'right'
              },
              tooltip: {},
              animationDuration: 1500,
              animationEasingUpdate: 'quinticInOut',
              series: [
                {
                  name: 'Connections',
                  type: 'graph',
                  layout: 'circular',
                  circular: {
                    rotateLabel: true
                  },
                  data: this.groupChordData.nodes,
                  links: this.groupChordData.links,
                  categories: this.groupChordData.categories,
                  roam: true,
                  label: {
                    position: 'right',
                    formatter: '{b}'
                  },
                  lineStyle: {
                    color: 'source',
                    curveness: 0.3,
                    width: 1 // 保持线的宽度，确保其清晰度
                  },
                  emphasis: {
                    focus: 'adjacency',
                    lineStyle: {
                      width: 10 // 鼠标悬停时线的宽度
                    }
                  }
                }
              ]
            }
            this.chartInstance.setOption(option)
          })
      },
      initNetChart() {
        axios.get('http://localhost:8080/hostile-association-net-data')
          .then(response => {
            // 使用后端返回的数据
            this.attentionRelations = response.data.attentionRelations
            this.hostileSactterData = response.data.hostileSactterData
          })
          .catch(error => {
            console.error('Error fetching hostile association net data from backend, using local data instead:', error)
            // 使用本地数据
            this.attentionRelations = {
              nodes: Array.from({ length: 100 }, (_, i) => ({ name: `Node ${i + 1}`, category: i % 9 })),
              links: Array.from({ length: 198 }, (_, i) => ({
                source: Math.floor(Math.random() * 100),
                target: Math.floor(Math.random() * 100),
                value: Math.floor(Math.random() * 5) + 1
              })).filter((link, index, self) =>
                index === self.findIndex((t) => (
                  t.source === link.source && t.target === link.target ||
                  t.source === link.target && t.target === link.source
                ))
              )
            };
            this.communicationRelations = {
              nodes: Array.from({ length: 100 }, (_, i) => ({ name: `Node ${i + 1}`, category: i % 9 })),
              links: Array.from({ length: 198 }, (_, i) => ({
                source: Math.floor(Math.random() * 100),
                target: Math.floor(Math.random() * 100),
                value: Math.floor(Math.random() * 5) + 1
              })).filter((link, index, self) =>
                index === self.findIndex((t) => (
                  t.source === link.source && t.target === link.target ||
                  t.source === link.target && t.target === link.source
                ))
              )
            };
          })
          .finally(() => {
            const attentionRelationsScatter = echarts.init(this.$refs.attentionRelationsScatter);
            const communicationRelationsScatter = echarts.init(this.$refs.communicationRelationsScatter);
            // 定义配置项
            this.createOptionForHostileAssociation(this.attentionRelations, 'attentionRelationsOptions');
            this.createOptionForHostileAssociation(this.communicationRelations, 'communicationRelationsOptions');
            // 使用配置项显示图表
            attentionRelationsScatter.setOption(this.attentionRelationsOptions);
            communicationRelationsScatter.setOption(this.communicationRelationsOptions);
          })
      },
      createOptionForHostileAssociation(data, optionName) {
        this[optionName] = {
          series: {
            type: 'graph',
            layout: 'force',
            symbolSize: 15, // 减小节点大小
            edgeSymbol: ['none', 'arrow'], // 可以去掉起点的标记使线条更简洁，或者根据需要调整
            edgeSymbolSize: [0, 5], // 调整箭头大小
            data: data.nodes.map(node => ({ ...node, itemStyle: { color: 'rgb(126, 191, 221)' }})),
            links: data.links,
            force: {
              repulsion: 150, // 增大斥力，让节点更分散
              gravity: 0.1, // 减小重力，允许节点分布得更开
              edgeLength: [15, 50]// 缩短连线长度
            },
            roam: true, // 允许用户缩放和平移
            zoom: 0.6 // 初始缩放比例，可以根据实际情况调整
          }
        };
      },
      initScatterChart() {
        axios.get('http://localhost:8080/hostile-speech-scatter-data')
          .then(response => {
            // 使用后端返回的数据
            this.hostileSpeechDistribution = response.data
          })
          .catch(error => {
            console.error('Error fetching group chord data from backend, using local data instead:', error)
            // 使用本地数据
            for (let i = 0; i < 400; i++) {
              this.hostileSpeechDistribution.push([
                Math.random() * 800 - 50, // x轴随机值
                Math.random() * 600 - 50 // y轴随机值
              ]);
            }
          })
          .finally(() => {
            // 群组语义----和弦关系图 
            this.chartInstance = echarts.init(this.$refs.scatterChart);
            const option = {
              xAxis: {
                show: true, // 保持显示以渲染网格线
                type: 'value',
                splitLine: {
                  lineStyle: {
                    type: 'dashed',
                    color: '#ccc'
                  }
                },
                axisLabel: { show: false }, // 隐藏x轴刻度标签
                axisLine: { show: false } // 隐藏x轴线
              },
              yAxis: {
                show: true, // 保持显示以渲染网格线
                type: 'value',
                splitLine: {
                  lineStyle: {
                    type: 'dashed',
                    color: '#ccc'
                  }
                },
                axisLabel: { show: false }, // 隐藏y轴刻度标签
                axisLine: { show: false } // 隐藏y轴线
              },
              legend: { show: false },
              series: [
                {
                  type: 'scatter',
                  symbolSize: 10,
                  itemStyle: {
                    color: 'rgb(125, 140, 208)'
                  },
                  data: this.hostileSpeechDistribution // 从data中引用数据
                }
              ]
            };
            this.chartInstance.setOption(option)
          })
      },
      // ===============================
      // CH4 群组发现
      renderChartBasedOnSelection(selectedLabel) {
        switch (selectedLabel) {
          case '基于社交关系的群组发现':
            this.initSocialForceLayoutChart();
            break;
          case '基于（共）互动行为的群组发现':
            this.initInteractionForceLayoutChart();
            break;
          case '基于相似语义的群组发现':
            this.initSemanticForceLayoutChart();
            break;
          default:
            console.warn('未识别的标签选项');
        }
      },
      // 001 基于社交关系的群组发现
      initSocialForceLayoutChart() {
        axios.get('http://localhost:8080/Social-Relationships-data')
          .then(response => {
            // 使用后端返回的数据
            this.groupDiscoveryData = response.data
          })
          .catch(error => {
            console.error('Error fetching group chord data from backend, using local data instead:', error)
            // 使用本地数据
            this.groupDiscoveryData = ForceData
          })
          .finally(() => {
            this.chartInstance = echarts.init(this.$refs.radioChatter);
            this.groupDiscoveryData.nodes.forEach(function(node) {
              node.symbolSize = 5;
            });
  
            // 设置初始化视角和中心点
            const initialZoom = 2; // 这是一个示例缩放因子，可以根据需要调整
            const centerX = this.groupDiscoveryData.nodes.reduce((acc, cur) => acc + cur.x, 0) / this.groupDiscoveryData.nodes.length; // 计算所有节点x坐标的平均值作为中心点x坐标
            const centerY = this.groupDiscoveryData.nodes.reduce((acc, cur) => acc + cur.y, 0) / this.groupDiscoveryData.nodes.length; // 同理计算y坐标的平均值
  
            const option = {
              title: {
                top: 'bottom',
                left: 'right'
              },
              tooltip: {},
              series: [
                {
                  type: 'graph',
                  layout: 'force',
                  data: this.groupDiscoveryData.nodes,
                  links: this.groupDiscoveryData.links,
                  categories: this.groupDiscoveryData.categories,
                  roam: true,
                  label: {
                    position: 'right'
                  },
                  initialLayout: { // 新增此配置项来初始化布局的视角和中心点
                    zoom: initialZoom,
                    x: centerX,
                    y: centerY
                  },
                  force: {
                    repulsion: 200
                  }
                }
              ]
            }
            this.chartInstance.setOption(option)
          })
      },
      // 002 基于（共）互动行为的群组发现
      initInteractionForceLayoutChart() {
        axios.get('http://localhost:8080/Interaction-data')
          .then(response => {
            // 使用后端返回的数据
            this.groupDiscoveryData = response.data
          })
          .catch(error => {
            console.error('Error fetching group chord data from backend, using local data instead:', error)
            // 使用本地数据
            this.groupDiscoveryData = ForceData
          })
          .finally(() => {
            this.chartInstance = echarts.init(this.$refs.radioChatter);
            this.groupDiscoveryData.nodes.forEach(function(node) {
              node.symbolSize = 5;
            });
            const option = {
              title: {
                top: 'bottom',
                left: 'right'
              },
              tooltip: {},
              series: [
                {
                  type: 'graph',
                  layout: 'force',
                  data: this.groupDiscoveryData.nodes,
                  links: this.groupDiscoveryData.links,
                  categories: this.groupDiscoveryData.categories,
                  roam: true,
                  label: {
                    position: 'right'
                  },
                  force: {
                    repulsion: 200
                  }
                }
              ]
            }
            this.chartInstance.setOption(option)
          })
      },
      // 003 基于相似语义的群组发现
      initSemanticForceLayoutChart() {
        axios.get('http://localhost:8080/Semantic-data')
          .then(response => {
            // 使用后端返回的数据
            this.groupDiscoveryData = response.data
          })
          .catch(error => {
            console.error('Error fetching group chord data from backend, using local data instead:', error)
            // 使用本地数据
            this.groupDiscoveryData = ForceData
          })
          .finally(() => {
            this.chartInstance = echarts.init(this.$refs.radioChatter);
            this.groupDiscoveryData.nodes.forEach(function(node) {
              node.symbolSize = 5;
            });
            const option = {
              title: {
                top: 'bottom',
                left: 'right'
              },
              tooltip: {},
              series: [
                {
                  type: 'graph',
                  layout: 'force',
                  data: this.groupDiscoveryData.nodes,
                  links: this.groupDiscoveryData.links,
                  categories: this.groupDiscoveryData.categories,
                  roam: true,
                  label: {
                    position: 'right'
                  },
                  force: {
                    repulsion: 200
                  }
                }
              ]
            }
            this.chartInstance.setOption(option)
          })
      },
      // 004 添加一个方法来定义单元格样式，确保所有边框都能显示
      initTableCellStyle({ row, rowIndex, column, columnIndex }) {
        const style = {
          border: '1px solid #ccc',
          padding: '8px',
          textAlign: 'center'
        };
        if (rowIndex !== 0 && columnIndex !== 0) {
          style.color = 'gray'
        }
        // 为最后一行的每个单元格添加底部边框
        if (rowIndex === this.displayData.length - 1) {
          style.borderBottom = '2px solid #ccc'; // 可以适当加粗以确保可见
        }
        // 为最右侧列的单元格添加右侧边框
        if (columnIndex === this.displayData[0].length - 1) {
          style.borderRight = '2px solid #ccc'; // 同样，可适当加粗
        }
  
        return style;
      },
      // ------------------------------------------------------------
      // 表格数据请求
      fetchHostileUsersList() {
        axios.get('http://localhost:8080/hostile-BigVImgUrlList-list')
          .then(response => {
            this.hostileUsersList = response.data
          })
          .catch(error => {
            console.error('Error fetching hostile BigVImgUrlList list from backend:', error)
            // Fallback to local data
            this.hostileUsersList = [
              {
                username: 'rapplerdotcom',
                avatar: require('@/assets/QunZu/group_users_images/demo.jpg'),
                bonus: '',
                maliciousContentCount: '',
                retweetCount: '',
                reviewCount: '',
                likeCount: '',
                example: '当我们在西菲律宾海（WPS）专属经济区的自己的水域和土地上受到欺凌和占领时，全体菲律宾人民必须为了祖国团结起来...'
              },
              {
                username: 'tvpatrol',
                avatar: '',
                bonus: '',
                maliciousContentCount: '',
                retweetCount: '',
                reviewCount: '',
                likeCount: '',
                example: 'OCTA Research 的一项调查显示，七成菲律宾人认为，总统小费迪南德·马科斯的政府应该通过军事行动和外交手段维护该国在西菲律宾海 (WPS) 的领土权利。'
              },
              {
                username: 'abscbnnews',
                avatar: require('@/assets/QunZu/group_users_images/demo.jpg'),
                bonus: '',
                maliciousContentCount: '',
                retweetCount: '',
                reviewCount: '',
                likeCount: '',
                example: 'OCTA Research 的一项调查显示，七成菲律宾人认为，总统小费迪南德·马科斯的政府应该通过军事行动和外交手段维护该国在西菲律宾海 (WPS) 的领土权利。'
              },
              {
                username: 'jaytaryela',
                avatar: require('@/assets/QunZu/group_users_images/demo.jpg'),
                bonus: '',
                maliciousContentCount: '',
                retweetCount: '',
                reviewCount: '',
                likeCount: '',
                example: 'OCTA Research 的一项调查显示，七成菲律宾人认为，总统小费迪南德·马科斯的政府应该通过军事行动和外交手段维护该国在西菲律宾海 (WPS) 的领土权利。'
              }
            ]
          }
          )
      },
      fetchHostileSpeechData() {
        axios.get('http://localhost:8080/hostile-speech-data')
          .then(response => {
            this.hostileSpeechData = response.data
          })
          .catch(error => {
            console.error('Error fetching hostile speech data from backend:', error)
            // Fallback to local data
            this.hostileSpeechData = [
              { key: '1', value: '中国在西菲律宾海的强硬行动和虚假主张' },
              { key: '2', value: '菲律宾人呼吁团结，主张捍卫在西菲律宾海的主权' },
              { key: '3', value: ' 当我们在西菲律宾海（WPS）专属经济区的自己的水域和土地上受到欺凌和占领时，全体菲律宾人民必须为了祖国团结起来...' },
              { key: '4', value: ' 中国海警在西菲律宾海骚扰菲律宾船只' }
            ]
            this.hostileSpeechData = [...this.hostileSpeechData, ...Array.from({ length: 10 }, () => ({ key: '', value: '' }))];
          }
          )
      },
      // ===============================
      // CH4 群组发现
      fetchGroupStatisticTable() {
        axios.get('http://localhost:8080/group-statistic-table-data')
          .then(response => {
            this.groupStatisticTable = response.data
          })
          .catch(error => {
            console.error('Error fetching hostile speech data from backend:', error)
            // Fallback to local data
            this.groupStatisticTable = [
              { metric: '节点数', values: [2941, 2455, 2290] },
              { metric: '连边数', values: [4785, 7563, 15825] },
              { metric: '密度', values: [0.0010, 0.0023, 0.0048] },
              { metric: '直径', values: [11, 10, 8] },
              { metric: '平均距离', values: [4.0965, 3.7000, 3.1239] },
              { metric: '平均度', values: [2.9473, 5.6293, 11.0943] },
              { metric: '平均聚类系数', values: [0.0105, 0, 0.0010] },
              { metric: '活跃度/原发数', values: [216, 8252, 577] },
              { metric: '活跃度/转评赞次数', values: [16926, 101137, 23566] },
              { metric: '影响力/被转发人数', values: [9496, 28272, 8484] },
              { metric: '影响力/粉丝人数', values: [4841, 5277, 3953] }
            ]
            this.groupStatisticTableHeader = ['Rank1社团', 'Rank2社团', 'Rank3社团']
          }
          )
      },
      fetchMainPointsAndOriginalText() {
        axios.get('http://localhost:8080/main-point-data')
          .then(response => {
            this.mainPointsAndOriginalText = response.data
          })
          .catch(error => {
            console.error('Error fetching hostile speech data from backend:', error)
            // Fallback to local data
            this.mainPointsAndOriginalText = [
              { point: '观点-6', text: '菲律宾人呼吁团结捍卫在西菲律宾海的主权' },
              {
                point: '原文（部分）',
                text: `当我们在西菲律宾海（WPS）专属经济区的自己的水域和土地上受到欺凌和占领时，全体菲律宾人民必须为了祖国团结起来\n
                              ---\n
                              当我们在西菲律宾海（WPS）专属经济区的自己的水域和土地上受到欺凌和占领时，全体菲律宾人民必须为了祖国团结起来\n
                              ---\n
                              我们是菲律宾青年。我们停在了西菲律宾海\n
                              ---\n
                              我们是菲律宾青年。我们停在了西菲律宾海\n
                              ---\n
                              OCTA Research 的一项调查显示，七成菲律宾人认为，总统小费迪南德·马科斯的政府应该通过军事行动和外交手段维护该国在西菲律宾海 (WPS) 的领土权利。\n
                              ---\n
                              OCTA Research 的一项调查显示，七成菲律宾人认为，总统小费迪南德·马科斯的政府应该通过军事行动和外交手段维护该国在西菲律宾海 (WPS) 的领土权利。\n
                              ---\n
                              当我们在西菲律宾海（WPS）专属经济区的自己的水域和土地上受到欺凌和占领时，全体菲律宾人民必须为了祖国团结起来---我们是菲律宾青年。我们停在了西菲律宾海
                              ---\n
                              OCTA Research 的一项调查显示，七成菲律宾人认为，总统小费迪南德·马科斯的政府应该通过军事行动和外交手段维护该国在西菲律宾海(WPS) 的领土权利。\n
                              ---\n
                              OCTA Research 的一项调查显示，七成菲律宾人认为，总统小费迪南德·马科斯的政府应该通过军事行动和外交手段维护该国在西菲律宾海(WPS) 的领土权利。\n
                              ---\n
                              当我们在西菲律宾海（WPS）专属经济区的自己的水域和土地上受到欺凌和占领时，全体菲律宾人民必须为了祖国团结起来-- - 我们是菲律宾青年。我们停在了西菲律宾海`
              }
            ]
          }
          )
      },
      fetchBigVImgUrlList() {
        axios.get('http://localhost:8080/big-v-url-data')
          .then(response => {
            this.BigVImgUrlList = response.data
          })
          .catch(error => {
            console.error('Error fetching big v url data from backend:', error)
            // Fallback to local data
            this.BigVImgUrlList = [
              require('@/assets/QunZu/group_users_images/demo.jpg'),
              require('@/assets/QunZu/group_users_images/cnn.png'),
              require('@/assets/QunZu/group_users_images/demo.jpg'),
              require('@/assets/QunZu/group_users_images/woman.png'),
              require('@/assets/QunZu/group_users_images/demo.jpg'),
              require('@/assets/QunZu/group_users_images/demo.jpg'),
              require('@/assets/QunZu/group_users_images/man.png'),
              require('@/assets/QunZu/group_users_images/demo.jpg'),
              require('@/assets/QunZu/group_users_images/demo.jpg'),
              require('@/assets/QunZu/group_users_images/demo.jpg'),
              require('@/assets/QunZu/group_users_images/leader.png'),
              require('@/assets/QunZu/group_users_images/demo.jpg'),
              require('@/assets/QunZu/group_users_images/demo.jpg')
            ]
          }
          )
      },
      fetchGroupParticipationTimeline() {
        axios.get('http://localhost:8080/group-participation-timeline-data')
          .then(response => {
            this.groupParticipationTimeline = response.data
          })
          .catch(error => {
            console.error('Error fetching group participation timeline data from backend:', error)
            // Fallback to local data
            this.groupParticipationTimeline = [
              {
                date: 'May.',
                images: [{ src: require('@/assets/QunZu/group_users_images/LQD.jpg'), title: '中国快艇越界' },
                { src: require('@/assets/QunZu/group_users_images/LQD.jpg'), title: '赖清德入选《时代》百大人物' },
                { src: require('@/assets/QunZu/group_users_images/LQD.jpg'), title: '中国快艇越界' }
                ],
                texts: [` 中共对台湾武力恐吓
                      台湾用行动抵御外部势力守护民主`,
                  `给中国渔民教训
                      国军终于硬气了一次`]
              },
              {
                date: 'Apr.',
                images: [{ src: require('@/assets/QunZu/group_users_images/LQD.jpg'), title: '中国快艇越界' },
                { src: require('@/assets/QunZu/group_users_images/LQD.jpg'), title: '赖清德入选《时代》百大人物' },
                { src: require('@/assets/QunZu/group_users_images/LQD.jpg'), title: '中国快艇越界' }
                ],
                texts: [` 中共对台湾武力恐吓
                      台湾用行动抵御外部势力守护民主`,
                  `给中国渔民教训
                      国军终于硬气了一次`]
              },
              {
                date: 'Feb.',
                images: [{ src: require('@/assets/QunZu/group_users_images/LQD.jpg'), title: '中国快艇越界' },
                { src: require('@/assets/QunZu/group_users_images/LQD.jpg'), title: '赖清德入选《时代》百大人物' },
                { src: require('@/assets/QunZu/group_users_images/LQD.jpg'), title: '中国快艇越界' }
                ],
                texts: [` 马英九有意承担两岸和平使者
                      马习会没有实质性内容`,
                  ` 赖清德备受美国认可
                      台海情势左右世界民主政治的发展`,
                  ` 中国继续就快艇事件威胁台湾`]
              },
              {
                date: 'Jan.',
                images: [{ src: require('@/assets/QunZu/group_users_images/LQD.jpg'), title: '中国快艇越界' },
                { src: require('@/assets/QunZu/group_users_images/LQD.jpg'), title: '赖清德入选《时代》百大人物' },
                { src: require('@/assets/QunZu/group_users_images/LQD.jpg'), title: '中国快艇越界' }
                ],
                texts: [` 马英九有意承担两岸和平使者
                      马习会没有实质性内容`,
                  ` 赖清德备受美国认可
                      台海情势左右世界民主政治的发展`,
                  ` 中国继续就快艇事件威胁台湾`]
              }
            ]
          }
          )
      }
    },
    async mounted() {
      // 敌意用户--表格数据
      await this.fetchHostileUsersList()
      // 敌意言论--表格数据
      await this.fetchHostileSpeechData()
      // 群组发现
      await this.fetchGroupStatisticTable()
      // 群组语义--表格数据
      await this.fetchMainPointsAndOriginalText()
      // 群组参与舆情--头像数据
      await this.fetchBigVImgUrlList()
      // 群组参与舆情--时间轴数据
      await this.fetchGroupParticipationTimeline()
      await this.$nextTick()
      // -------------------------------------------------
      // 敌意用户关联--网状关系图
      this.initNetChart()
      // 敌意言论--散点图
      this.initScatterChart()
      // 群组语义--和弦关系图
      this.initChordChart()
      // 
      this.initSocialForceLayoutChart() 
    }
  }
  </script>
  
  <style scoped>
  hr {
    margin-top: 20px;
    height: 1px;
    /* Increase the height of the hr */
    background-color: skyblue;
  }
  
  /*  导航栏样式 */
  .title-with-button {
    display: flex;
    align-items: center;
    margin-left: 10px;
  }
  
  .searchbar {
    margin-top: 20px;
    padding: 5px;
    display: flex;
    justify-content: space-between;
    align-items: center;
  
  }
  
  .searchbar .el-button {
    border: none;
    padding: 0;
  }
  
  .icon-group {
    /* 居中对齐 */
    display: flex;
    gap: 100px;
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
  
  
  /* 敌意用户 */
  .diyi-icon-group {
    /* 居中对齐 */
    display: flex;
    gap: 140px;
    margin-left: 100px;
    margin-bottom: 30px;
  }
  
  /* 敌意用户关联 */
  .charts-container {
    /* 添加额外样式以保证内外部容器协调 */
    display: flex;
    flex-direction: column;
  }
  
  .cell-with-space::before {
    content: '\20 \20 \20 \20';
    /* 两个全角空格（\u3000\u3000 的另一种写法），或者根据需要调整 */
    white-space: pre;
    /* 保留空格和换行 */
  }
  
  .custom-table td,
  .custom-table th.is-leaf {
    padding: 0 !important;
  }
  
  .custom-timeline {
    display: flex;
    flex-direction: column;
    align-items: center;
  }
  
  .timeline-node {
    display: flex;
    align-items: center;
  }
  
  .timeline-line {
    flex-grow: 1;
    border-left: 2px solid #ccc;
    margin: 0 20px;
  }
  
  .node-content {
    display: flex;
    align-items: center;
    min-width: 80%;
  }
  
  .image-section {
    display: flex;
    flex-wrap: wrap;
    margin-right: 20px;
  }
  
  .image-wrapper {
    position: relative;
    margin: 5px;
  }
  
  .image {
    width: 50px;
    height: 50px;
    object-fit: cover;
    border-radius: 5px;
  }
  
  .image-title {
    position: absolute;
    bottom: 0;
    left: 0;
    width: 100%;
    text-align: center;
    background-color: rgba(0, 0, 0, 0.5);
    color: #fff;
    padding: 3px 0;
  }
  
  .circle-section {
    width: 50px;
    height: 50px;
    border-radius: 50%;
    display: flex;
    justify-content: center;
    align-items: center;
    margin: 0 20px;
  }
  
  .text-section {
    display: flex;
    flex-direction: column;
  }
  
  .text-item {
    margin-bottom: 10px;
    list-style-type: none;
  }
  
  /* CH4 */
  .gray-text {
    color: gray;
  }
  
  /*  每部分结尾的样式  */
  .content-conclusion {
    text-align: center;
    font-family: "Microsoft YaHei", serif;
    margin-bottom: 20px;
    font-weight: bold;
    color: deepskyblue;
    margin-top: 20px;
  }
  </style>
  
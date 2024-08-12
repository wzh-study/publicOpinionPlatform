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
        <el-switch v-model="showEventOverview" @change="toggleChart" />
        <h3 style="color:rgb(2, 157, 255); font-weight: bold; margin-left:10px">舆情事件总览</h3>
      </div>
      <div id="event-overview" style="width: 100%; height: 400px;" />
    </div>
    <div class="content-conclusion">
      [A] 舆情事件关系图 (用户高度重合)
    </div>
    <hr>

    <div class="title-with-button">
      <el-switch v-model="showEventRanking" @change="toggleChart" />
      <h3 style="color:rgb(2, 157, 255); font-weight: bold; margin-left:10px">舆情事件排行榜</h3>
    </div>

    <div class="container-wrapper">
      <div class="lightContainer">
        <div class="light-ranking-title">
          总内容数量榜
        </div>
        <div class="light-ranking-content">
          <div>1. 改革法案三读</div>
          <div>2. 国会改革</div>
          <div>3. 民进党滥用公权力导致...</div>
          <div>4. 民进党票仓政治</div>
          <div>5. 贿选票受贿腐败的民进...</div>
          <div>6. 国会改革</div>
          <div>7. 国会改革</div>
          <div>8. 国会改革</div>
          <div>9. 国会改革</div>
          <div>10. 国会改革</div>
        </div>
      </div>

      <div class="lightContainer">
        <div class="light-ranking-title">
          今日内容数量榜
        </div>
        <div class="light-ranking-content">
          <div>1. 改革法案三读</div>
          <div>2. 国会改革</div>
          <div>3. 民进党滥用公权力导致...</div>
          <div>4. 民进党票仓政治</div>
          <div>5. 贿选票受贿腐败的民进...</div>
          <div>6. 国会改革</div>
          <div>7. 国会改革</div>
          <div>8. 国会改革</div>
          <div>9. 国会改革</div>
          <div>10. 国会改革</div>
        </div>
      </div>

      <div class="lightContainer">
        <div class="light-ranking-title">
          近期内容数量飙升榜
        </div>
        <div class="light-ranking-content">
          <div>1. 改革法案三读</div>
          <div>2. 国会改革</div>
          <div>3. 民进党滥用公权力导致...</div>
          <div>4. 民进党票仓政治</div>
          <div>5. 贿选票受贿腐败的民进...</div>
          <div>6. 国会改革</div>
          <div>7. 国会改革</div>
          <div>8. 国会改革</div>
          <div>9. 国会改革</div>
          <div>10. 国会改革</div>
        </div>
      </div>

      <div class="lightContainer">
        <div class="light-ranking-title">
          敌意言论数量榜
        </div>
        <div class="light-ranking-content">
          <div>1. 改革法案三读</div>
          <div>2. 国会改革</div>
          <div>3. 民进党滥用公权力导致...</div>
          <div>4. 民进党票仓政治</div>
          <div>5. 贿选票受贿腐败的民进...</div>
          <div>6. 国会改革</div>
          <div>7. 国会改革</div>
          <div>8. 国会改革</div>
          <div>9. 国会改革</div>
          <div>10. 国会改革</div>
        </div>
      </div>
    </div>

    <div class="container-wrapper">
      <div class="lightContainer">
        <div class="light-ranking-title">
          总参与人数榜
        </div>
        <div class="light-ranking-content">
          <div>1. 改革法案三读</div>
          <div>2. 国会改革</div>
          <div>3. 民进党滥用公权力导致...</div>
          <div>4. 民进党票仓政治</div>
          <div>5. 贿选票受贿腐败的民进...</div>
          <div>6. 国会改革</div>
          <div>7. 国会改革</div>
          <div>8. 国会改革</div>
          <div>9. 国会改革</div>
          <div>10. 国会改革</div>
        </div>
      </div>

      <div class="darkContainer">
        <div class="ranking-title">
          今日参与人数榜
        </div>
        <div class="ranking-content">
          <div>1. 改革法案三读</div>
          <div>2. 国会改革</div>
          <div>3. 民进党滥用公权力导致...</div>
          <div>4. 民进党票仓政治</div>
          <div>5. 贿选票受贿腐败的民进...</div>
          <div>6. 国会改革</div>
          <div>7. 国会改革</div>
          <div>8. 国会改革</div>
          <div>9. 国会改革</div>
          <div>10. 国会改革</div>
        </div>
      </div>

      <div class="lightContainer">
        <div class="light-ranking-title">
          近期参与人数飙升榜
        </div>
        <div class="light-ranking-content">
          <div>1. 改革法案三读</div>
          <div>2. 国会改革</div>
          <div>3. 民进党滥用公权力导致...</div>
          <div>4. 民进党票仓政治</div>
          <div>5. 贿选票受贿腐败的民进...</div>
          <div>6. 国会改革</div>
          <div>7. 国会改革</div>
          <div>8. 国会改革</div>
          <div>9. 国会改革</div>
          <div>10. 国会改革</div>
        </div>
      </div>

      <div class="lightContainer">
        <div class="light-ranking-title">
          敌意言论比例榜
        </div>
        <div class="light-ranking-content">
          <div>1. 改革法案三读</div>
          <div>2. 国会改革</div>
          <div>3. 民进党滥用公权力导致...</div>
          <div>4. 民进党票仓政治</div>
          <div>5. 贿选票受贿腐败的民进...</div>
          <div>6. 国会改革</div>
          <div>7. 国会改革</div>
          <div>8. 国会改革</div>
          <div>9. 国会改革</div>
          <div>10. 国会改革</div>
        </div>
      </div>
    </div>
    <div class="content-conclusion">
      [B] 舆情事件分维度排行榜
    </div>
    <hr>

    <div class="barChart">
      <div class="title-with-button">
        <el-switch v-model="showUserOverview" @change="toggleChart" />
        <h3 style="color:rgb(2, 157, 255); font-weight: bold; margin-left:10px">关键用户总览</h3>
      </div>
      <div id="bar-chart" style="width: 100%; height: 400px;" />
      <div class="content-conclusion">
        [C] 关键用户关系图 (参与事件高度重合)
      </div>
    </div>

    <hr>

    <div class="title-with-button">
      <el-switch v-model="showEventRanking" @change="toggleChart" />
      <h3 style="color:rgb(2, 157, 255); font-weight: bold; margin-left:10px">关键用户排行榜</h3>
    </div>
    <div class="container-wrapper">
      <div class="lightContainer">
        <div class="light-ranking-title">
          大V用户(出度)榜
        </div>
        <div class="light-ranking-content">
          <div>1. nabuntu</div>
          <div>2. obi_prodigalson</div>
          <div>3. jclem3</div>
          <div>4. ruminarist</div>
          <div>5. richheydarian</div>
          <div>6. rapplerdotcom</div>
          <div>7. jaytaryela</div>
          <div>8. jaytaryela</div>
          <div>9. jaytaryela</div>
          <div>10. jaytaryela</div>
        </div>
      </div>

      <div class="lightContainer">
        <div class="light-ranking-title">
          大V用户(出度)榜
        </div>
        <div class="light-ranking-content">
          <div>1. nabuntu</div>
          <div>2. obi_prodigalson</div>
          <div>3. jclem3</div>
          <div>4. ruminarist</div>
          <div>5. richheydarian</div>
          <div>6. rapplerdotcom</div>
          <div>7. jaytaryela</div>
          <div>8. jaytaryela</div>
          <div>9. jaytaryela</div>
          <div>10. jaytaryela</div>
        </div>
      </div>

      <div class="darkContainer">
        <div class="ranking-title">
          大V用户(出度)榜
        </div>
        <div class="ranking-content">
          <div>1. nabuntu</div>
          <div>2. obi_prodigalson</div>
          <div>3. jclem3</div>
          <div>4. ruminarist</div>
          <div>5. richheydarian</div>
          <div>6. rapplerdotcom</div>
          <div>7. jaytaryela</div>
          <div>8. jaytaryela</div>
          <div>9. jaytaryela</div>
          <div>10. jaytaryela</div>
        </div>
      </div>

      <div class="lightContainer">
        <div class="light-ranking-title">
          大V用户(出度)榜
        </div>
        <div class="light-ranking-content">
          <div>1. nabuntu</div>
          <div>2. obi_prodigalson</div>
          <div>3. jclem3</div>
          <div>4. ruminarist</div>
          <div>5. richheydarian</div>
          <div>6. rapplerdotcom</div>
          <div>7. jaytaryela</div>
          <div>8. jaytaryela</div>
          <div>9. jaytaryela</div>
          <div>10. jaytaryela</div>
        </div>
      </div>
    </div>
    <div class="container-wrapper">
      <div class="lightContainer">
        <div class="light-ranking-title">
          大V用户(出度)榜
        </div>
        <div class="light-ranking-content">
          <div>1. nabuntu</div>
          <div>2. obi_prodigalson</div>
          <div>3. jclem3</div>
          <div>4. ruminarist</div>
          <div>5. richheydarian</div>
          <div>6. rapplerdotcom</div>
          <div>7. jaytaryela</div>
          <div>8. jaytaryela</div>
          <div>9. jaytaryela</div>
          <div>10. jaytaryela</div>
        </div>
      </div>

      <div class="lightContainer">
        <div class="light-ranking-title">
          大V用户(出度)榜
        </div>
        <div class="light-ranking-content">
          <div>1. nabuntu</div>
          <div>2. obi_prodigalson</div>
          <div>3. jclem3</div>
          <div>4. ruminarist</div>
          <div>5. richheydarian</div>
          <div>6. rapplerdotcom</div>
          <div>7. jaytaryela</div>
          <div>8. jaytaryela</div>
          <div>9. jaytaryela</div>
          <div>10. jaytaryela</div>
        </div>
      </div>

      <div class="lightContainer">
        <div class="light-ranking-title">
          大V用户(出度)榜
        </div>
        <div class="light-ranking-content">
          <div>1. nabuntu</div>
          <div>2. obi_prodigalson</div>
          <div>3. jclem3</div>
          <div>4. ruminarist</div>
          <div>5. richheydarian</div>
          <div>6. rapplerdotcom</div>
          <div>7. jaytaryela</div>
          <div>8. jaytaryela</div>
          <div>9. jaytaryela</div>
          <div>10. jaytaryela</div>
        </div>
      </div>

      <div class="lightContainer">
        <div class="light-ranking-title">
          大V用户(出度)榜
        </div>
        <div class="light-ranking-content">
          <div>1. nabuntu</div>
          <div>2. obi_prodigalson</div>
          <div>3. jclem3</div>
          <div>4. ruminarist</div>
          <div>5. richheydarian</div>
          <div>6. rapplerdotcom</div>
          <div>7. jaytaryela</div>
          <div>8. jaytaryela</div>
          <div>9. jaytaryela</div>
          <div>10. jaytaryela</div>
        </div>
      </div>
    </div>
    <div class="content-conclusion">
      [D] 关键用户分维度排行榜
    </div>
  </div>

</template>

<script>
// import * as echarts from 'echarts'
// import axios from 'axios'
// import graphData from 'publicOpinionPlatform-main/frontend/build/les-miserables.js'

export default {
  name: 'Dashboard',
  data() {
    return {
      showEventOverview: true,
      showEventRanking: true,
      showUserOverview: true,
      showUserRanking: true
    }
  },
  mounted() {
    this.initLesMiserables()
  },

  methods: {
    // initLesMiserables() {
    //   const myChart = this.$refs.lesMiserablesChart
    //   myChart.showLoading()
    //
    //   // 使用导入的 graphData 代替手动创建的数据
    //   // eslint-disable-next-line no-undef
    //   const graph = graphData
    //
    //   myChart.hideLoading()
    //
    //   graph.nodes.forEach(node => {
    //     node.label = {
    //       show: node.symbolSize > 30
    //     }
    //   })
    //
    //   const option = {
    //     title: {
    //       text: 'Les Miserables',
    //       subtext: 'Default layout',
    //       top: 'bottom',
    //       left: 'right'
    //     },
    //     tooltip: {},
    //     legend: {
    //       data: graph.categories.map(a => a.name)
    //     },
    //     animationDuration: 1500,
    //     animationEasingUpdate: 'quinticInOut',
    //     series: [
    //       {
    //         name: 'Les Miserables',
    //         type: 'graph',
    //         legendHoverLink: false,
    //         layout: 'none',
    //         data: graph.nodes,
    //         links: graph.links,
    //         categories: graph.categories,
    //         roam: true,
    //         label: {
    //           position: 'right',
    //           formatter: '{b}'
    //         },
    //         lineStyle: {
    //           color: 'source',
    //           curveness: 0.3
    //         },
    //         emphasis: {
    //           focus: 'adjacency',
    //           lineStyle: {
    //             width: 10
    //           }
    //         }
    //       }
    //     ]
    //   }
    //
    //   myChart.setOption(option)
    // },

    toggleChart() {
      this.$nextTick(() => {
        if (this.showRadarChart) this.initRadarChart()
        if (this.showBarChart) this.initBarChart()
        if (this.showPositionAndSentiment) {
          this.initPositionChart()
          this.initSentimentChart()
        }
      })
    }
  }
}
</script>

<style scoped>

.dashboard {
  padding: 10px;
}

hr{
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

.searchbar  .el-button {
  border: none;
  padding: 0;
}

.icon-group {
  display: flex;
  gap: 50px; /* Add some space between icons */
}
.icon-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  cursor: pointer;
}
.icon-item div {
  margin-top: 5px; /* Add some space between icon and text */
}
.LesMiserables{
  margin-bottom:10px;
}

.date-selectors {
  display: flex;
  gap: 50px; /* Add some space between date pickers */
  justify-content: center; /* Center the date pickers */
}

.title-with-button {
  display: flex;
  align-items: center;
  margin-left:10px;
}

.content-conclusion{
  text-align:center;
  font-family: "Microsoft YaHei",serif;
  margin-bottom:20px;
  font-weight:bold;
  color:deepskyblue;
  margin-top:20px;
}

.darkContainer{
  height:400px;
  width: 200px; /* 容器的宽度，可以根据需要调整 */
  background-color: #57bdf8; /* 浅蓝色背景 */
  transform: skew(0,10deg); /* 使容器变成平行四边形 */
  padding: 20px;
  margin: 20px;
}

.lightContainer{
  height:400px;
  width: 200px; /* 容器的宽度，可以根据需要调整 */
  background-color: rgb(243, 251, 255); /* 浅蓝色背景 */
  transform: skew(0,10deg); /* 使容器变成平行四边形 */
  padding: 20px;
  margin: 20px;
}

.container-wrapper{
  display:flex;
  flex-wrap:wrap;
  justify-content: center;
}

.ranking-title{
  font-family: "Microsoft YaHei",serif;
  color: white;
  font-weight: bold;
  transform:skew(0,-10deg);
}

.ranking-content div{
  margin-top:15px;
  font-size:14px;
  color: white; /* 标题颜色 */
  transform: skew(0,-10deg); /* 使文本校正回正 */
}

.light-ranking-title{
  font-family: "Microsoft YaHei",serif;
  color:deepskyblue;
  font-weight: bold;
  transform:skew(0,-10deg);
}

.light-ranking-content div{
  margin-top:15px;
  font-size:14px;
  color: lightgray; /* 标题颜色 */
  transform: skew(0,-10deg); /* 使文本校正回正 */
}

</style>

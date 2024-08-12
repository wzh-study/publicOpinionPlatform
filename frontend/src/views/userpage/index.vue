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

    <div class="userinfo">

      <div class="title-with-button">
        <h3 style="color: #4552ba; font-weight: bold; ">一、用户的基本信息</h3>
      </div>

      <el-row style="margin-top: 0; width: 100%; display: flex; flex-wrap: wrap;">
        <!-- User Information Container -->
        <el-col :span="14" style="display: flex; flex-direction: column; justify-content: center; ">
          <div class="user-info-container">
            <el-row style="display: flex; justify-content: space-between;">
              <el-col :span="11" style="display: flex; align-items: center; justify-content: center;">
                <span style="margin-right: 16px;">用户名: </span>
                <el-select v-model="userinfo_selected" placeholder="请选择" @change="fetchUserData">
                  <el-option
                    v-for="username in usernames"
                    :key="username"
                    :label="username"
                    :value="username"
                  />
                </el-select>
              </el-col>

              <el-col :span="11" style="display: flex; align-items: center; justify-content: center;">
                <span style="margin-right: 16px;">用户头像: </span>
                <img :src="userProfile.imageUrl" style="border-radius: 50%; width: 120px; height: 120px;">
              </el-col>
            </el-row>

            <!-- User Data Table -->
            <el-table class="custom-el-table" :data="tableRows" style="margin-top: 100px;">
              <el-table-column
                v-for="(item, index) in tableData"
                :key="index"
                :prop="'col' + index"
                :label="item.key"
                :cell-style="{ background: 'yellow' }"
              />
            </el-table>

            <div
              style="text-align: center; color: skyblue; font-size: 14px; font-weight: bold; margin-top: 20px;"
            >
              [B] 活跃与影响面
            </div>
          </div>
        </el-col>

        <!-- Right Side (40% width) -->
        <el-col :span="9" style="margin-left: 20px; padding-left: 10px; box-sizing: border-box;">
          <el-collapse
            v-model="activeNames"
            style="width: 100%; max-height: 400px; overflow-y: auto; ; font-size: 12px;"
          >
            <el-collapse-item class="custom-collapse-item" title="基础信息" name="1">
              <el-table :data="basicInfo" border style="width: 100%">
                <el-table-column prop="key" label="信息项" width="100" />
                <el-table-column prop="value" label="详情" />
              </el-table>
            </el-collapse-item>
            <!-- Followers List -->
            <el-collapse-item class="custom-collapse-item" title="粉丝列表" name="2">
              <el-table :data="followersList" border style="width: 100%">
                <el-table-column prop="name" label="粉丝名称" />
              </el-table>
            </el-collapse-item>

            <!-- Following List -->
            <el-collapse-item class="custom-collapse-item" title="关注列表" name="3">
              <el-table :data="followingList" border style="width: 100%">
                <el-table-column prop="name" label="关注名称" />
              </el-table>
            </el-collapse-item>
          </el-collapse>
          <!-- Table Title -->
          <div style="text-align: center; color: skyblue; font-size: 14px; font-weight: bold; margin-top: 20px;">
            [A] 基础信息
          </div>
        </el-col>
      </el-row>

    </div>

    <hr>

    <div class="UserBehaviourRegular">
      <div class="title-with-button">
        <h3 style="color: #4552ba; font-weight: bold;">二、用户行为规律</h3>
      </div>
      <div class="UserBehaviourRegular-container">
        <div style="width: 800px; text-align: center;">
          <div ref="UserBehaviourRegularChart" style="width: 100%; height: 400px;" />
          <div style="margin-top: 10px; font-size: 14px; font-weight: bold; color: skyblue;">[C] 活跃时间</div>
        </div>
        <div style="width: 400px; text-align: center;">
          <div v-if="UserBehaviourRegularActiveTimePointsData.length > 0">
            <div
              v-for="(timePoint, index) in UserBehaviourRegularActiveTimePointsData"
              :key="index"
              class="time-tag"
            >
              <el-tag type="danger" effect="plain" style="margin-top: 40px;">
                <i class="el-icon-time" style="margin-left: 2px;" /> <!-- Clock icon -->
                {{ timePoint }}
              </el-tag>
            </div>
          </div>
          <div v-else>No active time points available</div>
          <div style="margin-top: 10px; font-size: 14px; font-weight: bold; color: skyblue;">[D] 活跃时点</div>
        </div>
      </div>
    </div>
    <hr>

    <div class="UserPersonalitiesValues">
      <div class="title-with-button">
        <h3 style="color: #4552ba; font-weight: bold; ">三、用户人格与价值观</h3>
      </div>
      <div class="UserPersonalitiesValues-container">
        <div style="width: 400px; text-align: center;">
          <div ref="UserPersonalitiesValuesRadarChart" style="height: 400px;" />
          <div style="margin-top: 10px; font-size: 14px; font-weight: bold; color: skyblue;">[E] 大五人格</div>
        </div>
        <div style="width: 400px; text-align: center;">
          <div ref="UserPersonalitiesValuesBarChart" style="height: 400px;" />
          <div style="margin-top: 10px; font-size: 14px; font-weight: bold; color: skyblue;">[F] 施瓦兹价值观</div>
        </div>
      </div>
    </div>

    <hr>

    <div class="UserCenterNetwork">
      <div class="title-with-button">
        <h3 style="color: #4552ba; font-weight: bold; ">四、用户中心网络</h3>
      </div>
      <div class="UserCenterNetwor-container">
        <div style="width: 400px; text-align: center;" />
        <div style="width: 400px; text-align: center;" />
      </div>
    </div>
    <hr>

    <div class="UserSemantic">
      <div class="title-with-button">
        <h3 style="color: #4552ba; font-weight: bold; ">五、用户语义</h3>
      </div>
    </div>
    <hr>

    <div class="userpublicopinion">
      <div class="title-with-button">
        <h3 style="color: #4552ba; font-weight: bold; ">六、用户参与舆情</h3>
      </div>
    </div>

    <hr>

  </div>
</template>

<script>
import * as echarts from 'echarts'
import axios from 'axios'

export default {
  name: 'Dashboard',
  data() {
    return {
      selected: null,
      startDate: '',
      endDate: '',

      userinfo_selected: '',
      //  用户基本信息数据
      usernames: ['用户1', '用户2', '用户3'], // 用户名选项
      userProfile: {
        username: '用户1',
        imageUrl: require('@/assets/userinfo_images/1.jpg'),
        url: 'https://example.com',
        bio: '这是一个示例用户。',
        dob: '1990-01-01',
        region: '上海',
        profession: '软件工程师',
        followers: ['用户A', '用户B'],
        following: ['用户C', '用户D'],
        posts: 120,
        reposts: 45,
        comments: 230,
        reposted: 60,
        commented: 75,
        liked: 350,
        // New fields
        commentedUserCount: 55, // 被评论人数
        repostedUserCount: 40, // 被转发人数
        likedUserCount: 200 // 被赞人数
      },
      tableData: [], // 用于存储键值对
      tableRows: [{}], // 用于表格行数据
      basicInfo: [],
      followersList: [],
      followingList: [],
      activeNames: ['1'], // 默认展开基础信息

      //  用户行为规律数据
      UserBehaviourRegularData: [
        ['2024/01', 120], ['2024/02', 132], ['2024/03', 101], ['2024/04', 134], ['2024/05', 90], ['2024/06', 110], ['2024/07', 120], ['2024/08', 83]
      ],

      UserBehaviourRegularActiveTimePointsData: ['2024/01/02 10:10:10', '2024/03/02 11:12:17', '2024/05/23 08:03:12', '2024/07/11 18:21:32'], // This will hold the fetched time points

      //  用户人格与价值观数据
      BigFivePersonalData: {
        'Openness': 0.1,
        'Conscientiousness': 0.5,
        'Extraversion': 0.7,
        'Agreeableness': 0.2,
        'Neuroticism': 0.3
      },
      PersonalValueData: {
        posvalue: [0.1, 0.2, 0.3, 0.4, 0.5],
        negvalue: [-0.1, -0.2, -0.3, -0.4, -0.5]
      }

      //  用户中心网络数据

      //  用户语义数据

      //  用户参与舆情数据
    }
  },
  mounted() {
    //  用户基本信息
    if (this.usernames.length > 0) {
      this.userinfo_selected = this.usernames[0] // 设置默认选择第一个用户名
      this.fetchUserData() // 可选：在组件加载时立即获取第一个用户的数据
    }
    //  用户行为规律
    this.fetchUserBehaviourRegularData() // 初始获取用户行为规律数据
    this.fetchActiveTimePoints() // 初始获取活跃时点数据

    //  用户人格和价值观
    this.fetchUserBigFivePersonalitiesData()
    this.fetchUserPersonalitiesValueData()

    //  用户中心网络

    //  用户语义

    //  用户参与舆情
  },

  methods: {
    /* 导航栏 */
    select(icon) {
      this.selected = icon
    },
    /*  第一部分 用户基本信息  */
    //  请求用户数据
    fetchUserData() {
      const username = this.userinfo_selected
      axios.post('https://localhost:8080/user', {
        username: username // 发送选择的用户名到后端
      })
        .then(response => {
          if (response.data) {
            this.userProfile = { ...this.userProfile, ...response.data }
            this.fillBasicInfo() // 使用默认值重新填充表格，确保数据的连续性
            this.fillTableData()
          }
        })
        .catch(error => {
          console.error('Error fetching user data:', error)
          this.fillBasicInfo() // 使用默认值重新填充表格，确保数据的连续性
          this.fillTableData() // 使用默认值重新填充表格，确保数据的连续性
        })
    },
    //  基本信息定义
    fillBasicInfo() {
      this.basicInfo = [
        { key: '用户名', value: this.userProfile.username },
        { key: '个人简介', value: this.userProfile.bio },
        { key: '出生日期', value: this.userProfile.dob },
        { key: '地区', value: this.userProfile.region },
        { key: '职业', value: this.userProfile.profession }
      ]

      this.followersList = this.userProfile.followers.map(follower => ({
        name: follower
      }))

      this.followingList = this.userProfile.following.map(following => ({
        name: following
      }))
    },
    //  用户信息表格中的原发 转发 评论 点赞等数据
    fillTableData() {
      const row = {} // 初始化 row 对象
      //  请求数据  暂时无  换成假数据
      this.tableData = [
        { key: '原发帖个数', value: this.userProfile.posts || 10 },
        { key: '转发帖个数', value: this.userProfile.reposts || 10 },
        { key: '评论个数', value: this.userProfile.comments || 10 },
        { key: '被评论个数', value: this.userProfile.commented || 10 },
        { key: '被评论人数', value: this.userProfile.commentedUserCount || 10 },
        { key: '被转发个数', value: this.userProfile.reposted || 10 },
        { key: '被转发人数', value: this.userProfile.repostedUserCount || 10 },
        { key: '被赞个数', value: this.userProfile.liked || 10 },
        { key: '被赞人数', value: this.userProfile.likedUserCount || 10 }

      ]

      this.tableData.forEach((item, index) => {
        row['col' + index] = item.value // 动态设置属性名对应于列
      })

      this.tableRows = [row] // 重要：确保将 row 对象放入 tableRows 数组
    },
    getCellStyle(key) { //  得到表格颜色的方法
      // Define which keys should have a pink background
      const pinkFields = ['被评论人数', '被转发人数', '被赞人数']

      if (pinkFields.includes(key)) {
        return { backgroundColor: '#ffe4e1' } // Light pink
      } else {
        return { backgroundColor: '#add8e6' } // Light blue
      }
    },

    /*  用户行为规律  */
    fetchUserBehaviourRegularData() {
      axios.get('https://localhost:8080/user/behaviourRegular')
        .then(response => {
          this.UserBehaviourRegularData = response.data
          this.initUserBehaviourTimeChart(this.UserBehaviourRegularData)
        })
        .catch(error => {
          console.error('Failed to fetch data, using default:', error)
          this.initUserBehaviourTimeChart(this.UserBehaviourRegularData)
        })
    },

    fetchActiveTimePoints() {
      axios.get('https://localhost:8080/user/activateTimePoints')
        .then(response => {
          this.UserBehaviourRegularActiveTimePointsData = response.data // Assuming the data is an array of time points
        })
        .catch(error => {
          console.error('Failed to fetch active time points:', error)
          // this.activeTimePoints = []; // Set to empty if there's an error
        })
    },

    initUserBehaviourTimeChart(data) {
      var myChart = echarts.init(this.$refs.UserBehaviourRegularChart)
      var option = {
        title: {
          text: 'UserBehaviourTimeRegular',
          left: '1%'
        },
        tooltip: {
          trigger: 'axis'
        },
        grid: {
          left: '5%',
          right: '15%',
          bottom: '10%'
        },
        xAxis: {
          data: data.map(item => item[0])
        },
        yAxis: {},
        toolbox: {
          right: 150, //   右侧工具箱
          feature: {
            dataZoom: {
              yAxisIndex: 'none'
            },
            restore: {},
            saveAsImage: {}
          }
        },
        dataZoom: [
          {
            startValue: '2024/01'
          },
          {
            type: 'inside'
          }
        ],
        visualMap: {
          top: 50,
          right: 40,
          pieces: [
            {
              gt: 0,
              lte: 50,
              color: '#93CE07'
            },
            {
              gt: 50,
              lte: 100,
              color: '#FBDB0F'
            },
            {
              gt: 100,
              lte: 150,
              color: '#FC7D02'
            },
            {
              gt: 150,
              lte: 200,
              color: '#FD0100'
            },
            {
              gt: 200,
              lte: 300,
              color: '#AA069F'
            },
            {
              gt: 300,
              color: '#AC3B2A'
            }
          ],
          outOfRange: {
            color: '#999'
          }
        },
        series: {
          name: 'UserBehaviourTimeRegular',
          type: 'line',
          data: data.map(function(item) {
            return item[1]
          }),
          markLine: {
            silent: true,
            lineStyle: {
              color: '#333'
            },
            data: [
              {
                yAxis: 50
              },
              {
                yAxis: 100
              },
              {
                yAxis: 150
              },
              {
                yAxis: 200
              },
              {
                yAxis: 300
              }
            ]
          }
        }
      }
      myChart.setOption(option)
    },

    /*  用户人格和价值观  */
    fetchUserBigFivePersonalitiesData() {
      axios.get('https://localhost:8080/user/BigFivePersonal')
        .then(response => {
          this.BigFivePersonalData = response.data
          this.initBigFivePersonalChart(this.BigFivePersonalData)
        })
        .catch(error => {
          console.error('Failed to fetch data, using default:', error)
          this.initBigFivePersonalChart(this.BigFivePersonalData)
        })
    },

    fetchUserPersonalitiesValueData() {
      axios.get('https://localhost:8080/user/PersonalitiesValue')
        .then(response => {
          this.PersonalValueData = response.data
          this.initPersonalitiesValuesBarChart(this.PersonalValue)
        })
        .catch(error => {
          console.error('Failed to fetch data, using default:', error)
          this.initPersonalitiesValuesBarChart(this.PersonalValueData)
        })
    },

    initBigFivePersonalChart(data) {
      const radarDom = this.$refs.UserPersonalitiesValuesRadarChart
      const radarChart = echarts.init(radarDom)

      const BigFivePersonalValues = [
        data.Openness,
        data.Conscientiousness,
        data.Extraversion,
        data.Agreeableness,
        data.Neuroticism
      ]

      const radarOption = {
        radar: {
          indicator: [
            { name: '开放性', max: 1 },
            { name: '责任心', max: 1 },
            { name: '外向性', max: 1 },
            { name: '宜人性', max: 1 },
            { name: '神经质', max: 1 }
          ]
        },
        series: [{
          type: 'radar',
          data: [
            {
              value: BigFivePersonalValues,
              name: '个人特质'
            }
          ]
        }]
      }

      radarChart.setOption(radarOption)
    },
    initPersonalitiesValuesBarChart(data) {
      const barDom = this.$refs.UserPersonalitiesValuesBarChart
      const barChart = echarts.init(barDom)

      const posvalue = data.posvalue
      const negvalue = data.negvalue

      const barOption = {
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        legend: {
          data: ['正向', '反向']
        },
        xAxis: {
          type: 'value'
        },
        yAxis: {
          type: 'category',
          data: ['合作', '诚实', '慷慨', '公正', '勇敢']
        },
        series: [
          {
            name: '正向',
            type: 'bar',
            stack: '总量',
            label: {
              show: true,
              position: 'insideRight'
            },
            data: posvalue
          },
          {
            name: '反向',
            type: 'bar',
            stack: '总量',
            label: {
              show: true,
              position: 'insideRight'
            },
            data: negvalue
          }
        ]
      }

      barChart.setOption(barOption)
    }
  }

  /*   用户舆情 */

}

</script>

<style scoped>
.dashboard {
  padding: 10px;
}

hr {
  margin-top: 20px;
  height: 1px;
  /* Increase the height of the hr */
  background-color: skyblue;
  /* 将整个 hr 标签的背景颜色更改为蓝色 */
}

/*  导航栏样式 */

.searchbar {
  margin-top: 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;

}

.searchbar .el-button {
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

/*  第一部分样式 */

.userinfo h3 {
  color: #4552ba;
  font-weight: bold;
}

.userinfo img {
  width: 100px;
  /* 根据需要调整尺寸 */
  height: 100px;
  /* 根据需要调整尺寸 */
  border-radius: 50%;
}

.custom-collapse-item {
  margin-top: 20px;
}

/* 表格样式 */
.custom-el-table {
  border-collapse: collapse;
  width: 100%;
  border: 1px solid #d3dce6;
}

/* 用户行为规律 */
.UserBehaviourRegular-container {
  display: flex;
  justify-content: space-around;
  align-items: center;
}

/* 用户人格价值观 */
.UserPersonalitiesValues-container {
  display: flex;
  justify-content: space-around;
  align-items: center;
}

/* 用户中心网络 */

</style>

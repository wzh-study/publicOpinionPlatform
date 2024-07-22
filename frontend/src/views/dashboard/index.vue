<template>
    <div class="dashboard">
        <!-- 用户信息大卡片 -->
      <!-- <el-row :gutter="20">
        
        <el-col :span="24">
          <el-card :body-style="{ padding: '20px' }">
            <div class="user-card">
              <div class="user-avatar">
                <img :src="avatar+'?imageView2/1/w/80/h/80'" alt="User Avatar">
              </div>
              <div class="user-info">
                <p class="user-name">admin</p>
                <p class="user-email">admin@example.com</p>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row> -->
  
      <!-- 统计卡片 -->
      <el-row :gutter="20">
        <el-col :span="6" v-for="data in infoData" :key="data.label">
          <el-card :body-style="{ padding: '20px' }">
            <div class="stat-card">
              <div class="stat-icon">
                <i :class="data.icon"></i>
              </div>
              <div class="stat-info">
                <p class="stat-value">{{ data.value }}</p>
                <p class="stat-label">{{ data.label }}</p>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>
  
      <el-row :gutter="20" class="chart-row">
        <!-- ECharts 图表等其他内容 -->
        <!-- 确保为每个图表和数据视图提供足够的空间和适当的样式 -->
      </el-row>
    </div>
  </template>
  
<script>

import { mapGetters } from 'vuex'

export default {
    computed: {
    ...mapGetters([
        'avatar'
    ])
    },
    data() {
        return {
        userInfo: {
            name: 'admin',
            email: 'zhangsan@example.com',
        },
        infoData: [
            { label: '用户总数', value: '2000', icon: 'el-icon-user-solid', unit: '人' },
            { label: '贴文总数', value: '850', icon: 'el-icon-s-comment', unit: '篇' },
            { label: '涉及国家', value: '15', icon: 'el-icon-s-promotion', unit: '个' },
            { label: '涉及平台数', value: '3', icon: 'el-icon-platform-eleme', unit: '个' }
        ]
        };
    }
    }
</script>

<style scoped lang="scss">
.dashboard {
background-color: #f0f2f5; /* 浅灰背景 */
padding: 30px;

.el-row {
    margin-bottom: 20px;
}

.user-card {
    display: flex;
    align-items: center;
    .user-avatar {
    img {
        width: 60px;
        height: 60px;
        border-radius: 50%;
    }
    }
    .user-info {
    margin-left: 12px;
    .user-name {
        font-size: 20px;
        font-weight: bold;
        color: #333;
    }
    .user-email {
        font-size: 14px;
        color: #666;
    }
    }
}

.stat-card {
    display: flex;
    align-items: center;
    .stat-icon {
    font-size: 40px;
    color: #409EFF; /* Element UI 默认的蓝色主题色 */
    }
    .stat-info {
    margin-left: 12px;
    .stat-value {
        font-size: 24px;
        font-weight: bold;
        color: #333;
    }
    .stat-label {
        font-size: 16px;
        color: #666;
    }
    }
}

.chart-row {
    background-color: #fff; /* 为图表行设置白色背景 */
    padding: 20px;
    border-radius: 4px;
}
}
</style>

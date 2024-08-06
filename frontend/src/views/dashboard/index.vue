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
                    ></el-button>
                    <div>Facebook</div>
                </div>
                <div class="icon-item" @click="select('twitter')">
                    <el-button
                        :type="selected === 'twitter' ? 'primary' : 'default'"
                        icon="el-icon-platform-eleme"
                        circle
                        :style="{ fontSize: '25px' }"
                    ></el-button>
                    <div>Twitter</div>
                </div>
                <div class="icon-item" @click="select('custom')">
                    <el-button
                        :type="selected === 'custom' ? 'primary' : 'default'"
                        icon="el-icon-platform-eleme"
                        circle
                        :style="{ fontSize: '25px' }"
                    ></el-button>
                    <div>Custom</div>
                </div>
            </div>
            <div class="date-selectors" >
                <el-date-picker v-model="startDate" type="date" placeholder="开始日期"></el-date-picker>
                <el-date-picker v-model="endDate" type="date" placeholder="结束日期"></el-date-picker>
            </div>
        </div>

        <hr/>

        <div class="userinfo">
            
            <div class="title-with-button">
                <h3 style="color: #4552ba; font-weight: bold; ">一、用户的基本信息</h3>
            </div>
            
         
            <el-col :span="8" style="display: flex; align-items: center; justify-content: center;">
                <span style="margin-right: 20px;">用户名: </span>
                <el-select v-model="userinfo_selected" placeholder="请选择" @change="fetchUserData">
                    <el-option
                        v-for="username in usernames"
                        :key="username"
                        :label="username"
                        :value="username">
                    </el-option>
                </el-select>
            </el-col>
            <el-col :span="8" style="display: flex; align-items: center; justify-content: center;">
                <span style="margin-right: 20px;">用户头像: </span>
                <img :src="userProfile.imageUrl" style="border-radius: 50%; width: 100px; height: 100px;">
            </el-col>

            <el-col :span="8" style="display: flex; align-items: center; justify-content: center; margin-top: -50px;">
                <el-collapse v-model="activeNames">
                    <el-collapse-item class="custom-collapse-item" title="基础信息" name="1">
                        <el-table :data="basicInfo" border style="width: 100%">
                            <el-table-column prop="key" label="信息项" width="100"></el-table-column>
                            <el-table-column prop="value" label="详情"></el-table-column>
                        </el-table>
                    </el-collapse-item>
                </el-collapse>
            </el-col>
    
   
            <el-table class="custom-el-table" :data="tableRows" style="margin-top: 30px; width: 40%" :max-height="400">
                <el-table-column
                    v-for="(item, index) in tableData"
                    :key="index"
                    :prop="'col' + index"
                    :label="item.key">
                </el-table-column>
            </el-table>
        </div>

        <hr/>





        <div class="usersemantic">
            <div class="title-with-button">
                <h3 style="color: #4552ba; font-weight: bold; ">二、用户语义</h3>
            </div>
            <div id="usersemantic-chart" style="width: 100%; height: 400px;"></div>
        </div>

        <hr/>



        <div class="userpublicopinion">
            <div class="title-with-button">
                <h3 style="color: #4552ba; font-weight: bold; ">三、用户参与舆情</h3>
            </div>
        </div>

        <hr/>




    </div>
    
    
    

</template>

<script>
import * as echarts from 'echarts';
import axios from 'axios';

export default {
    name: 'Dashboard',
    data() {
        return {
            selected: null,
            startDate: '',
            endDate: '',
            
            userinfo_selected: '',
            usernames: ['用户1', '用户2', '用户3'], // 用户名选项
            userProfile: {
                username: '用户1',
                imageUrl: require('@/assets/userinfo_images/1.jpg'),
                url: "https://example.com",
                bio: "这是一个示例用户。",
                dob: "1990-01-01",
                region: "上海",
                profession: "软件工程师",
                followers: ["用户A", "用户B"],
                following: ["用户C", "用户D"],
                posts: 120,
                reposts: 45,
                comments: 230,
                reposted: 60,
                commented: 75,
                liked: 350
            },
            tableData: [], // 用于存储键值对
            tableRows: [{}], // 用于表格行数据
            basicInfo: [],
            activeNames: ['1']  // 默认展开基础信息
        };
    },
    mounted() {
        if (this.usernames.length > 0) {
            this.userinfo_selected = this.usernames[0]; // 设置默认选择第一个用户名
            this.fetchUserData(); // 可选：在组件加载时立即获取第一个用户的数据
        }
        this.initUserSemanticChart();
    },

    methods: {
        select(icon) {
            this.selected = icon;
        },
        fetchUserData() {
            const username = this.userinfo_selected;
            axios.post('https://localhost:8080/user', {
                username: username // 发送选择的用户名到后端
            })
            .then(response => {
                if (response.data) {
                this.userProfile = {...this.userProfile, ...response.data};
                this.fillBasicInfo(); // 使用默认值重新填充表格，确保数据的连续性
                this.fillTableData();
                }
            })
            .catch(error => {
                console.error('Error fetching user data:', error);
                this.fillBasicInfo(); // 使用默认值重新填充表格，确保数据的连续性
                this.fillTableData(); // 使用默认值重新填充表格，确保数据的连续性
            });
        },
        fillBasicInfo() {
            this.basicInfo = [
                { key: '用户名', value: this.userProfile.username },
                { key: '个人简介', value: this.userProfile.bio },
                { key: '出生日期', value: this.userProfile.dob },
                { key: '地区', value: this.userProfile.region },
                { key: '职业', value: this.userProfile.profession },
                { key: "粉丝列表", value: this.userProfile.followers.join(", ") || 'a,b,c'},
                { key: "关注列表", value: this.userProfile.following.join(", ") || 'a,b,c'},
            ];
        },
        fillTableData() {
            const row = {}; // 初始化 row 对象
            //  请求数据  暂时无  换成假数据
            this.tableData = [
                { key: "原发帖个数", value: this.userProfile.posts || 10},
                { key: "转发帖个数", value: this.userProfile.reposts || 10},
                { key: "评论个数", value: this.userProfile.comments || 10},
                { key: "被转人数", value: this.userProfile.reposted || 10},
                { key: "被评人数", value: this.userProfile.commented || 10},
                { key: "被赞人数", value: this.userProfile.liked || 10}

            ];

            this.tableData.forEach((item, index) => {
                row['col' + index] = item.value; // 动态设置属性名对应于列
            });

            this.tableRows = [row]; // 重要：确保将 row 对象放入 tableRows 数组
        },
        initUserSemanticChart() {
            const chartDom = document.getElementById('usersemantic-chart');
            console.log(chartDom); // Ensure this logs to console
            const myChart = echarts.init(chartDom);
            myChart.showLoading();

            // Generate 40 nodes
            const nodes = Array.from({ length: 40 }, (_, i) => ({
                name: `Node${i + 1}`,
                category: i % 3 // Assign categories cyclically
            }));

            // Generate random links
            const links = [];
            for (let i = 0; i < 40; i++) {
                const targetIndex = Math.floor(Math.random() * 40);
                if (i !== targetIndex) {
                    links.push({ source: `Node${i + 1}`, target: `Node${targetIndex + 1}` });
                }
            }

            const fakeData = {
                nodes,
                categories: [
                    { name: 'Category1' },
                    { name: 'Category2' },
                    { name: 'Category3' }
                ],
                links
            };

            myChart.hideLoading();
            const option = {
                legend: {
                    data: ['Category1', 'Category2', 'Category3']
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
                        data: fakeData.nodes,
                        categories: fakeData.categories,
                        force: {
                            edgeLength: 5,
                            repulsion: 20,
                            gravity: 0.2
                        },
                        edges: fakeData.links
                    }
                ]
            };
            myChart.setOption(option);
        }
    },
    
}
</script>

<style scoped>


.dashboard {
    padding: 10px;
}

hr {
    margin-top: 20px;
    height: 1px; /* Increase the height of the hr */
    background-color: skyblue; /* 将整个 hr 标签的背景颜色更改为蓝色 */
}

/*  导航栏样式 */

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

.date-selectors {
    display: flex;
    gap: 50px; /* Add some space between date pickers */
    justify-content: center; /* Center the date pickers */
}

/*  第一部分样式 */
.userinfo h3 {
  color: #4552ba;
  font-weight: bold;
}.userinfo img {
  width: 100px;  /* 根据需要调整尺寸 */
  height: 100px; /* 根据需要调整尺寸 */
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

.custom-el-table td {
    text-align: left;
    padding: 8px;
    border-bottom: 1px solid #ebeef5;
}

.custom-el-table th {
    background-color: #f5f7fa;
    color: #606266;
}
</style>
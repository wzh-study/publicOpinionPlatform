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
export default {
    name: 'Dashboard',
    data() {
        return {
            selected: null,
            startDate: '',
            endDate: '',
        };
    },
    methods: {
        select(icon) {
            this.selected = icon;
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
    mounted() {
        this.initUserSemanticChart();
    }
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
</style>
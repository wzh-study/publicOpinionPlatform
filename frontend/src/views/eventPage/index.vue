<template>
    <div class="dashboard">
        <div class="searchbar">
            <div class="icon-group">
                <div class="icon-item" @click="select('facebook')">
                    <el-button :type="selected === 'facebook' ? 'primary' : 'default'" icon="el-icon-user-solid" circle
                        :style="{ fontSize: '25px' }" />
                    <div>Facebook</div>
                </div>
                <div class="icon-item" @click="select('twitter')">
                    <el-button :type="selected === 'twitter' ? 'primary' : 'default'" icon="el-icon-platform-eleme" circle
                        :style="{ fontSize: '25px' }" />
                    <div>Twitter</div>
                </div>
                <div class="icon-item" @click="select('custom')">
                    <el-button :type="selected === 'custom' ? 'primary' : 'default'" icon="el-icon-platform-eleme" circle
                        :style="{ fontSize: '25px' }" />
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
                <el-switch v-model="showEventInfo" @change="toggleChart" />
                <h3 style="color:rgb(2, 157, 255); font-weight: bold; margin-left:10px">舆情事件树</h3>
            </div>
        </div>
        <div id="event-tree" style="width: 100%; height: 500px; margin: 0 auto" />
        <div class="content-conclusion">
            [A] 舆情事件树
        </div>
        <hr>

        <div class="title-with-button">
            <el-switch v-model="showEventRanking" @change="toggleChart" />
            <h3 style="color:rgb(2, 157, 255); font-weight: bold; margin-left:10px">舆情事件分析——传播</h3>
        </div>
        <div class="centered-select">
            <el-select v-model="eventinfo_selected" placeholder="请选择" @change="fetchEventData">
                <el-option v-for="event in events" :key="event" :label="event" :value="event" />
            </el-select>
        </div>
        <div class="content-conclusion">
            [B] 传播总览图
        </div>
        <div class="content-conclusion">
            [C] 声量与新用户趋势
        </div>
        <div id="trend-chart" style="width:400px;height:400px; margin: 0 auto" />
        <div id="pie-chart" style="width:400px; height:400px; margin: 0 auto" />
        <div class="content-conclusion">
            [D.1] 传播链路长度分析
        </div>
        <div class="content-conclusion">
            [D.2] 长传播链示意
        </div>
        <hr>

        <div class="LesMiserables">
            <div class="title-with-button">
                <el-switch v-model="showUserInfo" @change="toggleChart" />
                <h3 style="color:rgb(2, 157, 255); font-weight: bold; margin-left:10px">舆情事件分析——关键用户</h3>
            </div>
        </div>
        <div class="centered-select">
            <el-select v-model="eventinfo_selected" placeholder="请选择" @change="fetchEventData">
                <el-option v-for="event in events" :key="event" :label="event" :value="event" />
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
        <hr>

        <div class="title-with-button">
            <el-switch v-model="showUserRanking" @change="toggleChart" />
            <h3 style="color:rgb(2, 157, 255); font-weight: bold; margin-left:10px">舆情事件分析——内容</h3>
        </div>
        <div class="centered-select">
            <el-select v-model="eventinfo_selected" placeholder="请选择" @change="fetchEventData">
                <el-option v-for="event in events" :key="event" :label="event" :value="event" />
            </el-select>
        </div>
        <el-table :data="tableData" border style="width: 80%; justify-content: center;">
            <el-table-column prop="ranking1" label="排名"  />
            <el-table-column prop="ranking2" label="排名*"  />
            <el-table-column prop="content" label="高频传播内容"  />
            <el-table-column prop="imgurl" label="源头" >
                <template slot-scope="scope">
                    <img :src="scope.row.imgurl" alt="image" style="width: 20%; height:auto;" />
                </template>
            </el-table-column>
        </el-table>
        <div class="content-conclusion">
            [F] 高频传播内容
        </div>
        <div class="content-conclusion">
            [G.1] 高频词云图
        </div>
        <div class="content-conclusion">
            [G.2] 高频词的共现
        </div>
        <div class="content-conclusion">
            [G.3] 指定关注词的共现
        </div>

        <div class="title-with-button">
            <el-switch v-model="showUserRanking" @change="toggleChart" />
            <h3 style="color:rgb(2, 157, 255); font-weight: bold; margin-left:10px">舆情事件分析——语义</h3>
        </div>
        <el-select v-model="eventinfo_selected" placeholder="请选择" @change="fetchEventData">
            <el-option v-for="event in events" :key="event" :label="event" :value="event" />
        </el-select>
        <div class="content-conclusion">
            [H] 主要话题短语
        </div>
        <div class="content-conclusion">
            [I] 主要观点
        </div>
        <div id="point-chart" style="width:400px;height:400px; margin: 0 auto" />
        <div id="point-pie-chart" style="width:200px;height:200px; margin: 0 auto" />
        <div class="content-conclusion">
            [J] 针对XXX的立场
        </div>
        <div class="content-conclusion">
            [K] 情绪分布
        </div>
        <div id="sentiment-chart" style="width:300px;height:300px;margin:0 auto" />
        <div class="title-with-button">
            <el-switch v-model="showUserRanking" @change="toggleChart" />
            <h3 style="color:rgb(2, 157, 255); font-weight: bold; margin-left:10px">舆情事件对比</h3>
        </div>
        <div id="contrast-chart" style="width:600px;height:400px; margin:0 auto" />
        <div class="content-conclusion">
            [L] 舆情事件间对比
        </div>

        <div class="title-with-button">
            <el-switch v-model="showUserRanking" @change="toggleChart" />
            <h3 style="color:rgb(2, 157, 255); font-weight: bold; margin-left:10px">舆情事件演化</h3>
        </div>

    </div>
</template>

<script>
/* eslint-disable */
import * as echarts from 'echarts'
import axios from 'axios'
import localEventTree from '@/assets/eventPage/flare.json'
import localPointChart from '@/assets/eventPage/les-miserables.json'
import userinfo_pic from '@/assets/userinfo_images/1.jpg'

export default {
    data() {
        return {
            tableData: [
                {
                    ranking1: '1',
                    ranking2: '1',
                    content: '加油！愿你平安喜乐，万事如意！在西菲律宾海！！！',
                    imgurl: userinfo_pic,
                },
                {
                    ranking1: '2',
                    ranking2: '2',
                    content: '他们会这样做，然后却没有采取任何措施来保护在西菲律宾海受到骚扰的渔民💀；同意 BA KAYO，芭比粉丝？参议员们正在呼吁禁令即将上映的《芭比娃娃》电影中，有一段场景据称展示了中国对九段线的主权主张，这引起了争议。',
                    imgurl: userinfo_pic,
                },
                {
                    ranking1: '3',
                    ranking2: '3',
                    content: '中国不仅在破坏西菲律宾海的生物多样性，还在破坏整个南海。多年来，我们我见过他们过度开采濒临灭绝的巨蛤，建造破坏珊瑚的岛屿，',
                    imgurl: userinfo_pic,
                },
                {
                    ranking1: '4',
                    ranking2: '4',
                    content: '但是那些穿着“西菲律宾海”衬衫但在 2016-2022 年期间却一动不动的人，真是可笑？',
                    imgurl: userinfo_pic,
                },
                {
                    ranking1: '5',
                    ranking2: '5',
                    content: '中国不仅非法宣称对西菲律宾海拥有主权，而且越南、马来西亚、印度尼西亚和东南亚其他地区的专属经济区！ 中国海岸警卫队在马来西亚的专属经济区内，一群马来西亚人在 Terumbu Sahap 附近捕鱼',
                    imgurl: userinfo_pic,
                },
            ],
            chartInstance: null,
            eventinfo_selected: '民进党执政不力',
            flareData: [],
            nodes: [],
            links: [],
            events: ['民进党滥用公权力', '民进党黑金政治', '民进党执政不力'],
            showEventInfo: true,
            showEventRanking: true,
            showUserInfo: true,
            showUserRanking: true
        }
    },
    mounted() {
        this.initEventTree()
        this.initTrendChart()
        this.initPieChart()
        this.initPointChart()
        this.initPointPieChart()
        this.initSentimentChart()
        this.initContrastChart()
    },
    methods: {
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
            const self = this // 保存上下文
            axios.get('https://localhost:8080/event-tree')
                .then(response => {
                    self.flareData = response.data
                })
                .catch(error => {
                    console.log('Error fetching event tree data from backend, using local data instead:', error)
                    if (localEventTree) {
                        self.flareData = localEventTree
                    } else {
                        console.log('Local event tree data is undefined.')
                    }
                })
                .finally(() => {
                    console.log('Final flareData:', self.flareData) // 这里检查最终的 flareData
                    if (self.flareData) {
                        self.chartInstance = echarts.init(document.getElementById('event-tree'))
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
                                    top: '15%',
                                    bottom: '15%',
                                    symbol: 'emptyCircle',
                                    orient: 'vertical',
                                    expandAndCollapse: true,
                                    label: {
                                        position: 'top',
                                        rotate: -90,
                                        verticalAlign: 'middle',
                                        align: 'right',
                                        fontSize: 12
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
                        }
                        self.chartInstance.setOption(option)
                    } else {
                        console.log('No data available to display the chart.')
                        document.getElementById('event-tree').innerText = 'No data available'
                    }
                })
        },

        initPointChart() {
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
                    show: false
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
        initSentimentChart() {
            const sentimentChartDom = document.getElementById('sentiment-chart')
            const sentimentChart = echarts.init(sentimentChartDom)
            sentimentChart.showLoading()
            const option = {
                angleAxis: {
                    type: 'category',
                    data: ['愤怒', '厌恶', '恐惧', '悲伤', '惊喜', '蔑视', '兴奋', '自豪']
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
        initContrastChart() {
            const contrastChartDom = document.getElementById('contrast-chart')
            const contrastChart = echarts.init(contrastChartDom)
            contrastChart.showLoading()
            const option = {
                title: {
                    text: 'Stacked Area Chart',
                    show: false
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
                if (this.showEventInfo) this.initEventChart()
                if (this.showEventRanking) this.initEventChart()
                if (this.showUserInfo) this.initUserChart()
                if (this.showUserRanking) this.initUserChart()
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

.content-conclusion {
    text-align: center;
    font-family: "Microsoft YaHei", serif;
    margin-bottom: 30px;
    font-weight: bold;
    color: deepskyblue;
    margin-top: 30px;
}

.centered-select {
    display: flex;
    justify-content: center;
    /* 水平居中 */
    align-content: center;
    /* 垂直居中（可选，视需求而定） */
    height: 100%;
    /* 如果需要整个容器高度 */
}

.light-ranking-content div {
    margin-top: 30px;
    font-size: 22px;
    color: lightgray;
    /* 标题颜色 */
    transform: skew(0, -10deg);
    /* 使文本校正回正 */
}
</style>

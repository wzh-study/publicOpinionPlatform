<template>
    <div class="app-container">
        <el-card class="box-card">
            <div slot="header" class="clearfix">
                <span>我国与菲律宾南海冲突 重大事件贴文时间轴</span>
                <el-button style="float: right; padding: 3px 0" type="text">更多信息</el-button>
            </div>
            <div ref="chart" class="chart-container"></div>
        </el-card>
    </div>
</template>
  
<script>
import * as echarts from 'echarts';
import 'element-ui/lib/theme-chalk/index.css';
import { Card, Button, Autocomplete } from 'element-ui';

export default {
    name: 'TimeLineChart',
    components: {
        'el-card': Card,
        'el-button': Button
    },
    data() {
        return {
            chart: null,
        };
    },
    mounted() {
        this.chart = echarts.init(this.$refs.chart);
        this.initializeChart();
    },
    methods: {

        initializeChart() {
            const option = {
                tooltip: {
                    trigger: 'axis',
                    formatter: '{b}<br/>{c} (次数)'
                },
                grid: {
                    left: '0%',
                    right: '0%',
                    bottom: '0%',
                    containLabel: true
                },
                xAxis: {
                    type: 'time',
                    boundaryGap: false,
                    axisLabel: {
                        formatter: function (value) {
                            return echarts.format.formatTime('yyyy-MM-dd', value);
                        }
                    },
                    name: '日期'
                },
                yAxis: {
                    type: 'value',
                    min: 0, // 纵坐标的最小值
                    max: 9000, // 纵坐标的最大值
                    interval: 1000, // 纵坐标的间隔
                    axisLabel: {
                        formatter: '{value} '
                    },
                    name: '贴文数量'
                    
                },
                series: [
                    {
                        name: '事件数量',
                        type: 'line',
                        showSymbol: true,
                        data: [
                            ['2023-06-30', 100],
                            ['2023-07-06', 200],
                            ['2023-11-11', 100],
                            ['2023-11-15', 120],
                            ['2023-12-09', 130],
                            ['2023-12-12', 170],
                            ['2024-03-23', 320],
                            ['2024-03-28', 210],
                            ['2024-04-07', 310],
                            ['2024-04-15', 440],
                            ['2024-04-22', 350],
                            ['2024-05-10', 190],
                            ['2024-05-15', 332],
                            ['2024-05-18', 445]
                        ].map(item => ({ value: [item[0], item[1]], symbol: 'circle', symbolSize: 8, itemStyle: {color: 'skyblue'} })),
                        markPoint: {
                            symbol: 'pin',
                            symbolSize: 40,
                            itemStyle: {
                                color: 'red'
                            },
                            data: [
                                { coord: ['2023-06-30', 500], name: '事件', value: '2023.6.30→7.6\n 菲律宾炒中国“危险拦截”其船只', label: {
                                    normal: {
                                        show: true,
                                        position: 'top',
                                        backgroundColor: 'rgba(52, 152, 219, 0.5)', // 半透明蓝色背景
                                        borderColor: '#3498db',
                                        borderWidth: 1,
                                        borderType: 'dashed', // 虚线边框
                                        borderRadius: 5,
                                        padding: 5,
                                        color: '#fff',
                                        formatter: function (params) {
                                        return params.value;
                                        },
                                        textStyle: {
                                        align: 'center',
                                        // width: 170, // 文本框宽度，超出部分自动换行
                                        overflow: 'break',
                                        lineHeight: 18 // 行高
                                        }
                                    }
                                }},
                                { coord: ['2023-11-11', 3000], name: '事件', value: '2023.11.1→11.5\n 日本首相访问菲并计划首次向菲提供装备', label: {
                                    normal: {
                                        show: true,
                                        position: 'left',
                                        backgroundColor: 'rgba(52, 152, 219, 0.5)', // 半透明蓝色背景
                                        borderColor: '#3498db',
                                        borderWidth: 1,
                                        borderType: 'dashed', // 虚线边框
                                        borderRadius: 5,
                                        padding: 4,
                                        color: '#fff',
                                        formatter: function (params) {
                                        return params.value;
                                        },
                                        textStyle: {
                                            fontSize:12,
                                            align: 'center',
                                            // width: 170,
                                            // width: auto, // 文本框宽度，超出部分自动换行
                                            // height: auto,
                                            overflow: 'break',
                                            lineHeight: 16 // 行高
                                        },
                                        distance: 120,  //  调整与数据点的距离
                                    }
                                }},
                                { coord: ['2023-12-09', 2800], name: '事件', value: '2023.12.9→12.12\n 菲律宾船只非法侵闯仁爱礁', label: {
                                    normal: {
                                        show: true,
                                        position: 'top',
                                        backgroundColor: 'rgba(52, 152, 219, 0.5)', // 半透明蓝色背景
                                        borderColor: '#3498db',
                                        borderWidth: 1,
                                        borderType: 'dashed', // 虚线边框
                                        borderRadius: 5,
                                        padding: 5,
                                        color: '#fff',
                                        formatter: function (params) {
                                        return params.value;
                                        },
                                        textStyle: {
                                        align: 'center',
                                        // width: 170, // 文本框宽度，超出部分自动换行
                                        overflow: 'break',
                                        lineHeight: 16 // 行高
                                        }
                                    }
                                }
                            },
                            { coord: ['2024-03-23', 3500], name: '事件', value: '2024.3.23→3.28\n 菲方非法运补船向其非法坐滩军舰实施非正常运补', label: {
                                    normal: {
                                        show: true,
                                        position: 'bottom',
                                        backgroundColor: 'rgba(52, 152, 219, 0.5)', // 半透明蓝色背景
                                        borderColor: '#3498db',
                                        borderWidth: 1,
                                        borderType: 'dashed', // 虚线边框
                                        borderRadius: 5,
                                        padding: 5,
                                        color: '#fff',
                                        formatter: function (params) {
                                        return params.value;
                                        },
                                        textStyle: {
                                        align: 'center',
                                        // width: 170, // 文本框宽度，超出部分自动换行
                                        overflow: 'break',
                                        lineHeight: 16 // 行高
                                        }
                                    }
                                }
                            },
                            { coord: ['2024-04-07', 4500], name: '事件', value: '2024.4.7一>4.15\n 菲律宾协同美日澳在南海展开联合演习', label: {
                                    normal: {
                                        show: true,
                                        position: 'top',
                                        backgroundColor: 'rgba(52, 152, 219, 0.5)', // 半透明蓝色背景
                                        borderColor: '#3498db',
                                        borderWidth: 1,
                                        borderType: 'dashed', // 虚线边框
                                        borderRadius: 5,
                                        padding: 5,
                                        color: '#fff',
                                        formatter: function (params) {
                                        return params.value;
                                        },
                                        textStyle: {
                                        align: 'center',
                                        // width: 180, // 文本框宽度，超出部分自动换行
                                        overflow: 'break',
                                        lineHeight: 16 // 行高
                                        }
                                    }
                                }
                            },
                            { coord: ['2024-04-22', 6000], name: '事件', value: '2024.4.22→5.10\n 美菲年度“肩并肩”军演结束', label: {
                                    normal: {
                                        show: true,
                                        position: 'left',
                                        backgroundColor: 'rgba(52, 152, 219, 0.5)', // 半透明蓝色背景
                                        borderColor: '#3498db',
                                        borderWidth: 1,
                                        borderType: 'dashed', // 虚线边框
                                        borderRadius: 5,
                                        padding: 5,
                                        color: '#fff',
                                        formatter: function (params) {
                                        return params.value;
                                        },
                                        textStyle: {
                                        align: 'center',
                                        // width: 180, // 文本框宽度，超出部分自动换行
                                        overflow: 'break',
                                        lineHeight: 16 // 行高
                                        },
                                        distance: 100,
                                    }
                                }
                            },
                            { coord: ['2024-05-15', 7200], name: '事件', value: '2024.5.15→5.18\n 菲律宾“船海战术”冲击黄岩岛', label: {
                                    normal: {
                                        show: true,
                                        position: 'left',
                                        backgroundColor: 'rgba(52, 152, 219, 0.5)', // 半透明蓝色背景
                                        borderColor: '#3498db',
                                        borderWidth: 1,
                                        borderType: 'dashed', // 虚线边框
                                        borderRadius: 5,
                                        padding: 5,
                                        color: '#fff',
                                        formatter: function (params) {
                                        return params.value;
                                        },
                                        textStyle: {
                                        align: 'center',
                                        width: 180, // 文本框宽度，超出部分自动换行
                                        overflow: 'break',
                                        lineHeight: 16 // 行高
                                        },
                                        distance: 100,
                                    }
                                }
                            }
                            
                            ],
                            label: {
                                show: true,
                                backgroundColor: '#3498db',
                                color: '#ffffff',
                                padding: 4,
                                borderRadius: 4
                            }
                        }
                    }
                ]
            };

            this.chart.setOption(option);
        }
    },
    beforeDestroy() {
        if (this.chart) {
            this.chart.dispose();
        }
    }
}
</script>
  
<style scoped>
.app-container {
    width: 100%;
    height: auto;
}
.box-card {
    margin: 10px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.15);
}

.chart-container {
    width: 1050px;
    /* 根据实际容器大小调整 */
    height: 650px;
}
</style>
  







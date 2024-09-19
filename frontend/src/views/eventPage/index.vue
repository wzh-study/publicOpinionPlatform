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
          <el-select
            v-model="timeinfo_selected"
            placeholder="请选择"
            @change="fetchEventData"
            class="custom-select"
          >
            <el-option
              v-for="event in times"
              :key="event"
              :label="event"
              :value="event"
            />
          </el-select>
          
        </div>
      </div>
  
      <hr>
      <div class="LesMiserables">
        <div class="title-with-button">
          <el-switch v-model="showEventTree" @change="toggleChart" style="transform: scale(1.25); "/>
          <h2 style="color:rgb(0, 157, 255); font-weight: bold; margin-left:15px">舆情事件树</h2>
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
        <el-switch v-model="showSpreadingInfo" @change="toggleChart" style="transform: scale(1.25); "/>
        <h2 style="color:rgb(2, 157, 255); font-weight: bold; margin-left:15px">舆情事件分析——传播</h2>
      </div>
      <div v-if="showSpreadingInfo">
        <div class="centered-select">
          <el-select
            v-model="eventinfo_selected.content"
            placeholder="请选择"
            @change="fetchEventData"
            class="custom-select"
          >
            <el-option
              v-for="event in events.content"
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
        <div>
          <el-steps :active="active">
            <el-step>
              <i class="step01" slot="icon"></i>
            </el-step>
            
            <el-step >
              <i class="step01" slot="icon"></i>
            </el-step>
  
            <el-step>
              <i class="step01" slot="icon"></i>
            </el-step>
              
            <el-step >
              <i class="step01" slot="icon"></i>
            </el-step>
          </el-steps>
        </div>
        
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
              v-model="eventinfo_selected.content"
              placeholder="请选择"
              @change="fetchEventData"
              class="custom-select"
            >
              <el-option
                v-for="event in events.content"
                :key="event"
                :label="event"
                :value="event"
              />
            </el-select>
          </div>
          <div class="content-conclusion">
            [E.1] 基于传播
          </div>
          <div>
            <el-row class="demo-avatar demo-basic" style="display: flex;" type="flex" justify="center">
              <el-col :span="3" >
                <div class="sub-title">大V用户（出度）</div>
  
                <div class="demo-basic--circle" style="display: flex;flex-wrap: wrap;">
                  
                  <div class="block" v-for="size in sizeList" :key="size" style="margin-right: 5px;text-align: center;m">
                    <el-avatar :size="medium" :src="circleUrl.content.content"></el-avatar>
                  </div>
                  
                </div>
                <div class="demo-basic--circle" style="display: flex;flex-wrap: wrap;">
                  
                  <div class="block" v-for="size in sizeList" :key="size" style="margin-right: 5px;text-align: center;m">
                    <el-avatar :size="medium" :src="circleUrl.content"></el-avatar>
                  </div>
                  
                </div>
                <div class="demo-basic--circle" style="display: flex;flex-wrap: wrap;">
                  
                  <div class="block" v-for="size in sizeList" :key="size" style="margin-right: 5px;text-align: center;m">
                    <el-avatar :size="medium" :src="circleUrl.content"></el-avatar>
                  </div>
                  
                </div>
              </el-col>
              
              
              <el-col :span="3">
                <div class="sub-title">大V用户（高阶互动）</div>
  
                <div class="demo-basic--circle" style="display: flex ;flex-wrap: wrap;">
                  
                  <div class="block" v-for="size in sizeList" :key="size" style="margin-right: 5px;text-align: center;m">
                    <el-avatar :size="medium" :src="circleUrl.content"></el-avatar>
                  </div>
                  
                </div>
                <div class="demo-basic--circle" style="display: flex;flex-wrap: wrap;">
                  
                  <div class="block" v-for="size in sizeList" :key="size" style="margin-right: 5px;text-align: center;">
                    <el-avatar :size="medium" :src="circleUrl.content"></el-avatar>
                  </div>
                  
                </div>
                <div class="demo-basic--circle" style="display: flex;flex-wrap: wrap;">
                  
                  <div class="block" v-for="size in sizeList" :key="size" style="margin-right: 5px;text-align: center;">
                    <el-avatar :size="medium" :src="circleUrl.content"></el-avatar>
                  </div>
                  
                </div>
              </el-col>  
  
  
              <el-col :span="3">
                <div class="sub-title" >&nbsp;&nbsp;&nbsp;&nbsp;桥梁用户</div>
  
                <div class="demo-basic--circle" style="display:flex;flex-wrap: wrap;">
                  
                  <div class="block" v-for="size in sizeList" :key="size" style="margin-right: 5px;text-align: center;m">
                    <el-avatar :size="medium" :src="circleUrl.content"></el-avatar>
                  </div>
                  
                </div>
                <div class="demo-basic--circle" style="display: flex;flex-wrap: wrap;">
                  
                  <div class="block" v-for="size in sizeList" :key="size" style="margin-right: 5px;text-align: center;m">
                    <el-avatar :size="medium" :src="circleUrl.content"></el-avatar>
                  </div>
                  
                </div>
                <div class="demo-basic--circle" style="display: flex;flex-wrap: wrap;">
                  
                  <div class="block" v-for="size in sizeList" :key="size" style="margin-right: 5px;text-align: center;m">
                    <el-avatar :size="medium" :src="circleUrl.content"></el-avatar>
                  </div>
                  
                </div>
              </el-col>
              
              <el-col :span="3">
                <div class="sub-title" >疑似水军用户（入度）</div>
  
                <div class="demo-basic--circle" style="display:flex;flex-wrap: wrap;">
                  
                  <div class="block" v-for="size in sizeList" :key="size" style="margin-right: 5px;text-align: center;m">
                    <el-avatar :size="medium" :src="circleUrl.content"></el-avatar>
                  </div>
                  
                </div>
                <div class="demo-basic--circle" style="display: flex;flex-wrap: wrap;">
                  
                  <div class="block" v-for="size in sizeList" :key="size" style="margin-right: 5px;text-align: center;m">
                    <el-avatar :size="medium" :src="circleUrl.content"></el-avatar>
                  </div>
                  
                </div>
                <div class="demo-basic--circle" style="display: flex;flex-wrap: wrap;">
                  
                  <div class="block" v-for="size in sizeList" :key="size" style="margin-right: 5px;text-align: center;m">
                    <el-avatar :size="medium" :src="circleUrl.content"></el-avatar>
                  </div>
                  
                </div>
              </el-col> 
  
  
              <el-col :span="3">
                <div class="sub-title" >疑似水军用户（共互动）</div>
  
                <div class="demo-basic--circle" style="display:flex;flex-wrap: wrap;">
                  
                  <div class="block" v-for="size in sizeList" :key="size" style="margin-right: 5px;text-align: center;m">
                    <el-avatar :size="medium" :src="circleUrl.content"></el-avatar>
                  </div>
                  
                </div>
                <div class="demo-basic--circle" style="display: flex;flex-wrap: wrap;">
                  
                  <div class="block" v-for="size in sizeList" :key="size" style="margin-right: 5px;text-align: center;m">
                    <el-avatar :size="medium" :src="circleUrl.content"></el-avatar>
                  </div>
                  
                </div>
                <div class="demo-basic--circle" style="display: flex;flex-wrap: wrap;">
                  
                  <div class="block" v-for="size in sizeList" :key="size" style="margin-right: 5px;text-align: center;m">
                    <el-avatar :size="medium" :src="circleUrl.content"></el-avatar>
                  </div>
                  
                </div>
              </el-col> 
              
              
            </el-row>
          </div>
  
          <div class="content-conclusion">
            [E.2] 基于内容
          </div>
          <div>
            <el-row class="demo-avatar demo-basic" style="display: flex;" type="flex" justify="center">
              <el-col :span="3" >
                <div class="sub-title">高产用户（原贴）</div>     
  
                <div class="demo-basic--circle" style="display: flex;flex-wrap: wrap;">
                  
                  <div class="block" v-for="size in sizeList" :key="size" style="margin-right: 5px;text-align: center;m">
                    <el-avatar :size="medium" :src="circleUrl.content"></el-avatar>
                  </div>
                  
                </div>
                <div class="demo-basic--circle" style="display: flex;flex-wrap: wrap;">
                  
                  <div class="block" v-for="size in sizeList" :key="size" style="margin-right: 5px;text-align: center;m">
                    <el-avatar :size="medium" :src="circleUrl.content"></el-avatar>
                  </div>
                  
                </div>
                <div class="demo-basic--circle" style="display: flex;flex-wrap: wrap;">
                  
                  <div class="block" v-for="size in sizeList" :key="size" style="margin-right: 5px;text-align: center;m">
                    <el-avatar :size="medium" :src="circleUrl.content"></el-avatar>
                  </div>
                  
                </div>
              </el-col>
              
              
              <el-col :span="3">
                <div class="sub-title">高产用户（转贴）</div>
  
                <div class="demo-basic--circle" style="display: flex;flex-wrap: wrap;">
                  
                  <div class="block" v-for="size in sizeList" :key="size" style="margin-right: 5px;text-align: center;m">
                    <el-avatar :size="medium" :src="circleUrl.content"></el-avatar>
                  </div>
                  
                </div>
                <div class="demo-basic--circle" style="display: flex;flex-wrap: wrap;">
                  
                  <div class="block" v-for="size in sizeList" :key="size" style="margin-right: 5px;text-align: center;">
                    <el-avatar :size="medium" :src="circleUrl.content"></el-avatar>
                  </div>
                  
                </div>
                <div class="demo-basic--circle" style="display: flex;flex-wrap: wrap;">
                  
                  <div class="block" v-for="size in sizeList" :key="size" style="margin-right: 5px;text-align: center;">
                    <el-avatar :size="medium" :src="circleUrl.content"></el-avatar>
                  </div>
                  
                </div>
              </el-col>  
  
  
              <el-col :span="3">
                <div class="sub-title" >高产用户（评论）</div>
  
                <div class="demo-basic--circle" style="display:flex;flex-wrap: wrap;">
                  
                  <div class="block" v-for="size in sizeList" :key="size" style="margin-right: 5px;text-align: center;m">
                    <el-avatar :size="medium" :src="circleUrl.content"></el-avatar>
                  </div>
                  
                </div>
                <div class="demo-basic--circle" style="display: flex;flex-wrap: wrap;">
                  
                  <div class="block" v-for="size in sizeList" :key="size" style="margin-right: 5px;text-align: center;m">
                    <el-avatar :size="medium" :src="circleUrl.content"></el-avatar>
                  </div>
                  
                </div>
                <div class="demo-basic--circle" style="display: flex;flex-wrap: wrap;">
                  
                  <div class="block" v-for="size in sizeList" :key="size" style="margin-right: 5px;text-align: center;m">
                    <el-avatar :size="medium" :src="circleUrl.content"></el-avatar>
                  </div>
                  
                </div>
              </el-col>
              
              <el-col :span="3">
                <div class="sub-title" >敌意言论</div>
  
                <div class="demo-basic--circle" style="display: flex ;flex-wrap: wrap;">
                  
                  <div class="block" v-for="size in sizeList" :key="size" style="margin-right: 5px;text-align: center;m">
                    <el-avatar :size="medium" :src="circleUrl.content"></el-avatar>
                  </div>
                  
                </div>
                <div class="demo-basic--circle" style="display: flex;flex-wrap: wrap;">
                  
                  <div class="block" v-for="size in sizeList" :key="size" style="margin-right: 5px;text-align: center;m">
                    <el-avatar :size="medium" :src="circleUrl.content"></el-avatar>
                  </div>
                  
                </div>
                <div class="demo-basic--circle" style="display: flex;flex-wrap: wrap;">
                  
                  <div class="block" v-for="size in sizeList" :key="size" style="margin-right: 5px;text-align: center;m">
                    <el-avatar :size="medium" :src="circleUrl.content"></el-avatar>
                  </div>
                  
                </div>
              </el-col> 
  
  
              <el-col :span="3">
                <div class="sub-title" >激情情绪用户</div>
  
                <div class="demo-basic--circle" style="display:flex;flex-wrap: wrap;">
                  
                  <div class="block" v-for="size in sizeList" :key="size" style="margin-right: 5px;text-align: center;m">
                    <el-avatar :size="medium" :src="circleUrl.content"></el-avatar>
                  </div>
                  
                </div>
                <div class="demo-basic--circle" style="display: flex;flex-wrap: wrap;">
                  
                  <div class="block" v-for="size in sizeList" :key="size" style="margin-right: 5px;text-align: center;m">
                    <el-avatar :size="medium" :src="circleUrl.content"></el-avatar>
                  </div>
                  
                </div>
                <div class="demo-basic--circle" style="display: flex;flex-wrap: wrap;">
                  
                  <div class="block" v-for="size in sizeList" :key="size" style="margin-right: 5px;text-align: center;m">
                    <el-avatar :size="medium" :src="circleUrl.content"></el-avatar>
                  </div>
                  
                </div>
              </el-col> 
              
              
            </el-row>
          </div>
  
          <el-row :gutter="50">
            
            <el-col :xs="4" :sm="6" :md="8" :lg="9" :xl="10">
              <div >
                <el-col :span="13">
                      
                </el-col> 
                <div class="content-conclusion">
                  [E.3] 算法
                </div>
                <div>
                  <el-row class="demo-avatar demo-basic" style="display: flex;" type="flex" justify="center">
                    <el-col :span="13">
                      
                    </el-col> 
                    <el-col :span="7">
                      <div class="sub-title" >PageRank</div>
  
                      <div class="demo-basic--circle" style="display:flex;flex-wrap: wrap;">
                        
                        <div class="block" v-for="size in sizeList" :key="size" style="margin-right: 5px;text-align: center;m">
                          <el-avatar :size="medium" :src="circleUrl.content"></el-avatar>
                        </div>
                        
                      </div>
                      <div class="demo-basic--circle" style="display: flex;flex-wrap: wrap;">
                        
                        <div class="block" v-for="size in sizeList" :key="size" style="margin-right: 5px;text-align: center;m">
                          <el-avatar :size="medium" :src="circleUrl.content"></el-avatar>
                        </div>
                        
                      </div>
                      <div class="demo-basic--circle" style="display: flex;flex-wrap: wrap;">
                        
                        <div class="block" v-for="size in sizeList" :key="size" style="margin-right: 5px;text-align: center;m">
                          <el-avatar :size="medium" :src="circleUrl.content"></el-avatar>
                        </div>
                        
                      </div>
                    </el-col> 
  
  
                    <el-col :span="8">
                      <div class="sub-title" >HITS</div>
  
                      <div class="demo-basic--circle" style="display:flex;flex-wrap: wrap;">
                        
                        <div class="block" v-for="size in sizeList" :key="size" style="margin-right: 5px;text-align: center;m">
                          <el-avatar :size="medium" :src="circleUrl.content"></el-avatar>
                        </div>
                        
                      </div>
                      <div class="demo-basic--circle" style="display: flex;flex-wrap: wrap;">
                        
                        <div class="block" v-for="size in sizeList" :key="size" style="margin-right: 5px;text-align: center;m">
                          <el-avatar :size="medium" :src="circleUrl.content"></el-avatar>
                        </div>
                        
                      </div>
                      <div class="demo-basic--circle" style="display: flex;flex-wrap: wrap;">
                        
                        <div class="block" v-for="size in sizeList" :key="size" style="margin-right: 5px;text-align: center;m">
                          <el-avatar :size="medium" :src="circleUrl.content"></el-avatar>
                        </div>
                        
                      </div>
                    </el-col> 
                    
                  </el-row>
                  <el-row class="demo-avatar demo-basic" style="display: flex;" type="flex" justify="center">
                    <el-col :span="13">
                      
                    </el-col> 
                    <el-col :span="7">
                      <div class="sub-title" >Info Index</div>
  
                      <div class="demo-basic--circle" style="display:flex;flex-wrap: wrap;">
                        
                        <div class="block" v-for="size in sizeList" :key="size" style="margin-right: 5px;text-align: center;m">
                          <el-avatar :size="medium" :src="circleUrl.content"></el-avatar>
                        </div>
                        
                      </div>
                      <div class="demo-basic--circle" style="display: flex;flex-wrap: wrap;">
                        
                        <div class="block" v-for="size in sizeList" :key="size" style="margin-right: 5px;text-align: center;m">
                          <el-avatar :size="medium" :src="circleUrl.content"></el-avatar>
                        </div>
                        
                      </div>
                      <div class="demo-basic--circle" style="display: flex;flex-wrap: wrap;">
                        
                        <div class="block" v-for="size in sizeList" :key="size" style="margin-right: 5px;text-align: center;m">
                          <el-avatar :size="medium" :src="circleUrl.content"></el-avatar>
                        </div>
                        
                      </div>
                    </el-col> 
  
  
                    <el-col :span="8">
                      <div class="sub-title" >Coreness</div>
  
                      <div class="demo-basic--circle" style="display:flex;flex-wrap: wrap;">
                        
                        <div class="block" v-for="size in sizeList" :key="size" style="margin-right: 5px;text-align: center;m">
                          <el-avatar :size="medium" :src="circleUrl.content"></el-avatar>
                        </div>
                        
                      </div>
                      <div class="demo-basic--circle" style="display: flex;flex-wrap: wrap;">
                        
                        <div class="block" v-for="size in sizeList" :key="size" style="margin-right: 5px;text-align: center;m">
                          <el-avatar :size="medium" :src="circleUrl.content"></el-avatar>
                        </div>
                        
                      </div>
                      <div class="demo-basic--circle" style="display: flex;flex-wrap: wrap;">
                        
                        <div class="block" v-for="size in sizeList" :key="size" style="margin-right: 5px;text-align: center;m">
                          <el-avatar :size="medium" :src="circleUrl.content"></el-avatar>
                        </div>
                        
                      </div>
                    </el-col> 
                    
                  </el-row>
  
                  <el-row class="demo-avatar demo-basic" style="display: flex;" type="flex" justify="center">
                    <el-col :span="13">
                      
                    </el-col> 
                    <el-col :span="7">
                      <div class="sub-title" >Eigenvector Centrality</div>
  
                      <div class="demo-basic--circle" style="display:flex;flex-wrap: wrap;">
                        
                        <div class="block" v-for="size in sizeList" :key="size" style="margin-right: 5px;text-align: center;m">
                          <el-avatar :size="medium" :src="circleUrl.content"></el-avatar>
                        </div>
                        
                      </div>
                      <div class="demo-basic--circle" style="display: flex;flex-wrap: wrap;">
                        
                        <div class="block" v-for="size in sizeList" :key="size" style="margin-right: 5px;text-align: center;m">
                          <el-avatar :size="medium" :src="circleUrl.content"></el-avatar>
                        </div>
                        
                      </div>
                      <div class="demo-basic--circle" style="display: flex;flex-wrap: wrap;">
                        
                        <div class="block" v-for="size in sizeList" :key="size" style="margin-right: 5px;text-align: center;m">
                          <el-avatar :size="medium" :src="circleUrl.content"></el-avatar>
                        </div>
                        
                      </div>
                    </el-col> 
  
  
                    <el-col :span="8">
                      <div class="sub-title" >Katz Centrality</div>
  
                      <div class="demo-basic--circle" style="display:flex;flex-wrap: wrap;">
                        
                        <div class="block" v-for="size in sizeList" :key="size" style="margin-right: 5px;text-align: center;m">
                          <el-avatar :size="medium" :src="circleUrl.content"></el-avatar>
                        </div>
                        
                      </div>
                      <div class="demo-basic--circle" style="display: flex;flex-wrap: wrap;">
                        
                        <div class="block" v-for="size in sizeList" :key="size" style="margin-right: 5px;text-align: center;m">
                          <el-avatar :size="medium" :src="circleUrl.content"></el-avatar>
                        </div>
                        
                      </div>
                      <div class="demo-basic--circle" style="display: flex;flex-wrap: wrap;">
                        
                        <div class="block" v-for="size in sizeList" :key="size" style="margin-right: 5px;text-align: center;m">
                          <el-avatar :size="medium" :src="circleUrl.content"></el-avatar>
                        </div>
                        
                      </div>
                    </el-col> 
                    
                  </el-row>
  
                  <el-row class="demo-avatar demo-basic" style="display: flex;" type="flex" justify="center">
                    <el-col :span="13">
                      
                    </el-col> 
                    <el-col :span="7">
                      <div class="sub-title" >Degree Centrality</div>
  
                      <div class="demo-basic--circle" style="display:flex;flex-wrap: wrap;">
                        
                        <div class="block" v-for="size in sizeList" :key="size" style="margin-right: 5px;text-align: center;m">
                          <el-avatar :size="medium" :src="circleUrl.content"></el-avatar>
                        </div>
                        
                      </div>
                      <div class="demo-basic--circle" style="display: flex;flex-wrap: wrap;">
                        
                        <div class="block" v-for="size in sizeList" :key="size" style="margin-right: 5px;text-align: center;m">
                          <el-avatar :size="medium" :src="circleUrl.content"></el-avatar>
                        </div>
                        
                      </div>
                      <div class="demo-basic--circle" style="display: flex;flex-wrap: wrap;">
                        
                        <div class="block" v-for="size in sizeList" :key="size" style="margin-right: 5px;text-align: center;m">
                          <el-avatar :size="medium" :src="circleUrl.content"></el-avatar>
                        </div>
                        
                      </div>
                    </el-col> 
  
  
                    <el-col :span="8">
                      <div class="sub-title" >疑似水军用户（共互动）</div>
  
                      <div class="demo-basic--circle" style="display:flex;flex-wrap: wrap;">
                        
                        <div class="block" v-for="size in sizeList" :key="size" style="margin-right: 5px;text-align: center;m">
                          <el-avatar :size="medium" :src="circleUrl.content"></el-avatar>
                        </div>
                        
                      </div>
                      <div class="demo-basic--circle" style="display: flex;flex-wrap: wrap;">
                        
                        <div class="block" v-for="size in sizeList" :key="size" style="margin-right: 5px;text-align: center;m">
                          <el-avatar :size="medium" :src="circleUrl.content"></el-avatar>
                        </div>
                        
                      </div>
                      <div class="demo-basic--circle" style="display: flex;flex-wrap: wrap;">
                        
                        <div class="block" v-for="size in sizeList" :key="size" style="margin-right: 5px;text-align: center;m">
                          <el-avatar :size="medium" :src="circleUrl.content.content"></el-avatar>
                        </div>
                        
                      </div>
                    </el-col> 
                    
                  </el-row>
                </div>
              </div>
            </el-col>
  
  
            <el-col :xs="4" :sm="6" :md="8" :lg="9" :xl="10">
              <div >
                <table class="sentiment-table" style="width: 500px;margin:30px auto;">
                <tr class="header-row">
                  <td colspan="3">原贴个数</td>
                  <td colspan="3">转帖个数</td>
                  <td colspan="3">被转次数</td>
                  <td colspan="3">被评次数</td>
                  <td colspan="3">被赞次数</td>
                </tr>
                <tr class="content-row">
                  <td colspan="3" height="40px">  </td>
                  <td colspan="3" height="40px">  </td>
                  <td colspan="3" height="40px">  </td>
                  <td colspan="3" height="40px">  </td>
                  <td colspan="3" height="40px">  </td>
                </tr>
                
              </table>
              <div class="content-conclusion">用户参与事件概况</div>
  
              <table class="sentiment-table" style="width: 500px;margin:30px auto;">
                <tr class="header-row">
                  <td colspan="3">排名</td>
                  <td colspan="12">高频传播内容</td>
                
                </tr>
                <tr class="content-row">
                  <td colspan="3" height="40px">2024-07-01</td>
                  <td colspan="12" height="40px"> 他们会这样做，然后却没有采取任何措施来保护在西菲律宾海受到骚扰的渔民💀；同意 BA KAYO，芭比粉丝？参议员们正在呼吁禁令即将上映的《芭比娃娃》电影中，有一段场景据称展示了中国对九段线的主权主张，这引起了争议。 </td>
                 
                </tr>
                
              </table>
              <div class="content-conclusion">用户参与事件内容</div>
                <div id="spreading-chart" style="width:400px;height:400px;margin:30px auto" />
              </div>
            </el-col>
           
          </el-row>
  
  
  
        
       
      </div>
      <hr>
  
      <div class="title-with-button">
        <el-switch v-model="showContentInfo" @change="toggleChart" style="transform: scale(1.25); "/>
        <h2 style="color:rgb(2, 157, 255); font-weight: bold; margin-left:15px">舆情事件分析——内容</h2>
      </div>
      <div v-if="showContentInfo">
        <el-row>
          <el-col :span="24">
            <div class="centered-select">
              <el-select
                v-model="eventinfo_selected.content"
                placeholder="请选择"
                @change="fetchEventData"
                class="custom-select"
              >
                <el-option
                  v-for="event in events.content"
                  :key="event"
                  :label="event"
                  :value="event"
                />
              </el-select>
            </div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-table :data="tableDataWithTime.content" border style="width: 950px;margin:30px auto;" :row-class-name="getRowClassName">
              <el-table-column prop="ranking1" label="排名" width="50px" />
              <el-table-column prop="ranking2" label="排名*" width="50px" />
              <el-table-column prop="content" label="高频传播内容" width="810px" />
              <el-table-column prop="origin" label="源头" width="40px" />
            </el-table>
            <div class="content-conclusion">
              [F] 高频传播内容
            </div>
          </el-col>
          
        </el-row>
        
        <el-row :gutter="10" style="margin-top: 30px;" type="flex" justify="center">
          <el-col :span="7">
            <div ref="chart4" style="width:400px;height:400px;margin-bottom: 20px;"></div>
            <div class="cloudMap">
              [G.1] 高频词云图
            </div>
          </el-col>
          <el-col :span="10">
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
          </el-col>
          
        </el-row>
       
        
        
  
  
        
        
      </div>
      <hr>
  
  
      <div class="title-with-button">
        <el-switch v-model="showSentimentInfo" @change="toggleChart" style="transform: scale(1.25); "/>
        <h2 style="color:rgb(2, 157, 255); font-weight: bold; margin-left:15px">舆情事件分析——语义</h2>
      </div>
      <div v-if="showSentimentInfo">
        <div class="centered-select">
          <el-select
            v-model="eventinfo_selected.content"
            placeholder="请选择"
            @change="fetchEventData"
            class="custom-select"
          >
            <el-option
              v-for="event in events.content"
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
          <div id="point-pie-chart" style="width:700px;height:400px; margin-left: 0" />
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
          <div id="sentiment-chart" style="width:700px;height:400px;margin-left:0" />
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
          <el-switch v-model="showContrastInfo" @change="toggleChart" style="transform: scale(1.25); "/>
          <h2 style="color:rgb(2, 157, 255); font-weight: bold; margin-left:15px">舆情事件对比</h2>
        </div>
      <div v-if="showContrastInfo">
        <div id="contrast-chart" style="width:1000px;height:400px; margin:0 auto" />
        <div class="content-conclusion">
          [L] 舆情事件间对比
        </div>
      </div>
      <hr>
  
  
      <div class="title-with-button">
        <el-switch v-model="showEvolutionInfo" @change="toggleChart" style="transform: scale(1.25); "/>
        <h2 style="color:rgb(2, 157, 255); font-weight: bold; margin-left:15px">舆情事件演化</h2>
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
  import "echarts-wordcloud/dist/echarts-wordcloud";
  import "echarts-wordcloud/dist/echarts-wordcloud.min";
  
  import avatarURL from "../../assets/QunZu/group_users_images/demo.jpg";
  
  export default {
    data() {
      return {
        startDate: '2021-01-01',
        endDate: '2021-12-31',
        tableDataWithTime:{
          content:[
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
        time:2024-9-9
        },
        chartInstanceWithTime: {
          content: null,
          time: 2024-9-9
        },
        eventinfo_selected:{
          content:'民进党执政不力',
  
          time:2024-9-9
        },
        timeinfo_selected:'近一天',
        flareData:{
          content:null,
          time:2024-9-9
        },
        nodes:[],
        links:[],
        events:{
          content:['民进党滥用公权力','民进党黑金政治','民进党执政不力'],
          time:2024-9-9
        },
        times:[
          '近一天',
          '近三天',
          '近七天'
        ],
        showEventTree:true,
        showSpreadingInfo:true,
        showUserInfo:true,
        showContentInfo:true,
        showSentimentInfo:true,
        showContrastInfo:true,
        showEvolutionInfo:true,
        
        circleUrl:{
          content: avatarURL,
          time: 2024-9-9
        },
        sizeList: ["large", "medium", "small"],
        sizeList2: ["medium"]
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
      this.showWordCloudWithHighFrequencyWords()
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
            this.chartInstanceWithTime.content = echarts.init(document.getElementById('overview'));
            const option = {
              legend: {
                data: ['民进党执政不力', '民进党滥用公权', '民进党黑金政治', '国民党', '其他']
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
            this.chartInstanceWithTime.content.setOption(option);
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
            this.chartInstanceWithTime.content = echarts.init(document.getElementById('spreading-chart'));
            const option = {
              legend: {
                data: ['民进党执政不力', '民进党滥用公权', '民进党黑金政治', '国民党', 'Other']
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
            this.chartInstanceWithTime.content.setOption(option);
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
            data: ['用户总声量', '新用户数', '同比上月增长声量']
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
              name: '用户总声量',
              position: 'right',
              alignTicks: true,
              axisLine: {
                show: false,
                lineStyle: {
                  color: colors[0]
                }
              },
              axisLabel: {
                formatter: '{value} w'
              }
            },
            {
              type: 'value',
              name: '新用户数',
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
                formatter: '{value} k'
              }
            },
            {
              type: 'value',
              name: '同比上月增长声量',
              position: 'left',
              alignTicks: true,
              axisLine: {
                show: false,
                lineStyle: {
                  color: colors[2]
                }
              },
              axisLabel: {
                formatter: '{value} %'
              }
            }
          ],
          series: [
            {
              name: '用户总声量',
              type: 'bar',
              data: [
                2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3
              ]
            },
            {
              name: '新用户数',
              type: 'bar',
              yAxisIndex: 1,
              data: [
                2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3
              ]
            },
            {
              name: '同比上月增长声量',
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
        //   title: {
        //     text: 'Referer of a Website',
        //     subtext: 'Fake Data',
        //     left: 'center'
        //   },
          tooltip: {
            trigger: 'item'
          },
          legend: {
            orient: 'vertical',
            left: 'left'
          },
          series: [
            {
              name: '转发链路跳数统计',
              type: 'pie',
              radius: '50%',
              data: [
                { value: 1048, name: '1跳转发' },
                { value: 735, name: '原发' },
                { value: 580, name: '2跳转发' },
                { value: 484, name: '3跳转发' },
                { value: 300, name: '4跳转发' }
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
            self.flareData.content = response.data;
            addDepthInfo(self.flareData.content); // 为每个节点添加depth信息
          })
          .catch(error => {
            console.log('Error fetching event tree data from backend, using local data instead:', error);
            if (localEventTree) {
              self.flareData.content = localEventTree;
              addDepthInfo(self.flareData.content); // 为本地数据添加depth信息
            } else {
              console.log('Local event tree data is undefined.');
            }
          })
          .finally(() => {
            if (self.flareData.content) {
              const chartElement = document.getElementById('event-tree');
              chartElement.style.height = `${window.innerWidth * 0.5}px`;
  
              self.chartInstanceWithTime.content= echarts.init(chartElement);
              const option = {
                tooltip: {
                  trigger: 'item',
                  triggerOn: 'mousemove'
                },
                series: [
                  {
                    type: 'tree',
                    data: [self.flareData.content],
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
              self.chartInstanceWithTime.content.setOption(option);
  
              window.addEventListener('resize', () => {
                chartElement.style.height = `${window.innerWidth * 0.5}px`;
                self.chartInstanceWithTime.content.resize();
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
            this.chartInstanceWithTime.content = echarts.init(document.getElementById('point-chart'))
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
            this.chartInstanceWithTime.content.setOption(option)
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
            data: ['西菲律宾海', '捍卫西菲律宾海主权', '某国否认一个中国原则', '乌克兰突袭俄罗斯', 'Other']
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
      },
      showWordCloudWithHighFrequencyWords(){
        var chart4 = echarts.init(this.$refs.chart4);
        var data4 = [
             {'name': '香港', 'value': 118},
             {'name': '台湾', 'value': 89},
             {'name': '上海', 'value': 56},
             {'name': '广东', 'value': 51},
             {'name': '云南', 'value': 46},
             {'name': '四川', 'value': 30},
             {'name': '福建', 'value': 25},
             {'name': '浙江', 'value': 22},
             {'name': '海南', 'value': 17},
             {'name': '江苏', 'value': 8},
             {'name': '天津', 'value': 7},
             {'name': '山西', 'value': 7},
             {'name': '广西', 'value': 7},
             {'name': '陕西', 'value': 6},
             {'name': '湖北', 'value': 6},
             {'name': '重庆', 'value': 6},
             {'name': '内蒙古', 'value': 4},
             {'name': '湖南', 'value': 4},
             {'name': '山东', 'value': 3},
             {'name': '北京', 'value': 2},
             {'name': '河南', 'value': 1},
             {'name': '甘肃', 'value': 1},
             {'name': '西藏', 'value': 0},
             {'name': '吉林', 'value': 0},
             {'name': '河北', 'value': 0},
             {'name': '青海', 'value': 0},
             {'name': '澳门', 'value': 0},
             {'name': '新疆', 'value': 10},
             {'name': '辽宁', 'value': 0},
             {'name': '安徽', 'value': 0},
             {'name': '黑龙江', 'value': 0},
             {'name': '贵州', 'value': 0},
             {'name': '江西', 'value': 0},
             {'name': '宁夏', 'value': 0}
             ];
  
             var chart4Option = {
              title: {
                  // text: '各市现有确诊-词云',//标题
                  x: 'center',
                  textStyle: {
                      fontSize: 23
                  }
     
              },
              backgroundColor: '#fff',
              tooltip: {
                  show: true
              },
              series: [
                  {
                      name: '各市现有确诊',//数据提示窗标题
                      type: 'wordCloud',
                      sizeRange: [12, 64],//画布范围，如果设置太大会出现少词（溢出屏幕）
                      rotationRange: [-45, 90],//数据翻转范围
                      //shape: 'circle',
                      textPadding: 0,
                      autoSize: {
                          enable: true,
                          minSize: 6
                      },
                      textStyle: {
                          normal: {
                              color: function() {
                                  console.log('rgb(' + [
                                      Math.round(Math.random() * 160),
                                      Math.round(Math.random() * 160),
                                      Math.round(Math.random() * 160)
                                  ].join(',') + ')')
                                  return 'rgb(' + [
                                      Math.round(Math.random() * 160),
                                      Math.round(Math.random() * 160),
                                      Math.round(Math.random() * 160)
                                  ].join(',') + ')';
                              }
                          },
                          emphasis: {
                              shadowBlur: 10,
                              shadowColor: '#333'
                          }
                      },
                  data: data4,
              }]
          };
          chart4.setOption(chart4Option);
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
  .content-conclusion2 {
    
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
    width:500px;
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
  .step01, .step02, .step03, .step04{
    width: 35px;
    height: 35px;
    background-size: 100% 100%;
  }
  .step01{
    background-image: url("../../assets/QunZu/group_users_images/cnn.png");
  }
  .step02{
    background-image: url("../../assets/QunZu/group_users_images/demo.jpg");
  }
  .step03{
    background-image: url("../../assets/QunZu/group_users_images/leader.png");
  }
  .step04{
    background-image: url("../../assets/QunZu/group_users_images/LQD.jpg");
  }
   .el-steps{
    width: 100%;
    .el-step.is-horizontal .el-step__line{
      top: 50%;
      left: 0px;
      right: 0px;
    }
    .el-step__head.is-process{
      border-color:  #285edf;
    }
    .el-step__head.is-finish{
      border-color: #285edf;
    }
    .el-step__title.is-process{
      color:  #285edf;
    }
    .el-step__title.is-finish{
      color: #285edf;
    }
    .el-step__icon{
      width:56px;
      height:56px;
    }
  
  
    .is-finish .step02{
      background-image: url("../../assets/QunZu/group_users_images/demo.jpg");
    }
    .is-finish .step03{
      background-image: url("../../assets/QunZu/group_users_images/leader.png");
    }
    .is-finish .step04{
      background-image: url("../../assets/QunZu/group_users_images/LQD.jpg");
    }
   
  }
  
  
  
  </style>
  
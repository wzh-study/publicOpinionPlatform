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

        <div class="userinfo">
            <div class="title-with-button">
                <el-switch v-model="showUserInfo" style="transform: scale(1.25);" />
                <h2 style="color:rgb(2, 157, 255); font-weight: bold; margin-left:10px">用户的基本信息</h2>
            </div>

            <div v-show="showUserInfo">
                <el-row style="margin-top: 0; width: 100%; display: flex; flex-wrap: wrap;">
                    <!-- User Information Container -->
                    <el-col :span="14" style="display: flex; flex-direction: column; justify-content: center; ">
                        <div class="user-info-container">
                            <el-row style="display: flex; justify-content: space-between;">
                                <el-col :span="11" style="display: flex; align-items: center; justify-content: center;">
                                    <span style="margin-right: 16px;">用户名: </span>
                                    <el-select v-model="userinfo_selected" placeholder="请选择" @change="fetchUserData">
                                        <el-option v-for="username in usernames" :key="username" :label="username"
                                            :value="username" />
                                    </el-select>
                                </el-col>

                                <el-col :span="11" style="display: flex; align-items: center; justify-content: center;">
                                    <span style="margin-right: 16px;">用户头像: </span>
                                    <img :src="userProfile.imageUrl"
                                        style="border-radius: 50%; width: 120px; height: 120px;">
                                </el-col>
                            </el-row>

                            <!-- User Data Table -->
                            <el-table class="custom-el-table" :data="tableRows" style="margin-top: 100px;">
                                <el-table-column v-for="(item, index) in tableData" :key="index" :prop="'col' + index"
                                    :label="item.key" :cell-style="{ background: 'yellow' }" />
                            </el-table>

                            <div class="content-conclusion">
                                [B] 活跃与影响面
                            </div>
                        </div>
                    </el-col>

                    <!-- Right Side (40% width) -->
                    <el-col :span="9" style="margin-left: 20px; padding-left: 10px; box-sizing: border-box;">
                        <el-collapse v-model="activeNames"
                            style="width: 100%; max-height: 400px; overflow-y: auto; ; font-size: 12px;">
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
                        <div class="content-conclusion">
                            [A] 基础信息
                        </div>
                    </el-col>

                </el-row>
            </div>
        </div>

        <hr>

        <div class="UserBehaviourRegular">
            <div class="title-with-button">
                <el-switch v-model="showBehaviourRegular" style="transform: scale(1.25);" />
                <h2 style="color:rgb(2, 157, 255); font-weight: bold; margin-left:15px">用户行为规律</h2>
            </div>
            <div class="UserBehaviourRegular-container" v-show="showBehaviourRegular"
                style="display: flex; justify-content: space-around; align-items: center;">
                <div style="width: 800px; text-align: center;">
                    <div ref="UserBehaviourRegularChart" style="width: 100%; height: 400px;" />
                    <div class="content-conclusion">[C] 活跃时间</div>
                </div>
                <div style="width: 400px; text-align: center;">
                    <div v-if="UserBehaviourRegularActiveTimePointsData.length > 0">
                        <div v-for="(timePoint, index) in UserBehaviourRegularActiveTimePointsData" :key="index"
                            class="time-tag">
                            <el-tag type="danger" effect="plain" style="margin-top: 40px;">
                                <i class="el-icon-time" style="margin-left: 2px;" />
                                {{ timePoint }}
                            </el-tag>
                        </div>
                    </div>
                    <div v-else>No active time points available</div>
                    <div class="content-conclusion">[D] 活跃时点</div>
                </div>
            </div>
        </div>
        <hr>

        <div class="UserPersonalitiesValues">
            <div class="title-with-button">
                <el-switch v-model="showUserPersonalitiesValues"  style="transform: scale(1.25);" />
                <h2 style="color:rgb(2, 157, 255); font-weight: bold; margin-left:15px">用户人格与价值观</h2>
            </div>
            <div class="UserPersonalitiesValues-container" v-show="showUserPersonalitiesValues"
                style="display: flex; justify-content: space-around; align-items: center;">
                <div style="width: 400px; text-align: center;">
                    <div ref="UserPersonalitiesValuesRadarChart" style="height: 400px;" />
                    <div class="content-conclusion">[E] 大五人格</div>
                </div>
                <div style="width: 400px; text-align: center;">
                    <div ref="UserPersonalitiesValuesBarChart" style="height: 400px;" />
                    <div class="content-conclusion">[F] 施瓦茨价值观</div>
                </div>
            </div>
        </div>

        <hr>

        <div class="UserCenterNetwork">
            <div class="title-with-button">
                <el-switch v-model="showUserCenterNetwork" style="transform: scale(1.25);"/>
                <h2 style="color:rgb(2, 157, 255); font-weight: bold; margin-left:15px">用户中心网络</h2>
            </div>
            <div class="UserCenterNetwork-container"  v-show="showUserCenterNetwork"
                style="display: flex; justify-content: space-around; align-items: center;">
                <!-- Social Graph -->
                <div style="width: 300px; text-align: center;">
                    <div ref="socialGraph" style="width: 100%; height: 300px;"></div>

                    <el-table :data="generateRows(topSocialUsers)" border style="width: 100%; max-width: 300px;"
                        :show-header="false">
                        <el-table-column v-for="index in 3" :key="index" :prop="'img' + index" align="center">
                            <template slot-scope="{ row }">
                                <div style="position: relative; display: inline-block; width: 90px; height: 70px;">
                                    <img :src="row[index - 1].img" style="width: 60px; height: 60px;">
                                    <span style="position: absolute; top: 0; left: 0; font-size: 8px; ">
                                        {{ row[index - 1].relation }}
                                    </span>
                                </div>
                            </template>
                        </el-table-column>
                    </el-table>

                    <div class="content-conclusion">[G.1] 社交网络与高频互动好友
                    </div>
                </div>
                <!-- High Interaction Graph -->
                <div style="width: 300px; text-align: center;">
                    <div ref="highInteractionGraph" style="width: 100%; height: 300px;"></div>
                    <el-table :data="generateRows(topInteractionUsers)" border style="width: 100%; max-width: 300px;"
                        :show-header="false">
                        <el-table-column v-for="index in 3" :key="index" :prop="'img' + index" align="center">
                            <template slot-scope="{ row }">
                                <div style="position: relative; display: inline-block; width: 90px; height: 70px;">
                                    <img :src="row[index - 1].img" style="width: 60px; height: 60px;">
                                    <span style="position: absolute; top: 0; left: 0; font-size: 8px; ">
                                        {{ row[index - 1].relation }}
                                    </span>
                                </div>
                            </template>
                        </el-table-column>
                    </el-table>
                    <div class="content-conclusion">[G.2] 两跳传播网络与高频互动网络
                    </div>
                </div>
                <!-- Co-interaction Graph -->
                <div style="width: 300px; text-align: center;">
                    <div ref="coInteractionGraph" style="width: 100%; height: 300px;"></div>
                    <el-table :data="generateRows(topCoInteractionUsers)" border style="width: 100%; max-width: 300px;"
                        :show-header="false">
                        <el-table-column v-for="index in 3" :key="index" :prop="'img' + index" align="center">
                            <template slot-scope="{ row }">
                                <div style="position: relative; display: inline-block; width: 90px; height: 70px;">
                                    <img :src="row[index - 1].img" style="width: 60px; height: 60px;">
                                    <span style="position: absolute; top: 0; left: 0; font-size: 8px; ">
                                        {{ row[index - 1].relation }}
                                    </span>
                                </div>
                            </template>
                        </el-table-column>
                    </el-table>
                    <div class="content-conclusion">[G.3] 共互动网络与高频共互动用户
                    </div>
                </div>
            </div>
        </div>
        <hr>

        <div class="UserSemantic">
            <div class="title-with-button">
                <el-switch v-model="showUserSemantic" style="transform: scale(1.25);" />
                <h2 style="color:rgb(2, 157, 255); font-weight: bold; margin-left:15px">用户语义</h2>
            </div>

            <div class="UserSemantic-container" v-show="showUserSemantic">
                <div class="content-conclusion" style="margin-bottom: 10px;">[H] 主要话题短语和观点</div>
                <div style="width: 100%; display: flex; justify-content: space-around; align-items: top center;">
                    <!-- Todo 划分成两部分  各占50%   左半部分是echarts图 axios请求的数据为OpinionViewData  包含观点和原文信息  右半部分是对应的表格(element-ui) 表格左边第一列为字段(观点，对应原文)，第二列为对应观点和原文字符串-->
                    <!-- Left Part: ECharts Visualization -->
                    <div ref="opinionChart" style="width: 50%; height: 400px;"></div>

                    <!-- Right Part: Element UI Table -->
                    <div style="width: 50%; margin-top: 20px;">
                        <div style="
                                background-color: rgb(255, 240, 245);
                                padding: 10px;
                                text-align: center;
                                width: 90%;
                                /* font-weight: bold; */
                                border-bottom: 1px solid #ddd;
                            ">
                            话题╱观点与对应原文
                        </div>
                        <el-table :data="OpinionTableData" style="width: 90%;" stripe :show-header="false" border>
                            <!-- 第二行：话题 -->
                            <el-table-column prop="field" width="120">
                            </el-table-column>
                            <!-- 第三行：原文(部分) -->
                            <el-table-column prop="value">
                            </el-table-column>

                        </el-table>
                    </div>
                </div>

                <div style="text-align: center;">
                    <div class="content-conclusion" style="margin-bottom: 10px; display: inline-block;">[J] 针对{{ xxx }}的立场
                    </div>

                    <el-select v-model="selectedStandPointUser" placeholder="选择用户名"
                        style="display: inline-block; vertical-align: middle; margin-left: 10px;"
                        @change="handleUserChange">
                        <el-option v-for="user in StandpointData" :key="user.username" :label="user.username"
                            :value="user.username">
                        </el-option>
                    </el-select>
                    <!-- <el-button type="primary" style="display: inline-block; vertical-align: middle; margin-left: 10px;"
                        round @click="switchUserStandPoint">切换</el-button> -->
                </div>
                <div style="width: 100%; display: flex; justify-content: space-around; align-items: top center;">
                    <div ref="standpointChart" style="width: 50%; height: 400px;"></div>

                    <!-- Todo 下面为对应的表格(element-ui) 表格为2行三列 第一行为一个表头(占三列), 第二行第一列为StandpointData[0]中的对应的username字段，第二列为对应的StandpointData[0].data中name字段，第三列为StandpointData[0].data中text字段-->
                    <div style="width: 50%; margin-top: 20px;">
                        <div v-on="selectedStandPointUser" style="
                                background-color: rgb(255, 240, 245);
                                padding: 10px;
                                text-align: center;
                                width: 90%;
                                /* font-weight: bold; */
                                border-bottom: 1px solid #ddd;
                             ">
                            针对{{ selectedStandPointUser }}的【xx】立场对应原文(部分)
                        </div>
                        <el-table :data="StandpointtableData" style="width: 90%" stripe :show-header="false">
                            <!-- 表头 -->
                            <el-table-column prop="header" align="center">
                            </el-table-column>

                            <!-- 用户名 -->
                            <el-table-column prop="username" width="120">
                            </el-table-column>

                            <!-- 立场名称 -->
                            <el-table-column prop="name" width="120">
                            </el-table-column>

                            <!-- 立场描述 -->
                            <el-table-column prop="text">
                            </el-table-column>
                        </el-table>
                    </div>
                </div>



                <div class="content-conclusion" style="margin-bottom: 10px;">[K] 情绪分布 </div>
                <div style="width: 100%; display: flex; justify-content: space-around; align-items: top center;">
                    <!-- Todo 划分成两部分  各占50%   左半部分是echarts图 axios请求的数据为OpinionViewData  包含观点和原文信息  右半部分是对应的表格(element-ui) 表格左边第一列为字段(观点，对应原文)，第二列为对应观点和原文字符串-->
                    <!-- Left Part: ECharts Visualization -->
                    <div ref="emotionChart" style="width: 50%; height: 400px;"></div>

                    <!-- Right Part: Element UI Table -->
                    <div style="width: 50%;">
                        <div v-on="selectedStandPointUser" style="
                                background-color: rgb(255, 240, 245);
                                padding: 10px;
                                text-align: center;
                                width: 90%;
                                /* font-weight: bold; */
                                border-bottom: 1px solid #ddd;
                             ">
                            【极端愤怒】情绪对应原文(部分)
                        </div>
                        <el-table :data="OpinionTableData" style="width: 90%;" border stripe :show-header="false">
                            <!-- 第二行：话题 -->
                            <el-table-column prop="field" width="120">
                            </el-table-column>
                            <!-- 第三行：原文(部分) -->
                            <el-table-column prop="value">
                            </el-table-column>
                        </el-table>
                    </div>
                </div>
            </div>
        </div>
        <hr>


        <!-- -------------------------------------------------------------------------------------------------- -->
      <!-- CH6 用户参与舆情   TODO：残次品，需要大修改- -->
      <!-- -------------------------------------------------------------------------------------------------- -->
      <div class="UserPublicSentiment">
        <div class="title-with-button">
                <el-switch v-model="showUserPublicOpinion" style="transform: scale(1.25); "/>
                <h2 style="color:rgb(2, 157, 255); font-weight: bold; margin-left:15px">用户参与舆情</h2>
        </div>

        <div
          v-show="showUserPublicOpinion"
          class="user-panel"
          style="display: flex; flex-direction: column; align-items: center; height: 100%;"
        >
          <!-- 底部区域（待定） -->
          <div class="custom-timeline">
            <div
              v-for="(item, index) in userParticipationTimeline"
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
import * as echarts from 'echarts'
import axios from 'axios'

export default {
    name: 'Dashboard',
    data() {
        return {
            selected: null,
            startDate: '',
            endDate: '',

            userinfo_selected: 'cnnphilippines',
            //  用户基本信息数据
            usernames: ['cnnphilippines', '用户2', '用户3'], // 用户名选项
            userProfile: {
                username: 'cnnphilippines',
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
            activeNames: ['1', '2', '3'], // 默认展开基础信息

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
            },

            //  用户中心网络数据
            SocialGraphData: {
                username_x: ['用户1', '用户2', '用户3', '用户4', '用户5'],
                username_y: ['用户2', '用户3', '用户1', '用户2', '用户3']
            },

            TwoHopInteractionGraphData: {
                username_x: ['用户1', '用户2', '用户3', '用户4', '用户5'],
                username_y: ['用户2', '用户3', '用户1', '用户2', '用户3']
            },
            CoInteractionGraphData: {
                username_x: ['用户1', '用户2', '用户3', '用户4', '用户5'],
                username_y: ['用户2', '用户3', '用户1', '用户2', '用户3']
            },

            topSocialUsers: {
                relationType: ['粉丝', '粉丝', '关注', '粉丝', '互粉', '粉丝', '互粉', '关注', '粉丝'],
                imgUrl: Array(9).fill(require('@/assets/userinfo_images/1.jpg'))
            },
            topInteractionUsers: {
                relationType: ['非好友', '粉丝', '非好友', '粉丝', '互粉', '粉丝', '互粉', '关注', '粉丝'],
                imgUrl: Array(9).fill(require('@/assets/userinfo_images/1.jpg'))
            },
            topCoInteractionUsers: {
                relationType: ['非好友', '粉丝', '非好友', '粉丝', '互粉', '粉丝', '互粉', '关注', '粉丝'],
                imgUrl: Array(9).fill(require('@/assets/userinfo_images/1.jpg'))
            },
            socialRows: [],
            interactionRows: [],
            coInteractionRows: [],

            //  用户语义数据

            OpinionTopicData: [
                {
                    name: '菲律宾人呼吁团结捍卫在西菲律宾海的主权',
                    group: 0,
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
                            当我们在西菲律宾海（WPS）专属经济区的自己的水域和土地上受到欺凌和占领时，全体菲律宾人民必须为了祖国团结起来---我们是菲律宾青年。我们停在了西菲律宾海`

                },
                {
                    name: '菲律宾人呼吁团结捍卫在西菲律宾海的主权123',
                    group: 0,
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
                            当我们在西菲律宾海（WPS）专属经济区的自己的水域和土地上受到欺凌和占领时，全体菲律宾人民必须为了祖国团结起来---我们是菲律宾青年。我们停在了西菲律宾海`

                },
                {
                    name: '美国人呼吁总统当选应当公平竞争',
                    group: 1,
                    text: `当我们在西菲律宾海（WPS）专属经济区的自己的水域和土地上受到欺凌和占领时，全体菲律宾人民必须为了祖国团结起来\n
                            ---\n
                            当我们在西菲律宾海（WPS）专属经济区的自己的水域和土地上受到欺凌和占领时，全体菲律宾人民必须为了祖国团结起来\n
                            ---\n
                            我们是菲律宾青年。我们停在了西菲律宾海\n
                            ---\n`

                },
                {
                    name: '美国人呼吁总统当选应当公平竞争123',
                    group: 1,
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
                            当我们在西菲律宾海（WPS）专属经济区的自己的水域和土地上受到欺凌和占领时，全体菲律宾人民必须为了祖国团结起来---我们是菲律宾青年。我们停在了西菲律宾海`

                },

                {
                    name: '美国人呼吁总统当选应当公平竞争12345',
                    group: 1,
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
                            当我们在西菲律宾海（WPS）专属经济区的自己的水域和土地上受到欺凌和占领时，全体菲律宾人民必须为了祖国团结起来---我们是菲律宾青年。我们停在了西菲律宾海`

                },

                {
                    name: '菲律宾人呼吁团结捍卫在西菲律宾海的主权11111',
                    group: 0,
                    text: `当我们在西菲律宾海（WPS）专属经济区的自己的水域和土地上受到欺凌和占领时，全体菲律宾人民必须为了祖国团结起来\n
                            ---\n
                            当我们在西菲律宾海（WPS）专属经济区的自己的水域和土地上受到欺凌和占领时，全体菲律宾人民必须为了祖国团结起来\n
                            ---\n
                            我们是菲律宾青年。我们停在了西菲律宾海\n
                            ---\n`

                },


                {
                    name: '菲律宾人呼吁团结捍卫在西菲律宾海的主权789',
                    group: 0,
                    text: `当我们在西菲律宾海（WPS）专属经济区的自己的水域和土地上受到欺凌和占领时，全体菲律宾人民必须为了祖国团结起来\n
                            ---\n
                            当我们在西菲律宾海（WPS）专属经济区的自己的水域和土地上受到欺凌和占领时，全体菲律宾人民必须为了祖国团结起来\n
                            ---\n`

                },

                {
                    name: '菲律宾人呼吁团结捍卫在西菲律宾海的主权9998',
                    group: 0,
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
                            当我们在西菲律宾海（WPS）专属经济区的自己的水域和土地上受到欺凌和占领时，全体菲律宾人民必须为了祖国团结起来---我们是菲律宾青年。我们停在了西菲律宾海`

                },
            ],
            OpinionTableData: [
                {
                    name: '菲律宾人呼吁团结捍卫在西菲律宾海的主权',
                    group: 0,
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
                            当我们在西菲律宾海（WPS）专属经济区的自己的水域和土地上受到欺凌和占领时，全体菲律宾人民必须为了祖国团结起来---我们是菲律宾青年。我们停在了西菲律宾海`

                },
            ],
            StandpointData: [
                {
                    username: '中共政党',
                    data: [
                        { value: 0.20, name: '声援', text: '我们是菲律宾青年，我们呼吁团结捍卫在西菲律宾海的主权，声援西菲律宾政府' },
                        { value: 0.20, name: '敌意', text: '杀杀杀' },
                        { value: 0.20, name: '自豪', text: '中国祖国建设真好' },
                        { value: 0.20, name: '中立', text: '我觉得还不错' },
                        { value: 0.20, name: '反对', text: '好好好' }
                    ]
                },

                {
                    username: '台湾政党',
                    data: [
                        { value: 0.25, name: '声援', text: '我们是菲律宾青年，我们呼吁团结捍卫在西菲律宾海的主权，声援西菲律宾政府11' },
                        { value: 0.15, name: '敌意', text: '杀杀杀1111' },
                        { value: 0.19, name: '自豪', text: '中国祖国建设真好11' },
                        { value: 0.21, name: '中立', text: '我觉得还不错111' },
                        { value: 0.20, name: '反对', text: '好好好11' }
                    ]
                },
            ],
            selectedStandPointUser: '中共政党',
            StandpointtableData: [],


            EmotionData: [],
            //  用户参与舆情数据

            userParticipationTimeline: [],


            //  所有左上角按钮值
            showUserInfo: true,
            showBehaviourRegular: true,
            showUserPersonalitiesValues: true,
            showUserCenterNetwork: true,
            showUserSemantic: true,
            showUserPublicOpinion: true
        }
    },
    async mounted() {
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
        this.fetchUserCenterGraphData()
        this.fetchUserTopRelationData()
        //  用户语义
        this.fetchUserTopicsOpinionsData()
        this.fetchUserStandpointData()
        this.fetchUserEmotionalData()
        //  用户参与舆情  //头像    // 群组参与舆情--时间轴数据
        await this.fetchuserParticipationTimeline()
        await this.$nextTick()
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
                // title: {
                //     text: 'UserBehaviourTimeRegular',
                //     left: '1%'
                // },
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
                // dataZoom: [
                //     {
                //         startValue: '2024/01'
                //     },
                //     {
                //         type: 'inside'
                //     }
                // ],
                visualMap: {
                    top: 50,
                    right: 0,
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
                    data: data.map(function (item) {
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
        },

        /*   用户中心网络 */
        fetchUserCenterGraphData() {
            axios.get('https://localhost:8080/user/ThreeGraph')
                .then(response => {
                    this.SocialGraphData = response.data.SocialGraphData
                    this.TwoHopInteractionGraphData = response.data.TwoHopInteractionGraphData
                    this.CoInteractionGraphData = response.data.CoInteractionGraphData

                    this.initSocialGraphChart(this.SocialGraphData)
                    this.initTwoHopInteractionGraphChart(this.TwoHopInteractionGraphData)
                    this.initCoInteractionGraphChart(this.CoInteractionGraphData)
                })
                .catch(error => {
                    console.error('Failed to fetch data, using default:', error)
                    this.initSocialGraphChart(this.SocialGraphData)
                    this.initTwoHopInteractionGraphChart(this.TwoHopInteractionGraphData)
                    this.initCoInteractionGraphChart(this.CoInteractionGraphData)
                })
        },

        initSocialGraphChart(data) {
            const socialDom = this.$refs.socialGraph
            const GraphChart = echarts.init(socialDom)

            // Prepare nodes and links for the ECharts graph series
            const nodes = [];
            const links = [];
            const nodeSet = new Set(); // Use a set to avoid duplicate nodes

            // Populate the set with unique usernames
            data.username_x.forEach(user => nodeSet.add(user));
            data.username_y.forEach(user => nodeSet.add(user));

            // Convert set to array for nodes data
            nodeSet.forEach((user, index) => {
                nodes.push({
                    name: user,
                    draggable: true,
                    value: index, // Arbitrary value, can be adjusted or removed
                    symbolSize: 10 // Adjust size as needed
                });
            });

            // Create links based on the username_x and username_y arrays
            for (let i = 0; i < data.username_x.length; i++) {
                links.push({
                    source: data.username_x[i],
                    target: data.username_y[i]
                });
            }

            // ECharts option setup
            const option = {
                // title: {
                //     text: '社交关系图',
                // },
                tooltip: {},
                animationDurationUpdate: 1500, // For smooth animation
                animationEasingUpdate: 'quinticInOut',
                series: [{
                    type: 'graph',
                    layout: 'force',
                    symbolSize: 20,
                    roam: true, // Allow user to move graph and zoom
                    edgeSymbol: ['circle', 'arrow'],
                    edgeSymbolSize: [4, 10],
                    force: {
                        repulsion: 400,
                        edgeLength: [10, 50]
                    },
                    draggable: true,
                    data: nodes,
                    links: links,
                    lineStyle: {
                        color: 'source',
                        curveness: 0.3
                    }
                }]
            };

            GraphChart.setOption(option);

            // Resize chart on container resize to make it responsive
            window.addEventListener('resize', function () {
                GraphChart.resize();
            });
        },



        initTwoHopInteractionGraphChart(data) {
            const highInteractionDom = this.$refs.highInteractionGraph
            const GraphChart = echarts.init(highInteractionDom)

            // Prepare nodes and links for the ECharts graph series
            const nodes = [];
            const links = [];
            const nodeSet = new Set(); // Use a set to avoid duplicate nodes

            // Populate the set with unique usernames
            data.username_x.forEach(user => nodeSet.add(user));
            data.username_y.forEach(user => nodeSet.add(user));

            // Convert set to array for nodes data
            nodeSet.forEach((user, index) => {
                nodes.push({
                    name: user,
                    draggable: true,
                    value: index, // Arbitrary value, can be adjusted or removed
                    symbolSize: 10 // Adjust size as needed
                });
            });

            // Create links based on the username_x and username_y arrays
            for (let i = 0; i < data.username_x.length; i++) {
                links.push({
                    source: data.username_x[i],
                    target: data.username_y[i]
                });
            }

            // ECharts option setup
            const option = {
                // title: {
                //     text: '两跳传播网络',
                // },
                tooltip: {},
                animationDurationUpdate: 1500, // For smooth animation
                animationEasingUpdate: 'quinticInOut',
                series: [{
                    type: 'graph',
                    layout: 'force',
                    symbolSize: 20,
                    roam: true, // Allow user to move graph and zoom
                    edgeSymbol: ['circle', 'arrow'],
                    edgeSymbolSize: [4, 10],
                    force: {
                        repulsion: 400,
                        edgeLength: [10, 50]
                    },
                    draggable: true,
                    data: nodes,
                    links: links,
                    lineStyle: {
                        color: 'source',
                        curveness: 0.3
                    }
                }]
            };

            GraphChart.setOption(option);

            // Resize chart on container resize to make it responsive
            window.addEventListener('resize', function () {
                GraphChart.resize();
            });
        },


        initCoInteractionGraphChart(data) {
            const coInteractionDom = this.$refs.coInteractionGraph
            const GraphChart = echarts.init(coInteractionDom)

            // Prepare nodes and links for the ECharts graph series
            const nodes = [];
            const links = [];
            const nodeSet = new Set(); // Use a set to avoid duplicate nodes

            // Populate the set with unique usernames
            data.username_x.forEach(user => nodeSet.add(user));
            data.username_y.forEach(user => nodeSet.add(user));

            // Convert set to array for nodes data
            nodeSet.forEach((user, index) => {
                nodes.push({
                    name: user,
                    draggable: true,
                    value: index, // Arbitrary value, can be adjusted or removed
                    symbolSize: 10, // Adjust size as needed
                    itemStyle: {
                        normal: {
                            color: index % 2 === 0 ? '#c23531' : '#2f4554' // Alternate colors or use a function to set based on data
                        }
                    }
                });
            });

            // Create links based on the username_x and username_y arrays
            for (let i = 0; i < data.username_x.length; i++) {
                links.push({
                    source: data.username_x[i],
                    target: data.username_y[i],
                    lineStyle: {
                        normal: {
                            color: '#6e7074', // Uniform color for all edges or use a function to set based on data
                            curveness: 0.3 // Add some curve to distinguish overlapping edges
                        }
                    }
                });
            }

            // ECharts option setup
            const option = {
                // title: {
                //     text: '共互动网络',
                // },
                tooltip: {},
                animationDurationUpdate: 1500, // For smooth animation
                animationEasingUpdate: 'quinticInOut',
                series: [{
                    type: 'graph',
                    layout: 'force',
                    symbolSize: 20,
                    roam: true, // Allow user to move graph and zoom
                    edgeSymbol: ['circle', 'arrow'],
                    edgeSymbolSize: [4, 10],
                    force: {
                        repulsion: 400,
                        edgeLength: [10, 50]
                    },
                    draggable: true,
                    data: nodes,
                    links: links,
                    lineStyle: {
                        color: 'source',
                        curveness: 0.3
                    }
                }]
            };

            GraphChart.setOption(option);

            // Resize chart on container resize to make it responsive
            window.addEventListener('resize', function () {
                GraphChart.resize();
            });
        },



        fetchUserTopRelationData() {
            axios.post('https://localhost:8080/user/TopRelation', {})
                .then(response => {
                    this.topSocialUsers = response.data.topSocialUsers
                    this.topInteractionUsers = response.data.topInteractionUsers
                    this.topCoInteractionUsers = response.data.topCoInteractionUsers
                })
                .catch(error => {
                    console.error('Failed to fetch data, using default:', error)
                })
        },

        generateRows(users) {
            const { relationType, imgUrl } = users;
            let rows = [];
            for (let i = 0; i < relationType.length; i += 3) {
                let row = [];
                for (let j = 0; j < 3; j++) {
                    if (i + j < relationType.length) {
                        row.push({
                            img: imgUrl[i + j],
                            relation: relationType[i + j]
                        });
                    }
                }
                rows.push(row);
            }
            return rows;
        },


        /*   用户语义 */
        fetchUserTopicsOpinionsData() {
            axios.get('https://localhost:8080/user/TopicsOpinions')
                .then(response => {
                    this.OpinionTopicData = response.data;  //  这里应该获取所有话题观点数据
                    this.initOpinionChart(this.OpinionTopicData);  //  图加载所有话题观点
                    this.prepareTableData(this.OpinionTopicData[0]);  //  这里表格应该展示选中的echarts图上的数据 默认有一个选中
                })
                .catch(error => {
                    console.error('Error fetching OpinionViewData:', error);
                    this.initOpinionChart(this.OpinionTopicData);
                    this.prepareTableData(this.OpinionTopicData[0]);
                });
        },

        initOpinionChart(data) {
            const chartDom = this.$refs.opinionChart;
            const myChart = echarts.init(chartDom);

            const option = {
                title: {
                    text: '话题观点分析',
                },
                tooltip: {
                    trigger: 'item',
                    formatter: function (params) {
                        return params.data.name;  // 在tooltip中显示节点名称
                    },
                },
                series: [{
                    type: 'graph',
                    layout: 'force',
                    data: data.map(opinion => ({
                        name: opinion.name,
                        text: opinion.text,  // 保存对应的观点文本
                        symbolSize: 10 + opinion.name.length,  // 根据名称长度调整节点大小
                        itemStyle: {
                            color: opinion.group === 0 ? '#FFA07A' : '#9370DB'  // 根据不同分组设定颜色
                        }
                    })),
                    //links: this.generateLinks(data),  // 自动生成links，表示节点之间的关系
                    force: {
                        repulsion: 150,  // 设置节点之间的排斥力
                    },
                    roam: true,
                    label: {
                        show: true,
                        position: 'right',
                        formatter: '{b}',
                    },
                }],
            };

            myChart.setOption(option);

            // 监听节点点击事件
            myChart.on('click', (params) => {
                if (params.dataType === 'node') {
                    console.log(params.data)
                    this.prepareTableData(params.data);  // 点击节点时，调用函数展示详细信息
                }
            });
        },

        prepareTableData(selectedNode) {
            this.OpinionTableData = [
                {
                    field: '话题',
                    value: selectedNode.name,
                },
                {
                    field: '原文(部分)',
                    value: selectedNode.text,  // 对应的原文文本
                }
            ];
        },

        fetchUserStandpointData() {
            axios.get('https://localhost:8080/user/Standpoint')
                .then(response => {
                    this.StandpointData = response.data;  //  这里应该获取所有话题观点数据
                    this.initStandpointChart(this.StandpointData[0]);  //  图加载所有话题观点
                    this.generateStandpointTableData(this.StandpointData[0])
                })
                .catch(error => {
                    console.error('Error fetching OpinionViewData:', error);
                    this.initStandpointChart(this.StandpointData[0]);
                    this.generateStandpointTableData(this.StandpointData[0])
                });
        },

        switchUserStandPoint() {
            if (this.selectedStandPointUser) {
                const user = this.StandpointData.find(user => user.username === this.selectedStandPointUser);
                if (user) {
                    this.initStandpointChart(user);
                }
            } else {
                // 如果没有选择用户，默认展示第一个
                const defaultUser = this.StandpointData[0];
                this.initStandpointChart(defaultUser);
            }
        },
        handleUserChange(value) {
            // 当选择改变时，立即调用 initOpinionChart 展示选中用户的观点图表
            const user = this.StandpointData.find(user => user.username === value);
            if (user) {
                this.initStandpointChart(user);
            }
        },

        initStandpointChart(data) {
            const chartDom = this.$refs.standpointChart;
            const myChart = echarts.init(chartDom);

            // 转换输入数据
            const formattedData = data.data.map(item => {
                return {
                    value: item.value,
                    name: item.name
                };
            });

            const option = {
                tooltip: {
                    trigger: 'item',
                    formatter: function (params) {
                        // 根据传入的 params 找到对应的描述
                        const item = data.data.find(d => d.name === params.name);
                        return `${params.name}: ${params.value * 100}%<br/>${item.text}`;
                    },
                },
                legend: {
                    top: '5%',
                    left: 'center'
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
                            show: true,  // 显示标签
                            position: 'outside',  // 标签显示在图表外侧（或可以设置为 'inside' 显示在图内）
                            formatter: '{b}:{d}%'  // 格式化标签内容为 名称: (百分比)
                        },
                        emphasis: {
                            label: {
                                show: true,
                                fontSize: 40,
                                fontWeight: 'bold'
                            }
                        },
                        labelLine: {
                            show: false
                        },
                        data: formattedData
                    }
                ]
            };

            myChart.setOption(option);
        },

        generateStandpointTableData() {
            // 将 StandpointData 转换为表格数据格式
            this.StandpointtableData = this.StandpointData.reduce((acc, curr) => {
                curr.data.forEach(dataItem => {
                    acc.push({
                        username: curr.username,
                        name: dataItem.name,
                        text: dataItem.text
                    });
                });
                return acc;
            }, []);
        },

        fetchUserEmotionalData() {
            axios.get('https://localhost:8080/user/EmotionDistribution')
                .then(response => {
                    this.OpinionTopicData = response.data;  //  这里应该获取所有话题观点数据
                    this.initEmotionalChart(this.EmotionData);  //  图加载所有话题观点
                    // this.prepareTableData(this.OpinionTopicData[0]);  //  这里表格应该展示选中的echarts图上的数据 默认有一个选中
                })
                .catch(error => {
                    console.error('Error fetching OpinionViewData:', error);
                    this.initEmotionalChart(this.EmotionData);
                    // this.prepareTableData(this.OpinionTopicData[0]);
                });
        },

        initEmotionalChart(data) {
            const chartDom = this.$refs.emotionChart;
            const myChart = echarts.init(chartDom);

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
                        data: [2, 4, 6, 1, 3, 2, 1, 3],
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
                    data: ['情绪', '极端情绪']
                }
            }
            myChart.setOption(option)
        },


        // 用户参与舆情
        fetchBigVImgUrlList() {
        axios.get('http://localhost:8080/big-v-url-data')
          .then(response => {
            this.BigVImgUrlList = response.data
          })
          .catch(error => {
            console.error('Error fetching big v url data from backend:', error)
            // Fallback to local data
            this.BigVImgUrlList = [
              require('@/assets/userinfo_images/group_users_images/demo.jpg'),
              require('@/assets/userinfo_images/group_users_images/cnn.png'),
              require('@/assets/userinfo_images/group_users_images/demo.jpg'),
              require('@/assets/userinfo_images/group_users_images/woman.png'),
              require('@/assets/userinfo_images/group_users_images/demo.jpg'),
              require('@/assets/userinfo_images/group_users_images/demo.jpg'),
              require('@/assets/userinfo_images/group_users_images/man.png'),
              require('@/assets/userinfo_images/group_users_images/demo.jpg'),
              require('@/assets/userinfo_images/group_users_images/demo.jpg'),
              require('@/assets/userinfo_images/group_users_images/demo.jpg'),
              require('@/assets/userinfo_images/group_users_images/leader.png'),
              require('@/assets/userinfo_images/group_users_images/demo.jpg'),
              require('@/assets/userinfo_images/group_users_images/demo.jpg')
            ]
          }
          )
      },
      fetchuserParticipationTimeline() {
        axios.get('http://localhost:8080/user-participation-timeline-data')
          .then(response => {
            this.userParticipationTimeline = response.data
          })
          .catch(error => {
            console.error('Error fetching user participation timeline data from backend:', error)
            // Fallback to local data
            this.userParticipationTimeline = [
              {
                date: 'May.',
                images: [{ src: require('@/assets/userinfo_images/group_users_images/LQD.jpg'), title: '中国快艇越界' },
                { src: require('@/assets/userinfo_images/group_users_images/LQD.jpg'), title: '赖清德入选《时代》百大人物' },
                { src: require('@/assets/userinfo_images/group_users_images/LQD.jpg'), title: '中国快艇越界' }
                ],
                texts: [` 中共对台湾武力恐吓
                      台湾用行动抵御外部势力守护民主`,
                  `给中国渔民教训
                      国军终于硬气了一次`]
              },
              {
                date: 'Apr.',
                images: [{ src: require('@/assets/userinfo_images/group_users_images/LQD.jpg'), title: '中国快艇越界' },
                { src: require('@/assets/userinfo_images/group_users_images/LQD.jpg'), title: '赖清德入选《时代》百大人物' },
                { src: require('@/assets/userinfo_images/group_users_images/LQD.jpg'), title: '中国快艇越界' }
                ],
                texts: [` 中共对台湾武力恐吓
                      台湾用行动抵御外部势力守护民主`,
                  `给中国渔民教训
                      国军终于硬气了一次`]
              },
              {
                date: 'Feb.',
                images: [{ src: require('@/assets/userinfo_images/group_users_images/LQD.jpg'), title: '中国快艇越界' },
                { src: require('@/assets/userinfo_images/group_users_images/LQD.jpg'), title: '赖清德入选《时代》百大人物' },
                { src: require('@/assets/userinfo_images/group_users_images/LQD.jpg'), title: '中国快艇越界' }
                ],
                texts: [` 马英九有意承担两岸和平使者
                      马习会没有实质性内容`,
                  ` 赖清德备受美国认可
                      台海情势左右世界民主政治的发展`,
                  ` 中国继续就快艇事件威胁台湾`]
              },
              {
                date: 'Jan.',
                images: [{ src: require('@/assets/userinfo_images/group_users_images/LQD.jpg'), title: '中国快艇越界' },
                { src: require('@/assets/userinfo_images/group_users_images/LQD.jpg'), title: '赖清德入选《时代》百大人物' },
                { src: require('@/assets/userinfo_images/group_users_images/LQD.jpg'), title: '中国快艇越界' }
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
};

</script>

<style scoped>
hr {
    margin-top: 20px;
    height: 1px;
    /* Increase the height of the hr */
    background-color: skyblue;
    /* 将整个 hr 标签的背景颜色更改为蓝色 */
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

.userinfo h2 {
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


/*  每部分结尾的样式  */
.content-conclusion {
    text-align: center;
    font-family: "Microsoft YaHei", serif;
    margin-bottom: 20px;
    font-weight: bold;
    color: deepskyblue;
    margin-top: 20px;
}




/* 用户参与舆情样式 */
.UserPublicOpinion-container {
    display: flex;
    flex-direction: column-reverse;
    align-items: center;
    padding: 20px;
    background-color: #f9f9f9;
}

.UserPublicOpinion-item {
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-bottom: 20px;
}

.UserPublicOpinion-month {
    background-color: #4CAF50;
    color: white;
    padding: 10px 20px;
    border-radius: 50%;
    margin-bottom: 10px;
    font-size: 1.2em;
}

.UserPublicOpinion-content {
    display: flex;
    align-items: center;
}

.UserPublicOpinion-image img {
    width: 100px;
    height: 100px;
    object-fit: cover;
    margin-right: 20px;
}

.UserPublicOpinion-description {
    max-width: 400px;
}

.UserPublicOpinion-description ul {
    list-style-type: none;
    padding: 0;
}

.UserPublicOpinion-description li {
    margin-bottom: 5px;
}
</style>



<template>
    <!-- rgb(0,32,96) -->
    <div class="dashboard">
        <div class="searchbar">
        <div class="icon-group">
          <div class="icon-item" @click="select('Facebook')"
               :class="{ 'selected-item': selected === 'Facebook' }">
            <el-button
              :type="selected === 'facebook' ? 'primary' : 'default'"
              plain
              :style="{ fontSize: '25px', padding: '1',
              backgroundColor: selected === 'Facebook' ? 'lightgray' : 'transparent', /* 自定义背景色 */
              borderColor: 'transparent'}"
            >
              <img
                src="@/assets/dashboard/facebook.png"
                alt="icon"
                style="width: 50px; height: 50px;"
              />
            </el-button>
            <div>Facebook</div>
          </div>
          <div class="icon-item" @click="select('Twitter')"
               :class="{ 'selected-item': selected === 'Twitter' }">
            <el-button
              :type="selected === 'twitter' ? 'primary' : 'default'"
              plain
              :style="{ fontSize: '25px', padding: '1',
              backgroundColor: selected === 'Twitter' ? 'lightgray' : 'transparent', /* 自定义背景色 */
              borderColor: 'transparent'}"
            >
              <img
                src="@/assets/dashboard/twitter.png"
                alt="icon"
                style="width: 50px; height: 50px;"
              />
            </el-button>
            <div>Twitter</div>
          </div>
          <div class="icon-item" @click="select('Youtube')"
               :class="{ 'selected-item': selected === 'Youtube' }">
            <el-button
              :type="selected === 'Youtube' ? 'primary' : 'default'"
              plain
              :style="{ fontSize: '25px', padding: '1',
              backgroundColor: selected === 'Youtube' ? 'lightgray' : 'transparent', /* 自定义背景色 */
              borderColor: 'transparent'}"
            >
              <img
                src="@/assets/dashboard/youtube.png"
                alt="icon"
                style="width: 70px; height: 50px;"
              />
            </el-button>
            <div>Youtube</div>
          </div>
          <div class="icon-item"
               @click="select('weibo')"
               :class="{ 'selected-item': selected === 'weibo' }">
            <el-button
              :type="selected === 'weibo' ? 'primary' : 'default'"
              plain
              :style="{ fontSize: '25px', padding: '1',
              backgroundColor: selected === 'weibo' ? 'lightgray' : 'transparent', /* 自定义背景色 */
              borderColor: 'transparent'}"
            >
              <img
                src="@/assets/dashboard/weibo.png"
                alt="icon"
                style="width: 70px; height: 50px;"
              />
            </el-button>
            <div>Weibo</div>
          </div>
        </div>
            <div class="filtered-date" >
                <el-select v-model="recentDate_selected" clearable placeholder="时间线" @change="fetchRecentDate"
                    @clear="resetData" :style="{ width: '500px' }">
                    <el-option v-for="date in dates" :key="date" :label="date" :value="date">
                    </el-option>
                </el-select>
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
                                    <span style="margin-right: 16px; font-weight: bold; font-family: sans-serif;">用户名:
                                    </span>
                                    <el-select v-model="userinfo_selected" placeholder="请选择"
                                        @change="fetchUserData(userinfo_selected)">
                                        <el-option v-for="username in usernames" :key="username" :label="username"
                                            :value="username" />
                                    </el-select>
                                </el-col>

                                <el-col :span="11" style="display: flex; align-items: center; justify-content: center;">
                                    <span style="margin-right: 16px; font-weight: bold; font-family: sans-serif;">用户头像:
                                    </span>
                                    <img :src="selectedUserProfileNow.imageUrl"
                                        style="border-radius: 50%; width: 120px; height: 120px;">
                                </el-col>
                            </el-row>

                            <!-- User Data Table -->
                            <!-- <el-table class="custom-el-table" :data="tableRows"
                                style="margin-top: 100px; width: 100%; font-size: 10px; font-weight: bold; font-family: sans-serif;">
                                <el-table-column v-for="(item, index) in tableData" :key="index" :prop="'col' + index"
                                    :label="item.key" :header-cell-style="{ fontSize: '10px', fontWeight: 'bold', fontFamily: 'sans-serif', Color: 'black' }"/>
                            </el-table> -->

                            <table class="custom-table"
                                style="margin-top: 100px; width: 100%;  font-family: sans-serif; border-collapse: collapse;">
                                <thead>
                                    <tr>
                                        <th v-for="(item, index) in tableData" :key="index"
                                            style="font-size: 14px; font-weight: bold; color: #333; background-color: #f2f2f2; border-radius: 4px; padding: 4px; border: 2px solid #ddd; ">
                                            {{ item.key }}
                                        </th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr v-for="(row, rowIndex) in tableRows" :key="rowIndex"
                                        style="background-color: #fff;">
                                        <td v-for="(item, index) in tableData" :key="index"
                                            style="border: 2px solid #ddd; padding: 4px; text-align: center; border-radius: 4px; background-color: #fafafa;">
                                            {{ row['col' + index] }}
                                        </td>
                                    </tr>
                                </tbody>
                            </table>



                            <!-- <div class="content-conclusion">
                                [B] 活跃与影响面
                            </div> -->
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
                                <el-table :data="followersList" border style="width: 100% " :show-header="false">
                                    <el-table-column prop="name" />
                                </el-table>
                            </el-collapse-item>

                            <!-- Following List -->
                            <el-collapse-item class="custom-collapse-item" title="关注列表" name="3">
                                <el-table :data="followingList" border style="width: 100%" :show-header="false">
                                    <el-table-column prop="name" />
                                </el-table>
                            </el-collapse-item>
                        </el-collapse>
                    </el-col>

                </el-row>
                <div style="display: flex; justify-content: center;">
                    <div class="content-conclusion" style="flex: 1; text-align: center;">
                        [B] 活跃与影响面
                    </div>

                    <div class="content-conclusion" style="flex: 1; text-align: center;">
                        [A] 基础信息
                    </div>
                </div>
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
                    <div ref="UserBehaviourRegularChart" style="width: 100%; height: 350px;" />
                    <!-- <div class="content-conclusion">[C] 活跃时间</div> -->
                </div>


                <div
                    style="width: 400px; text-align: center; border: 1px dashed rgb(2, 157, 255); padding: 10px; border-radius: 10px;">
                    <div v-if="UserBehaviourRegularActiveTimePointsData.length > 0" class="time-tag-wrapper">
                        <div v-for="(timePoint, index) in UserBehaviourRegularActiveTimePointsData" :key="index"
                            class="time-tag-container">
                            <el-tag type="danger" effect="plain"
                                style="width: 100% height: 40px; font-size: 18px; padding: 6px;">
                                <i class="el-icon-time" style=" font-size: 18px;" />
                                {{ timePoint }}
                            </el-tag>
                        </div>
                    </div>
                    <div v-else style="font-size: 18px; color: #666; margin-top: 40px;">No active time points available
                    </div>
                    <!-- <div class="content-conclusion">[D] 活跃时点</div> -->
                </div>


            </div>

            <div style="display: flex; justify-content: center;">
                <div class="content-conclusion" style="flex: 2; text-align: center;">
                    [C] 活跃时间
                </div>

                <div class="content-conclusion" style="flex: 1; text-align: center;">
                    [D] 活跃时点
                </div>
            </div>
        </div>
        <hr>

        <div class="UserPersonalitiesValues">
            <div class="title-with-button">
                <el-switch v-model="showUserPersonalitiesValues" style="transform: scale(1.25);" />
                <h2 style="color:rgb(2, 157, 255); font-weight: bold; margin-left:15px">用户人格与价值观</h2>
            </div>
            <div class="UserPersonalitiesValues-container" v-show="showUserPersonalitiesValues"
                style="display: flex; justify-content: space-around; align-items: center;">
                <div style="width: 600px; text-align: center;">
                    <div ref="UserPersonalitiesValuesRadarChart" style="height: 400px;" />
                    <div class="content-conclusion">[E] 大五人格</div>
                </div>
                <div style="width: 600px; text-align: center;">
                    <div ref="UserPersonalitiesValuesBarChart" style="height: 400px;" />
                    <div class="content-conclusion">[F] 施瓦茨价值观</div>
                </div>
            </div>
        </div>

        <hr>

        <div class="UserCenterNetwork">
            <div class="title-with-button">
                <el-switch v-model="showUserCenterNetwork" style="transform: scale(1.25);" />
                <h2 style="color:rgb(2, 157, 255); font-weight: bold; margin-left:15px">用户中心网络</h2>
            </div>
            <div class="UserCenterNetwork-container" v-show="showUserCenterNetwork"
                style="display: flex; justify-content: space-around; align-items: center;">
                <!-- Social Graph -->
                <div style="width: 300px; text-align: center;">
                    <div ref="socialGraph" style="width: 100%; height: 300px;"></div>

                    <el-table :data="generateRows(topSocialUsers)" border style="width: 100%; max-width: 300px;"
                        :show-header="false">
                        <el-table-column v-for="index in 3" :key="index" :prop="'img' + index" align="center">
                            <template slot-scope="{ row }">
                                <div style="position: relative; display: inline-block; width: 90px; height: 70px;">
                                    <img :src="row[index - 1].img"
                                        style="position: absolute; top: 18px; left: 10px; width: 60px; height: 60px; border-radius: 50%; object-fit: cover;">
                                    <span style="position: absolute; top: 0; left: 0; font-size: 12px; ">
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
                                    <img :src="row[index - 1].img"
                                        style="position: absolute; top: 18px; left: 10px; width: 60px; height: 60px; border-radius: 50%; object-fit: cover;">
                                    <span style="position: absolute; top: 0; left: 0; font-size: 12px; ">
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
                                    <img :src="row[index - 1].img"
                                        style="position: absolute; top: 18px; left: 10px; width: 60px; height: 60px; border-radius: 50%; object-fit: cover;">
                                    <span style="position: absolute; top: 0; left: 0; font-size: 12px; ">
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
                <div style="width: 100%; display: flex; justify-content: center; align-items: flex-start;">
                    <!-- Todo 划分成两部分  各占50%   左半部分是echarts图 axios请求的数据为OpinionViewData  包含观点和原文信息  右半部分是对应的表格(element-ui) 表格左边第一列为字段(观点，对应原文)，第二列为对应观点和原文字符串-->
                    <!-- Left Part: ECharts Visualization -->
                    <div ref="opinionChart"
                        style="width: 45%; height: 500px; border: 1px dashed grey; border-radius: 2px; margin-right: 30px;">
                    </div>

                    <!-- Right Part: Element UI Table -->
                    <div style="width: 45%; margin-top: 20px;">
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
                            <el-table-column prop="field" width="120" :cell-style="{ fontSize: '24px' }">
                            </el-table-column>
                            <!-- 第三行：原文(部分) -->
                            <el-table-column prop="value">
                            </el-table-column>

                        </el-table>
                    </div>
                </div>

                <div style="text-align: center;">
                    <div class="content-conclusion" style="margin-bottom: 10px; display: inline-block;">[J] 针对<el-select
                            v-model="selectedStandPointUser" placeholder="选择用户名"
                            style="display: inline-block; width: 120px; height: 40px; margin-bottom: 10px"
                            @change="handleUserChange(selectedStandPointUser)">
                            <el-option v-for="user in StandpointData" :key="user.username" :label="user.username"
                                :value="user.username">
                            </el-option>
                        </el-select>的立场
                    </div>


                    <!-- <el-button type="primary" style="display: inline-block; vertical-align: middle; margin-left: 10px;"
                        round @click="switchUserStandPoint">切换</el-button> -->
                </div>
                <div style="width: 100%; display: flex; justify-content: space-around; align-items: top center;">
                    <div ref="standpointChart" style="width: 50%; height: 400px;"></div>

                    <!-- Todo 下面为对应的表格(element-ui) 表格为2行三列 第一行为一个表头(占三列), 第二行第一列为StandpointData[0]中的对应的username字段，第二列为对应的StandpointData[0].data中name字段，第三列为StandpointData[0].data中text字段-->
                    <div style="width: 50%; margin-top: 20px;">
                        <div style="
                                background-color: rgb(255, 240, 245);
                                padding: 10px;
                                text-align: center;
                                width: 90%;
                                /* font-weight: bold; */
                                border-bottom: 1px solid #ddd;
                             ">
                            针对{{ selectedStandPointUser }}的立场对应原文(部分)
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
                        <div style="
                                background-color: rgb(255, 240, 245);
                                padding: 10px;
                                text-align: center;
                                width: 90%;
                                /* font-weight: bold; */
                                border-bottom: 1px solid #ddd;
                             ">
                            极端愤怒情绪对应原文(部分)
                        </div>

                        <el-table :data="EmotionData" stripe :show-header="false" style="width: 90%; border color: gray">
                            <el-table-column prop="point" width="80" />
                            <el-table-column>
                                <template slot-scope="scope">
                                    <div v-html="formatText(scope.row.text)"></div>
                                </template>
                            </el-table-column>
                        </el-table>
                    </div>
                </div>
            </div>
        </div>
        <hr>


        <!-- -------------------------------------------------------------------------------------------------- -->
        <!-- CH6 用户参与舆情 -->
        <!-- -------------------------------------------------------------------------------------------------- -->
        <div class="UserPublicSentiment">
            <div class="title-with-button">
                <el-switch v-model="showUserPublicOpinion" style="transform: scale(1.25); " />
                <h2 style="color:rgb(2, 157, 255); font-weight: bold; margin-left:15px">用户参与舆情</h2>
            </div>

            <div v-show="showUserPublicOpinion" class="user-panel"
                style="display: flex; flex-direction: column; align-items: center; height: 100%;">
                <!-- 底部区域（待定） -->
                <div class="custom-timeline">
                    <div class="timelineContainer">
                        <div class="itemContainer flex titleContainer">
                            <div class="leftContainer flex" />
                            <div class="exceptLeft">
                                <div class=" middleContainer1 headerContainer">
                                    <div class="title">主要观点</div>
                                </div>
                                <div class="rightContainer" />
                            </div>
                        </div>
                        <div v-for="(item, index) in userParticipationTimeline.list" :key="item.id"
                            class="itemContainer flex" :class="{ first: index === 0 }">
                            <div class="leftContainer flex">
                                <!-- 左侧的图片和图片标题 -->
                                <div v-for="leftItem in item.leftContent" :key="leftItem.id" class="leftItem">
                                    <div v-for="t in leftItem.title" :key="t" class="l-tit">{{ t }}</div>
                                    <img :src="leftItem.img" class="l-img">
                                </div>
                            </div>
                            <!-- 中间的圆圈和右边的列表 -->
                            <div class="exceptLeft">
                                <div class="middleContainer">
                                    <div class="circle">{{ item.date }}</div>
                                </div>
                                <div class="rightContainer">
                                    <div v-for="(rightItem, rIndex) in item.rightContent" :key="rIndex">
                                        <div class="flex">
                                            <div class="r-index">{{ rIndex + 1 }}</div>
                                            <div class="r-content">
                                                <div v-for="rTxt in rightItem" :key="rTxt" class="r-txt">{{ rTxt }}</div>
                                            </div>
                                        </div>
                                    </div>
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
            dates: ['近1天', '近7天', '近30天'],
            recentDate_selected: '',

            userinfo_selected: 'cnnphilippines',
            //  用户基本信息数据
            usernames: ['cnnphilippines', 'factibulrk', 'jabbyck'], // 用户名选项
            userProfile: [{
                username: 'cnnphilippines',
                imageUrl: require('@/assets/userinfo_images/1.jpg'),
                url: 'https://example.com',
                bio: '用人工智能AI和软件造福人类，改变世界。',
                dob: '1990-01-01',
                region: '上海',
                profession: '软件工程师',
                followers: ['factibulrk', 'globyck'],
                following: ['cnnphi', 'cnnfollowing'],
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
            {
                username: 'factibulrk',
                imageUrl: require('@/assets/userinfo_images/2.jpg'),
                url: 'https://example.com',
                bio: '为人民奉献，保障生命财产安全，人人有责',
                dob: '2000-04-21',
                region: 'Policeman',
                profession: '警察',
                followers: ['factibulrk', 'globyck', 'tensora', 'Tommyck'],
                following: ['cnnphi', 'cnnfollowing', 'Jackxi'],
                posts: 421,
                reposts: 89,
                comments: 110,
                reposted: 360,
                commented: 750,
                liked: 44,
                // New fields
                commentedUserCount: 52, // 被评论人数
                repostedUserCount: 41, // 被转发人数
                likedUserCount: 220 // 被赞人数
            },
            {
                username: 'jabbyck',
                imageUrl: require('@/assets/userinfo_images/3.jpg'),
                url: 'https://example.com',
                bio: '救人救己，医人医心',
                dob: '1992-03-04',
                region: '伦敦',
                profession: '医生',
                followers: ['factibulrk', 'globyck', 'syouke', 'torchyuu', 'tensora'],
                following: ['cnnphi', 'factibulrk', 'tensora'],
                posts: 197,
                reposts: 52,
                comments: 450,
                reposted: 121,
                commented: 77,
                liked: 332,
                // New fields
                commentedUserCount: 25, // 被评论人数
                repostedUserCount: 91, // 被转发人数
                likedUserCount: 21 // 被赞人数
            },
            ],
            selectedUserProfileNow: {},
            tableData: [], // 用于存储键值对
            tableRows: [{}], // 用于表格行数据
            basicInfo: [],
            followersList: [],
            followingList: [],
            activeNames: ['1', '2', '3'], // 默认展开基础信息

            //  用户行为规律数据
            UserBehaviourRegularData: [
                ['2024/01', 220], ['2024/02', 289], ['2024/03', 250], ['2024/04', 44], ['2024/05', 120], ['2024/06', 234], ['2024/07', 49], ['2024/08', 193]
            ],

            UserBehaviourRegularActiveTimePointsData: ['2024/01/02 10:10:10', '2024/03/02 11:12:17', '2024/05/23 08:03:12', '2024/07/11 18:21:32', '2024/07/19 11:11:42'], // This will hold the fetched time points

            //  用户人格与价值观数据
            BigFivePersonalData: {
                'Openness': 0.9,
                'Conscientiousness': 0.5,
                'Extraversion': 0.7,
                'Agreeableness': 0.4,
                'Neuroticism': 0.6
            },
            PersonalValueData: {
                posvalue: [0.1, 0.2, 0, 0.4, 0, 0.3, 0.9, 0.5, 0, 0],
                negvalue: [0, 0, -0.3, 0, -0.5, 0, 0, 0, -0.6, -0.4]
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
                imgUrl: [require('@/assets/userinfo_images/1.jpg'), require('@/assets/userinfo_images/2.jpg'), require('@/assets/userinfo_images/3.jpg'), require('@/assets/userinfo_images/4.jpg'), require('@/assets/userinfo_images/5.jpg'), require('@/assets/userinfo_images/6.jpg'), require('@/assets/userinfo_images/7.jpg'), require('@/assets/userinfo_images/8.jpg'), require('@/assets/userinfo_images/9.jpg')]
            },
            topInteractionUsers: {
                relationType: ['非好友', '粉丝', '非好友', '粉丝', '互粉', '粉丝', '互粉', '关注', '粉丝'],
                imgUrl: [require('@/assets/userinfo_images/7.jpg'), require('@/assets/userinfo_images/8.jpg'), require('@/assets/userinfo_images/5.jpg'), require('@/assets/userinfo_images/3.jpg'), require('@/assets/userinfo_images/9.jpg'), require('@/assets/userinfo_images/6.jpg'), require('@/assets/userinfo_images/4.jpg'), require('@/assets/userinfo_images/2.jpg'), require('@/assets/userinfo_images/1.jpg')]
            },
            topCoInteractionUsers: {
                relationType: ['非好友', '粉丝', '非好友', '粉丝', '互粉', '粉丝', '互粉', '关注', '粉丝'],
                imgUrl: [require('@/assets/userinfo_images/9.jpg'), require('@/assets/userinfo_images/6.jpg'), require('@/assets/userinfo_images/7.jpg'), require('@/assets/userinfo_images/1.jpg'), require('@/assets/userinfo_images/3.jpg'), require('@/assets/userinfo_images/8.jpg'), require('@/assets/userinfo_images/5.jpg'), require('@/assets/userinfo_images/4.jpg'), require('@/assets/userinfo_images/2.jpg')]
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
                            当我们在西菲律宾海（WPS）专属经济区的自己的水域和土地上受到欺凌和占领时，全体菲律宾人民必须为了祖国团结起来\n
                            我们是菲律宾青年。我们停在了西菲律宾海\n
                            OCTA Research 的一项调查显示，七成菲律宾人认为，总统小费迪南德·马科斯的政府应该通过军事行动和外交手段维护该国在西菲律宾海 (WPS) 的领土权利。\n
                            当我们在西菲律宾海（WPS）专属经济区的自己的水域和土地上受到欺凌和占领时，全体菲律宾人民必须为了祖国团结起来---我们是菲律宾青年。我们停在了西菲律宾海`

                },
                {
                    name: '菲律宾人呼吁团结捍卫在西菲律宾海的主权123',
                    group: 0,
                    text: `当我们在西菲律宾海（WPS）专属经济区的自己的水域和土地上受到欺凌和占领时，全体菲律宾人民必须为了祖国团结起来\n
                            当我们在西菲律宾海（WPS）专属经济区的自己的水域和土地上受到欺凌和占领时，全体菲律宾人民必须为了祖国团结起来\n
                            我们是菲律宾青年。我们停在了西菲律宾海\n
                            OCTA Research 的一项调查显示，七成菲律宾人认为，总统小费迪南德·马科斯的政府应该通过军事行动和外交手段维护该国在西菲律宾海 (WPS) 的领土权利。\n
                            当我们在西菲律宾海（WPS）专属经济区的自己的水域和土地上受到欺凌和占领时，全体菲律宾人民必须为了祖国团结起来---我们是菲律宾青年。我们停在了西菲律宾海`

                },
                {
                    name: '美国人呼吁总统当选应当公平竞争',
                    group: 1,
                    text: `当我们在西菲律宾海（WPS）专属经济区的自己的水域和土地上受到欺凌和占领时，全体菲律宾人民必须为了祖国团结起来\n
                            当我们在西菲律宾海（WPS）专属经济区的自己的水域和土地上受到欺凌和占领时，全体菲律宾人民必须为了祖国团结起来\n
                            我们是菲律宾青年。我们停在了西菲律宾海\n`

                },
                {
                    name: '美国人呼吁总统当选应当公平竞争123',
                    group: 1,
                    text: `当我们在西菲律宾海（WPS）专属经济区的自己的水域和土地上受到欺凌和占领时，全体菲律宾人民必须为了祖国团结起来\n
                            当我们在西菲律宾海（WPS）专属经济区的自己的水域和土地上受到欺凌和占领时，全体菲律宾人民必须为了祖国团结起来\n
                            我们是菲律宾青年。我们停在了西菲律宾海\n
                            我们是菲律宾青年。我们停在了西菲律宾海\n
                            OCTA Research 的一项调查显示，七成菲律宾人认为，总统小费迪南德·马科斯的政府应该通过军事行动和外交手段维护该国在西菲律宾海 (WPS) 的领土权利。\n
                            OCTA Research 的一项调查显示，七成菲律宾人认为，总统小费迪南德·马科斯的政府应该通过军事行动和外交手段维护该国在西菲律宾海 (WPS) 的领土权利。\n
                            当我们在西菲律宾海（WPS）专属经济区的自己的水域和土地上受到欺凌和占领时，全体菲律宾人民必须为了祖国团结起来---我们是菲律宾青年。我们停在了西菲律宾海`

                },

                {
                    name: '美国人呼吁总统当选应当公平竞争12345',
                    group: 1,
                    text: `当我们在西菲律宾海（WPS）专属经济区的自己的水域和土地上受到欺凌和占领时，全体菲律宾人民必须为了祖国团结起来\n
                            当我们在西菲律宾海（WPS）专属经济区的自己的水域和土地上受到欺凌和占领时，全体菲律宾人民必须为了祖国团结起来\n
                            OCTA Research 的一项调查显示，七成菲律宾人认为，总统小费迪南德·马科斯的政府应该通过军事行动和外交手段维护该国在西菲律宾海 (WPS) 的领土权利。\n
                            当我们在西菲律宾海（WPS）专属经济区的自己的水域和土地上受到欺凌和占领时，全体菲律宾人民必须为了祖国团结起来---我们是菲律宾青年。我们停在了西菲律宾海`

                },

                {
                    name: '菲律宾人呼吁团结捍卫在西菲律宾海的主权11111',
                    group: 0,
                    text: `当我们在西菲律宾海（WPS）专属经济区的自己的水域和土地上受到欺凌和占领时，全体菲律宾人民必须为了祖国团结起来\n
                            当我们在西菲律宾海（WPS）专属经济区的自己的水域和土地上受到欺凌和占领时，全体菲律宾人民必须为了祖国团结起来\n
                            我们是菲律宾青年。我们停在了西菲律宾海\n`

                },


                {
                    name: '菲律宾人呼吁团结捍卫在西菲律宾海的主权789',
                    group: 0,
                    text: `当我们在西菲律宾海（WPS）专属经济区的自己的水域和土地上受到欺凌和占领时，全体菲律宾人民必须为了祖国团结起来\n
                            当我们在西菲律宾海（WPS）专属经济区的自己的水域和土地上受到欺凌和占领时，全体菲律宾人民必须为了祖国团结起来\n`

                },

                {
                    name: '菲律宾人呼吁团结捍卫在西菲律宾海的主权9998',
                    group: 0,
                    text: `当我们在西菲律宾海（WPS）专属经济区的自己的水域和土地上受到欺凌和占领时，全体菲律宾人民必须为了祖国团结起来\n
                            当我们在西菲律宾海（WPS）专属经济区的自己的水域和土地上受到欺凌和占领时，全体菲律宾人民必须为了祖国团结起来\n
                            OCTA Research 的一项调查显示，七成菲律宾人认为，总统小费迪南德·马科斯的政府应该通过军事行动和外交手段维护该国在西菲律宾海 (WPS) 的领土权利。\n
                            当我们在西菲律宾海（WPS）专属经济区的自己的水域和土地上受到欺凌和占领时，全体菲律宾人民必须为了祖国团结起来---我们是菲律宾青年。我们停在了西菲律宾海`

                },
            ],
            OpinionTableData: [
                {
                    name: '菲律宾人呼吁团结捍卫在西菲律宾海的主权',
                    group: 0,
                    text: `当我们在西菲律宾海（WPS）专属经济区的自己的水域和土地上受到欺凌和占领时，全体菲律宾人民必须为了祖国团结起来\n
                            当我们在西菲律宾海（WPS）专属经济区的自己的水域和土地上受到欺凌和占领时，全体菲律宾人民必须为了祖国团结起来\n
                            OCTA Research 的一项调查显示，七成菲律宾人认为，总统小费迪南德·马科斯的政府应该通过军事行动和外交手段维护该国在西菲律宾海 (WPS) 的领土权利。\n
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
            // this.userinfo_selected = this.usernames[0] // 设置默认选择第一个用户名
            this.fetchUserData(this.usernames[0]) // 可选：在组件加载时立即获取第一个用户的数据
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
        select(platform) {
        // 如果再次点击已经选中的平台，取消选择
        if (this.selected === platform) {
          this.selected = null;  // 恢复到未选中的状态
        } else {
          this.selected = platform;  // 更新选中的平台
        }
      },
        // 格式化换行
        formatText(text) {
            return text.replace(/\n/g, '<br>');
        },
        /*  第一部分 用户基本信息  */
        //  请求用户数据
        fetchUserData(data) {
            const username = data
            const selectedUserProfile = this.userProfile.find(profile => profile.username === username);


            if (selectedUserProfile) {
                this.selectedUserProfileNow = { ...selectedUserProfile };
                this.fillBasicInfo(); // Use default or existing values to fill the table
                this.fillTableData();
                // If user found in the profile array, fill the data
                //     axios.post('https://localhost:8080/user', {
                //         username: username // Send the selected username to the backend
                //     })
                //         .then(response => {
                //             if (response.data) {
                //                 // Merge the response data with the selected user profile
                //                 this.selectedUserProfileNow = { ...selectedUserProfile, ...response.data };
                //                 this.fillBasicInfo(selectedUserProfileNow); // Refill the table with the new data
                //                 this.fillTableData(selectedUserProfileNow);
                //             }
                //         })
                //         .catch(error => {
                //             console.error('Error fetching user data:', error);
                //             // Fallback to use the selected user's data even if an error occurs
                //             this.selectedUserProfileNow = { ...selectedUserProfile };
                //             console.log(selectedUserProfileNow)
                //             this.fillBasicInfo(selectedUserProfileNow); // Use default or existing values to fill the table
                //             this.fillTableData(selectedUserProfileNow);
                //         });
                // } else {
                //     console.error('No user profile found with the given username:', username);
                //     // Optionally handle the case where no user profile matches
                // }
            }
        },
        //  基本信息定义
        fillBasicInfo() {
            this.basicInfo = [
                { key: '用户名', value: this.selectedUserProfileNow.username },
                { key: '个人简介', value: this.selectedUserProfileNow.bio },
                { key: '出生日期', value: this.selectedUserProfileNow.dob },
                { key: '地区', value: this.selectedUserProfileNow.region },
                { key: '职业', value: this.selectedUserProfileNow.profession }
            ]

            this.followersList = this.selectedUserProfileNow.followers.map(follower => ({
                name: follower
            }))

            this.followingList = this.selectedUserProfileNow.following.map(following => ({
                name: following
            }))
        },
        //  用户信息表格中的原发 转发 评论 点赞等数据
        fillTableData() {
            const row = {} // 初始化 row 对象
            //  请求数据  暂时无  换成假数据
            this.tableData = [
                { key: '原发帖个数', value: this.selectedUserProfileNow.posts || 10 },
                { key: '转发帖个数', value: this.selectedUserProfileNow.reposts || 10 },
                { key: '评论个数', value: this.selectedUserProfileNow.comments || 10 },
                { key: '被评论个数', value: this.selectedUserProfileNow.commented || 10 },
                { key: '被评论人数', value: this.selectedUserProfileNow.commentedUserCount || 10 },
                { key: '被转发个数', value: this.selectedUserProfileNow.reposted || 10 },
                { key: '被转发人数', value: this.selectedUserProfileNow.repostedUserCount || 10 },
                { key: '被赞个数', value: this.selectedUserProfileNow.liked || 10 },
                { key: '被赞人数', value: this.selectedUserProfileNow.likedUserCount || 10 }

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
                    text: '用户不同时间段发布贴文的数量折线图',
                    left: 'center',  // Centers the title horizontally
                    top: 'top',      // Positions the title at the top of the chart (default)
                    textStyle: {
                        fontSize: 16,  // Adjust the font size if needed
                        fontWeight: 'bold'  // Optional: make the title bold
                    }
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
                    right: 100, //   右侧工具箱
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
                            lte: 250,
                            color: '#AA069F'
                        },
                        {
                            gt: 250,
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
                                yAxis: 250
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

            const OtherPersonalValues = [
                 0.4,
                 0.8,
                0.1,
                0.7,
                 0.2
            ]


            const radarOption = {
                tooltip: {
                    trigger: 'item',  // 设置为触发项
                    formatter: function (params) {
                        const labels = ['开放性', '责任心', '外向性', '宜人性', '神经质'];
                        const value = params.value;
                        let result = '';
                        for (let i = 0; i < value.length; i++) {
                            result += `${labels[i]}: ${value[i]}<br>`;
                        }
                        return result;
                    }
                },
                legend: {
                        show: true,
                        left: 'center',
                        top: 'top'
                        
                },
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
                            name: '大五人格'
                        },
                        {
                            value: OtherPersonalValues,
                            name: '其他价值观'
                        },
                    ],
                    
                }]
            }

            radarChart.setOption(radarOption)
        },


        initPersonalitiesValuesBarChart(data) {
            const barDom = this.$refs.UserPersonalitiesValuesBarChart;
            const barChart = echarts.init(barDom);

            const posvalue = data.posvalue;
            const negvalue = data.negvalue;

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
                    type: 'category',  // 改为 'category'
                    data: ['合作', '诚实', '慷慨', '公正', '勇敢', '权力', '成就', '享乐', '刺激', '自主']  // 将类别放在 X 轴上
                },
                yAxis: {
                    type: 'value'  // 改为 'value'
                },
                series: [
                    {
                        name: '正向',
                        type: 'bar',
                        stack: '总量',
                        label: {
                            show: true,
                            position: 'top',  // 改为 'insideTop'，显示在柱子的顶部
                            formatter: function (params) {
                                // 值为0时不显示标签
                                return params.value === 0 ? '' : params.value;
                            }
                        },
                        data: posvalue
                    },
                    {
                        name: '反向',
                        type: 'bar',
                        stack: '总量',
                        label: {
                            show: true,
                            position: 'insideBottom',  // 改为 'insideTop'，显示在柱子的顶部
                            formatter: function (params) {
                                // 值为0时不显示标签
                                return params.value === 0 ? '' : params.value;
                            }
                        },
                        data: negvalue
                    }
                ]
            };

            barChart.setOption(barOption);
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
                    left: 'center'
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
                        repulsion: 180,  // 设置节点之间的排斥力
                    },
                    roam: true,
                    // label: {
                    //     show: true,
                    //     position: 'right',
                    //     formatter: '{b}',
                    // },
                    label: {
                        show: true,
                        position: 'right',
                        formatter: function (params) {
                            return params.name.slice(0, 4) + '...'; // 只显示前三个字符
                        },
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


        handleUserChange(value) {
            // 当选择改变时，立即调用 initOpinionChart 展示选中用户的观点图表
            const user = this.StandpointData.find(user => user.username === value);
            if (user) {
                this.initStandpointChart(user);
                this.generateStandpointTableData(user);
            } else {
                // 如果没有选择用户，默认展示第一个
                const defaultUser = this.StandpointData[0];
                this.initStandpointChart(defaultUser);
                this.generateStandpointTableData(defaultUser);
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

        generateStandpointTableData(data) {
            this.StandpointtableData = data.data.map(dataItem => {
                return {
                    username: data.username, // 如果需要显示用户名，这里需要传递
                    name: dataItem.name,
                    text: dataItem.text
                };
            });
        },


        fetchUserEmotionalData() {
            axios.get('https://localhost:8080/user/EmotionDistribution')
                .then(response => {
                    this.EmotionData = response.data;  //  这里应该获取所有话题观点数据
                    this.initEmotionalChart(this.EmotionData);  //  图加载所有话题观点
                    // this.prepareTableData(this.OpinionTopicData[0]);  //  这里表格应该展示选中的echarts图上的数据 默认有一个选中
                })
                .catch(error => {
                    console.error('Error fetching OpinionViewData:', error);
                    this.EmotionData = [
                        {
                            point: '极端愤怒',
                            text: `当我们在西菲律宾海（WPS）专属经济区的自己的水域和土地上受到欺凌和占领时，全体菲律宾人民必须为了祖国团结起来\n
                                        当我们在西菲律宾海（WPS）专属经济区的自己的水域和土地上受到欺凌和占领时，全体菲律宾人民必须为了祖国团结起来\n
                                        我们是菲律宾青年。我们停在了西菲律宾海\n
                                        OCTA Research 的一项调查显示，七成菲律宾人认为，总统小费迪南德·马科斯的政府应该通过军事行动和外交手段维护该国在西菲律宾海 (WPS) 的领土权利。\n
                                        当我们在西菲律宾海（WPS）专属经济区的自己的水域和土地上受到欺凌和占领时，全体菲律宾人民必须为了祖国团结起来---我们是菲律宾青年。我们停在了西菲律宾海`
                        }
                    ]
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
                    data: ['情绪', '极端情绪'],
                    // top: '2%',
                    left: 'center'
                }
            }
            myChart.setOption(option)
        },


        // 用户参与舆情
        fetchuserParticipationTimeline() {
            axios.get('http://localhost:8080/user-participation-timeline-data')
                .then(response => {
                    this.userParticipationTimeline = response.data
                })
                .catch(error => {
                    console.error('Error fetching user participation timeline data from backend:', error)
                    // Fallback to local data
                    const initData = {
                        'title': '主要观点',
                        'list': [
                            {
                                'date': 'May',
                                'leftContent': [
                                    { 'title': '洪秀柱访陆', 'img': require('@/assets/userinfo_images/group_users_images/Demo2.jpg') },
                                    { 'title': '国会改革提案', 'img': require('@/assets/userinfo_images/group_users_images/Demo2.jpg') },
                                    { 'title': '立法院斗殴事件', 'img': require('@/assets/userinfo_images/group_users_images/Demo2.jpg') },
                                    { 'title': '赖清德就职演说', 'img': require('@/assets/userinfo_images/group_users_images/Demo2.jpg') }
                                ],
                                'rightContent': [
                                    ['民主国家不容许立法院擅自扩权', '暴力不是民主政治'],
                                    ['民进党是双标诈骗党', '民进党少数不服多数'],
                                    ['国民党只代表脆弱多数'],
                                    ['洪秀柱在国民党内已无话语权']
                                ]
                            },
                            {
                                'date': 'Apr.',
                                'leftContent': [
                                    {
                                        'title': '中国快艇越界',
                                        'img': require('@/assets/userinfo_images/group_users_images/Demo1.jpg')
                                    },
                                    {
                                        'title': '赖清德入选《时代》百大人物',
                                        'img': require('@/assets/userinfo_images/group_users_images/LQD.jpg')
                                    },
                                    { 'title': '马习会', 'img': require('@/assets/userinfo_images/group_users_images/Demo2.jpg') }
                                ],
                                'rightContent': [
                                    ['马英九有意承担两岸和平使者', '马习会没有实质性内容'],
                                    ['赖清德备受美国认可', '台海情势左右世界民主政治的发展'],
                                    ['中国继续就快艇事件威胁台湾']
                                ]
                            },
                            {
                                'date': 'Feb.',
                                'leftContent': [
                                    {
                                        'title': '中国快艇越界',
                                        'img': require('@/assets/userinfo_images/group_users_images/Demo1.jpg')
                                    },
                                    {
                                        'title': '中国快艇越界',
                                        'img': require('@/assets/userinfo_images/group_users_images/Demo1.jpg')
                                    }
                                ],
                                'rightContent': [
                                    [' 中共对台湾武力恐吓', '台湾用行动抵御外部势力守护民主', '给中国渔民教训', '国军终于硬气了一次']
                                ]
                            },
                            {
                                'date': 'Jan.',
                                'leftContent': [
                                    { 'title': '台湾大选', 'img': require('@/assets/userinfo_images/group_users_images/Demo3.jpg') }
                                ],
                                'rightContent': [
                                    ['选举是台湾民主的体现', '民进党经过选举应该凝聚共识', '国民党此次失败后将再无翻身机会', '民进党打破轮流执政魔咒']
                                ]
                            }
                        ]
                    };
                    initData.list.map(item => {
                        item.leftContent.forEach(content => {
                            content.title = this.splitTitle(content.title);
                        });
                    });
                    this.userParticipationTimeline = initData;
                }
                )
        },

        splitTitle(title) {
            const result = [];
            for (let i = 0; i < title.length; i += 5) {
                result.push(title.slice(i, i + 5));
            }
            return result;
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
    margin-top: 5px;
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
    margin-bottom: 0px;
    font-weight: bold;
    color: deepskyblue;
    margin-top: 20px;
}


/* 活跃时点样式 */
.time-tag-wrapper {
    display: flex;
    flex-direction: column;
    justify-content: space-evenly;
    /* Distributes items with equal space above and below */
    align-items: center;
    /* Centers the items horizontally */
    height: 100%;
    /* Make sure the parent container has a defined height */
    padding: 10px;
    /* Optional: padding to provide space inside the parent container */
    gap: 22px;
    /* Add some space between items */
}

.time-tag-container {
    width: 100%;
    /* Ensure the tags take up the necessary space */
    display: flex;
    justify-content: center;
    /* Center the tags inside each container */
}

/* 用户参与舆情样式 */
.custom-timeline {
    display: flex;
    flex-direction: column;
    align-items: center;
}


.timelineContainer {
    height: 100vh;
    width: 80vw;
    min-width: 700px;
    /* 居中显示 */
    margin-left: 45%;
}

.titleContainer {
    padding-bottom: 0;
}

.headerContainer::before {
    width: 0;
    height: 0;
    border-width: 10px 5px;
    border-style: solid;
    border-color: transparent transparent #0a97ef transparent;
    z-index: 999;
    background: transparent;
}

.title {
    white-space: nowrap;
    position: absolute;
    font-size: 14px;
    left: 447px;
    top: 40px;
    color: #c6c6c6;
}

.flex {
    display: flex;
}

.itemContainer {
    display: flex;
    padding-bottom: 20px;
    align-items: center;
    position: relative;
    width: 100%;
}

.exceptLeft {
    flex: 1;
    display: flex;
    align-items: center;
    margin-left: 20px;
    margin-top: 20px;
}

.middleContainer {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 40px;
}

.middleContainer1 {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 40px;
}

.middleContainer1::before {
    content: "";
    height: calc(100% + 0px);
    width: 2px;
    /* background: #1abac8; */
    background: transparent;
    position: absolute;
    z-index: -1;
}

.middleContainer::before {
    content: "";
    height: calc(100% + 0px);
    width: 2px;
    /* background: #1abac8; */
    background: #0a97ef;
    position: absolute;
    z-index: -1;
}

.circle {
    width: 40px;
    height: 40px;
    border-radius: 50%;
    color: #fff;
    background: #0a97ef;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 14px;
    font-weight: 700;
}

.first {
    padding-top: 20px;
}

.first .middleContainer::before {
    height: calc(100% + 22px);
}

.leftContainer {
    width: 400px;
    display: flex;
    justify-content: flex-end;
    align-items: flex-end;
    column-gap: 40px;
}

.l-img {
    height: 70px;
    width: 70px;
}

.l-tit {
    font-size: 14px;
    line-height: 1;
    text-align: center;
    color: #878787;
}

.rightContainer {
    margin-left: 10px;
}

.r-index {
    font-size: 12px;
    color: #c9c9c7;
    background: #656565;
    height: 15px;
    width: 15px;
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
    margin-right: 3px;
    transform: scale(0.75);
}

.r-txt {
    font-size: 14px;
    line-height: 1;
    text-align: center;
    color: #878787;
    margin-bottom: 10px;
}

.r-content {
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    margin-bottom: 0px;
}
</style>



<template>
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
<!--        <div class="date-selectors">-->
<!--          <el-date-picker v-model="startDate" type="date" placeholder="开始日期" />-->
<!--          <el-date-picker v-model="endDate" type="date" placeholder="结束日期" />-->
<!--        </div>-->
          <div class="filtered-date">
            <el-select v-model="recentDate_selected" clearable placeholder="时间线" @change="fetchRecentDate" @clear="resetData" :style="{ width: '500px' }">
              <el-option
                v-for="date in dates"
                :key="date"
                :label="date"
                :value="date">
              </el-option>
            </el-select>
          </div>
      </div>

      <hr>

      <div class="LesMiserables">
        <div class="title-with-button">
          <el-switch v-model="showEventInfo" @change="toggleChart" style="transform: scale(1.25);" />
          <h2 style="color:rgb(2, 157, 255); font-weight: bold; margin-left:15px; " >
            舆情事件总览
          </h2>
        </div>
      </div>

      <!-- This div will be toggled based on the showEventInfo value -->
      <div v-if="showEventInfo">
        <div id="eventChart" style="width: 800px; height: 400px; margin: 0 auto" />
        <div class="content-conclusion">
          [A] 舆情事件关系图 (用户高度重合)
        </div>
      </div>
      <hr>

      <div class="title-with-button">
        <el-switch v-model="showEventRanking" @change="toggleChart" style="transform: scale(1.25)" />
        <h2 style="color: rgb(2, 157, 255); font-weight: bold; margin-left: 15px;">
          舆情事件排行榜
        </h2>
      </div>

      <!-- 这部分会根据 showEventRanking 的值显示或隐藏 -->
      <div v-if="showEventRanking">
        <div class="container-wrapper">
          <div class="lightContainer">
            <div class="light-ranking-title">
              总内容数量榜
            </div>
            <div class="light-ranking-content">
                <!-- 列出所有事件 -->
                <div
                  v-for="(event, index) in filteredEventRanking"
                  :key="index"
                  class="ranking-item"
                  @click="openGeneralRankingDialog(event)"
                >
                  {{ index + 1 }}. {{ event.event }}
                </div>

                <!-- 弹窗 -->
                <el-dialog
                  :visible.sync="generalDialogVisible"
                  width="50%"
                  :before-close="handleClose"
                  append-to-body
                >
                  <!-- 只显示选中的事件 -->
                  <el-form :model="form">
                    <el-form-item label="标题">
                      <el-input :value="dialogTitle"></el-input>
                    </el-form-item>
                  </el-form>
                  <el-form :model="form">
                    <el-form-item label="详细信息">
                      <el-input :value="`${generalSelectedEvent}`" readonly></el-input>
                    </el-form-item>
                    <el-form-item label="创建时间" style="margin-left:0; display: flex;flex-direction: column;justify-content: center;align-items: flex-start">
                      <el-tag type="danger" effect="plain" style="margin-top: 0; margin-left:0">
                        <i class="el-icon-time" style="margin-left: 2px;" />
                        {{generalSelectedDate}}
                      </el-tag>
                    </el-form-item>
                  </el-form>
                  <span slot="footer" class="dialog-footer">
                    <el-button @click="generalDialogVisible = false">Cancel</el-button>
                    <el-button type="primary" @click="generalDialogVisible = false">Confirm</el-button>
                  </span>
                </el-dialog>
              </div>
          </div>

          <div class="lightContainer">
            <div class="light-ranking-title">
              今日内容数量榜
            </div>
            <div class="light-ranking-content">
                <!-- 列出所有事件 -->
                <div
                  v-for="(event, index) in filteredEventRanking"
                  :key="index"
                  class="ranking-item"
                  @click="openTodayRankingDialog(event)"
                >
                  {{ index + 1 }}. {{ event.event }}
                </div>

                <!-- 弹窗 -->
                <el-dialog
                  :visible.sync="todayDialogVisible"
                  width="50%"
                  :before-close="handleClose"
                  append-to-body
                >
                  <!-- 只显示选中的事件 -->
                  <el-form :model="form">
                    <el-form-item label="标题">
                      <el-input :value="dialogTitle"></el-input>
                    </el-form-item>
                  </el-form>
                  <el-form :model="form">
                    <el-form-item label="详细信息">
                      <el-input :value="`${todaySelectedEvent}`" readonly></el-input>
                    </el-form-item>
                    <el-form-item label="创建时间" style="margin-left:0; display: flex;flex-direction: column;justify-content: center;align-items: flex-start">
                      <el-tag type="danger" effect="plain" style="margin-top: 0; margin-left:0">
                        <i class="el-icon-time" style="margin-left: 2px;" />
                        {{todaySelectedDate}}
                      </el-tag>
                    </el-form-item>
                  </el-form>
                  <span slot="footer" class="dialog-footer">
                    <el-button @click="todayDialogVisible = false">Cancel</el-button>
                    <el-button type="primary" @click="todayDialogVisible = false">Confirm</el-button>
                  </span>
                </el-dialog>
              </div>
          </div>

          <div class="lightContainer">
            <div class="light-ranking-title">
              近期内容数量飙升榜
            </div>
            <div class="light-ranking-content">
                <!-- 列出所有事件 -->
                <div
                  v-for="(event, index) in filteredEventRanking"
                  :key="index"
                  class="ranking-item"
                  @click="openRecentContentDialog(event)"
                >
                  {{ index + 1 }}. {{ event.event }}
                </div>

                <!-- 弹窗 -->
                <el-dialog
                  :visible.sync="recentContentDialogVisible"
                  width="50%"
                  :before-close="handleClose"
                  append-to-body
                >
                  <!-- 只显示选中的事件 -->
                  <el-form :model="form">
                    <el-form-item label="标题">
                      <el-input :value="dialogTitle"></el-input>
                    </el-form-item>
                  </el-form>
                  <el-form :model="form">
                    <el-form-item label="详细信息">
                      <el-input :value="`${recentContentSelectedEvent}`" readonly></el-input>
                    </el-form-item>
                    <el-form-item label="创建时间" style="margin-left:0; display: flex;flex-direction: column;justify-content: center;align-items: flex-start">
                      <el-tag type="danger" effect="plain" style="margin-top: 0; margin-left:0">
                        <i class="el-icon-time" style="margin-left: 2px;" />
                        {{recentContentSelectedDate}}
                      </el-tag>
                    </el-form-item>
                  </el-form>
                  <span slot="footer" class="dialog-footer">
                    <el-button @click="recentContentDialogVisible = false">Cancel</el-button>
                    <el-button type="primary" @click="recentContentDialogVisible = false">Confirm</el-button>
                  </span>
                </el-dialog>
              </div>
          </div>

          <div class="lightContainer">
            <div class="light-ranking-title">
              敌意言论数量榜
            </div>
            <div class="light-ranking-content">
                <!-- 列出所有事件 -->
                <div
                  v-for="(event, index) in filteredEventRanking"
                  :key="index"
                  class="ranking-item"
                  @click="openHostileDialog(event)"
                >
                  {{ index + 1 }}. {{ event.event }}
                </div>

                <!-- 弹窗 -->
                <el-dialog
                  :visible.sync="hostileDialogVisible"
                  width="50%"
                  :before-close="handleClose"
                  append-to-body
                >
                  <!-- 只显示选中的事件 -->
                  <el-form :model="form">
                    <el-form-item label="标题">
                      <el-input :value="dialogTitle"></el-input>
                    </el-form-item>
                  </el-form>
                  <el-form :model="form">
                    <el-form-item label="详细信息">
                      <el-input :value="`${hostileSelectedEvent}`" readonly></el-input>
                    </el-form-item>
                    <el-form-item label="创建时间" style="margin-left:0; display: flex;flex-direction: column;justify-content: center;align-items: flex-start">
                      <el-tag type="danger" effect="plain" style="margin-top: 0; margin-left:0">
                        <i class="el-icon-time" style="margin-left: 2px;" />
                        {{hostileSelectedDate}}
                      </el-tag>
                    </el-form-item>
                  </el-form>
                  <span slot="footer" class="dialog-footer">
                    <el-button @click="hostileDialogVisible = false">Cancel</el-button>
                    <el-button type="primary" @click="hostileDialogVisible = false">Confirm</el-button>
                  </span>
                </el-dialog>
              </div>
          </div>
        </div>

        <div class="container-wrapper">
          <div class="lightContainer">
            <div class="light-ranking-title">
              总参与人数榜
            </div>
            <div class="light-ranking-content">
                <!-- 列出所有事件 -->
                <div
                  v-for="(event, index) in filteredEventRanking"
                  :key="index"
                  class="ranking-item"
                  @click="openTotalParticipantDialog(event)"
                >
                  {{ index + 1 }}. {{ event.event }}
                </div>

                <!-- 弹窗 -->
                <el-dialog
                  :visible.sync="totalParticipantsDialogVisible"
                  width="50%"
                  :before-close="handleClose"
                  append-to-body
                >
                  <!-- 只显示选中的事件 -->
                  <el-form :model="form">
                    <el-form-item label="标题">
                      <el-input :value="dialogTitle"></el-input>
                    </el-form-item>
                  </el-form>
                  <el-form :model="form">
                    <el-form-item label="详细信息">
                      <el-input :value="`${totalParticipantsSelectedEvent}`" readonly></el-input>
                    </el-form-item>
                    <el-form-item label="创建时间" style="margin-left:0; display: flex;flex-direction: column;justify-content: center;align-items: flex-start">
                      <el-tag type="danger" effect="plain" style="margin-top: 0; margin-left:0">
                        <i class="el-icon-time" style="margin-left: 2px;" />
                        {{totalParticipantsSelectedDate}}
                      </el-tag>
                    </el-form-item>
                  </el-form>
                  <span slot="footer" class="dialog-footer">
                    <el-button @click="totalParticipantsDialogVisible = false">Cancel</el-button>
                    <el-button type="primary" @click="totalParticipantsDialogVisible = false">Confirm</el-button>
                  </span>
                </el-dialog>
              </div>
          </div>

          <div class="darkContainer">
            <div class="ranking-title">
              今日参与人数榜
            </div>
            <div class="ranking-content">
              <div v-for="(event, index) in filteredEventRanking" :key="index"  class="ranking-item" @click="openTodayParticipantDialog(event)">
                {{ index + 1 }}. {{ event.event }}
                <img :src="eventImgSrc[index]" :alt="`Image ${index + 1}`">
              </div>
              <el-dialog
                  :visible.sync="todayParticipantsDialogVisible"
                  width="50%"
                  :before-close="handleClose"
                  append-to-body
                >
                  <!-- 只显示选中的事件 -->
                  <el-form :model="form">
                    <el-form-item label="标题">
                      <el-input :value="dialogTitle"></el-input>
                    </el-form-item>
                  </el-form>
                  <el-form :model="form">
                    <el-form-item label="详细信息">
                      <el-input :value="`${todayParticipantsSelectedEvent}`" readonly></el-input>
                    </el-form-item>
                    <el-form-item label="创建时间" style="margin-left:0; display: flex;flex-direction: column;justify-content: center;align-items: flex-start">
                      <el-tag type="danger" effect="plain" style="margin-top: 0; margin-left:0">
                        <i class="el-icon-time" style="margin-left: 2px;" />
                        {{todayParticipantsSelectedDate}}
                      </el-tag>
                    </el-form-item>
                  </el-form>
                  <span slot="footer" class="dialog-footer">
                    <el-button @click="todayParticipantsDialogVisible = false">Cancel</el-button>
                    <el-button type="primary" @click="todayParticipantsDialogVisible = false">Confirm</el-button>
                  </span>
                </el-dialog>
            </div>
          </div>

          <div class="lightContainer">
            <div class="light-ranking-title">
              近期参与人数飙升榜
            </div>
            <div class="light-ranking-content">
                <!-- 列出所有事件 -->
                <div
                  v-for="(event, index) in filteredEventRanking"
                  :key="index"
                  class="ranking-item"
                  @click="openRecentUserDialog(event)"
                >
                  {{ index + 1 }}. {{ event.event }}
                </div>

                <!-- 弹窗 -->
                <el-dialog
                  :visible.sync="recentUserDialogVisible"
                  width="50%"
                  :before-close="handleClose"
                  append-to-body
                >
                  <!-- 只显示选中的事件 -->
                  <el-form :model="form">
                    <el-form-item label="标题">
                      <el-input :value="dialogTitle"></el-input>
                    </el-form-item>
                  </el-form>
                  <el-form :model="form">
                    <el-form-item label="详细信息">
                      <el-input :value="`${recentUserSelectedEvent}`" readonly></el-input>
                    </el-form-item>
                    <el-form-item label="创建时间" style="margin-left:0; display: flex;flex-direction: column;justify-content: center;align-items: flex-start">
                      <el-tag type="danger" effect="plain" style="margin-top: 0; margin-left:0">
                        <i class="el-icon-time" style="margin-left: 2px;" />
                        {{recentUserSelectedDate}}
                      </el-tag>
                    </el-form-item>
                  </el-form>
                  <span slot="footer" class="dialog-footer">
                    <el-button @click="recentUserDialogVisible = false">Cancel</el-button>
                    <el-button type="primary" @click="recentUserDialogVisible = false">Confirm</el-button>
                  </span>
                </el-dialog>
              </div>
          </div>

          <div class="lightContainer">
            <div class="light-ranking-title">
              敌意言论比例榜
            </div>
            <div class="light-ranking-content">
                <!-- 列出所有事件 -->
                <div
                  v-for="(event, index) in filteredEventRanking"
                  :key="index"
                  class="ranking-item"
                  @click="openHostileRatioDialog(event)"
                >
                  {{ index + 1 }}. {{ event.event }}
                </div>

                <!-- 弹窗 -->
                <el-dialog
                  :visible.sync="hostileRatioDialogVisible"
                  width="50%"
                  :before-close="handleClose"
                  append-to-body
                >
                  <!-- 只显示选中的事件 -->
                  <el-form :model="form">
                    <el-form-item label="标题">
                      <el-input :value="dialogTitle"></el-input>
                    </el-form-item>
                  </el-form>
                  <el-form :model="form">
                    <el-form-item label="详细信息">
                      <el-input :value="`${hostileRatioSelectedEvent}`" readonly></el-input>
                    </el-form-item>
                    <el-form-item label="创建时间" style="margin-left:0; display: flex;flex-direction: column;justify-content: center;align-items: flex-start">
                      <el-tag type="danger" effect="plain" style="margin-top: 0; margin-left:0">
                        <i class="el-icon-time" style="margin-left: 2px;" />
                        {{hostileRatioSelectedDate}}
                      </el-tag>
                    </el-form-item>
                  </el-form>
                  <span slot="footer" class="dialog-footer">
                    <el-button @click="hostileRatioDialogVisible = false">Cancel</el-button>
                    <el-button type="primary" @click="hostileRatioDialogVisible = false">Confirm</el-button>
                  </span>
                </el-dialog>
              </div>
          </div>
        </div>

        <div class="content-conclusion">
          [B] 舆情事件分维度排行榜
        </div>
      </div>
      <hr>

      <div class="LesMiserables">
        <div class="title-with-button">
          <el-switch v-model="showUserInfo" @change="toggleChart" style="transform: scale(1.25)" />
          <h2 style="color:rgb(2, 157, 255); font-weight: bold; margin-left:15px">关键用户总览</h2>
        </div>
      </div>
      <div v-if="showUserInfo">
        <div id="userChart" style="width: 1000px; height: 400px; margin: 0 auto" />
        <div class="content-conclusion">
          [C] 关键用户关系图 (参与事件高度重合)
        </div>
      </div>
      <hr>

      <div class="title-with-button">
        <el-switch v-model="showUserRanking" @change="toggleChart" style="transform: scale(1.25)" />
        <h2 style="color:rgb(2, 157, 255); font-weight: bold; margin-left:15px">关键用户排行榜</h2>
      </div>
      <div v-if="showUserRanking">
        <div class="container-wrapper">
          <div class="lightContainer">
            <div class="light-ranking-title">
              大V用户(出度)榜
            </div>
            <div class="light-ranking-content">
              <!-- 列出所有事件 -->
              <div
                v-for="(user, index) in filteredUserRanking"
                :key="index"
                class="ranking-item"
                @click="openUser1Dialog(user)"
              >
                {{ index + 1 }}. {{ user.user }}
              </div>
                <!-- 弹窗 -->
              <el-dialog
                :visible.sync="user1DialogVisible"
                width="50%"
                :before-close="handleClose"
                append-to-body
              >
                <!-- 只显示选中的事件 -->
                <el-form :model="form">
                  <el-form-item label="标题">
                    <el-input :value=dialogTitle></el-input>
                  </el-form-item>
                </el-form>
                <el-form :model="form">
                  <el-form-item label="详细信息">
                    <el-input :value="`${user1Ranking}`" readonly></el-input>
                  </el-form-item>
                  <el-form-item label="创建时间" style="margin-left:0; display: flex;flex-direction: column;justify-content: center;align-items: flex-start">
                    <el-tag type="danger" effect="plain" style="margin-top: 0; margin-left:0">
                      <i class="el-icon-time" style="margin-left: 2px;" />
                      {{user1RankingDate}}
                    </el-tag>
                  </el-form-item>
                </el-form>
                <span slot="footer" class="dialog-footer">
                  <el-button @click="user1DialogVisible = false">Cancel</el-button>
                  <el-button type="primary" @click="user1DialogVisible = false">Confirm</el-button>
                </span>
              </el-dialog>
            </div>
          </div>

          <div class="lightContainer">
            <div class="light-ranking-title">
              大V用户(高阶互动)榜
            </div>
            <div class="light-ranking-content">
              <!-- 列出所有事件 -->
              <div
                v-for="(user, index) in filteredUserRanking"
                :key="index"
                class="ranking-item"
                @click="openUser2Dialog(user)"
              >
                {{ index + 1 }}. {{ user.user }}
              </div>
                <!-- 弹窗 -->
              <el-dialog
                :visible.sync="user2DialogVisible"
                width="50%"
                :before-close="handleClose"
                append-to-body
              >
                <!-- 只显示选中的事件 -->
                <el-form :model="form">
                  <el-form-item label="标题">
                    <el-input :value=dialogTitle></el-input>
                  </el-form-item>
                </el-form>
                <el-form :model="form">
                  <el-form-item label="详细信息">
                    <el-input :value="`${user2Ranking}`" readonly></el-input>
                  </el-form-item>
                  <el-form-item label="创建时间" style="margin-left:0; display: flex;flex-direction: column;justify-content: center;align-items: flex-start">
                    <el-tag type="danger" effect="plain" style="margin-top: 0; margin-left:0">
                      <i class="el-icon-time" style="margin-left: 2px;" />
                      {{user2RankingDate}}
                    </el-tag>
                  </el-form-item>
                </el-form>
                <span slot="footer" class="dialog-footer">
                  <el-button @click="user2DialogVisible = false">Cancel</el-button>
                  <el-button type="primary" @click="user2DialogVisible = false">Confirm</el-button>
                </span>
              </el-dialog>
            </div>
          </div>

          <div class="darkContainer">
            <div class="ranking-title" style="margin-left:8px">
              大V用户(出度)榜
            </div>
            <div class="user-ranking-content">
              <div v-for="(user, index) in filteredUserRanking" :key="index"  class="ranking-item" @click="openUser3Dialog(user)">
                {{ index + 1 }}. {{ user.user }}
                <img :src="userImgSrc[index]" :alt="`Image ${index + 1}`">
              </div>
              <el-dialog
                :visible.sync="user3DialogVisible"
                width="50%"
                :before-close="handleClose"
                append-to-body
              >
                <!-- 只显示选中的事件 -->
                <el-form :model="form">
                  <el-form-item label="标题">
                    <el-input :value=dialogTitle></el-input>
                  </el-form-item>
                </el-form>
                <el-form :model="form">
                  <el-form-item label="详细信息">
                    <el-input :value="`${user3Ranking}`" readonly></el-input>
                  </el-form-item>
                  <el-form-item label="创建时间" style="margin-left:0; display: flex;flex-direction: column;justify-content: center;align-items: flex-start">
                    <el-tag type="danger" effect="plain" style="margin-top: 0; margin-left:0">
                      <i class="el-icon-time" style="margin-left: 2px;" />
                      {{user3RankingDate}}
                    </el-tag>
                  </el-form-item>
                </el-form>
                <span slot="footer" class="dialog-footer">
                  <el-button @click="user3DialogVisible = false">Cancel</el-button>
                  <el-button type="primary" @click="user3DialogVisible = false">Confirm</el-button>
                </span>
              </el-dialog>
            </div>
          </div>

          <div class="lightContainer">
            <div class="light-ranking-title">
              高产用户(原帖)榜
            </div>
            <div class="light-ranking-content">
              <!-- 列出所有事件 -->
              <div
                v-for="(user, index) in filteredUserRanking"
                :key="index"
                class="ranking-item"
                @click="openUser4Dialog(user)"
              >
                {{ index + 1 }}. {{ user.user }}
              </div>
                <!-- 弹窗 -->
              <el-dialog
                :visible.sync="user4DialogVisible"
                width="50%"
                :before-close="handleClose"
                append-to-body
              >
                <!-- 只显示选中的事件 -->
                <el-form :model="form">
                  <el-form-item label="标题">
                    <el-input :value=dialogTitle></el-input>
                  </el-form-item>
                </el-form>
                <el-form :model="form">
                  <el-form-item label="详细信息">
                    <el-input :value="`${user4Ranking}`" readonly></el-input>
                  </el-form-item>
                  <el-form-item label="创建时间" style="margin-left:0; display: flex;flex-direction: column;justify-content: center;align-items: flex-start">
                    <el-tag type="danger" effect="plain" style="margin-top: 0; margin-left:0">
                      <i class="el-icon-time" style="margin-left: 2px;" />
                      {{user4RankingDate}}
                    </el-tag>
                  </el-form-item>
                </el-form>
                <span slot="footer" class="dialog-footer">
                  <el-button @click="user4DialogVisible = false">Cancel</el-button>
                  <el-button type="primary" @click="user4DialogVisible = false">Confirm</el-button>
                </span>
              </el-dialog>
            </div>
          </div>
        </div>


        </div>
        <div class="container-wrapper">
          <div class="lightContainer">
            <div class="light-ranking-title">
              台独/统一·议题活跃榜
            </div>
            <div class="light-ranking-content">
              <!-- 列出所有事件 -->
              <div
                v-for="(user, index) in filteredUserRanking"
                :key="index"
                class="ranking-item"
                @click="openUser5Dialog(user)"
              >
                {{ index + 1 }}. {{ user.user }}
              </div>
                <!-- 弹窗 -->
              <el-dialog
                :visible.sync="user5DialogVisible"
                width="50%"
                :before-close="handleClose"
                append-to-body
              >
                <!-- 只显示选中的事件 -->
                <el-form :model="form">
                  <el-form-item label="标题">
                    <el-input :value=dialogTitle></el-input>
                  </el-form-item>
                </el-form>
                <el-form :model="form">
                  <el-form-item label="详细信息">
                    <el-input :value="`${user5Ranking}`" readonly></el-input>
                  </el-form-item>
                  <el-form-item label="创建时间" style="margin-left:0; display: flex;flex-direction: column;justify-content: center;align-items: flex-start">
                    <el-tag type="danger" effect="plain" style="margin-top: 0; margin-left:0">
                      <i class="el-icon-time" style="margin-left: 2px;" />
                      {{user5RankingDate}}
                    </el-tag>
                  </el-form-item>
                </el-form>
                <span slot="footer" class="dialog-footer">
                  <el-button @click="user5DialogVisible = false">Cancel</el-button>
                  <el-button type="primary" @click="user5DialogVisible = false">Confirm</el-button>
                </span>
              </el-dialog>
            </div>
          </div>

          <div class="lightContainer">
            <div class="light-ranking-title">
              中国/中共·议题活跃榜
            </div>
            <div class="light-ranking-content">
              <!-- 列出所有事件 -->
              <div
                v-for="(user, index) in filteredUserRanking"
                :key="index"
                class="ranking-item"
                @click="openUser6Dialog(user)"
              >
                {{ index + 1 }}. {{ user.user }}
              </div>
                <!-- 弹窗 -->
              <el-dialog
                :visible.sync="user6DialogVisible"
                width="50%"
                :before-close="handleClose"
                append-to-body
              >
                <!-- 只显示选中的事件 -->
                <el-form :model="form">
                  <el-form-item label="标题">
                    <el-input :value=dialogTitle></el-input>
                  </el-form-item>
                </el-form>
                <el-form :model="form">
                  <el-form-item label="详细信息">
                    <el-input :value="`${user6Ranking}`" readonly></el-input>
                  </el-form-item>
                  <el-form-item label="创建时间" style="margin-left:0; display: flex;flex-direction: column;justify-content: center;align-items: flex-start">
                    <el-tag type="danger" effect="plain" style="margin-top: 0; margin-left:0">
                      <i class="el-icon-time" style="margin-left: 2px;" />
                      {{user6RankingDate}}
                    </el-tag>
                  </el-form-item>
                </el-form>
                <span slot="footer" class="dialog-footer">
                  <el-button @click="user6DialogVisible = false">Cancel</el-button>
                  <el-button type="primary" @click="user6DialogVisible = false">Confirm</el-button>
                </span>
              </el-dialog>
            </div>
          </div>

          <div class="lightContainer">
            <div class="light-ranking-title">
              民进党·议题活跃榜
            </div>
            <div class="light-ranking-content">
              <!-- 列出所有事件 -->
              <div
                v-for="(user, index) in filteredUserRanking"
                :key="index"
                class="ranking-item"
                @click="openUser7Dialog(user)"
              >
                {{ index + 1 }}. {{ user.user }}
              </div>
                <!-- 弹窗 -->
              <el-dialog
                :visible.sync="user7DialogVisible"
                width="50%"
                :before-close="handleClose"
                append-to-body
              >
                <!-- 只显示选中的事件 -->
                <el-form :model="form">
                  <el-form-item label="标题">
                    <el-input :value=dialogTitle></el-input>
                  </el-form-item>
                </el-form>
                <el-form :model="form">
                  <el-form-item label="详细信息">
                    <el-input :value="`${user7Ranking}`" readonly></el-input>
                  </el-form-item>
                  <el-form-item label="创建时间" style="margin-left:0; display: flex;flex-direction: column;justify-content: center;align-items: flex-start">
                    <el-tag type="danger" effect="plain" style="margin-top: 0; margin-left:0">
                      <i class="el-icon-time" style="margin-left: 2px;" />
                      {{user7RankingDate}}
                    </el-tag>
                  </el-form-item>
                </el-form>
                <span slot="footer" class="dialog-footer">
                  <el-button @click="user7DialogVisible = false">Cancel</el-button>
                  <el-button type="primary" @click="user7DialogVisible = false">Confirm</el-button>
                </span>
              </el-dialog>
            </div>
          </div>

          <div class="lightContainer">
            <div class="light-ranking-title">
              赖清德·议题活跃榜
            </div>
            <div class="light-ranking-content">
              <!-- 列出所有事件 -->
              <div
                v-for="(user, index) in filteredUserRanking"
                :key="index"
                class="ranking-item"
                @click="openUser8Dialog(user)"
              >
                {{ index + 1 }}. {{ user.user }}
              </div>
                <!-- 弹窗 -->
              <el-dialog
                :visible.sync="user8DialogVisible"
                width="50%"
                :before-close="handleClose"
                append-to-body
              >
                <!-- 只显示选中的事件 -->
                <el-form :model="form">
                  <el-form-item label="标题">
                    <el-input :value=dialogTitle></el-input>
                  </el-form-item>
                </el-form>
                <el-form :model="form">
                  <el-form-item label="详细信息">
                    <el-input :value="`${user8Ranking}`" readonly></el-input>
                  </el-form-item>
                  <el-form-item label="创建时间" style="margin-left:0; display: flex;flex-direction: column;justify-content: center;align-items: flex-start">
                    <el-tag type="danger" effect="plain" style="margin-top: 0; margin-left:0">
                      <i class="el-icon-time" style="margin-left: 2px;" />
                      {{user8RankingDate}}
                    </el-tag>
                  </el-form-item>
                </el-form>
                <span slot="footer" class="dialog-footer">
                  <el-button @click="user8DialogVisible = false">Cancel</el-button>
                  <el-button type="primary" @click="user8DialogVisible = false">Confirm</el-button>
                </span>
              </el-dialog>
            </div>
          </div>
        </div>
        <div class="content-conclusion">
          [D] 关键用户分维度排行榜
        </div>
      </div>
  </template>

  <script>
  import * as echarts from 'echarts'
  import axios from 'axios'
  import graphData from '@/assets/dashboard/les-miserables.json'
  import localUserRanking from '@/assets/dashboard/userRanking.json'
  import localEventRanking from '@/assets/dashboard/eventRanking.json'
  import {right} from "core-js/internals/array-reduce";


  export default {
    data() {
      return {
        dialogTitle: '',
        generalDialogVisible: false,
        todayDialogVisible: false,
        recentContentDialogVisible: false,
        hostileDialogVisible: false,
        totalParticipantsDialogVisible: false,
        todayParticipantsDialogVisible: false,
        recentUserDialogVisible: false,
        hostileRatioDialogVisible: false,

        user1DialogVisible: false,
        user2DialogVisible: false,
        user3DialogVisible: false,
        user4DialogVisible: false,
        user5DialogVisible: false,
        user6DialogVisible: false,
        user7DialogVisible: false,
        user8DialogVisible: false,

        // Selected event and date for each ranking
        generalSelectedEvent: '',
        generalSelectedDate: '',
        todaySelectedEvent: '',
        todaySelectedDate: '',
        recentContentSelectedEvent: '',
        recentContentSelectedDate: '',
        hostileSelectedEvent: '',
        hostileSelectedDate: '',
        totalParticipantsSelectedEvent: '',
        totalParticipantsSelectedDate: '',
        todayParticipantsSelectedEvent: '',
        todayParticipantsSelectedDate: '',
        recentUserSelectedEvent: '',
        recentUserSelectedDate: '',
        hostileRatioSelectedEvent: '',
        hostileRatioSelectedDate: '',

        user1Ranking:'',
        user1RankingDate:'',
        user2Ranking:'',
        user2RankingDate:'',
        user3Ranking:'',
        user3RankingDate:'',
        user4Ranking:'',
        user4RankingDate:'',
        user5Ranking:'',
        user5RankingDate:'',
        user6Ranking:'',
        user6RankingDate:'',
        user7Ranking:'',
        user7RankingDate:'',
        user8Ranking:'',
        user8RankingDate:'',

        startDate: '',
        endDate: '',
        form:{},
        dialogVisible: false,
        selectedEvent: '',
        selected: null,
        selectedDate: '',
        chartInstance: null,
        userRanking: [],
        eventRanking: [],
        userImgSrc: [],
        eventImgSrc: [],
        dates: ['近1天','近7天','近30天'],
        recentDate_selected: '',
        nodes: [],
        links: [],
        showEventInfo: true,
        showEventRanking: true,
        showUserInfo: true,
        showUserRanking: true
      }
    },
    async mounted() {
      this.initEventChart()
      this.initUserChart()

      await this.fetchUserRanking() // 等待 fetchUserRanking 完成
      await this.fetchEventRanking() // 等待 fetchEventRanking 完成

      this.fetchUserImgSrc()
      this.fetchEventImgSrc()
    },
    computed: {
      filteredUserRanking() {
          // 如果没有选择日期范围，则返回前 10 条所有数据
          if (!this.startDate && !this.endDate) {
              return this.userRanking.slice(0, 10);
          }

          // 对每个用户的数据进行筛选
          const rankedUsers = this.userRanking.filter(user => {
              const userDate = new Date(user.date);
              const isAfterStartDate = this.startDate ? userDate >= new Date(this.startDate) : true;
              const isBeforeEndDate = this.endDate ? userDate <= new Date(this.endDate) : true;

              // 返回符合筛选条件的用户数据
              return isAfterStartDate && isBeforeEndDate;
          });

          // 只返回前 10 条符合条件的数据
          return rankedUsers.slice(0, 10);
      },

      filteredEventRanking() {
          // 如果没有选择日期范围，则返回前 10 条所有数据
          if (!this.startDate && !this.endDate) {
              return this.eventRanking.slice(0, 10);
          }

          // 对每个事件的数据进行筛选
          const rankedEvents = this.eventRanking.filter(event => {
              const eventDate = new Date(event.date);
              const isAfterStartDate = this.startDate ? eventDate >= new Date(this.startDate) : true;
              const isBeforeEndDate = this.endDate ? eventDate <= new Date(this.endDate) : true;

              // 返回符合筛选条件的事件数据
              return isAfterStartDate && isBeforeEndDate;
          });

          // 只返回前 10 条符合条件的数据
          return rankedEvents.slice(0, 10);
      },
    },
    methods: {
      right,
      select(platform) {
        // 如果再次点击已经选中的平台，取消选择
        if (this.selected === platform) {
          this.selected = null;  // 恢复到未选中的状态
        } else {
          this.selected = platform;  // 更新选中的平台
        }
      },
      handleClose(done) {
        done();  // Close the dialog directly without confirmation
      },
      initUserChart() {
        axios.get('http://localhost:8080/user-chart-data')
          .then(response => {
            // 使用后端返回的数据
            this.nodes = response.data.nodes;
            this.links = response.data.links;
          })
          .catch(error => {
            console.error('Error fetching user chart data from backend, using local data instead:', error);
            // 使用本地数据
            this.nodes = graphData.nodes;
            this.links = graphData.links;
          })
          .finally(() => {
            const allowedCategories = [1, 2, 4, 7]; // 允许的节点类别
            const filteredNodes = this.nodes.filter(node => allowedCategories.includes(node.category));

            // 过滤出与筛选后的节点相关的所有连接
            const filteredLinks = this.links.filter(link =>
              filteredNodes.some(node => node.id === link.source || node.id === link.target)
            );

            // 初始化图表
            this.chartInstance = echarts.init(document.getElementById('userChart'));
            const option = {
              title: {
                top: 'bottom',
                left: 'right'
              },
              tooltip: {},
              animationDuration: 1500,
              animationEasingUpdate: 'quinticInOut',
              series: [
                {
                  name: 'Les Miserables',
                  type: 'graph',
                  legendHoverLink: false,
                  layout: 'none',
                  data: filteredNodes.map(node => ({
                    ...node,
                    x: node.x * 5, // 拉开节点间的距离
                    y: node.y * 5, // 拉开节点间的距离
                    symbolSize: node.symbolSize * 0.8 // 缩小点的大小为原来的 0.8
                  })),
                  links: filteredLinks, // 使用筛选后的链接
                  categories: graphData.categories,
                  roam: true,
                  label: {
                    position: 'right',
                    formatter: '{b}'
                  },
                  lineStyle: {
                    color: 'source',
                    curveness: 0.3,
                    width: 1 // 保持线的宽度，确保其清晰度
                  },
                  emphasis: {
                    focus: 'adjacency',
                    lineStyle: {
                      width: 10 // 鼠标悬停时线的宽度
                    }
                  }
                }
              ]
            };
            this.chartInstance.setOption(option);
          });
      },

      initEventChart() {
        axios.get('http://localhost:8080/event-chart-data')
          .then(response => {
            // 使用后端返回的数据
            this.nodes = response.data.nodes;
            this.links = response.data.links;
          })
          .catch(error => {
            console.error('Error fetching event chart data from backend, using local data instead:', error);
            // 使用本地数据
            this.nodes = graphData.nodes;
            this.links = graphData.links;
          })
          .finally(() => {
            const allowedCategories = [1, 2, 6, 7, 8]; // 允许的节点类别
            const filteredNodes = this.nodes.filter(node => allowedCategories.includes(node.category));

            // 过滤出与筛选后的节点相关的连接
            const filteredLinks = this.links.filter(link =>
              filteredNodes.some(node => node.id === link.source || node.id === link.target)
            );

            // 初始化图表
            this.chartInstance = echarts.init(document.getElementById('eventChart'));
            const option = {
              title: {
                top: 'bottom',
                left: 'right'
              },
              tooltip: {},
              animationDuration: 1500,
              animationEasingUpdate: 'quinticInOut',
              series: [
                {
                  name: 'Les Miserables',
                  type: 'graph',
                  legendHoverLink: false,
                  layout: 'none',
                  data: filteredNodes.map(node => ({
                    ...node,
                    x: node.x * 5, // 拉开节点间的距离
                    y: node.y * 5, // 拉开节点间的距离
                    symbolSize: node.symbolSize * 0.8 // 缩小点的大小为原来的 0.8
                  })),
                  links: filteredLinks, // 使用筛选后的连接
                  categories: graphData.categories,
                  roam: true,
                  label: {
                    position: 'right',
                    formatter: '{b}'
                  },
                  lineStyle: {
                    color: 'source',
                    curveness: 0.3,
                    width: 1 // 保持线的宽度，确保其清晰度
                  },
                  emphasis: {
                    focus: 'adjacency',
                    lineStyle: {
                      width: 10 // 鼠标悬停时线的宽度
                    }
                  }
                }
              ]
            };
            this.chartInstance.setOption(option);
          });
      },

      fetchUserImgSrc() {
          axios.get('http://localhost:8080/user-img-src')
              .then(response => {
                  this.userImgSrc = response.data;
              })
              .catch(error => {
                  console.error('Error fetching user image sources from backend:', error);
                  this.userImgSrc = this.selectRandomImages(this.userRanking, 10);
              });
      },

      selectRandomImages(rankingList, count) {
          const imgPaths = [
              require('@/assets/userinfo_images/1.jpg'),
              require('@/assets/userinfo_images/2.jpg'),
              require('@/assets/userinfo_images/3.jpg'),
              require('@/assets/userinfo_images/4.jpg'),
              require('@/assets/userinfo_images/5.jpg'),
              require('@/assets/userinfo_images/6.jpg'),
              require('@/assets/userinfo_images/7.jpg'),
              require('@/assets/userinfo_images/8.jpg'),
              require('@/assets/userinfo_images/9.jpg'),
              require('@/assets/userinfo_images/10.jpg'),
              require('@/assets/userinfo_images/11.jpg')
          ];

          return Array.from({ length: Math.min(count, rankingList.length) }, () => {
              const randomIndex = Math.floor(Math.random() * imgPaths.length);
              return imgPaths[randomIndex];
          });
      },

      fetchEventImgSrc() {
          axios.get('http://localhost:8080/event-img-src')
              .then(response => {
                  this.eventImgSrc = response.data;
              })
              .catch(error => {
                  console.error('Error fetching event image sources from backend:', error);
                  this.eventImgSrc = this.selectRandomImages(this.eventRanking, 10);
              });
      },

      fetchUserRanking() {
        return axios.get('http://localhost:8080')
          .then(response => {
            // 直接将响应数据赋值给 userRanking
            this.userRanking = response.data
          })
          .catch(error => {
            console.error('Error fetching user ranking from backend:', error)
            // Log the length of localUserRanking and check its content
            console.log('Local user ranking data:', localUserRanking)
            console.log('Type of userRanking:', typeof this.userRanking)
            console.log('User Ranking:', this.userRanking)
            // Fallback to local data
            this.userRanking = localUserRanking
          })
      },
      fetchEventRanking() {
        return axios.get('http://localhost:8080')
          .then(response => {
            // 直接将响应数据赋值给 eventRanking
            this.eventRanking = response.data
          })
          .catch(error => {
            console.error('Error fetching user ranking from backend:', error)
            // Log the length of localUserRanking and check its content
            console.log('Local Event ranking data:', localEventRanking)
            console.log('Type of eventRanking:', typeof this.eventRanking)
            console.log('Event Ranking:', this.eventRanking)
            // Fallback to local data
            this.eventRanking = localEventRanking
          })
      },
      fetchRecentDate() {
        // 获取当前时间
        const currentDate = new Date('2024-09-21');
        let startDate;

        // 根据选择的日期范围计算 startDate
        switch (this.recentDate_selected) {
          case '近1天':
            startDate = new Date(currentDate.setDate(currentDate.getDate() - 1));
            break;
          case '近7天':
            startDate = new Date(currentDate.setDate(currentDate.getDate() - 7));
            break;
          case '近30天':
            startDate = new Date(currentDate.setDate(currentDate.getDate() - 30));
            break;
          default:
            startDate = null; // 若无选择，则不进行过滤
        }

        // 格式化 startDate 和 endDate 为 YYYY-MM-DD
        if (startDate) {
          this.startDate = startDate.toISOString().split('T')[0];
        }
        this.endDate = new Date().toISOString().split('T')[0]; // 当前时间为 endDate

        // 调用过滤方法来更新数据
        this.userImgSrc = this.selectRandomImages(this.userRanking, 10);
        this.eventImgSrc = this.selectRandomImages(this.eventRanking, 10);
      },
      resetData() {
        this.recentDate_selected = null;
        this.startDate = null;
        this.endDate = null;

        // 调用过滤方法以重置数据为初始状态
        this.filteredUserRanking();
        this.filteredEventRanking();

        this.fetchUserImgSrc();
        this.fetchEventImgSrc();
      },
      toggleChart() {
        this.$nextTick(() => {
          if (this.showEventInfo) this.initEventChart()
          if (this.showEventRanking) this.initEventChart()
          if (this.showUserInfo) this.initUserChart()
          if (this.showUserRanking) this.initUserChart()
        })
      },

      openGeneralRankingDialog(event) {
        this.$router.push({
          path: '/hiddenPage',
          query: { event: event.event, date: event.date, dialogTitle: '总内容数量榜'}
        });
        this.dialogTitle = '总内容数量榜';
        this.generalSelectedEvent = event.event;
        this.generalSelectedDate = event.date;
        this.generalDialogVisible = true;
      },
      openTodayRankingDialog(event) {
        this.$router.push({
          path: '/hiddenPage',
          query: { event: event.event, date: event.date, dialogTitle: '今日内容数量榜'}
        });
        this.dialogTitle = '今日内容数量榜'; // Set the dialog title for this ranking
        this.todaySelectedEvent = event.event;
        this.todaySelectedDate = event.date;
        this.todayDialogVisible = true;
      },
      openRecentContentDialog(event) {
        this.$router.push({
          path: '/hiddenPage',
          query: { event: event.event, date: event.date, dialogTitle: '近期内容数量飙升榜' }
        });
        this.dialogTitle = '近期内容数量飙升榜'; // Set the dialog title for this ranking
        this.recentContentSelectedEvent = event.event;
        this.recentContentSelectedDate = event.date;
        this.recentContentDialogVisible = true;
      },
      openHostileDialog(event) {
        this.$router.push({
          path: '/hiddenPage',
          query: { event: event.event, date: event.date,dialogTitle: '敌意言论数量榜' }
        });
        this.dialogTitle = '敌意言论数量榜'; // Set the dialog title for this ranking
        this.hostileSelectedEvent = event.event;
        this.hostileSelectedDate = event.date;
        this.hostileDialogVisible = true;
      },

      openTotalParticipantDialog(event) {
        this.$router.push({
          path: '/hiddenPage',
          query: { event: event.event, date: event.date,dialogTitle: '总参与人数榜' }
        });
        this.dialogTitle = '总参与人数榜'; // Set the dialog title for this ranking
        this.totalParticipantsSelectedEvent = event.event;
        this.totalParticipantsSelectedDate = event.date;
        this.totalParticipantsDialogVisible = true;
      },
      openTodayParticipantDialog(event) {
        this.$router.push({
          path: '/hiddenPage',
          query: { event: event.event, date: event.date,dialogTitle: '今日参与人数榜' }
        });
        this.dialogTitle = '今日参与人数榜'; // Set the dialog title for this ranking
        this.todayParticipantsSelectedEvent = event.event;
        this.todayParticipantsSelectedDate = event.date;
        this.todayParticipantsDialogVisible = true;
      },
      openRecentUserDialog(event) {
        this.$router.push({
          path: '/hiddenPage',
          query: { event: event.event, date: event.date,dialogTitle: '近期参与人数飙升榜' }
        });
        this.dialogTitle = '近期参与人数飙升榜'; // Set the dialog title for this ranking
        this.recentUserSelectedEvent = event.event;
        this.recentUserSelectedDate = event.date;
        this.recentUserDialogVisible = true;
      },
      openHostileRatioDialog(event) {
        this.$router.push({
          path: '/hiddenPage',
          query: { event: event.event, date: event.date,dialogTitle: '敌意言论比例榜' }
        });
        this.dialogTitle = '敌意言论比例榜'; // Set the dialog title for this ranking
        this.hostileRatioSelectedEvent = event.event;
        this.hostileRatioSelectedDate = event.date;
        this.hostileRatioDialogVisible = true;
      },
      openUser1Dialog(user) {
        this.$router.push({
          path: '/hiddenPage',
          query: { user: user.user, date: user.date,dialogTitle: '大V用户(出度)榜' }
        });
        this.dialogTitle = '大V用户(出度)榜'
        this.user1DialogVisible = true;
        this.user1Ranking = user.user;
        this.user1RankingDate = user.date;
      },
      openUser2Dialog(user) {
        this.$router.push({
          path: '/hiddenPage',
          query: { user: user.user, date: user.date,dialogTitle: '大V用户(高阶互动)榜' }
        });
        this.dialogTitle = '大V用户(高阶互动)榜'
        this.user2DialogVisible = true;
        this.user2Ranking = user.user;
        this.user2RankingDate = user.date;
      },
      openUser3Dialog(user) {
        this.$router.push({
          path: '/hiddenPage',
          query: { user: user.user, date: user.date,dialogTitle: '大V用户(出度)榜' }
        });
        this.dialogTitle = '大V用户(出度)榜'
        this.user3DialogVisible = true;
        this.user3Ranking = user.user;
        this.user3RankingDate = user.date;
      },
      openUser4Dialog(user) {
        this.$router.push({
          path: '/hiddenPage',
          query: { user: user.user, date: user.date,dialogTitle: '高产用户(原帖)榜' }
        });
        this.dialogTitle = '高产用户(原帖)榜'
        this.user4DialogVisible = true;
        this.user4Ranking = user.user;
        this.user4RankingDate = user.date;
      },
      openUser5Dialog(user) {
        this.$router.push({
          path: '/hiddenPage',
          query: { user: user.user, date: user.date,dialogTitle: '台独/统一·议题活跃榜' }
        });
        this.dialogTitle = '台独/统一·议题活跃榜'
        this.user5DialogVisible = true;
        this.user5Ranking = user.user;
        this.user5RankingDate = user.date;
      },
      openUser6Dialog(user) {
        this.$router.push({
          path: '/hiddenPage',
          query: { user: user.user, date: user.date,dialogTitle: '中国/中共·议题活跃榜' }
        });
        this.dialogTitle = '中国/中共·议题活跃榜'
        this.user6DialogVisible = true;
        this.user6Ranking = user.user;
        this.user6RankingDate = user.date;
      },
      openUser7Dialog(user) {
        this.$router.push({
          path: '/hiddenPage',
          query: { user: user.user, date: user.date,dialogTitle: '民进党·议题活跃榜' }
        });
        this.dialogTitle = '民进党·议题活跃榜'
        this.user7DialogVisible = true;
        this.user7Ranking = user.user;
        this.user7RankingDate = user.date;
      },
      openUser8Dialog(user) {
        this.$router.push({
          path: '/hiddenPage',
          query: { user: user.user, date: user.date,dialogTitle: '赖清德·议题活跃榜' }
        });
        this.dialogTitle = '赖清德·议题活跃榜'
        this.user8DialogVisible = true;
        this.user8Ranking = user.user;
        this.user8RankingDate = user.date;
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
    border: none;
    padding: 0;
  }

  .icon-group {
    width:400px;
    display: flex;
    gap: 50px;
    /* margin-left: 220px;
    margin-right:50px; */
    justify-content: space-between; /* 确保图标均匀分布 */
  }

  .icon-item {
    display: flex;
    flex-direction: column;
    align-items: center;
    cursor: pointer;
    margin-left: 25px;
  }

  .icon-item div {
    margin-top: 5px;
    /* Add some space between icon and text */
    font-size:25px;
  }

  .LesMiserables {
    margin-bottom: 10px;
  }

  .icon-item:active {
    transform: scale(0.95); /* 点击时稍微缩小 */
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

  .darkContainer {
    height: 550px;
    width: 350px;
    /* 容器的宽度，可以根据需要调整 */
    background-color: #57bdf8;
    /* 浅蓝色背景 */
    transform: skew(0, 10deg);
    /* 使容器变成平行四边形 */
    padding: 5px;
    margin: 10px 20px 20px 10px;
    position: relative;
    /* 为容器内的绝对定位元素提供参照 */
  }

  .lightContainer {
    height: 550px;
    width: 350px;
    /* 容器的宽度，可以根据需要调整 */
    background-color: rgb(243, 251, 255);
    /* 浅蓝色背景 */
    transform: skew(0, 10deg);
    /* 使容器变成平行四边形 */
    padding: 20px;
    margin: 10px 20px 20px 10px;
  }

  .container-wrapper {
    display: flex;
    flex-wrap: nowrap;
    justify-content: center;
  }

  .ranking-title {
    font-family: "Microsoft YaHei", serif;
    color: white;
    font-weight: bold;
    font-size: 20px;
    transform: skew(0, -10deg);
    margin-top:15px;
  }

  .ranking-content div {
    margin-top: 22px;
    font-size: 18px;
    color: white;
    /* 标题颜色 */
    transform: skew(0, -10deg);
    justify-content: space-between;
    /* 使文本校正回正 */
    align-items: center;
    display: flex;
    line-height: 25px;
    /* 固定行高，使其与字体大小一致 */
    position: relative;
    /* 相对定位，为子元素的绝对定位提供参照 */
  }

  .user-ranking-content div {
    margin-top: 22px;
    font-size: 20px;
    color: white;
    /* 标题颜色 */
    transform: skew(0, -10deg);
    justify-content: space-between;
    /* 使文本校正回正 */
    align-items: center;
    display: flex;
    margin-left:8px;
    line-height: 25px;
    /* 固定行高，使其与字体大小一致 */
    position: relative;
    /* 相对定位，为子元素的绝对定位提供参照 */
  }

  .ranking-content img {
    height: 32px;
    /* 设置与字体大小相同的高度 */
    width: 32px;
    border-radius:100%;
    /* 根据图片比例自动调整宽度 */
    /* 图片和文字之间的间距 */
    /* 图片的上下间距为 1px */
    margin: 0 -3px 1px 10px;
    /* 图片的上下间距为 1px */
    vertical-align: middle;
    /* 使图片与文字基线对齐 */
    position: absolute;
    /* 绝对定位，固定图片位置 */
    right: 0;
    /* 图片贴着容器右端 */
  }

  .user-ranking-content img {
    height: 40px;
    /* 设置与字体大小相同的高度 */
    width: 40px;
    border-radius: 100%;
    /* 根据图片比例自动调整宽度 */
    /* 图片和文字之间的间距 */
    /* 图片的上下间距为 1px */
    margin: 1px -3px 1px 10px;
    /* 图片的上下间距为 1px */
    vertical-align: middle;
    /* 使图片与文字基线对齐 */
    position: absolute;
    /* 绝对定位，固定图片位置 */
    right: 0;
    /* 图片贴着容器右端 */
  }

  .light-ranking-title {
    font-family: "Microsoft YaHei", serif;
    color: deepskyblue;
    font-weight: bold;
    font-size: 20px;
    transform: skew(0, -10deg);
  }

  .light-ranking-content div {
    margin-top: 22px;
    font-size: 18px;
    color: rgb(0,32,96);
    /* 标题颜色 */
    transform: skew(0, -10deg);
    line-height: 25px;
    /* 使文本校正回正 */
  }

  .el-date-picker /deep/ .el-input__inner {
    height:60px;
    border: 1px solid rgb(121, 121, 121);
    margin-left:0;
    border-radius:0;
    font-size:20px;
  }

  .ranking-item {
    cursor: pointer;
    transition: color 0.2s ease-in-out;
  }

  .ranking-item:hover {
      color: blue;
      text-decoration: underline;
  }

  /deep/ .el-button {
  font-size: 25px;
  transform: scale(1.1);
  padding: 5px 10px;
}
  </style>

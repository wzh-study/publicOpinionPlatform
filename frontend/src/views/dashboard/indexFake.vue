<template>
  <div class="dashboard">
    <div class="light-ranking-content">

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
                      <el-input value="今日内容数量榜"></el-input>
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
    <div class="light-ranking-content">

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
                      <el-input value="总内容数量榜"></el-input>
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
                      <el-input value="近期内容数量飙升榜"></el-input>
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
                      <el-input value="敌意言论数量榜"></el-input>
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
                      <el-input value="总参与人数榜"></el-input>
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
                      <el-input value="今日参与人数榜"></el-input>
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
                      <el-input value="近期参与人数飙升榜"></el-input>
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
                      <el-input value="敌意言论比例榜"></el-input>
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
                    <el-input value="大V用户(出度)榜"></el-input>
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
                    <el-input value="大V用户(高阶互动)榜"></el-input>
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
                    <el-input value="大V用户(出度)榜"></el-input>
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
                    <el-input value="高产用户(原帖)榜"></el-input>
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
                    <el-input value="台独/统一·议题活跃榜"></el-input>
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
                    <el-input value="中国/中共·议题活跃榜"></el-input>
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
                    <el-input value="民进党·议题活跃榜"></el-input>
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
                    <el-input value="赖清德·议题活跃榜"></el-input>
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
</template>

<script>
export default {
  data() {
      return {
        form: {},
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
      }
    },
  mounted() {
    const eventParam = this.$route.query.event;
    const userParam = this.$route.query.user;
    const dateParam = this.$route.query.date;
    const dialogTitle = this.$route.query.dialogTitle;

    console.log("Event Param:", eventParam); // 调试
    console.log("User Param:", userParam);
    console.log("Date Param:", dateParam);   // 调试

    if (dialogTitle === '总内容数量榜') {
        this.generalSelectedEvent = eventParam;
        this.generalSelectedDate = dateParam;
        this.generalDialogVisible = true;
    }

    if (dialogTitle === '今日内容数量榜') {
      // 设置弹窗显示的事件数据
      this.todaySelectedEvent = eventParam;
      this.todaySelectedDate = dateParam;
      // 弹窗显示
      this.todayDialogVisible = true;
    }

    if (dialogTitle === '近期内容数量飙升榜') {
        this.recentContentSelectedEvent = eventParam;
        this.recentContentSelectedDate = dateParam;
        this.recentContentDialogVisible = true;
    }

    if (dialogTitle === '敌意言论数量榜') {
        this.hostileSelectedEvent = eventParam;
        this.hostileSelectedDate = dateParam;
        this.hostileDialogVisible = true;
    }

    if (dialogTitle === '总参与人数榜') {
        this.totalParticipantsSelectedEvent =eventParam;
        this.totalParticipantsSelectedDate = dateParam;
        this.totalParticipantsDialogVisible = true;
    }

    if (dialogTitle === '今日参与人数榜') {
        this.todayParticipantsSelectedEvent = eventParam;
        this.todayParticipantsSelectedDate = dateParam;
        this.todayParticipantsDialogVisible = true;
    }
    if (dialogTitle === '近期参与人数飙升榜') {
        this.recentUserSelectedEvent = eventParam;
        this.recentUserSelectedDate = dateParam;
        this.recentUserDialogVisible = true;
    }
    if (dialogTitle === '敌意言论比例榜') {
        this.hostileRatioSelectedEvent = eventParam;
        this.hostileRatioSelectedDate = dateParam;
        this.hostileRatioDialogVisible = true;
    }
    if (dialogTitle === '大V用户(出度)榜') {
        this.user1Ranking = userParam;
        this.user1RankingDate = dateParam;
        this.user1DialogVisible = true;
    }else if (dialogTitle === '大V用户(高阶互动)榜') {
      this.user2Ranking = userParam;
      this.user2RankingDate = dateParam;
      this.user2DialogVisible = true;
    } else if (dialogTitle === '大V用户(出度)榜') {
      this.user3Ranking = userParam;
      this.user3RankingDate = dateParam;
      this.user3DialogVisible = true;
    } else if (dialogTitle === '高产用户(原帖)榜') {
      this.user4Ranking = userParam;
      this.user4RankingDate = dateParam;
      this.user4DialogVisible = true;
    } else if (dialogTitle === '台独/统一·议题活跃榜') {
      this.user5Ranking = userParam;
      this.user5RankingDate = dateParam;
      this.user5DialogVisible = true;
    } else if (dialogTitle === '中国/中共·议题活跃榜') {
      this.user6Ranking = userParam;
      this.user6RankingDate = dateParam;
      this.user6DialogVisible = true;
    } else if (dialogTitle === '民进党·议题活跃榜') {
      this.user7Ranking = userParam;
      this.user7RankingDate = dateParam;
      this.user7DialogVisible = true;
    } else if (dialogTitle === '赖清德·议题活跃榜') {
      this.user8Ranking = userParam;
      this.user8RankingDate = dateParam;
      this.user8DialogVisible = true;
    }
  },
  methods: {
    handleClose(done) {
      done();
      this.$router.push('/dashboard'); // 关闭弹窗后跳回dashboard页面
    },
  },
};
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

  .icon-item div {
    margin-top: 5px;
    /* Add some space between icon and text */
  }

  .light-ranking-content div {
    margin-top: 30px;
    font-size: 22px;
    color: lightgray;
    /* 标题颜色 */
    transform: skew(0, -10deg);
    /* 使文本校正回正 */
  }

  .content-table td {
    width: 120px; /* Fixed width for each cell */
    height: 60px; /* Fixed height for each cell */
    border: 1px solid lightgray;
    text-align: center; /* Center align the text horizontally */
    vertical-align: middle; /* Center align the text vertically */
    font-size: calc(8px + 0.3vw); /* Adjust font size based on content */
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
  </style>

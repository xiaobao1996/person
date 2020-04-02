<!-- cronTime.vue -->
<template>
  <div class="cronTempExp">
    <div class="tab">
      <el-tabs type="border-card">
          <el-tab-pane label="秒">
            <div class="group group_second">
              <span>执行类型：</span>
              <el-select v-model="secondType" placeholder="请选择" @change="handleChangeSecond">
                <el-option
                  v-for="item in secondTypeArr"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
            </el-select>
            <h4>具体操作及含义：</h4>
            <div class="radio_con">
              <div class="second_every" v-show="secondType === 1">
                <span class="tip">含义：每秒执行一次，允许的通配符[, - * /]</span>
              </div>
              <div class="second_range" v-show="secondType === 2">
                <div class="second_do">
                  <span>操作：</span>
                  <div class="input_group">
                    <span>开始时间（start）:</span><el-input-number v-model="startTime1" :min="0" :max="59" label="描述文字"></el-input-number>
                  </div>
                  <div class="input_group">
                      <span>终止时间（end）:</span>
                      <el-input-number v-model="endTime1" :min="0" :max="59" label="描述文字"></el-input-number>
                    </div>
                </div>
                  <span class="tip">含义：从起始时间（start）到截止时间（end）范围内，每秒执行一次</span>
                </div>
                <div class="second_every" v-show="secondType === 3">
                  <div class="second_do">
                    <span>操作：</span>
                    <div class="input_group">
                      <span>开始时间（start）:</span>
                      <el-input-number v-model="startTime2" :min="0" :max="59" label="描述文字"></el-input-number>
                    </div>
                    <div class="input_group">
                      <span>循环间隔（interval）:</span>
                      <el-input-number v-model="interval" :min="0" :max="59" label="描述文字"></el-input-number>
                    </div>
                  </div>
                <span class="tip">含义：从开始时间（start），每隔（interval）执行一次</span>
              </div>
              <div class="second_every" v-show="secondType === 4">
                <div class="second_do">
                    <span>操作：</span>
                    <div class="input_group">
                      <span>秒：</span>
                      <el-select v-model="secondList" multiple placeholder="请选择" @change="handleChangeSecList">
                        <el-option
                          v-for="item in secondArr"
                          :key="item.value"
                          :value="item.value">
                        </el-option>
                    </el-select>
                    </div>
                  </div>
                <span class="tip">含义：所选时刻都执行</span>
              </div>
            </div>
          </div>
          </el-tab-pane>
          <el-tab-pane label="分">
            <div class="group group_minute">
            <span>执行类型：</span>
              <el-select v-model="minuteType" placeholder="请选择" @change="handleChangeMinute">
                <el-option
                  v-for="item in minuteTypeArr"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
            </el-select>
            <h4>具体操作及含义：</h4>
            <div class="radio_con">
              <div class="second_every" v-show="minuteType === 1">
                <span class="tip">含义：每分执行一次，允许的通配符[, - * /]</span>
              </div>
              <div class="second_range" v-show="minuteType === 2">
                <div class="second_do">
                  <span>操作：</span>
                  <div class="input_group">
                    <span>开始时间（start）:</span><el-input-number v-model="startMinuteT1" :min="0" :max="59" label="描述文字"></el-input-number>
                  </div>
                  <div class="input_group">
                      <span>终止时间（end）:</span>
                      <el-input-number v-model="endMinuteT1" :min="0" :max="59" label="描述文字"></el-input-number>
                    </div>
                </div>
                  <span class="tip">含义：从起始时间（start）到截止时间（end）范围内，每秒执行一次</span>
                </div>
                <div class="second_every" v-show="minuteType === 3">
                  <div class="second_do">
                    <span>操作：</span>
                    <div class="input_group">
                      <span>开始时间（start）:</span>
                      <el-input-number v-model="startMinuteT2" :min="0" :max="59" label="描述文字"></el-input-number>
                    </div>
                    <div class="input_group">
                      <span>循环间隔（interval）:</span>
                      <el-input-number v-model="interMinute" :min="0" :max="59" label="描述文字"></el-input-number>
                    </div>
                  </div>
                <span class="tip">含义：从开始时间（start），每隔（interval）执行一次</span>
              </div>
              <div class="second_every" v-show="minuteType === 4">
                <div class="second_do">
                    <span>操作：</span>
                    <div class="input_group">
                      <span>分：</span>
                      <el-select v-model="minuteList" multiple placeholder="请选择" @change="handleChangeMinuList">
                        <el-option
                          v-for="item in minuteArr"
                          :key="item.value"
                          :value="item.value">
                        </el-option>
                    </el-select>
                    </div>
                  </div>
                <span class="tip">含义：所选时刻都执行</span>
              </div>
            </div>
          </div>
          </el-tab-pane>
          <el-tab-pane label="时">
          <div class="group group_hour">
            <span>执行类型：</span>
              <el-select v-model="hourType" placeholder="请选择" @change="handleChangeHour">
                <el-option
                  v-for="item in hourTypeArr"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
            </el-select>
            <h4>具体操作及含义：</h4>
            <div class="radio_con">
              <div class="second_every" v-show="hourType === 1">
                <span class="tip">含义：每时执行一次，允许的通配符[, - * /]</span>
              </div>
              <div class="second_range" v-show="hourType === 2">
                <div class="second_do">
                  <span>操作：</span>
                  <div class="input_group">
                    <span>开始时间（start）:</span><el-input-number v-model="startHourT1" :min="0" :max="23" label="描述文字"></el-input-number>
                  </div>
                  <div class="input_group">
                      <span>终止时间（end）:</span>
                      <el-input-number v-model="endHourT1" :min="0" :max="23" label="描述文字"></el-input-number>
                    </div>
                </div>
                  <span class="tip">含义：从起始时间（start）到截止时间（end）范围内，每秒执行一次</span>
                </div>
                <div class="second_every" v-show="hourType === 3">
                  <div class="second_do">
                    <span>操作：</span>
                    <div class="input_group">
                      <span>开始时间（start）:</span>
                      <el-input-number v-model="startHourT2" :min="0" :max="23" label="描述文字"></el-input-number>
                    </div>
                    <div class="input_group">
                      <span>循环间隔（interval）:</span>
                      <el-input-number v-model="interHour" :min="0" :max="23" label="描述文字"></el-input-number>
                    </div>
                  </div>
                <span class="tip">含义：从开始时间（start），每隔（interval）执行一次</span>
              </div>
              <div class="second_every" v-show="hourType === 4">
                <div class="second_do">
                    <span>操作：</span>
                    <div class="input_group">
                      <span>时：</span>
                      <el-select v-model="hourList" multiple placeholder="请选择" @change="handleChangeHourList">
                        <el-option
                          v-for="item in hourArr"
                          :key="item.value"
                          :value="item.value">
                        </el-option>
                    </el-select>
                    </div>
                  </div>
                <span class="tip">含义：所选时刻都执行</span>
              </div>
            </div>
          </div>
          </el-tab-pane>
          <el-tab-pane label="日">
            <div class="group group_day">
              <span>执行类型：</span>
              <el-select v-model="dayType" placeholder="请选择" @change="handleChangeDay">
                <el-option
                  v-for="item in dayTypeArr"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
            </el-select>
            <h4>具体操作及含义：</h4>
            <div class="radio_con">
              <div class="second_every" v-show="dayType === 1">
                <span class="tip">含义：每日执行一次，允许的通配符[, - * / L W]</span>
              </div>
              <div class="second_range" v-show="dayType === 2">
                <div class="second_do">
                  <span>操作：</span>
                  <div class="input_group">
                    <span>开始时间（start）:</span><el-input-number v-model="startDayT1" :min="0" :max="days" label="描述文字"></el-input-number>
                  </div>
                  <div class="input_group">
                      <span>终止时间（end）:</span>
                      <el-input-number v-model="endDayT1" :min="0" :max="days" label="描述文字"></el-input-number>
                    </div>
                </div>
                  <span class="tip">含义：从起始时间（start）到截止时间（end）范围内，每秒执行一次</span>
                </div>
                <div class="second_every" v-show="dayType === 3">
                  <div class="second_do">
                    <span>操作：</span>
                    <div class="input_group">
                      <span>开始时间（start）:</span>
                      <el-input-number v-model="startDayT2" :min="0" :max="days" label="描述文字"></el-input-number>
                    </div>
                    <div class="input_group">
                      <span>循环间隔（interval）:</span>
                      <el-input-number v-model="interDay" :min="0" :max="days" label="描述文字"></el-input-number>
                    </div>
                  </div>
                <span class="tip">含义：从开始时间（start），每隔（interval）执行一次</span>
              </div>
              <div class="second_every" v-show="dayType === 5">
                <span class="tip">含义：不指定，缺省</span>
              </div>
              <div class="second_every" v-show="dayType === 7">
                <div class="second_do">
                    <span>操作：</span>
                    <div class="input_group">
                      <span>日期(day):</span>
                      <el-input-number v-model="startDayT3" :min="0" :max="days" label="描述文字"></el-input-number>
                    </div>
                  </div>
                <span class="tip">含义： 每月 (day) 号最近的那个工作日</span>
              </div>
              <div class="second_every" v-show="dayType === 6">
                <span class="tip">含义：本月最后一天</span>
              </div>
              <div class="second_every" v-show="dayType === 4">
                <div class="second_do">
                    <span>操作：</span>
                    <div class="input_group">
                      <span>日：</span>
                      <el-select v-model="dayList" multiple placeholder="请选择" @change="handleChangeDayList">
                        <el-option
                          v-for="item in dayArr"
                          :key="item.value"
                          :value="item.value">
                        </el-option>
                    </el-select>
                    </div>
                  </div>
                <span class="tip">含义：所选时刻都执行</span>
              </div>
            </div>
            </div>
          </el-tab-pane>
          <el-tab-pane label="月">
            <div class="group group_month">
              <span>执行类型：</span>
              <el-select v-model="monthType" placeholder="请选择" @change="handleChangeMonth">
                <el-option
                  v-for="item in monthTypeArr"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
            </el-select>
            <h4>具体操作及含义：</h4>
            <div class="radio_con">
              <div class="second_every" v-show="monthType === 1">
                <span class="tip">含义：每月执行一次，允许的通配符[, - * /]</span>
              </div>
              <div class="second_range" v-show="monthType === 2">
                <div class="second_do">
                  <span>操作：</span>
                  <div class="input_group">
                    <span>开始时间（start）:</span><el-input-number v-model="startMonthT1" :min="1" :max="12" label="描述文字"></el-input-number>
                  </div>
                  <div class="input_group">
                      <span>终止时间（end）:</span>
                      <el-input-number v-model="endMonthT1" :min="1" :max="12" label="描述文字"></el-input-number>
                    </div>
                </div>
                  <span class="tip">含义：从起始时间（start）到截止时间（end）范围内，每秒执行一次</span>
                </div>
                <div class="second_every" v-show="monthType === 3">
                  <div class="second_do">
                    <span>操作：</span>
                    <div class="input_group">
                      <span>开始时间（start）:</span>
                      <el-input-number v-model="startMonthT2" :min="1" :max="12" label="描述文字"></el-input-number>
                    </div>
                    <div class="input_group">
                      <span>循环间隔（interval）:</span>
                      <el-input-number v-model="interMonth" :min="1" :max="12" label="描述文字"></el-input-number>
                    </div>
                  </div>
                <span class="tip">含义：从开始时间（start），每隔（interval）执行一次</span>
              </div>
              <div class="second_every" v-show="monthType === 5">
                <span class="tip">含义：不指定，缺省</span>
              </div>
              <div class="second_every" v-show="monthType === 4">
                <div class="second_do">
                    <span>操作：</span>
                    <div class="input_group">
                      <span>月：</span>
                      <el-select v-model="monthList" multiple placeholder="请选择" @change="handleChangeMonthList">
                        <el-option
                          v-for="item of monthArr"
                          :key="item.value"
                          :value="item.value">
                        </el-option>
                    </el-select>
                    </div>
                  </div>
                <span class="tip">含义：所选时刻都执行</span>
              </div>
            </div>
            </div>
          </el-tab-pane>
          <el-tab-pane label="周">
            <div class="group group_week">
              <span>执行类型：</span>
              <el-select v-model="weekType" placeholder="请选择" @change="handleChangeWeek">
                <el-option
                  v-for="item in weekTypeArr"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
            </el-select>
            <h4>具体操作及含义：</h4>
            <div class="radio_con">
              <div class="second_every" v-show="weekType === 1">
                <span class="tip">含义：每周执行一次，允许的通配符[, - * / #]</span>
              </div>
              <div class="second_range" v-show="weekType === 2">
                <div class="second_do">
                  <span>操作：</span>
                  <div class="input_group">
                    <span>开始时间（start）:</span><el-input-number v-model="startWeekT1" :min="1" :max="7" label="描述文字"></el-input-number>
                  </div>
                  <div class="input_group">
                      <span>终止时间（end）:</span>
                      <el-input-number v-model="endWeekT1" :min="1" :max="7" label="描述文字"></el-input-number>
                    </div>
                </div>
                  <span class="tip">含义：从起始时间（start）到截止时间（end）范围内，每秒执行一次</span>
                </div>
                <div class="second_every" v-show="weekType === 3">
                  <div class="second_do">
                    <span>操作：</span>
                    <div class="input_group">
                      <span>周数（week）:</span>
                      <el-input-number v-model="startWeekT2" :min="1" :max="4" label="描述文字"></el-input-number>
                    </div>
                    <div class="input_group">
                      <span>星期（num）:</span>
                      <el-input-number v-model="interWeek" :min="1" :max="7" label="描述文字"></el-input-number>
                    </div>
                  </div>
                <span class="tip">含义：第（week）周的星期（num)</span>
              </div>
              <div class="second_every" v-show="weekType === 5">
                <span class="tip">含义：不指定，缺省</span>
              </div>
              <div class="second_every" v-show="weekType === 6">
                <div class="second_do">
                    <span>操作：</span>
                    <div class="input_group">
                      <span>星期（num）:</span>
                      <el-input-number v-model="startWeekT3" :min="1" :max="7" label="描述文字"></el-input-number>
                    </div>
                  </div>
                <span class="tip">含义： 本月最后一个星期(num)</span>
              </div>
              <div class="second_every" v-show="weekType === 4">
                <div class="second_do">
                    <span>操作：</span>
                    <div class="input_group">
                      <span>周：</span>
                      <el-select v-model="weekList" multiple placeholder="请选择" @change="handleChangeWeekList">
                        <el-option
                          v-for="item of weekArr"
                          :key="item.value"
                          :value="item.value">
                        </el-option>
                    </el-select>
                    </div>
                  </div>
                <span class="tip">含义：所选时刻都执行</span>
              </div>
            </div>
            </div>
          </el-tab-pane>
          <el-tab-pane label="年">
            <div class="group group_year">
              <span>执行类型：</span>
              <el-select v-model="yearType" placeholder="请选择" @change="handleChangeYear">
                <el-option
                  v-for="item in yearTypeArr"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
            </el-select>
            <h4>具体操作及含义：</h4>
            <div class="radio_con">
              <div class="second_every" v-show="yearType === 1">
                <span class="tip">含义：每年执行一次，允许的通配符[, - * /] 非必填</span>
              </div>
              <div class="second_every" v-show="yearType === 5">
                <span class="tip">含义：不指定，缺省</span>
              </div>
              <div class="second_range" v-show="yearType === 2">
                <div class="second_do">
                  <span>操作：</span>
                  <div class="input_group">
                    <span>开始时间（start）:</span><el-input-number v-model="startYearT1" :min="year" :max="2099" label="描述文字"></el-input-number>
                  </div>
                  <div class="input_group">
                      <span>终止时间（end）:</span>
                      <el-input-number v-model="endYearT1" :min="year" :max="2099" label="描述文字"></el-input-number>
                    </div>
                </div>
                  <span class="tip">含义：从起始时间（start）到截止时间（end）范围内，每秒执行一次</span>
                </div>

            </div>
            </div>
          </el-tab-pane>
      </el-tabs>
    </div>

    <div class="express_box" :class="computed_exp">
      <h3>-- 表达式 --</h3>
      <div class="exp_str">
        <strong>表达式字段：</strong>
        <span>秒：</span><span class="span_input">{{cron_second}}</span>
        <!-- <el-input type="text" v-model="cron_second"></el-input> -->
        <span>分：</span><span class="span_input">{{cron_minute}}</span>
        <!-- <el-input type="text" v-model="cron_minute"></el-input> -->
        <span>时：</span><span class="span_input">{{cron_hour}}</span>
        <!-- <el-input type="text" v-model="cron_hour"></el-input> -->
        <span>日：</span><span class="span_input">{{cron_day}}</span>
        <!-- <el-input type="text" v-model="cron_day"></el-input> -->
        <span>月：</span><span class="span_input">{{cron_month}}</span>
        <!-- <el-input type="text" v-model="cron_month"></el-input> -->
        <span>星期：</span><span class="span_input">{{cron_week}}</span>
        <!-- <el-input type="text" v-model="cron_week"></el-input> -->
        <span>年：</span><span class="span_input">{{cron_year}}</span>
        <!-- <el-input type="text" v-model="cron_year"></el-input> -->
      </div>
      <div class="exp_cron" :class="cron_express">
        <strong :class="operate_type">Cron 表达式：</strong>
        <el-input type="text" v-model="computed_exp"></el-input>
        <el-button class="btn_sub" type="primary" @click="handleSubExp">确定</el-button>
      </div>
    </div>
  </div>
</template>
<script>
  export default {
    data () {
      return {
        second_tag: null,
        minute_tag: null,
        hour_tag: null,
        day_tag: null,
        month_tag: null,
        week_tag: null,
        year_tag: null,
        year: (new Date()).getFullYear(),
        month: (new Date()).getMonth() + 1,
        day: (new Date()).getDate(),
        radio_second: 1,
        startTime1: 1,
        startTime2: 0,
        endTime1: 2,
        interval: 1,
        startMinuteT1: 1,
        endMinuteT1: 2,
        startMinuteT2: 0,
        interMinute: 1,
        startHourT1: 1,
        endHourT1: 2,
        startHourT2: 0,
        interHour: 1,
        startDayT1: 1,
        endDayT1: 2,
        startDayT2: 0,
        interDay: 1,
        startDayT3: 1,
        startMonthT1: 1,
        endMonthT1: 2,
        startMonthT2: 0,
        interMonth: 1,
        startWeekT1: 1,
        endWeekT1: 2,
        startWeekT2: 0,
        interWeek: 1,
        startWeekT3: 1,
        startYearT1: 1,
        endYearT1: 2,
        startYearT2: 0,
        interYear: 1,
        secondType: null,
        secondTypeArr: [
          {
            label: '每秒执行',
            value: 1
          },
          {
            label: '范围内周期循环',
            value: 2
          },
          {
            label: '起始后周期循环',
            value: 3
          },
          {
            label: '指定具体时间',
            value: 4
          }
        ],
        minuteType: null,
        minuteTypeArr: [
          {
            label: '每分钟执行',
            value: 1
          },
          {
            label: '范围内周期循环',
            value: 2
          },
          {
            label: '起始后周期循环',
            value: 3
          },
          {
            label: '指定具体时间',
            value: 4
          }
        ],
        hourType: null,
        hourTypeArr: [
          {
            label: '每小时执行',
            value: 1
          },
          {
            label: '范围内周期循环',
            value: 2
          },
          {
            label: '起始后周期循环',
            value: 3
          },
          {
            label: '指定具体时间',
            value: 4
          }
        ],
        dayType: null,
        dayTypeArr: [
          {
            label: '每日执行',
            value: 1
          },
          {
            label: '范围内周期循环',
            value: 2
          },
          {
            label: '起始后周期循环',
            value: 3
          },
          {
            label: '指定具体时间',
            value: 4
          },
          {
            label: '不指定时间',
            value: 5
          },
          {
            label: '本月最后一天',
            value: 6
          },
          {
            label: '每月某天的最近的工作日',
            value: 7
          }
        ],
        monthType: null,
        monthTypeArr: [
          {
            label: '每月执行',
            value: 1
          },
          {
            label: '范围内周期循环',
            value: 2
          },
          {
            label: '起始后周期循环',
            value: 3
          },
          {
            label: '指定具体时间',
            value: 4
          },
          // {
          //  label: '不指定时间',
          //  value: 5
          // }
        ],
        weekType: null,
        weekTypeArr: [
          {
            label: '每周执行',
            value: 1
          },
          {
            label: '范围内周期循环',
            value: 2
          },
          {
            label: '起始后周期循环',
            value: 3
          },
          {
            label: '指定具体时间',
            value: 4
          },
          {
            label: '不指定时间',
            value: 5
          },
          {
            label: '本月最后一个星期几',
            value: 6
          }
        ],
        yearType: null,
        yearTypeArr: [
          {
            label: '每年执行',
            value: 1
          },
          {
            label: '范围内周期循环',
            value: 2
          },
          // {
          //  label: '不指定',
          //  value: 5
          // }
        ],
        secondArr: [],
        minuteArr: [],
        hourArr: [],
        dayArr: [],
        monthArr: [],
        weekArr: [],
        yearArr: [],
        secondList: [],
        minuteList: [],
        hourList: [],
        dayList: [],
        monthList: [],
        weekList: [],
        yearList: [],
        arrExp: '',
        days: 30,
        edit_second: '',
        edit_second: '',
        edit_minute: '',
        edit_hour: '',
        edit_day: '',
        edit_month: '',
        edit_week: '',
        edit_year: '',
        cronTempExp: this.cron_express,
        comp_exp: '',
        validate: true
      }
    },
    props: {
      cron_express: '',
      operate_type: ''
    },
    created () {
      const SECONDS = 60
      const HOURS = 24
      const MONTHS = 12
      const WEEKS = 7

      let bigMonth = [1, 3, 5, 7, 8, 10, 12].find((value, index, arr) => {
        return value === this.month
      })

      // 月份大的，为31天
      if (bigMonth) {
        this.days = 31
      }
      // 判断是否为闰年
      if (this.month === 2) {
        if ((this.year % 4 === 0 && this.year % 100 !== 0) || this.year % 400 === 0) {
          this.days = 29
        } else {
          this.days = 28
        }
      }

      // 初始化下拉列表（s:m:h d-m-y, w）数据
      for (let i = 0; i < SECONDS; i++) {
        let optObj = {value: i}
        this.secondArr.push(optObj)
        this.minuteArr.push(optObj)
      }
      for (let i = 0; i < HOURS; i++) {
        let optObj = {value: i}
        this.hourArr.push(optObj)
      }
      for (let i = 0; i < this.days; i++) {
        let optObj = {value: i}
        this.dayArr.push(optObj)
      }
      for (let i = 0; i < MONTHS; i++) {
        let optObj = {value: i}
        this.monthArr.push(optObj)
      }
      for (let i = 0; i < WEEKS; i++) {
        let optObj = {value: i}
        this.weekArr.push(optObj)
      }
      for (let i = this.year; i < 2100; i++) {
        let optObj = {value: i}
        this.yearArr.push(optObj)
      }
      // console.log(this.secondArr)
    },
    mounted () {
      if (this.operate_type === 'add') {
        this.second_tag = this.minute_tag = this.hour_tag = this.day_tag = this.month_tag = this.year_tag = '*'
        this.week_tag = '?'
        this.secondType = this.minuteType = this.hourType = this.dayType = this.monthType = this.weekType = this.yearType = 1
      } else {
        let arr = this.cronTempExp.split(' ')
        this.comp_exp = this.cronTempExp
        this.edit_second = arr[0]
        this.edit_minute = arr[1]
        this.edit_hour = arr[2]
        this.edit_day = arr[3]
        this.edit_month = arr[4]
        this.edit_week = arr[5]
        this.edit_year = arr[6]
      }
    },
    computed: {
      computed_exp: {
        get: function () {
          let cron_str = this.cron_second + ' ' + this.cron_minute + ' ' + this.cron_hour + ' ' + this.cron_day + ' ' + this.cron_month + ' ' + this.cron_week + ' ' + this.cron_year
          return cron_str
        },
        set: function (newVal) {
          // console.log(newVal)
          this.comp_exp = newVal
        }
      },
      cron_second: {
          get: function () {
            if (this.second_tag) {
              let obj = this.computedTag(this.second_tag, this.startTime1, this.endTime1, this.startTime2, this.interval, this.secondList)
              return obj.exp_str
            } else {
              return this.edit_second
            }
          },
          set: function (newValue) {
            this.setTag(newValue, this.second_tag, this.startTime1, this.endTime1, this.startTime2, this.interval, this.secondList)
          }
        },
        cron_minute: {
          get: function () {
            if (this.minute_tag) {
              let obj = this.computedTag(this.minute_tag, this.startMinuteT1, this.endMinuteT1, this.startMinuteT2, this.interMinute, this.minuteList)
              return obj.exp_str
            } else {
              return this.edit_minute
            }
          },
          set: function (newValue) {
            this.setTag(newValue, this.minute_tag, this.startMinuteT1, this.endMinuteT1, this.startMinuteT2, this.interMinute, this.minuteList)
          }
      },
      cron_hour: {
          get: function () {
            if (this.hour_tag) {
              let obj = this.computedTag(this.hour_tag, this.startHourT1, this.endHourT1, this.startHourT2, this.interHour, this.hourList)
              return obj.exp_str
            } else {
              return this.edit_hour
            }
          },
          set: function (newValue) {
            this.setTag(newValue, this.hour_tag, this.startHourT1, this.endHourT1, this.startHourT2, this.interHour, this.hourList)
          }
      },
      cron_day: {
          get: function () {
            if (this.day_tag) {
              let exp_str = ''
            switch (this.day_tag) {
              case '*': exp_str = '*';break;
              case '-': exp_str = this.startDayT1 + '-' + this.endDayT1;break;
              case '/': exp_str = this.startDayT2 + '/' + this.interDay; break;
              case ',': exp_str = this.dayList.join(','); break;
              case '?': exp_str = '?'; break;
              case 'L': exp_str = 'L'; break;
              case 'W': exp_str = this.startDayT3 + 'W'; break;
            }
            return exp_str
            } else {
              return this.edit_day
            }
          },
          set: function (newValue) {
            let str = []
            switch (this.day_tag) {
              case '*': break
            case '-': {
              str = newValue.split('-')
              this.startDayT1 = str[0]
              this.endDayT1 = str[1]
              break
            }
            case '/': {
              str = newValue.split('/')
              this.startDayT2 = str[0]
              this.interDay = str[1]
              break
            }
            }
          }
      },
      cron_month: {
          get: function () {
            if (this.month_tag) {
              let obj = this.computedTag(this.month_tag, this.startMonthT1, this.endMonthT1, this.startMonthT2, this.interMonth, this.monthList)
              return obj.exp_str
            } else {
              return this.edit_month
            }
          },
          set: function (newValue) {
            this.setTag(newValue, this.month_tag, this.startMonthT1, this.endMonthT1, this.startMonthT2, this.interMonth, this.monthList)
          }
      },
      cron_week: {
        get: function () {
          if (this.week_tag) {
              let exp_str = ''
            switch (this.week_tag) {
              case '*': exp_str = '*';break;
              case '-': exp_str = this.startWeekT1 + '-' + this.endWeekT1;break;
              case '#': exp_str = this.startWeekT2 + '#' + this.interWeek; break;
              case ',': exp_str = this.weekList.join(','); break;
              case '?': exp_str = '?'; break;
              case 'L': exp_str = this.startWeekT3 + 'L'; break;
            }
            return exp_str
            } else {
              return this.edit_week
            }
        },
          set: function (newValue) {
            let str = []
            switch (this.week_tag) {
              case '*': break
            case '-': {
              str = newValue.split('-')
              this.startWeekT1 = str[0]
              this.endWeekT1 = str[1]
              break
            }
            case '/': {
              str = newValue.split('/')
              this.startWeekT2 = str[0]
              this.interWeek = str[1]
              break
            }
            }
          }
      },
      cron_year: {
        get: function () {
          if (this.year_tag) {
              let obj = this.computedTag(this.year_tag, this.startYearT1, this.endYearT1)
              return obj.exp_str
            } else {
              return this.edit_year
            }
        },
          set: function (newValue) {
            this.setTag(newValue, this.year_tag, this.startYearT1, this.endYearT1)
          }
      }
    },
    watch: {
      comp_exp (val) {
        this.returnExp(val)
      }
    },
    methods: {
      trim(str) {
          return str.replace(/^\s+|\s+$/gm,'');
      },
      returnExp (val) {
        console.log(val)
        let arr = val.split(' ')
        let secObj = this.returnShow(arr[0], 0)
        let minuObj = this.returnShow(arr[1], 1)
        let hourObj = this.returnShow(arr[2], 2)
        let dayObj = this.returnShow(arr[3], 3)
        let monthObj = this.returnShow(arr[4], 4)
        let weekObj = this.returnShow(arr[5], 5)
        let yearObj = this.returnShow(arr[6], 6)

        arr[0] = secObj.arrVal
        this.secondType = secObj.type
        this.second_tag = secObj.tag
        this.startTime1 = secObj.s1
        this.endTime1 = secObj.e1
        this.startTime2 = secObj.s2
        this.interval = secObj.interval
        this.secondList = secObj.list

        arr[1] = minuObj.arrVal
        this.minuteType = minuObj.type
        this.minute_tag = minuObj.tag
        this.startMinuteT1 = minuObj.s1
        this.endMinuteT1 = minuObj.e1
        this.startMinuteT2 = minuObj.s2
        this.interMinute = minuObj.interval
        this.minuteList = minuObj.list

        arr[2] = hourObj.arrVal
        this.hourType = hourObj.type
        this.hour_tag = hourObj.tag
        this.startHourT1 = hourObj.s1
        this.endHourT1 = hourObj.e1
        this.startHourT2 = hourObj.s2
        this.interHour = hourObj.interval
        this.hourList = hourObj.list

        arr[3] = dayObj.arrVal
        this.dayType = dayObj.type
        this.day_tag = dayObj.tag
        this.startDayT1 = dayObj.s1
        this.endDayT1 = dayObj.e1
        this.startDayT2 = dayObj.s2
        this.startDayT3 = dayObj.s3
        this.interDay = dayObj.interval
        this.dayList = dayObj.list

        arr[4] = monthObj.arrVal
        this.monthType = monthObj.type
        this.month_tag = monthObj.tag
        this.startMonthT1 = monthObj.s1
        this.endMonthT1 = monthObj.e1
        this.startMonthT2 = monthObj.s2
        this.interMonth = monthObj.interval
        this.monthList = monthObj.list

        arr[5] = weekObj.arrVal
        this.weekType = weekObj.type
        this.week_tag = weekObj.tag
        this.startWeekT1 = weekObj.s1
        this.endWeekT1 = weekObj.e1
        this.startWeekT2 = weekObj.s2
        this.startWeekT3 = weekObj.s3
        this.interWeek = weekObj.interval
        this.weekList = weekObj.list

        arr[6] = yearObj.arrVal
        this.yearType = yearObj.type
        this.year_tag = yearObj.tag
        this.startYearT1 = yearObj.s1
        this.endYearT1 = yearObj.e1
        // this.yearList = yearObj.list
      },
      returnShow (arrVal, num) {
        // console.log(num)
        let obj = {
          arrVal: arrVal,
          type: null,
          tag: '',
          s1: null,
          e1: null,
          s2: null,
          interval: null,
          list: [],
          s3: null
        }


        if (obj.arrVal.split('-').length > 1 && obj.arrVal.split('-')[1] && obj.arrVal.split('-')[1] !== '') {
          console.log('11111>>>>>>>>>>>>>>>')
          obj.tag = '-'
          obj.type = 2
          obj.s1 = obj.arrVal.split('-')[0]
          obj.e1 = obj.arrVal.split('-')[1]
        } else if (obj.arrVal.split('/').length > 1 && obj.arrVal.split('/')[1] && obj.arrVal.split('/')[1] !== '') {
          console.log('222>>>>>>>>>>>>>>>')
          obj.tag = '/'
          obj.type = 3
          obj.s2 = obj.arrVal.split('/')[0]
          obj.interval = obj.arrVal.split('/')[1]
        } else if (obj.arrVal.split(',').length > 0 && Number(obj.arrVal.split(',')[0])) {
          console.log('333>>>>>>>>>>>>>>>')
          obj.tag = ','
          obj.type = 4
          obj.list = obj.arrVal.split(',')
        } else if (this.trim(obj.arrVal) === '*') {
          console.log('444>>>>>>>>>>>>>>>')
          obj.tag = '*'
          obj.type = 1
        } else if ((num === 3 || num === 5) && this.trim(obj.arrVal) === '?') {
          console.log('555>>>>>>>>>>>>>>>')
          obj.tag = '?'
          obj.type = 5
        } else if ((num === 3 || num === 5) && obj.arrVal.indexOf('L') !== -1) {
          console.log('obj.tag=>>>>>>>>>>>>>>>')
          obj.tag = 'L'
          console.log('obj.tag=>>>>>>>>>>>>>>>', obj.tag)
          obj.type = 6
          if (num === 3) {
            obj.type = 6
            console.log('obj.type=>>>>>>>>>>>>>>>',obj.type)
          } else if (num === 5) {
            obj.s3 = obj.arrVal.split('L')[0]
          }
        } else if (num === 5 && obj.arrVal.split('#').length > 1 && obj.arrVal.split('#')[1] && obj.arrVal.split('#')[1] !== '') {
          console.log('777>>>>>>>>>>>>>>>')
          obj.tag = '#'
          obj.type = 3
          obj.s2 = obj.arrVal.split('#')[0]
          obj.interval = obj.arrVal.split('#')[1]
        } else if (num === 3 && obj.arrVal.split('W').length > 1 && obj.arrVal.split('W')[0]) {
          console.log('888>>>>>>>>>>>>>>>')
          obj.tag = 'W'
          obj.type = 7
          obj.s3 = obj.arrVal.split('W')[0]
        } else {
          this.validate = false
        }
        // console.log(obj)
        return obj
      },
      // returnShow (arrVal, type, tag, s1, e1, s2, interval, list) {
      //  // console.log(arrVal, type, tag, s1, e1, s2, interval, list)
      //  if (arrVal.split('-').length > 1 && arrVal.split('-')[1] && arrVal.split('-')[1] !== '') {
      //    tag = '-'
      //    type = 2
      //    s1 = arrVal.split('-')[0]
      //    e1 = arrVal.split('-')[1]
      //  } else if (arrVal.split('/').length > 1 && arrVal.split('/')[1] && arrVal.split('/')[1] !== '') {
      //    tag = '/'
      //    type = 3
      //    s2 = arr[0].split('/')[0]
      //    interval = arr[0].split('/')[1]
      //  } else if (arrVal.split(',').length > 0 && this.trim(arrVal) !== '*') {
      //    tag = ','
      //    type = 4
      //    list = arrVal.split(',')
      //  } else if (this.trim(arrVal) === '*') {
      //    tag = '*'
      //    type = 1
      //  }
      //  console.log(arrVal, type, tag, s1, e1, s2, interval, list)
      // },
      setTag (val, tag, s1, e1, s2, interval, list) {
        let str = []
          switch (tag) {
            case '*':
            case '?': break
          case '-': {
            str = val.split('-')
            s1 = str[0]
            e1 = str[1]
            break
          }
          case '/': {
            str = val.split('/')
            s2 = str[0]
            interval = str[1]
            break
          }
          case ',': {
            list = val.split(',')
            break
          }
          }
      },
      changeTag (type) {
        let tag = ''
        switch (type) {
          case 1: tag = '*'; break; // 任意
          case 2: tag = '-'; break; // 范围
          case 3: tag = '/'; break; // 起始选择
          case 4: tag = ','; break; // 指定
          case 5: tag = '?'; break; // 不指定
          case 6: tag = 'L'; break; // last最后
          case 7: tag = 'W'; break; // lastest最近
        }
        return tag
      },
      computedTag (tag, s1, e1, s2, interval, list) {
        let exp_str = ''
        switch (tag) {
          case '*': exp_str = '*';break;
          case '-': exp_str = s1 + '-' + e1;break;
          case '/': exp_str = s2 + '/' + interval; break;
          case ',': exp_str = list.join(','); break;
          case '?': exp_str = '?'; break;
        }
        return {exp_str, s1, e1, s2, interval, list}
      },
      handleChangeSecList (arr) {
        this.secondList = arr
      },
      handleChangeSecond (second) {
        this.second_tag = this.changeTag(second)
      },
      handleChangeMinuList (arr) {
        this.minuteList = arr
      },
      handleChangeMinute (minute) {
        this.minute_tag = this.changeTag(minute)
      },
      handleChangeHourList (arr) {
        this.hourList = arr
      },
      handleChangeHour (hour) {
        this.hour_tag = this.changeTag(hour)
      },
      handleChangeDayList (arr) {
        this.dayList = arr
      },
      handleChangeDay (day) {
        this.day_tag = this.changeTag(day)
      },
      handleChangeMonthList (arr) {
        this.monthList = arr
      },
      handleChangeMonth (month) {
        this.month_tag = this.changeTag(month)
      },
      handleChangeWeekList (arr) {
        this.weekList = arr
      },
      handleChangeWeek (week) {
        this.week_tag = this.changeTag(week)
      },
      handleChangeYear (year) {
        this.year_tag = this.changeTag(year)
      },
      handleSubExp () {
        if (!this.validate) {
          this.$message({message: 'cron 格式错误', duration: 600, type: 'error'})
        } else {
          this.$emit('getCronExp', this.computed_exp)
        }
      }

    }
  }
</script>
<style scoped>
  .group {
    text-align: left;
  }
  .tip {
    width: 100%;
    display: block;
    padding: 10px 0px;
  }
  .second_do span {
    margin: 0px 8px;
  }
  .input_group {
    display: inline-block;
    margin: 0px 8px;
  }

  .express_box {
    width: 100%;
    margin-top: 50px;
    box-sizing: border-box;
    padding: 0px 10px;
    text-align: left;
  }
  .express_box .exp_str, .express_box .exp_cron{
    width: 100%;
    margin: 10px 0px;
    /*text-align: left;*/
    /*height: 200px;*/
  }
  .express_box .exp_str span {
    display: inline-block;
    width: 50px;
    text-align: right;
    /*float: left;*/
  }
  .express_box .exp_str .el-input {
    width: 120px;
  }
  .express_box .exp_cron .el-input {
    width: 76%;
  }
  .express_box .exp_str .span_input {
    width: 120px;
      font-size: 14px;
      display: inline-block;
    -webkit-appearance: none;
      background-color: #fff;
      border-radius: 4px;
      border: 1px solid #dcdfe6;
      -webkit-box-sizing: border-box;
      box-sizing: border-box;
      color: #606266;
      height: 40px;
      line-height: 40px;
      text-align: left;
      outline: 0;
      padding: 0 15px;
  }
  .btn_sub {
    margin-left: 30px;
  }
</style>

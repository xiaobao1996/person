<template>
  <div class="app-container">
    <div class="filter-container">
      <div class="filter-block">
        <span>填报日期：</span>
        <!--<el-input size="small"  @keyup.enter.native="fetchData(true)"/>-->
        <el-date-picker
          :picker-options="pickerOptionsBeginTime"
          @change="changeCreateBeginTime"
          style="margin-right: 10px"
          size="small"

          v-model="listQuery.beginTime"
          type="date"
          value-format="yyyy-MM-dd 00:00:01"
          placeholder="请选择">
        </el-date-picker>
        至
        <el-date-picker
          :picker-options="pickerOptionsEndTime"
          @change="changeCreateEndTime"
          style="margin-left: 10px"
          v-model="listQuery.endTime"
          type="date"
          size="small"

          value-format="yyyy-MM-dd 23:59:59"
          placeholder="请选择">
        </el-date-picker>
      </div>
      <div class="filter-block">
        <span>姓名：</span>
        <el-input size="small" v-model="listQuery.realName" @keyup.enter.native="fetchData(true)"/>
      </div>
      <div class="filter-block">
        <span>手机号：</span>
        <el-input size="small"  v-model="listQuery.phone" @keyup.enter.native="fetchData(true)"/>
      </div>


      <div class="filter-block">
        <span>体温大于：</span>
        <el-input size="small"  v-model="listQuery.temperature" type=number min=0 @keyup.enter.native="fetchData(true)"/>
      </div>

      <div class="filter-block">
        <span>有无其他症状：</span>
        <el-select clearable style=": 220px" size="small"  v-model="listQuery.otherSymptom" clearable  @change="fetchData(true)">
          <el-option v-for="item in otherSymptomList"
                     :key="item.code"
                     :label="item.label"
                     :value="item.code"/>
        </el-select>
      </div>
    <template v-if="inHoliday === true">
      <div class="filter-block">
        <span>当前所在地：</span>
        <el-cascader

          size="small"
          :options="options"
          v-model="listQuery.currentLocation"
          @change="handleCurrentLocationChange">
        </el-cascader>
      </div>

      <div class="filter-block">
        <span>15天内有无去过其他区域：</span>
        <el-select clearable  size="small"  v-model="listQuery.leaveSchool" clearable  @change="fetchData(true)" >
          <el-option v-for="item in leaveSchoolList"
                     :key="item.code"
                     :label="item.label"
                     :value="item.code"/>
        </el-select>
      </div>

      <div class="filter-block">
        <span>15天内曾到过的区域：</span>
        <el-cascader

          size="small"
          :options="options"
          v-model="listQuery.arriveArea"
          @change="handle15AreaChange">
        </el-cascader>
      </div>


      <div class="filter-block">
        <span>有无和疫情人员接触：</span>
        <el-select clearable size="small"  v-model="listQuery.contact" clearable   @change="fetchData(true)" >
          <el-option v-for="item in contactList"
                     :key="item.code"
                     :label="item.label"
                     :value="item.code"/>
        </el-select>
      </div>
    </template>
      <div class="filter-btn-area">
        <el-button size="small" type="primary" @click="fetchData(true)">查询</el-button>
        <el-button size="small" type="none" @click="initListQuery">清除</el-button>
      </div>
      <div class="ope-btn-area">
        <!--<template v-if="permissions.indexOf('busi:student:import') !== -1">-->
        <el-button class="filter-item"  type="primary" icon="el-icon-upload2" @click="exportReport">数据导出</el-button>
        <!--</template>-->
      </div>
    </div>

    <div class="calc-area" style="font-size: 14px;margin-bottom: 5px;color: #909399">
      共计 <span style="color: #ff0000">{{unusualNumber}} </span>条记录反映教职工状态异常
    </div>
    <el-table ref="form" :data="tableData" v-loading="tableLoading"
              border show-overflow-tooltip style="width: 100%"
              :row-class-name="checkUnusual"
    >



      <el-table-column label="填报时间"  align="center">
        <template slot-scope="scope">
          <span>{{scope.row.createAt | pointTimeFormat}}</span>
        </template>
      </el-table-column>


      <el-table-column label="姓名" prop="userName" align="center"/>
      <el-table-column label="手机号" prop="mobile" align="center"/>
      <el-table-column label="体温是否异常" align="center">
        <template slot-scope="scope">
          <span>{{scope.row.temperatureUnusual === false ? "否" : scope.row.temperatureUnusual === true ? "是" : null}}</span>
        </template>
      </el-table-column>
      <el-table-column label="体温"  align="center">
        <template slot-scope="scope">
          <span>{{scope.row.temperature}}℃</span>
        </template>
      </el-table-column>


      <el-table-column label="其他症状" align="center">
        <template slot-scope="scope">
          <span>{{scope.row.otherSymptom === 0 ? "无" : scope.row.otherSymptom === 1 ? "有咳嗽、呼吸不顺畅等现象" : null}}</span>
        </template>
      </el-table-column>
      <template v-if="inHoliday === true">
      <el-table-column label="当前所在地" prop="currentLocation" align="center"/>
      <el-table-column label="有无和疫情人员接触" align="center">
        <template slot-scope="scope">
          <span>{{scope.row.contact === 0 ? "无" : scope.row.contact === 1 ? "有" : null}}</span>
        </template>
      </el-table-column>
      <el-table-column label="15天内有无去过其他区域" align="center">
        <template slot-scope="scope">
          <span>{{scope.row.arriveOtherArea === 0 ? "无" : scope.row.arriveOtherArea === 1 ? "有" : null}}</span>
        </template>
      </el-table-column>


      <el-table-column label="15天内曾到过的区域"  width="220" align="center">
        <template slot-scope="scope">
          <div v-for="item in scope.row.userArriveAreaList">{{item.province}} {{item.city}} {{item.area}} </div>
        </template>
      </el-table-column>

    </template>


      <el-table-column label="其他信息" prop="otherInfo" align="center"/>

      <!--<el-table-column label="学号">-->
      <!--<template slot-scope="scope">-->
      <!--<span>{{scope.row.student.studentNo}}</span>-->
      <!--</template>-->
      <!--</el-table-column>-->
    </el-table>

    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pageInfo.pageNum"
      :page-sizes="[10, 20, 50]"
      :page-size="10"
      layout="total, sizes, prev, pager, next, jumper"
      :total="pageInfo.total">
    </el-pagination>
  </div>
</template>

<script>
  import waves from '@/directive/waves'
  import elDragDialog from '@/directive/el-dragDialog'
  import {getStudentInfoReport, getConfigDetail} from '@/api/api'
  import {mapGetters} from 'vuex'
  import {download} from "@/lib/download"
  import { regionData , CodeToText } from 'element-china-area-data'
  export default {
    computed: {
      ...mapGetters([
        'permissions'
      ])
    },
    directives: {
      waves, elDragDialog
    },
    name: "teacherReportList",
    data () {
      return {
        exportUrl : process.env.VUE_APP_BASE_API + "/busi/irTemperatureReport/export",
        options: regionData,
        selectedOptions: [],
        classList: [],
        inHoliday: true, // 假期中
        // 有无其他症状
        otherSymptomList: [
          {label: "全部", code: null},
          {label: "有", code: 1},
          {label: "无", code: 0},
        ],
        // 有无其他症状
        leaveSchoolList: [
          {label: "全部", code: null},
          {label: "有", code: 1},
          {label: "无", code: 0},
        ],
        // 有无和疫情人员接触
        contactList: [
          {label: "全部", code: null},
          {label: "有", code: 1},
          {label: "无", code: 0},
        ],
        pickerOptionsBeginTime: {
        },
        pickerOptionsEndTime: {
        },
        tableLoading : false,
        listQuery :{
          realName : '',
          phone: '',
          beginTime: '',
          endTime: '',
          temperature : '',
          otherSymptom: '',
          leaveSchool: '',
          contact: '',
          currentLocation: [],
          currentLocationString: '',
          arriveArea : [],
          arriveAreaString: '',
          province : '', //省
          city: '',//市
          area  : '',// 区
        },
        unusualNumber: 0,
        tableData: [],
        pageInfo: {
          pageNum: 1,
          pageSize: 10,
          total: 0
        },
      }
    },
    methods : {
      initListQuery() {
        this.listQuery = {
          realName : '',
          phone: '',
          beginTime: '',
          endTime: '',
          temperature : '',
          otherSymptom: '',
          leaveSchool: '',
          contact: '',
          currentLocation: [],
          currentLocationString: '',
          arriveArea : [],
          arriveAreaString: '',
          province : '', //省
          city: '',//市
          area  : '',// 区
        }
        this.initPickOption()
        this.fetchData(true)
      },
      initPickOption() {
        this.pickerOptionsBeginTime = {
          disabledDate(time) {
            return null ;
          }
        }
        this.pickerOptionsEndTime = {
          disabledDate(time) {
            return null ;
          }
        }
      },
      initPageInfo() {
        this.pageInfo.pageNum = 1;
      },
      initConfig() {
        getConfigDetail().then()
      },
      fetchData(initPageInfo) {
        if (initPageInfo) {
          this.initPageInfo()
        }
        let params = {
          pageNum : this.pageInfo.pageNum,
          pageSize : this.pageInfo.pageSize,
          startTime :this.listQuery.beginTime,
          endTime : this.listQuery.endTime,
          userName : this.listQuery.realName,
          mobile : this.listQuery.phone,
          temperature : this.listQuery.temperature,
          otherSymptom : this.listQuery.otherSymptom,
          currentLocation : this.listQuery.currentLocationString,
          arriveOtherArea : this.listQuery.leaveSchool,
          contact : this.listQuery.contact,
          identity : 1, // 0:学生 1: 老师
          province :  this.listQuery.province,
          city : this.listQuery.city,
          area : this.listQuery.area,
        }
        this.tableLoading = true;
        getConfigDetail().then(res => {
          if (res.code === 200) {
            this.inHoliday = res.result.mode === 0 ? true : false
            getStudentInfoReport(params).then(res => {
              if (res.code == 200) {
                if (res.result) {
                  this.tableData = res.result && res.result.pageInfo &&  res.result.pageInfo.list ? res.result.pageInfo.list : [];
                  this.pageInfo.total = res.result.pageInfo.total;
                  this.unusualNumber = res.result.unusualNumber || 0
                }
              } else {
                this.$message.error("获取学生记录失败！" + res.message);
              }
            }).finally(() => {
              this.tableLoading = false;
            })
          } else {
            this.$message.error("获取系统配置失败！" + res.message)
          }
        })

      },
      handleSizeChange(val) {
        this.pageInfo.pageSize = val
        this.fetchData()
      },
      handleCurrentChange(val) {
        this.pageInfo.pageNum = val
        this.fetchData()
      },
      changeCreateBeginTime() {
        let createBeginTime = this.listQuery.beginTime
        if (createBeginTime) {
          let beginTime =  new Date(createBeginTime.replace('-', '/'))
          this.pickerOptionsEndTime = {
            disabledDate(time) {
              return time.getTime() < beginTime - 8.64e7 ;
            }
          }
        } else {
          this.pickerOptionsEndTime = {
            disabledDate(time) {
              return null ;
            }
          }
        }
      },
      changeCreateEndTime() {
        let createEndTime  = this.listQuery.endTime;
        if (createEndTime) {
          let endTime =   new Date(createEndTime.replace('-', '/'))
          this.pickerOptionsBeginTime = {
            disabledDate(time) {
              return time.getTime() > endTime ;
            }
          }
        } else {
          this.pickerOptionsBeginTime = {
            disabledDate(time) {
              return null ;
            }
          }
        }

      },
      checkUnusual(row, rowIndex) {
        if (row.row.abnormal === 1) {
          return  'unusual-warning'
        } else {
          return ''
        }
      },
      handleAriveAreaChange (value) {
        console.log(value)
        this.areaChangeCodeToText()
        this.fetchData(true)
      },
      handle15AreaChange() {
       if ( this.listQuery.arriveArea.length > 0) {
         this.listQuery.province = CodeToText[this.listQuery.arriveArea[0]] || ''
         this.listQuery.city = CodeToText[this.listQuery.arriveArea[1]] || ''
         this.listQuery.area = CodeToText[this.listQuery.arriveArea[2]] || ''
         this.fetchData(true)
       }
      },
      handleCurrentLocationChange (value) {
        this.locationChangeCodeToText()
        this.fetchData(true)
        // console.log(this.listQuery)
      },
      locationChangeCodeToText() {
        if (this.listQuery.currentLocation && this.listQuery.currentLocation.length > 0 ) {
          this.listQuery.currentLocationString = ''
          this.listQuery.currentLocation.forEach(item => {
            this.listQuery.currentLocationString =   this.listQuery.currentLocationString + CodeToText[item]
          })
        }

        // console.log(this.listQuery.currentLocationString)
      },
      areaChangeCodeToText() {
        if (this.listQuery.arriveArea && this.listQuery.arriveArea.length > 0 ) {
          this.listQuery.arriveAreaString = ''
          this.listQuery.arriveArea.forEach(item => {
            this.listQuery.arriveAreaString =   this.listQuery.arriveAreaString + CodeToText[item]
          })
        }

        // console.log(this.listQuery.arriveAreaString)
      },
      exportReport () {
        if (this.tableData == [] ||  this.tableData.length < 0 ||  this.tableData == null) {
          return
        }
        let url = this.exportUrl + "?schoolToken=" + sessionStorage.getItem("schoolId") + "&startTime="+ this.listQuery.beginTime
        + "&endTime="+ this.listQuery.endTime + "&userName=" + this.listQuery.realName + "&mobile=" + this.listQuery.phone
        + "&otherSymptom=" +  this.listQuery.otherSymptom + "&currentLocation=" + this.listQuery.currentLocationString
        + "&arriveOtherArea=" + this.listQuery.leaveSchool + "&contact=" + this.listQuery.contact + "&temperature=" + this.listQuery.temperature
        + "&identity=1" + "&province=" + this.listQuery.province + "&city=" + this.listQuery.city + "&area=" +  this.listQuery.area;;
        window.open(url)
      }
    },
    created () {
      this.fetchData(true);
      this.initConfig()
    },
    mounted() {

    }
  }
</script>

<style >
  .unusual-warning {
    color: #f00 !important;
  }
</style>

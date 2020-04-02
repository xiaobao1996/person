<template>
  <div class="app-container">
    <div class="edit-nav">
      <el-radio-group v-model="roleType" @change="roleTypeChange">
        <el-radio-button :label="0"><span style="padding: 0 40px">学生</span></el-radio-button>
        <el-radio-button :label="1"><span style="padding: 0 33px">教职工</span></el-radio-button>
      </el-radio-group>
    </div>

    <!--条件筛选-->
    <div class="filter-container" style="margin-top:20px">
      <div class="filter-block">
        <span>填报日期：</span>
        <!--<el-input size="small"  @keyup.enter.native="fetchData(true)"/>-->
        <el-date-picker
          :picker-options="pickerOptionsBeginTime"
          @change="changeCreateBeginTime"
          style="margin-right: 10px"
          v-model="listQuery.beginTime"
          type="date"
          size="small"
          :clearable="false"
          value-format="yyyy-MM-dd 00:00:01"
          placeholder="请选择">
        </el-date-picker>
      </div>
      <div class="filter-block">
        <span>姓名：</span>
        <el-input size="small" v-model="listQuery.userName" @keyup.enter.native="fetchData(true)"/>
      </div>
      <template v-if="roleType === 1">
        <div class="filter-block">
          <span>手机号：</span>
          <el-input size="small" v-model="listQuery.mobile" @keyup.enter.native="fetchData(true)"/>
        </div>
      </template>
      <template v-else>
        <div class="filter-block">
          <span>所在班级：</span>
          <el-select clearable  size="small"  v-model="listQuery.officeId" clearable @change="fetchData(true)"  >
            <el-option v-for="item in classList"
                       :key="item.officeId"
                       :label="item.className"
                       :value="item.officeId"/>
          </el-select>
        </div>
      </template>
      <div class="filter-block">
        <span>体温状态：</span>
        <el-select clearable  size="small"  v-model="listQuery.temperature" clearable  @change="fetchData(true)"  >
          <el-option v-for="item in temperatureTypeList"
                     :key="item.code"
                     :label="item.label"
                     :value="item.code"/>
        </el-select>
      </div>
      <div class="filter-block">
        <span>测温进展：</span>
        <el-select clearable  size="small"  v-model="listQuery.progress" clearable  @change="fetchData(true)"  >
          <el-option v-for="item in temperatureMeasurement"
                     :key="item.code"
                     :label="item.label"
                     :value="item.code"/>
        </el-select>
      </div>



      <div class="filter-btn-area">
        <el-button size="small" type="primary" @click="fetchData(true)">查询</el-button>
        <el-button size="small" type="none" @click="initListQuery">清除</el-button>
      </div>

      <div class="ope-btn-area">
        <!--<template v-if="permissions.indexOf('busi:student:import') !== -1">-->
        <el-button class="filter-item"  type="primary" icon="el-icon-upload2" @click="hanleClickExport" >数据导出</el-button>
        <el-button class="filter-item"  type="primary" icon="el-icon-edit"  @click="handleClickAddReport">添加记录</el-button>
        <!--</template>-->
      </div>
    </div>

    <!--列表数据-->
    <div class="calc-area" style="font-size: 14px;margin-bottom: 5px;color: #909399">
      今日共有 <span style="color: #ff0000"> {{calc.abnormalCount}} </span>位<span v-if="roleType === 0">学生</span><span v-else>教职工</span>体温异常，
      上午有<span style="color: #ff0000"> {{calc.amNotMonitorCount}} </span>位体温未测,
      下午有<span style="color: #ff0000"> {{calc.pmNotMonitorCount}} </span>位体温未测,
      <span style="color: #ff0000"> {{calc.needRecheckCount}} </span>位体温需要核查
    </div>
    <el-table ref="form" :data="tableData" v-loading="tableLoading"
              border show-overflow-tooltip style="width: 100%"
              :row-class-name="formatRowClassName"

    >
      <el-table-column label="填报日期"  align="center">
        <template slot-scope="scope">
          <span>{{scope.row.monitorDate | pointTimeFormat}}</span>
        </template>
      </el-table-column>
      <el-table-column label="姓名" prop="name" align="center"/>

      <el-table-column label="班级" prop="className" align="center" v-if="roleType === 0"/>
      <el-table-column label="手机" prop="mobile" align="center" v-else/>

      <el-table-column label="上午体温" align="center">
        <template slot-scope="scope">
          <span v-if="scope.row.amTemperature !== null && scope.row.amTemperature !== ''">{{scope.row.amTemperature}}℃</span>
        </template>
      </el-table-column>


      <el-table-column label="人工复测（上午）" align="center">
        <template slot-scope="scope">
          <span>{{scope.row.amRecheck === 0 ? "否" : scope.row.amRecheck === 1 ? "是" : "否"}}</span>
        </template>
      </el-table-column>

      <!--<el-table-column label="下午体温" prop="pmTemperature" align="center"/>-->

      <el-table-column label="下午体温" align="center">
        <template slot-scope="scope">
          <span v-if="scope.row.pmTemperature !== null && scope.row.pmTemperature !== ''">{{scope.row.pmTemperature}}℃</span>
        </template>
      </el-table-column>

      <el-table-column label="人工复测（下午）" align="center">
        <template slot-scope="scope">
          <span>{{scope.row.pmRecheck === 0 ? "否" : scope.row.pmRecheck === 1 ? "是" : "否"}}</span>
        </template>
      </el-table-column>


      <el-table-column label="操作"  width="440" align="center">
        <template slot-scope="scope">
          <template v-if="scope.row.monitorDate !== null">
            <div class="table-ope-btn-around">
              <!--<template v-if="scope.row.healthStatus === 2">-->
              <!--<el-link type="primary" :underline="false" @click="handleClickContactPerson(scope.row)">联系监护人</el-link>-->
              <!--</template>-->

              <!--<template v-if="scope.row.healthStatus === 1 || scope.row.healthStatus === 2">-->
              <!--<el-link type="primary" :underline="false" @click="handleClickContact(scope.row)">通知相关人员</el-link>-->
              <!--</template>-->

              <!--<template v-if="scope.row.healthStatus === 2">-->
              <!--<el-link type="primary" :underline="false" @click="viewContact(scope.row)">查看密切接触者</el-link>-->
              <!--</template>-->

              <!--<el-link type="primary" :underline="false" @click="handleClickRecord(scope.row)">查看测温记录</el-link>-->

              <!--体温异常-->
              <template v-if="scope.row.healthStatus === 2" >
                <template v-if="roleType === 0">
                  <el-link type="primary" :underline="false" @click="handleClickContactPerson(scope.row)">联系监护人</el-link>
                </template>
                <el-link type="primary" :underline="false" @click="handleClickContact(scope.row)">通知相关人员</el-link>
                <el-link type="primary" :underline="false" @click="viewContact(scope.row)">查看密切接触者</el-link>
                <el-link type="primary" :underline="false" @click="handleClickRecord(scope.row)">查看测温记录</el-link>
              </template>

              <!--体温需确认-->
              <template v-if="scope.row.healthStatus === 1" >
                <el-link type="primary" :underline="false" @click="handleClickRecord(scope.row)">查看测温记录</el-link>
                <el-link type="primary" :underline="false" @click="handleClickContact(scope.row)">通知相关人员</el-link>
              </template>

              <!--体温异常-->
              <template v-if="scope.row.healthStatus === 0" >
                <el-link type="primary" :underline="false" @click="handleClickRecord(scope.row)">查看测温记录</el-link>
              </template>

            </div>
          </template>
        </template>
      </el-table-column>

    </el-table>


    <!--导出列表弹框-->
    <el-dialog
      title="请选择导出日期"
      :visible.sync="exportQuery.dialog"
      width="600px">
      <div >
        <div class="filter-block">
          <span> <span style="color: #f00;">*</span> 日期：</span>
          <!--<el-input size="small"  @keyup.enter.native="fetchData(true)"/>-->
          <el-date-picker
            :picker-options="pickerExportQueryBeginTime"
            @change="changeExportQueryBeginTime"
            style="margin-right: 10px"

            v-model="exportQuery.beginTime"
            type="date"
            value-format="yyyy-MM-dd 00:00:01"
            placeholder="请选择">
          </el-date-picker>
          至
          <el-date-picker
            :picker-options="pickerExportQueryEndTime"
            @change="changeExportQueryEndTime"
            style="margin-left: 10px"
            v-model="exportQuery.endTime"
            type="date"
            value-format="yyyy-MM-dd 23:59:59"
            placeholder="请选择">
          </el-date-picker>
        </div>
        <div slot="footer" style="text-align: center; margin-top: 40px">
          <el-button size="small"  @click="exportQuery.dialog = false">取 消</el-button>
          <el-button type="primary" style="margin-left: 44px;" size="small" @click="exportData">导 出</el-button>

        </div>
      </div>

    </el-dialog>

    <!--联系监护人-->
    <el-dialog
      title="联系监护人"
      :visible.sync="concatPerson.dialog"
      class="viewContact" width="692px"   >

      <div class="contactContent  concatPerson">
        <el-table
          :data="concatPerson.data"
          style="width: 90%; text-align: center;margin: 0 auto">
          <el-table-column
            prop="userName"
            label="姓名"
          >
          </el-table-column>
          <el-table-column
            prop="userTitle"
            label="称谓"
          >
          </el-table-column>
          <el-table-column
            prop="mobile"
            label="手机号码">
          </el-table-column>
        </el-table>

      </div>

      <div  style="text-align: center; margin-top: 20px">
        <el-button size="small"  type="primary" @click="concatPerson.dialog = false">关 闭</el-button>
      </div>
    </el-dialog>
    <!--通知相关人员-->
    <el-dialog
      title="通知提示"
      :visible.sync="noticePerson.dialog"
      class="viewContact noticePerson " width="600px"  >
      <div class="contactContent h-200"  >
        <template v-if="noticePerson.roleList.length > 0 || noticePerson.userNameList.length > 0 " >
          <div style=" height: 30px; line-height: 30px"  v-if="noticePerson.roleList.length > 0">体温异常信息已成功通知了
            <span style="color: #ff0000;" v-for="(item, index) in noticePerson.roleList" :key="index"> {{item}} <span v-if="index !=  noticePerson.roleList.length - 1">、</span> </span>
            身份人员</div>
          <div style=" height: 30px; line-height: 30px" v-if="noticePerson.userNameList.length > 0" >

            <span v-if="noticePerson.roleList.length === 0">体温异常信息已成功通知了</span>
            <span v-else>以及通知给了</span>
            <span style="color: #ff0000;height: 30px; line-height: 30px"  v-for="(item, index) in noticePerson.userNameList" :key="index">{{item}}<span v-if="index !=  noticePerson.userNameList.length - 1">、</span></span>

          </div>
        </template>
        <template v-else >
          <div style="text-align: center">
            系统尚未配置
            <span v-if="roleType === 0"> 学生</span>
            <span v-else>教职工</span>
            体温异常通知人员
          </div>
        </template>


        <div style="text-align: center; margin-top:  80px">
          <el-button size="small"  type="primary" @click="noticePerson.dialog = false">关 闭</el-button>
        </div>
      </div>
    </el-dialog>
    <!--亲密接触者弹框-->
    <el-dialog
      title="查看密切接触者"
      custom-class="concat-dialog"
      :visible.sync="viewContactDialog.dialog"
      class="viewContact" width="692px">
      <div style="font-size: 14px;font-weight: bold; margin-bottom: 37px;text-align: center;height: 32px; line-height: 32px;" >
        <div style="margin: auto;display: flex;align-items: center;justify-content: center">
          <span style="width: 32px;height: 32px;border-radius: 16px;height: 32px; line-height: 32px;display:inline-block;">
         <template v-if="viewContactDialog.avatar !== null && viewContactDialog.avatar !== ''">
           <img :src="viewContactDialog.avatar"  class="avatar" style="width: 32px;height: 32px;border-radius: 16px;height: 32px;">
         </template>
          <template v-else>
             <img :src="defaultAvatar"  class="avatar">
          </template>
        </span>
          <span style="color: #333333;margin: 0 4px;">{{viewContactDialog.userName}}</span>
          <span style="margin: 0 4px; color: #999999;">|</span>
          <span style="color: #999999;height: 32px; " v-if="roleType === 0">{{viewContactDialog.officeName}}</span>
          <span style="color: #999999;height: 32px;" v-else>{{viewContactDialog.mobile}}</span>
        </div>
      </div>
      <div class="timeline">
        <div class="timeline-left" style="text-align: right; padding-right: 26px;font-size: 16px;font-weight: bold">途径地点</div>
        <div class="timeline-right" style="text-align: center;font-size: 16px;font-weight: bold">疑似密切接触者</div>
      </div>
      <div class="contactContent" style="height: 350px;">
        <!--//:class="{'cbp_div': index === (detailContent.length - 1)}-->

        <div v-if="viewContactDialog.data.length > 0">
          <div v-for="(item, index) in viewContactDialog.data" :key="index" class="timeline" >
            <div class="timeline-left">
              <div class="timeline-time">
                <div>{{item.time | pointTimeFormat}}</div>
                <div class="timeline-time-week">({{item.time | weekFormat}})</div>
                <div>{{item.time | timeFormatHm}}</div>
              </div>
              <div class="timeline-place">{{item.place}}</div>
            </div>
            <div class="timeline-right clearfix">
              <div class="timeline-icon" ></div>
              <div class="tl-right-cont">
                <div v-if="item.detailContents.length > 0">
                  <div v-for="(item, index) in item.detailContents" :key="index" >
                    <span style="color: #333333; padding-right: 4px;width: 70px;display: inline-block;">{{item.userName}}</span>
                    <span style="color: #333333;width: 110px;display: inline-block;text-align: center" v-if="item.officeName">{{item.officeName}}</span>
                    <span style="color: #333333;width: 110px;display: inline-block;text-align: center" v-else>
                        <template v-if="item.mobile !== null && item.mobile !== ''"> ({{item.mobile}})</template>
                      </span>
                    <span style="padding-left: 4px;">{{item.time | pointTimeFormat}}  <span style="padding: 0 4px">({{item.time | weekFormat}})</span> {{item.time | timeFormatHm}} </span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div v-else style="text-align: center">
          暂无疑似密切接触者记录
        </div>
      </div>
      <div slot="footer" style="text-align: center; margin-top: 20px">
        <el-button size="small"  type="primary" @click="viewContactDialog.dialog = false">关 闭</el-button>
      </div>
    </el-dialog>

    <!--测温记录弹框-->
    <el-dialog
      title="测温记录"
      :visible.sync="temperatureRecordingDialog.dialog"
      class="viewContact" width="692px">
      <div style="font-size: 14px;font-weight: bold; margin-bottom: 37px;margin-left: 32px; text-align: left">
        <span style="color: #333333; font-size: 14px">{{temperatureRecordingDialog.userName}}</span>
        <span style="color: #333333;margin-left: 20px ; font-size: 14px"  v-if="roleType === 0">{{temperatureRecordingDialog.officeName}}</span>
        <span style="color: #333333;margin-left: 20px ; font-size: 14px" v-else>{{temperatureRecordingDialog.mobile}}</span>
      </div>
      <div class="contactContent" >
        <el-table
          :data="temperatureRecordingDialog.data"
          :row-class-name="tRecordingDialogUnusual"
          style="width: 90%; text-align: center;margin: 0 auto">
          <el-table-column
            prop="time"
            label="测温时间"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="temperatureBy"
            label="测温人"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="temperatureDevice"
            label="测温设备"
            align="center">
          </el-table-column>
          <el-table-column
            prop="temperaturePlace"
            label="测温地点"
            align="center">
          </el-table-column>
          <el-table-column
            prop="temperature"
            label="体温"
            align="center">
          </el-table-column>
        </el-table>

      </div>
      <div slot="footer" style="text-align: center; margin-top: 20px">
        <el-button size="small"  type="primary" @click="temperatureRecordingDialog.dialog = false">关 闭</el-button>
      </div>
    </el-dialog>


    <!--添加记录弹框-->
    <el-dialog
      title="添加记录"
      :visible.sync="addRecording.dialog"
      class="viewContact" width="1000px">
      <div class="contactContent addRecording">

        <div class="form clearfix form-title ">
          <div class="form-item " style="width: 190px;" v-if="roleType === 0">学生</div>
          <div class="form-item " style="width: 190px;" v-else>教职工</div>
          <div class="form-item" style="width: 94px">体温</div>
          <div class="form-item" style="width: 180px">测温地点</div>
          <div class="form-item" style="width: 200px">测温时间</div>
          <div class="form-item" style="width: 180px">测温人</div>
        </div>

        <div class="form clearfix" v-for="(item, index) in addRecording.form" :key="index">
          <div class="form-item " style="width: 190px;text-align: center">
            <span class="icon">*</span>
            <el-select clearable style="width: 190px" size="small"  v-model="item.userId" clearable placeholder="请选择 (搜索)"  filterable  @change="handleChangeUserList(item.userId, index)"  >
              <el-option v-for="item in userList"
                         :key="item.userId"
                         :label="item.userName + '  '+ item.otherInfo"
                         :value="item.userId"/>
            </el-select>
          </div>
          <div class="form-item" style="width: 94px">
            <span class="icon">*</span>
            <el-input size="small" v-model="item.temperature"  />
          </div>
          <div class="form-item" style="width: 180px">
            <span class="icon">*</span>
            <el-select clearable style="width: 180px" size="small"  v-model="item.place"  clearable @click=" handleChangeMonitorPlace(item.device, index) " >
              <el-option v-for="item in monitorPlaceList"
                         :key="item.device"
                         :label="item.monitorPlace"
                         :value="item.device"/>
            </el-select>
          </div>
          <div class="form-item" style="width: 200px">
            <span class="icon">*</span>
            <el-date-picker
              size="small"
              class="my-data-picker"
              :clearable="false"
              v-model="item.time"
              type="datetime"
              placeholder="选择日期时间">
            </el-date-picker>
          </div>
          <div class="form-item" style="width: 180px">
            <span class="icon">*</span>
            <el-select clearable style="width: 180px"  size="small"  v-model="item.temperatureBy" clearable placeholder="请选择 (搜索)"  filterable  @click="handleChangeMonitorList(item.temperatureBy, index) "  >
              <el-option v-for="item in monitorUserList"
                         :key="item.userId"
                         :label="item.userName + '  '+ item.otherInfo"
                         :value="item.userId"/>
            </el-select>
          </div>
          <div class="delete" @click="handleDeleteFormItem(index)" v-if="index !== 0">
            删除
          </div>
        </div>
      </div>
      <div class="add-form-item" style="text-align: right; margin-top: 20px;margin-right: 70px">
        <el-button size="small"  type="primary" @click="handleAddFormItem">添加新记录</el-button>
      </div>
      <div slot="footer" style="text-align: center; margin-top: 20px">
        <el-button size="small"   @click="addRecording.dialog = false">取 消</el-button>
        <el-button size="small" style="margin-left: 40px" type="primary" @click="submitAddRecord">保 存</el-button>
      </div>
    </el-dialog>


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
  import {mapGetters} from 'vuex'
  import {download} from "@/lib/download"
  import elDragDialog from '@/directive/el-dragDialog'
  import {getStudentHealthMonitor, getStaffHealthMonitor, saveMonitorHistory,
    getIdentifyList, getStudentList, getStaffList, getParents, getMonitorHistory, getPlaceList,
    getStudentCalc, getStaffCalc, getClassList, notifyConcat, findUserInfo
  }  from  '@/api/api'
  import moment from 'moment'
  export default {
    computed: {
      ...mapGetters([
        'permissions'
      ])
    },
    directives: {
      waves, elDragDialog
    },
    name: "index",
    data() {
      return {
        roleType: 0, // 0: 学生 1: 教职工
        currentTime : '',//当前时间
        pickerOptionsBeginTime: {},
        deviceList : [], //设备列表数据
        pickerExportQueryBeginTime : {},
        pickerExportQueryEndTime: {},
        defaultAvatar : require("../../assets/image/person.png"),
        calc:{
          abnormalCount: '',
          needMonitorCount: '',
          needRecheckCount : '',
          amNotMonitorCount: '',
          pmNotMonitorCount: '',
        },
        studentCalc:{
          abnormalCount: '',
          needMonitorCount: '',
          needRecheckCount : '',
          amNotMonitorCount: '',
          pmNotMonitorCount: '',
        },
        staffCalc:{
          abnormalCount: '',
          needMonitorCount: '',
          needRecheckCount : '',
          amNotMonitorCount: '',
          pmNotMonitorCount: '',
        },
        classList: [],
        studentList: [],
        staffList: [],
        // 用户集合
        userList: [],
        // 地点集合
        monitorPlaceList: [],
        // 测温人集合
        monitorUserList : [],
        unusualNumber : '',
        // 体温状态
        temperatureTypeList : [
          {label: '全部', code : null},
          {label: '异常', code : 2},
          {label: '需确认', code : 1},
        ],
        // 测温进展
        temperatureMeasurement: [
          {label: '全部', code : null},
          {label: '全天未测', code : 0},
          {label: '上午未测', code : 1},
          {label: '下午未测', code : 2},
        ],
        tableLoading: false,
        unusualNumber: 0,
        tableData: [],
        listQuery: {
          beginTime: new Date(),
          userName: '',
          mobile: '',
          officeId: '',
          temperature:'',
          progress: '',//测温进展
          roleType : ''
        },
        // 导出日期
        exportQuery: {
          beginTime : new Date(),
          endTime : new Date(),
          dialog : false,
        },
        //查看联系人
        concatPerson: {
          userId : '',
          data : [ ],
          dialog: false,
        },
        // 测温记录
        temperatureRecordingDialog : {
          userId: '',
          userName: '',
          mobile : '',
          officeName: '',
          data: [ ],
          dialog: false,
        },
        // 查看亲密接触者弹框
        viewContactDialog: {
          userId: '',
          userName : '',
          officeName: '',
          mobile : '',
          avatar: '',
          dialog: false,
          data: [ ]
        },
        // 通知相关人员
        noticePerson: {
          dialog : false,
          roleList: [],
          userNameList: []
        },
        //添加记录
        addRecording: {
          dialog: false,
          form: [{userId : '', userName: '', temperature: '', place : '', device :'', time : '', temperatureBy : '',monitorUsername: '' }]
        },
        form : {userId: '', userName: '', officeId: '',mobile:'', temperature: '', place :'', time: '', temperatureTime: ''},
        pageInfo: {
          pageNum: 1,
          pageSize: 10,
          total: 0
        },
      }
    },
    methods: {
      roleTypeChange() {
        this.initListQuery()
        // this.fetchData(true)
        if (this.roleType === 0) {
          this.calc = this.studentCalc
        } else if (this.roleType === 1) {
          this.calc = this.staffCalc
        }
      },
      changeCreateBeginTime() {
        let createBeginTime = this.listQuery.beginTime
        // console.log(this.listQuery.beginTime)
        if (createBeginTime) {
          let beginTime = new Date(createBeginTime.replace('-', '/'))
          this.fetchData(true, true)

        }
      },
      initListQuery() {
        this.listQuery = {
          beginTime: new Date(),
          userName: '',
          mobile: '',
          officeId: '',
          temperature:'',
          progress: '',//测温进展
        }
        this.fetchData(true, true)
      },

      initPageInfo() {
        this.pageInfo.pageNum = 1;
      },
      fetchData(initPageInfo, initCalc) {
        if (initPageInfo) {
          this.initPageInfo()
        }
        let params = {
          pageNum:  this.pageInfo.pageNum,
          pageSize: this.pageInfo.pageSize,
          monitorDate: moment(this.listQuery.beginTime).format("YYYY-M-D 00:00:00"),
          name : this.listQuery.userName,
          classId: this.listQuery.officeId,
          healthStatus : this.listQuery.temperature,
          checkStatus : this.listQuery.progress,
          mobile : this.listQuery.mobile
        }
        if (this.roleType === 0) {
          //处理学生逻辑
          getStudentHealthMonitor(params).then(res => {
            if (res.code === 200) {
              if (res.result) {
                this.tableData = res.result.list || []
                this.pageInfo.total = res.result.total || 0
              }
            } else {
              this.$message.error("获取学生健康监测错误" + res.message);
            }
          })
          if (initCalc) {
            this.initStudentCalc()
          }
        } else if (this.roleType === 1) {
          if (initCalc) {
            this.initStaffCalc()
          }
          //处理教职工逻辑
          getStaffHealthMonitor(params).then(res => {
            if (res.code === 200) {
              if (res.result) {
                this.tableData = res.result.list || []
                this.pageInfo.total = res.result.total || 0
              } else {
                this.tableData = []
              }
            } else {
              this.$message.error("获取学生健康监测错误" + res.message);
            }
          })
        }
      },
      hanleClickExport () {
        this. exportQuery = {
          beginTime : new Date(),
          endTime : new Date(),
          dialog : true,
        }
      },
      //导出excel
      exportData() {
        if (!this.exportQuery.beginTime) {
          this.$message.warning("请选择导出开始时间！")
          return
        }
        if (!this.exportQuery.endTime) {
          this.$message.warning("请选择导出结束时间！")
          return
        }

        if (this.roleType === 0) {
          let url = process.env.VUE_APP_BASE_API + '/busi/healthMonitor/studentExport'+ "?schoolToken=" + sessionStorage.getItem("schoolId") + "&startDate="+ moment(this.exportQuery.beginTime).format("YYYY-M-D HH:mm:ss")
            + "&endDate="+  moment(this.exportQuery.endTime).format("YYYY-M-D HH:mm:ss")
          // console.log('student', url)
          window.open(url)
          this.exportQuery.dialog = false
        } else if (this.roleType === 1){
          let url = process.env.VUE_APP_BASE_API + '/busi/healthMonitor/staffExport'+ "?schoolToken=" + sessionStorage.getItem("schoolId") + "&startDate="+  moment(this.exportQuery.beginTime).format("YYYY-M-D HH:mm:ss")
            + "&endDate="+  moment(this.exportQuery.endTime).format("YYYY-M-D HH:mm:ss")
          // console.log('student', url)
          window.open(url)
          this.exportQuery.dialog = false
        }
      },
      //查看监护人
      contactGuardian(data) {
        // console.log(data)
      },
      initDeviceList() {
        let schoolId = sessionStorage.getItem("schoolId")
        // let schoolId = "53083adf8e6a88e440e58cb928a95bf8c58388e3"
        let deviceUrl = process.env.VUE_APP_DEVICE_API + "/device-api/api/device/list?school_id=" + schoolId
        // console.log(deviceUrl);
        this.$axios.get(`${deviceUrl}`).then(res => {
          if (res.data.code === 200) {
            if (res.data.data.DeviceList.length > 0){
              let deviceArr = res.data.data.DeviceList
              deviceArr.forEach(deviceItem => {
                if (deviceItem.device_id && deviceItem.place_name) {
                  let obj = {
                    deviceId : deviceItem.device_id,
                    placeName :  deviceItem.place_name
                  }
                  this.deviceList.push(obj)
                }
              })
            }
          }
          // this.viewContact({userId: null})

        })
      },
      viewContact(data) {
        // console.log(data)
        if (data.userId) {
          this.viewContactDialog.userName = data.name || null
          this.viewContactDialog.officeName = data.className || null
          this.viewContactDialog.mobile = data.mobile || null
          // this.viewContactDialog.avatar = 'http://v4.test.cotf.cn/pms-ws/img/user_avatar/2020/03/18/ca07109296d64cd480b86cc76a12549d.jpg'
          // 获取用户行为轨迹
          findUserInfo({userId: data.userId}).then(res => {
            if (res.code === 200) {
              this.viewContactDialog.avatar = res.result.avatar || null
            }
          })
          let currentId = data.userId
          // let currentId = data.userId || '880878e4817b4e34bbbba525cd110062'
          this.viewContactDialog.data = []
          // console.log(this.deviceList)

          if (this.deviceList.length <= 0) {
            this.$message.warning("暂无设备场所信息！")
            return;
          }
          let trackUrl =  process.env.VUE_APP_TRACK_API + "/docking/locus/api/record/relation?sourceCode=" + currentId + "&page=1&=pageSize=500"
          this.$axios.get(`${trackUrl}`).then(res => {
            if (res.data && res.data.code && res.data.code === 200) {

              if (Array.isArray(res.data.data.data) && res.data.data.data.length > 0) {
                let trackData = res.data.data.data
                trackData.forEach(item => {
                  let obj = {
                    time : item.time,
                    deviceId : item.deviceId,
                    place : '',
                    detailContents: []
                  }
                  if (item.relationData && item.relationData.length > 0) {
                    let relationDataArr = item.relationData
                    relationDataArr.forEach(relationData => {
                      let detailConItem = {}
                      //过滤出userId
                      if (relationData.sourceType == 1) {
                        detailConItem = {
                          userId : relationData.sourceCode,
                          time : relationData.time,
                          userName : '',
                          officeName: '',
                          mobile : '',
                        }
                        obj.detailContents.push(detailConItem)
                      }
                    })
                  }
                  this.viewContactDialog.data.push(obj)
                })
                if (this.viewContactDialog.data.length > 0) {
                  this.viewContactDialog.data.forEach((item, index) => {
                    let device =  this.deviceList.filter(deviceItem => deviceItem.deviceId === item.deviceId)
                    if (device) {
                      this.viewContactDialog.data[index].place = device[0].placeName || null
                      if (item.detailContents.length > 0) {
                        // console.log('item.detailContents', item.detailContents)
                        item.detailContents.forEach(user => this.formatUserInfo(user, user.userId))
                      }
                    } else {
                      this.viewContactDialog.data.splice(index, 1)
                    }

                  })
                }
                this.viewContactDialog.dialog = true
                // console.log('222',this.viewContactDialog.data)
              } else {
                this.$message.warning("暂无轨迹数据！")
              }
            } else {
              let errMsg = res.data.msg || ''
              this.$message.error("获取学生轨迹数据错误！" + errMsg)
            }
          })
        }
        // this.viewContactDialog.dialog = true;
      },
      handleClickContact(data) {
        // console.log(data)
        if (data.userId && data.monitorDate) {
          let params  = {
            userId : data.userId,
            monitorDate : data.monitorDate,
            type : this.roleType
          }
          notifyConcat(params).then(res => {
            if (res.code === 200) {
              this.noticePerson.roleList = []
              this.noticePerson.userNameList = []
              if (res.result) {

                if (Array.isArray(res.result.identifyList) && res.result.identifyList.length > 0) {
                  res.result.identifyList.forEach(item => {
                    this.noticePerson.roleList.push(item.name)
                  })
                }
                if (Array.isArray(res.result.personList) && res.result.personList.length > 0) {
                  res.result.personList.forEach(item => {
                    this.noticePerson.userNameList.push(item.name)
                  })
                }
                //personList
              }
              this.noticePerson.dialog = true
            } else {
              this.$message.error("通知相关人员出错！" + res.message)
            }
          })

        }

      },
      tRecordingDialogUnusual(row, rowIndex) {
        if (row.row.unusual === 0) {
          return  'unusual-warning'
        } else {
          return ''
        }
      },
      // 查看联系人
      handleClickContactPerson(data) {
        if (data.userId === null || data.user === '') {
          this.$message.warning('当前学生不存在！')
          return
        }
        let params = {
          userId : data.userId
        }
        getParents(params).then(res => {
          if (res.code === 200) {
            if (res.result) {
              if (Array.isArray(res.result)) {
                res.result.forEach(item => {
                  let obj ={
                    userName : item.realName,
                    userTitle : item.relationTypeName || null,
                    mobile : item.mobile || item.familyPhone || null
                  }
                  this.concatPerson.data.push(obj)
                })
              } else {
                res.result = []
              }
            } else {
              res.result = []
            }
          } else {
            this.$message.error(`获取学生联系人信息失败！` + res.message)
          }
        })
        this.concatPerson.dialog = true
        this.concatPerson.data = [ ]
      },
      // 查看测温记录
      handleClickRecord(data) {
        if (data.userId == null || data.userId == '') {
          this.temperatureRecordingDialog.data = []
          this.$message.warning("该用户不存在！")
          return
        }
        let params = {
          userId : data.userId ,
          monitorDate : data.monitorDate ||  moment(new Date()).format("YYYY-M-D 00:00:00")
        }
        this.temperatureRecordingDialog.userName = data.name || ''
        this.temperatureRecordingDialog.mobile = data.mobile || ''
        this.temperatureRecordingDialog.officeName = data.className || ''
        getMonitorHistory(params).then(res => {
          if (res.code === 200) {
            this.temperatureRecordingDialog.data = []
            if (res.result) {
              if (Array.isArray(res.result) && res.result.length > 0) {
                res.result.forEach(item => {
                  let obj = {
                    time : item.monitorDatetime,
                    temperatureBy : item.monitorUserName,
                    temperatureDevice : item.monitorDevice,
                    temperaturePlace : this.getPlaceById(item.monitorPlace) || item.monitorPlace || null,
                    temperature : item.temperature,
                  }
                  this.temperatureRecordingDialog.data.push(obj)
                })
              }
            }
            this.temperatureRecordingDialog.dialog = true
          } else {
            this.$message.error("获取用户测温记录失败！" + res.message);
          }
        })

      },
      //删除记录
      handleDeleteFormItem(index) {
        if (index !== 0 && this.addRecording.form.length > index) {
          this.addRecording.form.splice(index, 1)
        }
      },
      //获取身份列表(学生/教职工)
      getIdentifyList() {
        let params = {
          type : this.roleType
        }
        getIdentifyList(params).then(res => {
          setTimeout(() => {
            // console.log(111)
          }, 2000)
        })

      },
      handleClickAddReport() {
        this.currentTime = moment(new Date()).format("YYYY-MM-DD HH:mm:ss")
        // todo 异步调用
        this.getIdentifyList()
        if (this.roleType === 0) {
          this.userList = this.studentList
        } else if (this.roleType === 1) {
          this.userList = this.staffList
        }
        this.addRecording.dialog = true
        this.addRecording.form = [{userName: '', officeId: '',mobile:'', temperature: '',
          temperatureBy: '', place :'', time:  this.currentTime,  userId: ''}]
      },
      // 添加新记录
      handleAddFormItem() {
        this.addRecording.form.push({userId : '', userName: '', temperature: '', place : '', device :'', time : this.currentTime, temperatureBy : '',monitorUsername: '' })
      },
      //校验新增记录
      checkRecord() {
        if (this.addRecording.form.length > 0) {
          for (let index = 0 ; index < this.addRecording.form.length; index++) {
            let item = this.addRecording.form[index]
            if(item.userId === null || item.userId === '') {
              this.$message.warning("请选择第"+( index + 1) +"条记录的用户！");
              return false
            }
            if(item.temperature === null || item.temperature === '') {
              this.$message.warning("请输入第"+( index + 1) +"条记录的体温！");
              return false
            }

            if (isNaN(item.temperature)) {
              this.$message.warning("请输入第"+( index + 1) +"条记录的体温应保留小数点后面一位！");
              return false
              // return callback(new Error("体温保留小数点后面一位"))
            }
            if (item.temperature.toString().split('.').length > 2) {
              this.$message.warning("请输入第"+( index + 1) +"条记录的体温应保留小数点后面一位！");
              return false
              // return callback(new Error("仅能输入1位小数点的数字"))
            }
            if (item.temperature.toString().split('.').length === 2) {
              if (item.temperature.toString().split('.')[1].length > 1) {
                this.$message.warning("请输入第"+( index + 1) +"条记录的体温应保留小数点后面一位！");
                return false
              }
              // return callback(new Error("仅能输入1位小数点的数字"))
            }

            if(item.place === null || item.place === '') {
              this.$message.warning("请选择第"+( index + 1) +"条记录的测温地点！");
              return false
            }
            if(item.temperatureTime === null || item.temperatureTime === '') {
              this.$message.warning("请输入第"+( index + 1) +"条记录的测温时间！");
              return false
            }
            if(item.temperatureBy === null || item.temperatureBy === '') {
              this.$message.warning("请选择第"+( index + 1) +"条记录的测温人！");
              return false
            }
          }

          return true;
        }

        return false;
      },
      // 保存测温记录
      submitAddRecord() {
        if (this.checkRecord()) {
          if (this.addRecording.form.length > 0) {
            let paramsArr = []
            for (let item of this.addRecording.form) {
              if (item.time !== this.currentTime) {
                item.time =  moment(item.time).format("YYYY-MM-DD HH:mm:ss")
              }

            }
            this.addRecording.form.forEach(item => {
              let obj = {
                userId : item.userId,
                name : item.userName,
                temperature : item.temperature,
                monitorPlace : item.place,
                device : item.device,
                monitorTime : item.time,
                monitorUserId : item.temperatureBy,
                monitorUsername : item.monitorUsername ,
              }
              paramsArr.push(obj)
            })

            let params = {
              type : this.roleType,
              paramsStr : JSON.stringify(paramsArr)
            }
            // console.log('params', params)
            saveMonitorHistory(params).then(res => {
              if (res.code === 200) {
                this.$message.success("保存成功！")
                this.fetchData(true, true)
                this.addRecording.dialog = false
              } else {
                this.$message.error("保存测温记录失败！"+ res.message)
              }
            })
          }
        }

      },
      handleChangeUserList(userId, index) {
        if (this.addRecording.form.length > index) {
          this.userList.forEach(item => {
            if (item.userId === userId) {
              this.addRecording.form[index].userName = item.userName
            }
          })
        }
      },
      handleChangeMonitorPlace(device, index) {
        if (this.addRecording.form.length > index) {
          this.monitorPlaceList.forEach(item => {
            if (item.delete === device) {
              this.addRecording.form[index].monitorPlace = item.monitorPlace
            }
          })
        }
      },
      handleChangeMonitorList(userId, index) {
        if (this.addRecording.form.length > index) {
          this.monitorUserList.forEach(item => {
            if (item.userId === userId) {
              this.addRecording.form[index].monitorUsername = item.userName
            }
          })
        }
      },
      changeExportQueryBeginTime() {
        let createBeginTime = this.exportQuery.beginTime
        if (createBeginTime) {
          let beginTime =  new Date(createBeginTime.replace('-', '/'))
          this.pickerExportQueryEndTime = {
            disabledDate(time) {
              return time.getTime() < beginTime  - 8.64e7 ;
            }
          }
        } else {
          this.pickerExportQueryEndTime = {
            disabledDate(time) {
              return null ;
            }
          }
        }
      },
      changeExportQueryEndTime () {
        let createEndTime  = this.exportQuery.endTime;
        if (createEndTime) {
          let endTime =   new Date(createEndTime.replace('-', '/'))
          this.pickerExportQueryBeginTime = {
            disabledDate(time) {
              return time.getTime() > endTime ;
            }
          }
        } else {
          this.pickerExportQueryBeginTime = {
            disabledDate(time) {
              return null ;
            }
          }
        }
      },
      initStudentList() {
        getStudentList().then(res => {
          if (res.code === 200) {
            if (Array.isArray(res.result)) {
              res.result.forEach(item => {
                let obj = {
                  userId: item.userId,
                  otherInfo: item.className,
                  userName: item.name
                }
                this.studentList.push(obj)
              })
            } else {
              this.studentList = []
            }
          }
        })
      },
      initStaffList() {
        getStaffList().then(res => {
          if (res.code === 200) {
            if(res.result) {
              if (Array.isArray(res.result)) {
                res.result.forEach(item => {
                  let obj = {
                    userId: item.userId,
                    otherInfo : item.mobile,
                    userName: item.name
                  }
                  this.staffList.push(obj)
                })
                // 默认教职工为测温人
                this.monitorUserList = this.staffList
              } else {
                this.staffList = []
              }
            }
          } else {
            this.$message.error("获取教职工信息失败！" + res.message)
          }
        })
      },
      // 更据场所Id获取名称
      getPlaceById(placeId) {
        if (placeId && this.monitorPlaceList.length > 0) {
          let placeArr = this.monitorPlaceList.filter(item => item.device === placeId)
          if (placeArr.length > 0) {
            return placeArr[0].monitorPlace
          } else{
            return null
          }
        }
        return null
      },
      initPlace() {
        getPlaceList().then(res => {
          if (res.code === 200) {
            if (res.result) {
              if (Array.isArray(res.result) && res.result.length > 0) {
                res.result.forEach(item => {
                  let obj = {
                    device : item.id,
                    monitorPlace : item.name,
                  }
                  this.monitorPlaceList.push(obj)
                })
              }
            } else {
              this.$message.warning("当前尚未配置场所信息！")
            }
          } else  {
            this.$message.error('获取场所信息失败!'+ res.message);
          }
        })
        // monitorPlaceList
      },
      formatRowClassName (row, rowIndex) {
        if (row.row.healthStatus == 2) {
          return 'error'
        } else if (row.row.healthStatus == 1) {
          return 'warning'
        } else {
          return 'normal'
        }
      },
      initStudentCalc() {
        let params = {
          monitorDate : this.listQuery.beginTime === null ?  moment(new Date()).format("YYYY-MM-DD 00:00:00") :  moment( this.listQuery.beginTime).format("YYYY-MM-DD 00:00:00")
        }
        getStudentCalc(params).then(res => {
          if (res.code === 200) {
            if (res.result) {
              this.studentCalc.abnormalCount = res.result.confirmedAbnormalCount || 0
              this.studentCalc.needMonitorCount = parseInt(res.result.amNotMonitorCount) + parseInt(res.result.pmNotMonitorCount)
              this.studentCalc.needRecheckCount = res.result.needRecheckCount || 0
              this.studentCalc.amNotMonitorCount = res.result.amNotMonitorCount || 0
              this.studentCalc.pmNotMonitorCount = res.result.pmNotMonitorCount || 0
              if (this.roleType === 0) {
                this.calc = this.studentCalc
              }
            }
          } else {
            this.$message.error('获取学生体温异常信息失败！' + res.message)
          }
        })
      },
      initStaffCalc () {
        let params = {
          monitorDate : this.listQuery.beginTime === null ? moment(new Date()).format("YYYY-MM-DD 00:00:00") :  moment( this.listQuery.beginTime).format("YYYY-MM-DD 00:00:00")
        }
        // console.log('params' , params)
        getStaffCalc(params).then(res => {
          if (res.code === 200) {
            if (res.result) {
              this.staffCalc.abnormalCount = res.result.confirmedAbnormalCount || 0
              this.staffCalc.needMonitorCount = parseInt(res.result.amNotMonitorCount) + parseInt(res.result.pmNotMonitorCount)
              this.staffCalc.needRecheckCount = res.result.needRecheckCount || 0
              this.staffCalc.amNotMonitorCount = res.result.amNotMonitorCount || 0
              this.staffCalc.pmNotMonitorCount = res.result.pmNotMonitorCount || 0
              if (this.roleType === 1) {
                this.calc = this.staffCalc
              }
            }
          } else {
            this.$message.error('获取学生体温异常信息失败！' + res.message)
          }
        })
      },
      getClassList() {
        getClassList().then(res => {
          if (res.code == 200) {
            if (res.result) {
              this.classList =   res.result ||  [];
              this.classList.unshift({id: null, className: "全部"})
            }
          } else {
            this.$message.error("获取班级信息失败！" + res.message);
          }
        })
      },
      formatUserInfo(obj ,userId) {
        // this.staffList.forEach(item => console.log(item.userId))
        if (this.staffList.length > 0) {
          let staff =  this.staffList.filter(item => item.userId === userId);
          if (staff.length > 0) {
            if (obj.mobile !== undefined) {
              obj.mobile = staff[0].otherInfo
              obj.userName = staff[0].userName

            }
          }
        }
        if  (this.studentList.length > 0) {
          let student = this.studentList.filter(item => item.userId === userId)
          if (student.length > 0) {
            if (student) {
              if (obj.officeName !== undefined) {
                obj.userName = student[0].userName
                obj.officeName = student[0].otherInfo
              }
            }
          }
        }
      },
      findUserInfo(userId) {
        if (userId) {
          let params = {
            userId : userId
          }
          findUserInfo(params).then(res => {
            if (res.data.code === 200) {

            }
          })
        }
      },
      handleSizeChange(val) {
        this.pageInfo.pageSize = val
        this.fetchData()
      },
      handleCurrentChange(val) {
        this.pageInfo.pageNum = val
        this.fetchData()
      },
    },
    created() {
      this.initStaffList()
      this.initStudentList()
      this.initPlace()
      this.initStudentCalc()
      this.initStaffCalc()
      this.getClassList()
      this.initDeviceList()

    },
    mounted() {
      this.fetchData(true)
      // this.$nextTick(() => {
      //   this.viewContact({userId: ''})
      // })
    },
  }
</script>

<style  scoped lang="scss">
  .viewContact {
    height: 100%;
    width: 100%;
    overflow-x: auto;
    overflow-y: hidden;
    margin-top: 5vh !important;
    /*display: flex;*/
    /*justify-content: center;*/
    /*align-items: center;*/
    .contactContent {
      margin-top: unset !important;
      /*width: 692px;*/
      max-height: 50vh;
      min-height: 36vh;
      overflow-y: auto;
      //position: relative;
    }
    .addRecording {
      min-height: unset !important;
    }
    .contactContent::-webkit-scrollbar{
      width: 6px;/*竖向滚动条的宽度*/
      height: 6px;/*横向滚动条的高度*/
    }
    .contactContent::-webkit-scrollbar-thumb{/*滚动条里面的小方块*/
      background: #ccc;
      border-radius: 3px;
    }
    .contactContent::-webkit-scrollbar-track{/*滚动条轨道的样式*/
      background: #F0F0E1;
      border-radius: 3px;
    }
  }
  .el-dialog {
    margin-top: 2vh !important;
  }
  .timeline {
    width: 632px;
    display: flex;
    justify-content: space-between;
    align-content: center;
    padding-bottom: 27px;
  }
  .timeline-left {
    width: 200px;
    text-align: right;
  }
  .timeline-time {
    div {
      display: inline-block;
      color: #b3b3b3;
    }
  }
  .timeline-time-week {
    margin: 0 14px 0 8px;
  }
  .timeline-place {
    line-height: 40px;
    height: 40px;
    font-size: 14px;
    color: #555555;
  }
  .timeline-right {
    width: 402px;
    box-sizing: border-box;
    /*display: flex;*/
    /*justify-content: flex-start;*/
    /*align-content: center;*/
  }
  .timeline-icon {
    width: 2px;
    height: calc(100% + 27px);
    background: #02B2B5;
    position: relative;
    float: left;
  }
  .timeline-icon:before {
    content: '';
    position: absolute;
    width: 12px;
    height: 12px;
    top: 0;
    left: -5px;
    -webkit-border-radius: 6px;
    -moz-border-radius: 6px;
    border-radius: 6px;
    background: #02B2B5;
  }
  .tl-right-cont {
    /*padding-left: 20px;*/
    width: 365px;
    background: #fafafa;
    margin: 0 7px 0 20px;
    padding: 14px 0 14px 7px;
    border: 1px #eeeeee solid;
    border-radius: 10px;
    span {
      font-size: 14px;
      height: 18px;
      line-height: 18px;
      color: #666666;
    }
  }

  .unusual-warning {
    color: #f00 !important;
  }
  .h-300 {
    height:  300px !important;
  }
  .h-200 {
    height:  200px !important;
    box-sizing: border-box;
  }
  .h-400 {
    height:  400px !important;
    box-sizing: border-box;
  }
  .addRecording .form {
    width: 98%;
    margin-top: 20px;
    .form-item {
      float: left;
      display: flex;
      margin-right: 12px;
      justify-content: flex-start;
      box-sizing: border-box;
      text-align: center;
      .icon {
        height: 32px;
        line-height: 32px;
        color: #f00;
        padding-right: 4px;
      }
    }
  }
  .form-title .form-item {
    text-align: center;
    font-size: 14px;
    font-weight: bold;
    display: unset!important;
  }
  .delete {
    height: 32px;
    line-height: 32px;
    color: #f00;
    cursor: pointer;
  }
  .avatar {
    display: inline-block;
    width: 32px;
    height: 32px;
  }
  /*.view-concat-header {*/
  /*display: flex;*/
  /*align-items: center;*/
  /*}*/
</style>
<style lang="scss">
  .error {
    color: #f00 !important;
  }
  .warning {
    color: #f6b375 !important;
  }
  .normal {
    color : #999999 !important;
  }
  .concatPerson {
    min-height: 200px !important;
    max-height: 400px !important;
  }
  .concat-dialog {
    margin-top: unset !important;
  }
  .my-data-picker {
    .el-input__inner {
      padding-right: 10px !important;
    }
  }

</style>

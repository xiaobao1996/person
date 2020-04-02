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
          v-model="listQuery.beginTime"
          type="date"
          size="small"
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
        <span>所在班级：</span>
        <el-select clearable  size="small" placeholder="请选择 (搜索)"  v-model="listQuery.classCode" clearable @change="fetchData(true)"  >
          <el-option v-for="item in classList"
                     :key="item.officeId"
                     :label="item.className"
                     :value="item.officeId"/>
        </el-select>
      </div>
      <div class="filter-btn-area">
        <el-button size="small" type="primary" @click="fetchData(true)">查询</el-button>
        <el-button size="small" type="none" @click="initListQuery">清除</el-button>
      </div>
      <div class="ope-btn-area">

        <el-button size="small" type="primary" icon="el-icon-download" @click="dataImport">导 入</el-button>
      </div>
    </div>
    <el-table ref="form" :data="tableData" v-loading="tableLoading"
              border show-overflow-tooltip style="width: 100%">
      <el-table-column label="测温时间" prop="monitorDate" align="center"/>
      <el-table-column label="班级" prop="className" align="center"/>
      <el-table-column label="姓名" prop="monitorUserName" align="center"/>
      <el-table-column label="体温" prop="temperature" align="center"/>
    </el-table>
    <el-dialog title="体温枪数据导入"
               width="500px"
               custom-class="upload-dialog"
               :before-close="handleUploadClose"
               :visible.sync="importData.dialog">
      <el-upload
        class="upload-item"
        style="width: 440px;margin: auto;"
        drag
        :action="importUrl"
        with-credentials
        :data="{schoolToken : schoolId}"
        :before-upload="beforeUpload"
        :on-success="handleUploadSuccess"
        :on-change="handleChange"
        :on-remove="handleRemove"
        :file-list="importData.fileList"
        single>
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖拽此区域，或<em>点击上传</em></div>
      </el-upload>
      <template v-if="importData.uploadFinish">
        <div class="import-tip-success" v-if="importData.success">上传成功</div>
        <div class="import-tip-fail" v-else>文件上传失败，请下载查看。<span style="text-decoration: underline;cursor: pointer;" @click="downloadImportErrorFile">下载</span></div>
      </template>
      <div slot="footer" style="text-align: center;">
        <!--<el-button type="primary" size="small" style="width: 140px;" @click="downloadTemplate">下载Excel模板</el-button>-->
      </div>
    </el-dialog>

    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pageInfo.pageNum"
      :page-sizes="[ 10, 20, 50]"
      :page-size="10"
      layout="total, sizes, prev, pager, next, jumper"
      :total="pageInfo.total">
    </el-pagination>
  </div>
</template>

<script>
  import {getClassList, getGunList} from '@/api/api'
  import {download} from "@/lib/download"
    export default {
      //天波体温枪数据到导入
        name: "dataImport",
        data() {
          return {
            schoolId : sessionStorage.getItem("schoolId") || '1111',
            pickerOptionsBeginTime: {},
            pickerOptionsEndTime: {},
            tableLoading : false,
            listQuery:{
              beginTime: '',
              endTime : '',
              realName : '',
              classCode: '',
            },
            classList: [],
            tableData: [],
            pageInfo: {
              pageNum: 1,
              pageSize: 10,
              total: 0
            },

            //导入相关
            importData : {
              title: '',
              dialog : false,
              fileList : [],
              uploadFinish : false,
              resultUrl : '',
              success : false,
              loading : ''
            },
            importTemplateUrl : process.env.VUE_APP_BASE_API + "/busi/healthMonitor/studentImportTemplate",
            importUrl : process.env.VUE_APP_BASE_API + '/busi/gun/studentMonitorImport',
          }
        },
      methods: {
        changeCreateBeginTime() {
          let createBeginTime = this.listQuery.beginTime
          if (createBeginTime) {
            let beginTime = new Date(createBeginTime.replace('-', '/'))
            this.pickerOptionsEndTime = {
              disabledDate(time) {
                return time.getTime() < beginTime - 8.64e7;
              }
            }
          } else {
            this.pickerOptionsEndTime = {
              disabledDate(time) {
                return null;
              }
            }
          }
        },
        changeCreateEndTime() {
          let createEndTime = this.listQuery.endTime;
          if (createEndTime) {
            let endTime = new Date(createEndTime.replace('-', '/'))
            this.pickerOptionsBeginTime = {
              disabledDate(time) {
                return time.getTime() > endTime;
              }
            }
          } else {
            this.pickerOptionsBeginTime = {
              disabledDate(time) {
                return null;
              }
            }
          }

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
        initListQuery() {
          this.listQuery = {
            realName: '',
            beginTime: '',
            endTime: '',
            classCode: '',
          }
          this.initPickOption()
          this.fetchData(true)
        },
        handleSizeChange(val) {
          this.pageInfo.pageSize = val
          this.fetchData()
        },
        handleCurrentChange(val) {
          this.pageInfo.pageNum = val
          this.fetchData()
        },
        initPageInfo() {
          this.pageInfo.pageNum = 1;
        },
        fetchData(initPageInfo) {
          if(initPageInfo) {
            this.initPageInfo()
          }
          let params = {
            pageNum: this.pageInfo.pageNum,
            pageSize : this.pageInfo.pageSize,
            startDate : this.listQuery.beginTime,
            endDate : this.listQuery.endTime,
            name : this.listQuery.realName,
            classId : this.listQuery.classCode,
          }
          this.tableLoading = true;
          getGunList(params).then(res => {
            if (res.code === 200) {
              this.tableData = []
              if (res.result && res.result.list && Array.isArray(res.result.list) && res.result.list.length > 0) {
                this.tableData = res.result.list
                this.pageInfo.total = res.result.total
              }
            } else {
              this.$message.error("获取体温枪数据失败！" + res.message);
            }
          }).finally(() => {
            this.tableLoading = false
          })

        },
        /***** 导入相关 ***********/
        // 下载错误信息
        downloadImportErrorFile() {
          download(this.importData.resultUrl, null, null, "体温枪记录导入错误信息.xlsx");
        },
        downloadTemplate() {
          window.open(this.importTemplateUrl + "?schoolToken=" + this.schoolId);
        },
        initImportData() {
          this.importData = {
            title: '',
            dialog : false,
            fileList : [],
            uploadFinish : false,
            resultUrl : '',
            success : false,
            loading : ''
          }
        },
        handleRemove() {
          this.importData.uploadFinish = false;
          this.importData.success = false;
          this.importData.resultUrl = "";
        },
        handleChange(file, fileList) {
          this.importData.fileList = []
          this.importData.fileList.push(file)
        },
        handleUploadClose(done) {
          if (this.importData.loading) {
            this.$alert("正在上传中，请稍后", "提示", {type : "warning"})
          } else {
            this.initImportData();
            done();
          }
        },
        handleUploadSuccess(res, file) {
          if (res.code === 200) {
            this.importData.uploadFinish = true;
            this.importData.success = true;
            this.fetchData();
          } else if (res.code === 5001) {
            this.importData.uploadFinish = true;
            this.importData.success = false;
            this.importData.resultUrl = res.result;
          } else {
            this.$alert("上传失败！" + res.message, "错误", {type : "error"});
          }

          this.importData.loading.close();
          this.importData.loading = '';
        },
        beforeUpload(file) {
          this.importData.uploadFinish = false;
          this.importData.success = false;
          this.importData.resultUrl = "";

          const isExcel = file.name.indexOf(".xls") !== -1 || file.name.indexOf(".xlsx") !== -1;
          const isLt2M = file.size / 1024 / 1024 < 2;

          if (!isExcel) {
            this.$alert('只能上传xls、xlsx文件!', "提示", {type : "warning"});
            return false;
          }
          if (!isLt2M) {
            this.$alert('上传文件大小不能超过 2MB!', "提示", {type : "warning"});
            return false;
          }
          this.importData.loading = this.$loading({
            lock: true,
            text: '导入中...',
            background: 'rgba(0, 0, 0, 0.1)',
            target: document.querySelector('.upload-dialog')
          });
          return true;
        },
        dataImport () {
          this.initImportData();
          this.importData.dialog = true;
        },
        /***** 导入结束 ***********/
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
      },
      created () {
        this.getClassList()
      },
      mounted() {
        this.fetchData();
      }
    }
</script>

<style scoped>

</style>

<template>
  <div class="app-container">
    <div class="config-list" ref="studentFocus">
      <div class="config-item" style="margin-bottom: 90px">
        <div class="config-title">依据身份添加</div>
        <div class="config-info">说明：此添加方式可以依据 <span style="color: #02B2B5">学生身份</span>,自动找到关联的人员，进行推送</div>
        <div class="config-container">
          <div class="cc-left">
            <div class="cc-left-img">
              <img :src="addPng" class="add-png">
            </div>
            <div class="cc-button">
              <el-button type="primary" width="84px" size="small" @click="handleClickAdd(0)">添加</el-button>
            </div>
          </div>
          <div class="cc-icon">
            <img :src="concatPng" class="concat-png">
          </div>
          <div class="cc-container">
            <button class="cc-container-item" v-for="(item, index) in checkedRoleTypeList" :key="index" >
              {{item.name}}
              <!--<i class="el-icon-close cc-close"> </i>-->
              <img :src="deletePng" class="cc-close" @click="deleteCheckedRole(index)">
            </button>
          </div>
        </div>
      </div>
      <div class="line"></div>
      <div class="config-item" style="margin-top: 20px">
        <div class="config-title"> 以用户为单位进行添加</div>
        <div class="config-info">说明：此添加方式 <span style="color: #02B2B5;">用以单独设置用户</span>，推送信息推送到设置的某个人</div>
        <div class="config-container">
          <div class="cc-left">
            <div class="cc-left-img">
              <img :src="addPng" class="add-png">
            </div>
            <div class="cc-button">
              <el-button type="primary" width="84px" size="small" @click="handleClickAdd(1)">添加</el-button>
            </div>
          </div>
          <div class="cc-icon">
            <img :src="concatPng" class="concat-png">
          </div>
          <div class="cc-container">
            <button class="cc-container-item" v-for="(item, index) in checkedUserList" :key="index" >
              {{item.name}}
              <template v-if="item.role !== null && item.role !== ''">
               ({{item.role}})
              </template>
              <!--<i class="el-icon-close cc-close"> </i>-->
              <img :src="deletePng" class="cc-close" @click="deleteCheckedUser(index)">
            </button>
          </div>
        </div>
      </div>
    </div>

    <div class="ope-btn-area"  style="margin-top: 90px; text-align: center">
      <!--<template v-if="permissions.indexOf('busi:student:import') !== -1">-->
      <el-button class="filter-item"  type="primary" @click="submitConfig" >保存</el-button>
      <el-button class="filter-item" style="margin-left: 60px;" @click="$router.push({path: '/config/index'})">返回</el-button>
      <!--</template>-->
    </div>
    <!--选择弹框-->
    <el-dialog
     :title="addDialog.title"
      :visible.sync="addDialog.dialog"
      width="600px">
      <div >
        <div class="filter-block" style="text-align: center">
          <span> <span style="color: #f00;">*</span> {{addDialog.label}}：</span>
          <el-select clearable size="small" v-model="addDialog.checkObj" placeholder="请选择">
            <el-option v-for="(item, index) in addDialog.data" :key="index" :label="item.name" :value="item.value"/>
          </el-select>
        </div>
        <div slot="footer" style="text-align: center; margin-top: 40px">
          <el-button size="small"  @click="initAddDialog">取 消</el-button>
          <el-button type="primary" style="margin-left: 44px;" size="small" @click="handleClickCheck">确 定</el-button>
        </div>
      </div>

    </el-dialog>
  </div>
</template>

<script>
  // require addPng from '@/image/add.png'
  import  {getReportConfig, getReportIdentifyList,
    getStaffList, saveReportConfig} from '@/api/api'
    export default {
        name: "student",
        data () {
          return {
            addPng: require("../../assets/image/add.png"),
            concatPng : require("../../assets/image/concat.png"),
            deletePng: require("../../assets/image/delete.png"),
            // 已选角色List
            checkedRoleTypeList: [ ],
            //所有角色List
            roleTypeList: [],
            checkedUserList: [],
            //所有角色List
              userList: [ ],
              addDialog: {
                title: '',
                checkObj: '',
                data: [],
                type: '',
                label: '',
                dialog: false,
              }
          }
        },
      methods : {
        deleteCheckedRole(index) {
          if (this.checkedRoleTypeList.length > index) {
            this.roleTypeList.push(this.checkedRoleTypeList[index])
            this.checkedRoleTypeList.splice(index, 1)
          }
        },
        deleteCheckedUser (index) {
          if (this.checkedUserList.length > index) {
            this.userList.push(this.checkedUserList[index])
            this.checkedUserList.splice(index, 1)
          }
        },
        handleClickCheck() {
          if (this.addDialog.checkObj === null || this.addDialog.checkObj === '') {
            this.$message.warning("请选择！")
            return
          }
          if (this.addDialog.type === 0) {
           let valueTemp = this.addDialog.checkObj
           this.roleTypeList.forEach((item, index) => {
             if (item.value === valueTemp) {
               this.checkedRoleTypeList.push(item)
               this.roleTypeList.splice(index, 1)
               this.initAddDialog()
               return;
             }
           })
          } else if (this.addDialog.type === 1) {
            let valueTemp = this.addDialog.checkObj
            this.userList.forEach((item, index) => {
              if (item.value === valueTemp) {
                this.checkedUserList.push(item)
                this.userList.splice(index, 1)
                this.initAddDialog()
                return;
              }
            })
          }
        },
        initAddDialog () {
          this.addDialog = {
            title: '',
            checkObj: '',
            data: [],
            type: '',
            label: '',
            dialog: false,
          }
        },
        handleClickAdd(data) {
          // 0: 依据身份添加 1： 依据用户为单位添加
          if (data === 0) {
            this.addDialog.dialog = true
            this.addDialog.title = '依据身份添加'
            this.addDialog.label = '身份'
            this.addDialog.type = data
            this.addDialog.data = this.roleTypeList
          } else if (data === 1) {
            this.addDialog.dialog = true
            this.addDialog.title = '依据教职工添加'
            this.addDialog.label = '教职工'
            this.addDialog.type = data
            this.addDialog.data = this.userList
          }
        },
        filterRoleTypeList() {
          if (this.roleTypeList.length > 0 && this.checkedRoleTypeList.length > 0) {
            for (let role of this.checkedRoleTypeList) {
              this.roleTypeList.forEach((item, index) => {
                if (item.value === role.value) {
                  this.roleTypeList.splice(index, 1)
                }
              })
            }
          }
        },
        filterUserList() {
          if (this.userList.length > 0 && this.checkedUserList.length > 0) {
            for (let role of this.checkedUserList) {
              this.userList.forEach((item, index) => {
                if (item.value === role.value) {
                  this.userList.splice(index, 1)
                }
              })
            }
          }
        },
        submitConfig() {
          let identifyArray = []
          let personArray = []
          if (this.checkedRoleTypeList.length > 0) {
              this.checkedRoleTypeList.forEach(item => {
                identifyArray.push(item.value)
              })
          }
          if (this.checkedUserList.length > 0) {
            this.checkedUserList.forEach(item => {
              personArray.push(item.value)
            })
          }
          let params  = {
            type : 0,
            identifyArray : identifyArray.join(","),
            personArray : personArray.join(",")
          }

          saveReportConfig(params).then(res => {
            if (res.code === 200) {
              this.$message.success("保存成功!");
              this.$router.push({path: '/config/index'})
            } else {
              this.$message.error("保存失败！" + res.message)
            }
          })
        },
        getStudentReportConfig() {
          // type 0:学生 1:教职工
          let params = {
            type : 0
          }
          getReportConfig(params).then(res => {

            if (res.code === 200) {
              this.checkedRoleTypeList = []
              this.checkedUserList = []
              if (res.result) {
                let identifyList = res.result.identifyList || []
                let personList = res.result.personList || []

                if (identifyList.length > 0) {
                  identifyList.forEach(item => {
                    let obj = {
                      name: item.name,
                      value: item.code
                    }
                    this.checkedRoleTypeList.push(obj)
                  })
                }
                  if (personList.length > 0) {
                    personList.forEach(item => {
                      let obj = {
                        name : item.name,
                        value : item.code,
                        role : this.arrToString(item.jobTypeList)
                      }
                      this.checkedUserList.push(obj)
                    })
                  }
                  this.filterRoleTypeList()
                  this.filterUserList()
              }
            } else {
              this.$message.error("获取学生通知人员配置错误！" + res.message)
            }
          })
        },
        getStudentReportIdentifyList() {
          // type 0:学生 1:教职工
          let params = {
            type : 0
          }
          getReportIdentifyList(params).then(res => {
            if (res.code === 200) {
              if (res.result) {
                  if (Array.isArray(res.result) && res.result.length > 0) {
                    res.result.forEach(item => {
                      let obj = {
                        name : item.name,
                        value : item.code
                      }
                      this.roleTypeList.push(obj)
                    })
                  }
                this.getStudentReportConfig()

              }
            } else {
              this.$message.error("获取身份列表错误！" + res.message)
            }
          })
        },
        getStaffList() {
          let params = {
            jobType : true
          }
          getStaffList(params).then(res => {
            if (res.code === 200) {
              this.userList = []
              if (res.result) {
                  if (Array.isArray(res.result) && res.result.length > 0) {
                   res.result.forEach(item => {
                     let obj = {
                       name : item.name,
                       value : item.userId,
                       role : this.arrToString(item.jobTypeNameList )
                     }
                     this.userList.push(obj)
                   })
                  }
                this.getStudentReportIdentifyList()
              }
            } else {
              this.$message.error("获取教职工列表！" + res.message)
            }
          })
        },
        arrToString (arr) {
          if (arr && Array.isArray(arr) && arr.length > 0) {
            let str = ''
            arr.forEach(item => {
              if (str === '' || str === null) {
                str = String(item)
              } else {
                str = str + ',' + String(item)
              }
            })
            return str
          }
          return null ;
        }
      },
      created () {
        document.body.scrollTop = document.documentElement.scrollTop = 0;
        this.getStaffList()
      },
      mounted() {
      }
    }
</script>

<style scoped lang="scss">
  .config-item {
    width: 100%;
  }
  .config-title {
    font-size: 14px;
    font-weight: bold;
    color: #555555;
  }
  .config-info {
    font-size: 12px;
    color: #999999;
    margin-top: 24px;
  }
  .config-container {
    display: flex;
    justify-content: flex-start;
    height: 120px;
    margin-top: 60px;
  }
  .cc-left {
    width: 124px;
    height: 100%;
    box-sizing: border-box;
    border: 1px solid #f2f1f1;
    border-radius: 4px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
  }
  .add-png {
    display: block;
    width: 30px;
  }
  .cc-button {
    margin-top: 10px;
  }
  .cc-icon {
    width: 62px;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  .concat-png {
    /*display: block;*/
  }
  .cc-container {
    width: calc(100% - 182px);
    height: 100%;
    box-sizing: border-box;
    padding: 10px 20px;
    background: #fcfcfc;
    border: 1px solid #f2f1f1;
    border-radius: 4px;
  }
  .cc-container-item {
    display: inline-block;
    line-height: 1;
    white-space: nowrap;
    background: #FFFFFF;
    border: 1px solid #D2D6DE;
    color: #666666;
    -webkit-appearance: none;
    text-align: center;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    outline: none;
    -webkit-transition: .1s;
    transition: .1s;
    font-weight: 500;
    -moz-user-select: none;
    -webkit-user-select: none;
    -ms-user-select: none;
    padding: 12px 20px;
    font-size: 14px;
    border-radius: 4px;
    position: relative;
    margin-right: 20px;
    margin-top: 10px;
  }
.cc-close {
  position: absolute;
  top: -8px;
  right: -8px;
  height: 16px;
  width: 16px;
}
</style>

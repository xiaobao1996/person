<template>
<div class="app-container config">
  <div>
    <el-form ref="configForm" :model="form" :rules="formRule">
      <div class="config-title">健康状态设置</div>
      <div class="config-item">
        <el-form-item label="异常体温下限" prop="temperature">
          <el-input v-model="form.temperature" class="width-180" size="small"></el-input> ℃
        </el-form-item>
      </div>

      <div class="config-title">异常响应设置</div>
      <div class="config-item">
        <el-form-item label="禁止通行" prop="delivery">
          <el-switch v-model="form.traffic"></el-switch>
        </el-form-item>
        <el-form-item label="设备报警" prop="delivery">
          <el-switch v-model="form.alarm"></el-switch>
        </el-form-item>
      </div>

      <div class="config-title">  体温异常通知设置</div>
      <div class="config-item">
        <el-form-item label="连续异常次数"  prop="times" >
        <el-input v-model.number="form.times" class="width-180" size="small"></el-input> 次 （说明：每隔一分钟识别记录，连续识别多次异常，即自动通知所设置的人员）
        </el-form-item>
        <el-form-item label="学生体温异常通知"  >
          <el-button type="primary" size="small" @click="$router.push({path : '/config/student'})" >设置通知人员</el-button>
          <span>已设置<span style="color: #f00;">{{studentConfigUserCount}}</span>人</span>
        </el-form-item>
        <el-form-item label="教职工体温异常通知"  >
          <el-button type="primary" size="small" @click="$router.push({path : '/config/staff'})" >设置通知人员</el-button>
          <span>已设置 <span style="color: #f00;">{{staffConfigUserCount}}</span>人</span>
        </el-form-item>
      </div>

      <div class="config-title">  健康打卡设置</div>
      <div class="config-item">
        <el-form-item label="模式"  prop="times" >
          <el-radio-group v-model="form.open">
            <el-tooltip  content="假期模式适用于放假居家进行个人健康信息填报" popper-class="border-color" placement="top-start" effect="light">
              <el-radio :label="0">假期</el-radio>
            </el-tooltip>
            <el-tooltip content="开学模式适用于开学后个人健康信息填报" popper-class="border-color" placement="top-start" effect="light">
              <el-radio :label="1">开学</el-radio>
            </el-tooltip>
          </el-radio-group>
        </el-form-item>
      </div>
    </el-form>
    <div style="text-align: center;margin-top: 20px">
      <el-button type="primary" size="small" @click="fromConfirm">保存</el-button>
    </div>
  </div>
</div>
</template>

<script>
    import  {getConfigDetail, saveConfig, getReportCountDetail} from '@/api/api'
    export default {
        name: "index",
        data() {
          let validateTemperature = (rule, value, callback)=> {
              if (!value) {
                return callback(new Error("请输入异常体温下限"))
              }
              if (value === "" || isNaN(value)) {
                return callback(new Error("仅能输入1位小数点的数字"))
              }
              if (value.toString().split('.').length > 2) {
                return callback(new Error("仅能输入1位小数点的数字"))
              }
            if (value.toString().split('.').length === 2) {
              let strArr = value.toString().split('.')
              let str = strArr[1]
              if (str.length > 1) {
                return callback(new Error("仅能输入1位小数点的数字"))
              }
            }
            callback()
          };
          return {
            studentConfigUserCount : 0,
            staffConfigUserCount: 0,
            form: {
              temperature: '',
              traffic: true, //通行
              alarm: true, // 警报
              times: '',
              open: 0,
            },
            formRule: {
              temperature : [
                {
                  required: true,
                  validator: validateTemperature,
                  trigger: ['blur']
                }
              ],
              times : [
                { type: 'number', message: '请输入正整数', trigger: 'blur'}
                ]
            },
          }
        },
      methods : {
        fromConfirm() {
          this.$refs.configForm.validate(valid => {
            if (valid) {
               let params = {
                 temperatureLowLimit : this.form.temperature,
                 noPass : this.form.traffic === true ? 1 : 0,
                 deviceAlarm : this.form.alarm === true ? 1 : 0,
                 notifyContinuousNumber : this.form.times,
                 mode : this.form.open
               }
              saveConfig(params).then(res => {
                if (res.code === 200) {
                  this.$message.success("保存成功！")
                  this.getConfigDetail()
                } else {
                  this.$message.error("保存配置信息错误!" + res.message);
                }
              })
            } else {
              this.$message.warning("还存在填写不合法的地方，请确认！");
              return false;
            }
          })
        },
        getConfigDetail() {
          getConfigDetail().then(res => {
            if (res.code === 200) {
              if (res.result) {
                this.form.temperature = res.result.temperatureLowLimit || 0
                this.form.traffic = res.result.noPass ? res.result.noPass === 1 : false
                this.form.alarm = res.result.deviceAlarm ? res.result.deviceAlarm === 1 : false
                this.form.times = res.result.notifyContinuousNumber || 0
                this.form.open = res.result.mode ? res.result.mode : 0
              }
            } else {
              this.$message.error("获取配置信息失败" + res.message)
            }
          })
        },
        getReportCountDetail(){
          getReportCountDetail().then(res => {
            if (res.code === 200) {
              if (res.result) {
                  this.studentConfigUserCount = res.result.studentConfigUserCount || 0
                  this.staffConfigUserCount = res.result.staffConfigUserCount || 0
              }
            } else {
              this.$message.error("获取配置人数错误！" + res.message)
            }
          })
        },

      },
      created () {
        this.getConfigDetail()
        this.getReportCountDetail()
      },
      mounted() {

      }
    }
</script>
<style lang="scss">
  .config-item {
    .el-form-item__label {
      width: 160px !important;
      text-align: right;
    }
  }
  .el-tooltip__popper[x-placement^="top"] .popper__arrow::after {
    border-top-color: #02B2B5 !important;
  }
  .el-tooltip__popper[x-placement^="top"] .popper__arrow {
    border-top-color: #02B2B5 !important;
  }

  .border-color{
    border: 1px solid #02B2B5 !important;
  }

</style>
<style scoped lang="scss">
.config-title {
  width: 100%;
  height: 40px;
  line-height: 40px;
  background: rgba(242, 242, 242, 1);
  color: #000000;
  font-size: 16px;
  text-indent: 1em;
  font-weight: bold;
}
  .config-item {
    margin-left: 70px;
    margin-top: 26px;
  }
  .margin-top-10 {
    margin-top: 10px !important;
  }

</style>

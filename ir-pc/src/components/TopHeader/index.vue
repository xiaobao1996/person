<template>
  <div class="container" id="message-header">
    <div class="appinfo-area">{{appName}}</div>
    <div class="common-head-area">
      <!--<div class="home-house" @click="toHomePage"></div>-->
      <!--<div class="fr">-->
        <!--<div class="user-info-head" @click.stop="hoverHeaderDialog = true">-->
          <!--<el-badge :value="unreadMsgCount" :max="99" class="item" v-if="unreadMsgCount">-->
            <!--<div class="header-icon-bg">-->
              <!--<img v-if="userInfo.avatar && userInfo.avatar.trim() !== ''" class="default-header avatar-header" :src="userInfo.avatar"/>-->
              <!--<img v-else class="default-header avatar-header" src="./assets/default-header.png"/>-->
            <!--</div>-->
          <!--</el-badge>-->
          <!--<div class="header-icon-bg" v-else>-->
            <!--<img v-if="userInfo.avatar && userInfo.avatar.trim() !== ''" class="default-header avatar-header" :src="userInfo.avatar"/>-->
            <!--<img v-else class="default-header avatar-header" src="./assets/default-header.png"/>-->
          <!--</div>-->
          <!--<span class="hover-header">-->
          <!--<span class="user-name-area">{{userInfo.realName && userInfo.realName.trim() !== '' ? userInfo.realName : userInfo.mobile && userInfo.mobile.trim() !== '' ? userInfo.mobile : userInfo.email}}</span>-->
          <!--<i class="el-icon-arrow-up head-arrow" v-if="hoverHeaderDialog"></i>-->
          <!--<i class="el-icon-arrow-down head-arrow" v-else></i>-->
        <!--</span >-->
          <!--<div class="hover-header-dialog " v-if="hoverHeaderDialog" @click="hideHeaderDialog">-->
            <!--<ul>-->
              <!--<li @click="openCfMessageCenter()" class="message-warn">-->
                <!--&lt;!&ndash;<el-badge :value="unreadMsgCount" :max="99" class="item" v-if="unreadMsgCount">&ndash;&gt;-->
                  <!--消息中心-->
                <!--&lt;!&ndash;</el-badge>&ndash;&gt;-->
                <!--&lt;!&ndash;<span v-else>消息提醒</span>&ndash;&gt;-->
                <!--<span class="icon"></span>-->
              <!--</li>-->
              <!--&lt;!&ndash;<li @click="personMeterial()">个人资料&ndash;&gt;-->
                <!--&lt;!&ndash;<span class="icon"></span>&ndash;&gt;-->
              <!--&lt;!&ndash;</li>&ndash;&gt;-->
              <!--<li @click="changePwd()">修改密码-->
                <!--<span class="icon"></span>-->
              <!--</li>-->
              <!--<li @click="exit">退出登录-->
                <!--<span class="icon"></span>-->
              <!--</li>-->
            <!--</ul>-->
          <!--</div>-->
        <!--</div>-->
      <!--</div>-->
      <div class="person-dialog-wrap" v-if="personDialog">
        <div class="person-dialog" ref="personDialogItem">
          <div class="close-button" @click="personDialog = false">X</div>
          <!--<div class="person-dialog-left" style="display: none">-->
            <!--<div class="options-check-area">-->
              <!--&lt;!&ndash;<div  :class="messageWarnActive ? 'option-check-item message-option-area active':'option-check-item message-option-area'" @click="messageWarn"><span class="option-check-icon"></span>&ndash;&gt;-->
                <!--&lt;!&ndash;<el-badge :value="unreadMsgCount" :max="99" class="item" v-if="unreadMsgCount">&ndash;&gt;-->
                  <!--&lt;!&ndash;消息中心&ndash;&gt;-->
                <!--&lt;!&ndash;</el-badge>&ndash;&gt;-->
                <!--&lt;!&ndash;<span v-else class="txt">消息中心</span>&ndash;&gt;-->
              <!--&lt;!&ndash;</div>&ndash;&gt;-->
              <!--<div :class="personInfoActive ? 'option-check-item person-option-area active':'option-check-item person-option-area'" @click="personInfo"><span class="option-check-icon"></span>个人资料</div>-->
              <!--<div :class="pwdActive ? 'option-check-item change-pwd-option-area active': 'option-check-item change-pwd-option-area'" @click="changePassword"><span class="option-check-icon"></span>修改密码</div>-->
              <!--&lt;!&ndash; <div class="option-check-item exit-option-area"><span class="option-check-icon"></span>退出登录</div> &ndash;&gt;-->
            <!--</div>-->
          <!--</div>-->
          <div class="person-dialog-right" v-if="pwdActive">
            <div class="option-input-area">
              <el-alert
                :title="errorTitle"
                type="error"
                :closable="false"
                show-icon
                v-if="errorShow"
              >
              </el-alert>

              <div class="option-input-item">
                <span class="input-icon change-pwd-icon"></span>
                <input class="input-ele" type="password" placeholder="请输入旧密码" v-model="oldPwd"/>
              </div>
              <div class="option-input-item">
                <span class="input-icon change-pwd-icon"></span>
                <input class="input-ele" type="password" placeholder="输入新密码" v-model="newPwd"/>
              </div>
              <div class="option-input-item">
                <span class="input-icon change-pwd-icon"></span>
                <input class="input-ele" type="password" placeholder="再次输入新密码" v-model="newPwdComfirm"/>
              </div>
              <div class="person-dialog-submit-button" @click="changePasswordSubmit">提交</div>
            </div>
          </div>
          <div class="person-dialog-right person-dialog-content" v-if="personInfoActive">
            <div class="option-input-area">
              <el-form label-position="right" style="position: relative;" label-width="130px" ref="userInfoForm" :model="userInfoForm" :rules="userInfoRules">
                <el-form-item label="头像：">
                  <el-upload
                    name="file"
                    class="avatar-uploader"
                    :action="importFileUrl"
                    :show-file-list="false"
                    :on-success="handleAvatarSuccess"
                    :before-upload="beforeAvatarUpload">
                    <img v-if="userInfoForm.avatar" :src="userInfoForm.avatar" class="avatar">
                    <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                  </el-upload>
                </el-form-item>
                <el-form-item label="真实姓名：">
                  <el-input v-model="userInfoForm.realName"></el-input>
                </el-form-item>
                <el-form-item label="用户名：">
                  <el-input v-model="userInfoForm.username" disabled class="user-name"> </el-input>
                </el-form-item>
                <el-form-item label="证件类型：" prop="papersType">
                  <el-select v-model="userInfoForm.papersType" placeholder="请选择证件类型">
                    <el-option
                      v-for="item in papersType"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                    </el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="证件号码：" prop="papersNo">
                  <el-input v-model="userInfoForm.papersNo"></el-input>
                </el-form-item>
                <el-form-item label="手机号码：">
                  <el-input v-model="userInfoForm.mobile"></el-input>
                </el-form-item>
                <el-form-item label="邮箱：">
                  <el-input v-model="userInfoForm.email"></el-input>
                </el-form-item>
                <div class="person-dialog-submit-button change-person-info" @click="changeUserInfoSubmit">更新个人信息</div>
              </el-form>
            </div>
          </div>
          <div class="person-dialog-right message-card-wrap" v-if="messageWarnActive">
            <div class="message-input-area message-card-area" v-if="messageListShow">
              <div v-for="item in userMsgListData" :key="item.id" @click="readUserMsg(item.id)">
                <el-col :span="24">
                  <el-card :body-style="{ padding: '0px' }" shadow="hover">
                    <div style="padding: 14px;">
                      <div class="bottom">
                        <span class="tit"><i :class="item.readStatus ? '' :'active'"></i>{{item.typeName}}</span>
                        <time class="time">{{item.createAt}}</time>
                      </div>
                      <span class="title">{{item.title}}</span>
                    </div>
                  </el-card>
                </el-col>
              </div>
              <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page="pageInfo.pageNum"
                :page-sizes="[5, 10, 20, 50]"
                :page-size="5"
                layout="total, sizes, prev, pager, next, jumper"
                :total="pageInfo.total">
              </el-pagination>
            </div>
            <div class="message-input-area message-detail-area" v-show="messageDetailShow">
              <el-row>
                <el-col :span="24">
                  <el-card :body-style="{ padding: '0px' }">
                    <div style="padding: 14px;">
                      <div class="bottom">
                        <span class="tit">{{detailList.typeName}}</span>
                        <time class="time">{{detailList.createAt}}</time>
                      </div>
                      <span class="title">{{detailList.title}}</span>
                      <div class="msg-detail">
                        <div v-if="applyExtension !== null">
                          <p>家长姓名：{{applyExtension.parentRealName}}</p>
                          <p>家长证件类型：{{applyExtension.parentPapersType ? '护照' : '身份证'}}</p>
                          <p v-if="applyExtension.parentPapersType === 0">家长证件号码：{{applyExtension.parentIdCard}}</p>
                          <p v-else>家长证件号码：{{applyExtension.parentPassport}}</p>
                          <p>关系：{{applyExtension.relationTypeName}}</p>
                          <p>学生姓名：{{applyExtension.childRealName}}</p>
                          <template v-if="mainSchoolName !== 'NJNHSX'">
                            <p>学生证件类型：{{applyExtension.childPapersType ? '护照' : '身份证'}}</p>
                            <p v-if="applyExtension.childPapersType === 0">学生证件号码：{{applyExtension.childIdCard}}</p>
                            <p v-else>学生证件号码：{{applyExtension.childPassport}}</p>
                          </template>
                          <template v-if="mainSchoolName === 'NJNHSX'">
                            <p>学生学籍辅号：{{applyExtension.studentNo}}</p>
                          </template>
                        </div>
                        <p v-for="(item,index) in requireInput" :key="index">{{item.key}}：
                          <span v-if="item.type !== 1">{{item.value}}</span>
                          <a v-else :href="item.value" class="link_color" download target="_blank">点击下载（文件）</a>
                        </p>
                      </div>
                    </div>
                  </el-card>
                </el-col>
              </el-row>
              <i class="el-icon-back back" @click="back"></i>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>


</template>

<script>
  import defaultSettings from '@/settings'
  import {logout, updatePwd, updateUserInfo, userMsgList, unreadMsgCount, readUserMsg, unreadMsgCountNew} from "./api/header"
  import { mapGetters } from 'vuex'
  import store from '@/store'
  export default {
    name: 'Header',
    computed: {
      ...mapGetters([
        'userInfo'
      ]),
    },
    props: {
      customWidth : {
        type: String,
        default: ''
      },
      showRole : {
        type : Boolean,
        default : true
      },
      // schoolName: String
    },
    data() {
      return {
        appName : defaultSettings.title,
        importFileUrl: '',
        schoolName : localStorage.getItem("schoolName"),
        studentName : localStorage.getItem("studentName"),
        changeChildDialog : false,
        personDialog : false,
        hoverHeaderDialog : false,
        changePasswordPart: true,
        personInfoPart: false,
        oldPwd: '',
        newPwd: '',
        newPwdComfirm: '',
        errorTitle: '',
        errorShow: false,
        pwdActive: true,
        personInfoActive: false,
        messageWarnActive: false,
        userMsgListData: [],
        messageListShow: true,
        messageDetailShow: false,
        schoolId : null,
        roleNameStr: null,
        requireInput: [],
        applyExtension: null,
        detailList: {},
        unreadMsgCount: 0,
        papersType : [
          {label : "身份证", value : 0},
          {label : "护照", value : 1},
        ],
        userInfoForm : {
          username : "",
          realName : "",
          papersType : null,
          papersNo : '',
          mobile : '',
          email : '',
          avatar: '',
        },
        pageInfo: {
          pageNum: 1,
          pageSize: 5,
          total: 0
        },
        userInfoRules : {
          papersType : [
            { required: true, message: '请选择证件类型', trigger: 'blur' },
          ],
          papersNo : [
            { required: true, message: '请输入证件号码', trigger: 'blur' },
          ],
        },
        mainSchoolName : sessionStorage.getItem("mainSchoolName")

      }
    },
    mounted() {
      document.addEventListener("click", (e)=>{
        this.hoverHeaderDialog = false;
      });
      // this.unreadMsgCountAction();
      // clearInterval(window.intervalId);
      // window.intervalId = setInterval(this.unreadMsgCountAction, 120000);

      this.importFileUrl = process.env.VUE_APP_PMS_API+"/busi/upload/img?code=1";
    },
    methods: {
      openCfMessageCenter() {
        let schoolId = sessionStorage.getItem('schoolId')
        let url = process.env.VUE_APP_CF_MESSAGE_CENTER
        window.open(url + "?schoolId="+schoolId + "/#/message/list")


      },
      toHomePage() {
        window.open("/dt");
      },
      handleAvatarSuccess(res, file) {
        this.userInfoForm.avatar = res.result;
      },
      beforeAvatarUpload(file) {
        const isLt300K = file.size / 1024 / 1024 < 0.3;
        if (!isLt300K) {
          this.$alert('上传头像图片大小不能超过300k!', "提示", {type : "warning"});
        }
        return isLt300K;
      },
      userMsgList(id) {
        let params = {
          pageNum: this.pageInfo.pageNum,
          pageSize: this.pageInfo.pageSize,
        }
        userMsgList(params).then((res) => {
          if (res.code === 200) {
            let list = res.result.list;
            this.userMsgListData = list;
            this.pageInfo.total = res.result.total;
            if(id){
              let detailList = list.filter((item,index)=>{
                if(item.id === id){
                  return item;
                }
              })
              this.detailList = detailList[0];
              this.requireInput = JSON.parse(detailList[0].registerApply.requireInput);
              if(detailList[0].registerApply.applyExtension !== null){
                this.applyExtension = detailList[0].registerApply.applyExtension;
              }
            }
          } else {
            this.errorShow = true;
            this.errorTitle = res.message;
          }
        });
      },
      getUserDetail(id){
        this.messageListShow = false;
        this.messageDetailShow = true;
        this.userMsgList(id);
      },
      back(){
        this.messageListShow = true;
        this.messageDetailShow = false;
      },
      handleSizeChange(val) {
        this.pageInfo.pageSize = val;
        this.userMsgList();
      },
      handleCurrentChange(val) {
        this.pageInfo.pageNum = val;
        this.userMsgList();
      },
      changeUserInfoSubmit() {
        this.$refs.userInfoForm.validate((valid) => {
          if (valid) {
            updateUserInfo(this.userInfoForm).then(res => {
              if (res.code === 200) {
                this.personDialog = false;
                this.$message.success("更新成功！");
                store.dispatch('GetInfo').then(() => {

                });
              } else {
                this.$alert("更新失败！" + res.message, "错误", {type : "error"});
              }
            });
          } else {
            this.$alert("输入有误，请检查", "提示", {type : "warning"});
          }
        });
      },

      exit(){
        this.$confirm('是否退出本次登录?', '退出登录', {
          confirmButtonText: '是',
          cancelButtonText: '否',
          type: 'warning',
          customClass: 'exit-box'
        }).then(() => {
          logout().then((res) => {
            if (res.code === 200) {
              window.location.href = res.result + "?service=" + process.env.VUE_APP_DT_HOME;
            } else {
              this.$message.error(res.message);
            }
          });
        })
      },
      unreadMsgCountAction(){
        // unreadMsgCount().then((res) => {
        //   if (res.code === 200) {
        //     this.unreadMsgCount = res.result;
        //   }
        // });
        let params = {
          userId : this.userInfo.id,
          schoolId : sessionStorage.getItem('schoolId')
        }
        unreadMsgCountNew(params).then(res => {
            this.unreadMsgCount = res.result > 99 ? "99+" : res.result
        })
      },
      hideHeaderDialog(e){
        e.stopPropagation();
      },
      initChangePassword() {
        this.errorShow = false,
          this.oldPwd = '',
          this.newPwd = '',
          this.newPwdComfirm = ''
      },
      changePassword(){
        this.initChangePassword();
        this.pwdActive = true;
        this.personInfoActive = false;
        this.messageWarnActive = false;
        this.changePasswordPart = true;
        this.personInfoPart = false;
      },
      personInfo(){
        this.pwdActive = false;
        this.personInfoActive = true;
        this.messageWarnActive = false;
        this.changePasswordPart = false;
        this.personInfoPart = true;
        this.userInfoForm.realName = this.userInfo.realName;
        this.userInfoForm.username = this.userInfo.username;
        this.userInfoForm.papersType = this.userInfo.papersType;
        this.userInfoForm.papersNo = (this.userInfo.papersType === 0 ? this.userInfo.idCard : this.userInfo.papersType === 1 ? this.userInfo.passport : "");
        this.userInfoForm.mobile = this.userInfo.mobile;
        this.userInfoForm.email = this.userInfo.email;
        this.userInfoForm.avatar = this.userInfo.avatar;
      },
      /*messageWarn(){
        this.pwdActive = false;
        this.personInfoActive = false;
        this.messageWarnActive = true;
        this.messageListShow = true;
        this.messageDetailShow = false;
        this.userMsgList();
      },*/
      changePasswordSubmit(){
        let reg = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$/;
        if(this.strTrim(this.oldPwd) === ''){
          this.errorShow = true;
          this.errorTitle = "请输入旧密码！"
        }else if(this.strTrim(this.newPwd) === ''){
          this.errorShow = true;
          this.errorTitle = "请输入新密码！"
        }else if(!reg.test(this.newPwd)){
          this.errorShow = true;
          this.errorTitle = "密码为6-16位数字和字母的组合！"
        }else if(this.strTrim(this.newPwdComfirm) === ''){
          this.errorShow = true;
          this.errorTitle = "请再次输入新密码！"
        }else if(this.newPwd !== this.newPwdComfirm){
          this.errorShow = true;
          this.errorTitle = "两次新密码输入不一致！"
        }else{
          this.errorShow = false;
          let params = {
            password: this.oldPwd,
            newPassword: this.newPwd
          }
          updatePwd(params).then((res) => {
            if (res.code === 200) {
              this.personDialog = false;
              this.errorShow = false;
              this.initChangePassword()
              this.$message.success("密码更新成功！")
            } else {
              this.errorShow = true;
              this.errorTitle = res.message;
            }
          });
        }
      },
      readUserMsg(id){
        let params = {
          id: id,
        }
        readUserMsg(params).then((res) => {
          if (res.code === 200) {
            this.unreadMsgCountAction();
            this.getUserDetail(id);
          } else {
            this.errorShow = true;
            this.errorTitle = res.message;
          }
        });
      },
      changePwd(){
        this.initChangePassword();
        this.errorShow = false;
        this.personDialog = true;
        this.pwdActive = true;
        this.personInfoActive = false;
        this.messageWarnActive = false;
      },
      personMeterial(){
        this.personDialog = true;
        this.pwdActive = false;
        this.personInfoActive = true;
        this.messageWarnActive = false;
        this.personInfo();
      },
      messageWarn(){
        this.personDialog = true;
        this.pwdActive = false;
        this.personInfoActive = false;
        this.messageWarnActive = true;
        this.userMsgList();
      },
      //去除字符串前后所有空格
      strTrim(str) {
        return str.replace(/(^\s*)|(\s*$)/g, "");
      }
    }
  }
</script>
<style>
  .el-badge__content {
    border: none !important;
  }
</style>
<style lang="scss" scoped>
 .common-head-area{
   position:absolute;right:0;
   top: 0;z-index:100;
 }

 .home-house{
   background: url("./assets/home-house.png") no-repeat;
   background-position: 18px 12px;
   width: 50px;height: 50px;display: inline-block;cursor: pointer;
   margin-right: 24px;
 }
  .fr{
    display: inline-block;
    vertical-align: bottom;
    a{
      &:hover{
        text-decoration: underline;
      }
      &.name{
        margin-right:20px;
      }
      &.exit{
        margin-right: 50px;
      }
    }
  }
  .user-info-head{
    height: 54px;
    line-height: 54px;
    display: inline-block;
    margin-right: 54px;
    margin-left: 30px;
    cursor: pointer;
    color: #fff;
  }
  .gear{
    width: 16px;height: 16px;display: inline-block;margin-right: 36px;margin-left: 15px;
    background: url("./assets/gear.png") no-repeat;
    background-size: 100% 100%;
    cursor: pointer;
  }
  .cross-arrow{
    background: url("./assets/double-arrow.png") no-repeat;
    background-size: 100% 100%;
    width: 22px;height: 24px;display:inline-block;
    vertical-align: middle;
    margin-right: 5px;
  }
  .change-children-button{
    display: inline-block;height: 40px;padding : 0 15px;border-radius: 5px;line-height: 40px;font-size: 16px;
    cursor: pointer;
    margin-right: 10px;
  }
  .change-children-button a{
    text-decoration:underline;
  }
  .change-role-button{
    display: inline-block;height: 40px;border-radius: 5px;line-height: 40px;font-size: 16px;
    cursor: pointer;
    padding: 0 15px;
  }
  .change-role-button a{
    text-decoration:underline;
  }
  .header-icon-bg{
    background: #e8e8e8;
    display:inline-block;width: 36px;height: 36px;border-radius: 50%;position: relative;top: 9px;
    position: relative;
  }
  .default-header{
    height: 100%;width: 100%;border-radius: 50%;
  }
  .user-name-area{
    font-size: 15px;
   line-height: 100%;margin-left: 7px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;display: inline-block;
  }
  .option-check-item{
    height: 44px;line-height: 44px;font-size: 15px;cursor: pointer;
    color: #222222;
    padding-left: 20px;
  }
  .change-pwd-option-area .option-check-icon{
    width: 21px;height: 30px;background: #67c23a;margin-right: 14px;display: inline-block;vertical-align: middle;
    background: url("./assets/icon_mima_n.png") no-repeat 0px 2px;
  }
  .person-option-area .option-check-icon{
    width: 21px;height: 30px;background: #67c23a;margin-right: 14px;display: inline-block;vertical-align: middle;
    background: url("./assets/icon_geren_n.png") no-repeat 0px 2px;
  }
  .message-option-area .option-check-icon{
    width: 21px;height: 30px;background: #67c23a;margin-right: 14px;display: inline-block;vertical-align: middle;
    background: url("./assets/icon_xiaoxi_n.png") no-repeat 0px 3px;
  }
  .exit-option-area .option-check-icon{
    width: 21px;height: 30px;background: #67c23a;margin-right: 14px;display: inline-block;vertical-align: middle;
    background: url("./assets/exit-option.png") no-repeat 0px 3px;
  }
  .change-pwd-option-area:hover .option-check-icon{
    width: 21px;height: 30px;background: #67c23a;margin-right: 14px;display: inline-block;vertical-align: middle;
    background: url("./assets/icon_mima_s.png") no-repeat 0px 2px;
  }
  .person-option-area:hover .option-check-icon{
    width: 21px;height: 30px;background: #67c23a;margin-right: 14px;display: inline-block;vertical-align: middle;
    background: url("./assets/icon_geren_s.png") no-repeat 0px 2px;
  }
  .message-option-area:hover .option-check-icon{
    width: 21px;height: 30px;background: #67c23a;margin-right: 14px;display: inline-block;vertical-align: middle;
    background: url("./assets/icon_xiaoxi_s.png") no-repeat 0px 3px;
  }
  .exit-option-area:hover .option-check-icon{
    width: 21px;height: 30px;background: #67c23a;margin-right: 14px;display: inline-block;vertical-align: middle;
    background: url("./assets/exit-option-checked.png") no-repeat 0px 3px;
  }

  .option-check-item:hover, .option-check-item.active{
    background:  #d2eded;
    color: #02b2b5;
  }
 .option-check-item.active .option-check-icon{
   width: 21px;height: 30px;background: #67c23a;margin-right: 14px;display: inline-block;vertical-align: middle;
   background: url("./assets/icon_xiaoxi_s.png") no-repeat 0px 3px;
 }
  .change-pwd-option-area.active .option-check-icon{
    width: 21px;height: 30px;background: #67c23a;margin-right: 14px;display: inline-block;vertical-align: middle;
    background: url("./assets/icon_mima_s.png") no-repeat 0px 2px;
  }
  .person-option-area.active .option-check-icon{
    width: 21px;height: 30px;background: #67c23a;margin-right: 14px;display: inline-block;vertical-align: middle;
    background: url("./assets/icon_geren_s.png") no-repeat 0px 2px;
  }
  .exit-option-area.active .option-check-icon{
    width: 21px;height: 30px;background: #67c23a;margin-right: 14px;display: inline-block;vertical-align: middle;
    background: url("./assets/exit-option-checked.png") no-repeat 0px 3px;
  }
  .option-input-item{
    height: 37px;border: 1px solid RGB(213,213,213);border-radius: 5px;background: #fff;margin-bottom: 24px;
  }
  .option-input-area{
    width: 44%;vertical-align: middle;display: inline-block;margin:auto;
  }
  .person-dialog-content  .option-input-area{
    width: 68%;
    margin-left: 38px;
  }
  .input-icon{
    width: 35px;display: inline-block;
    height: 100%;
  }
  .input-ele{
    outline: none;vertical-align:top;font-size: 14px;text-indent: 10px;
    width: calc(100% - 49px);
    height: 100%;
    border: 0px solid #000;
  }
  .person-dialog{
    width: 676px;
    height: 550px;
    position: fixed;z-index:99999;background: #fff;top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    margin: auto;
    border-radius: 5px;
    overflow: hidden;
  }
  .person-dialog-left{
    height: 100%;float: left;width:25%;
    background-color: #f7f7f7;
  }
  .person-dialog-header-area {
    background: url("./assets/header-bg.png") no-repeat;
    background-size: 100% 100%;
    height: 41%;width: 100%;overflow: auto;text-align: center;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  .header-white-bg{
    background: #fff;width: 126px;height: 126px;border-radius: 63px;margin: 2vh auto 0;
  }
  .header-avatar{
    width: 120px;height: 120px;border-radius: 60px;position: relative;top: 3px;left: 3px;
  }
  .header-info-name{
    color: #fff;font-size: 20px;margin-top: 10px;
  }
  .header-info-role {
    color:#fff;font-size: 16px;margin-bottom: 1vh;line-height: 18px;
  }
  .options-check-area{
    height: 59%;width: 100%;background: #f7f7f7;overflow: auto;
  }
  .close-button{
    cursor: pointer;border-radius: 29px;position: absolute; right: 18px;z-index: 10;top: 13px;
    color: #666;
    font-size: 18px;
  }
  .person-dialog-right{
    height: 100%;float: left;width: 100%;background: #fff;line-height: 100%; position:relative;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  .person-dialog-content{
    height: 100%;
    padding-top: 28px;
  }
  .message-card-wrap{
    align-items: flex-start;
  }
  .person-dialog-submit-button{
    background:#02b2b5; height: 37px;border-radius: 5px;text-align: center;line-height: 37px;font-size: 15px;color: #fff;cursor: pointer;
    margin-bottom: 60px;
  }
  .person-dialog-submit-button.change-person-info{
    width: calc(100% - 130px);
    margin-left: 130px;
  }
  .hh-header-area{
    background: url("./assets/hover-header-bg.png") no-repeat;
    background-size: 100% 100%;
    height: 178px;width: 100%;text-align: left;padding: 20px;border-top-left-radius: 10px;border-top-right-radius: 10px;
  }
  .exit-icon{
    background: url("./assets/exit-icon.png") no-repeat;
    background-size: 100% 100%;
    width: 32px;height: 32px;display: inline-block;vertical-align: middle;margin-right: 10px;
  }
  .hover-header-dialog{
    position: absolute;display: inline-block;width: 146px;right: 56px;
    overflow: hidden;
    background-color: #ffffff;
    box-shadow: 0px 0px 29px 0px
    rgba(0, 0, 0, 0.09);
    padding: 8px 0;
  }

  .hover-header-dialog ul{
    width: 100%;
  }
  .hover-header-dialog ul li{
    width: 100%;
    height: 33px;
    line-height: 33px;
    margin-bottom: 1px;
    padding-left: 58px;
    text-align: left;
    box-sizing:border-box;
    -moz-box-sizing:border-box; /* Firefox */
    -webkit-box-sizing:border-box; /* Safari */
    position: relative;
  }
  .hover-header-dialog ul li:nth-child(4){
    margin-bottom: 0px;
  }
  .hover-header-dialog ul li .icon{
    height: 28px;
    position: absolute;
    top: 2px;
    left: 26px;
  }
  .hh-header-avatar-bg{
    width: 140px;height: 140px;background: #fff;border-radius: 50%;float: left;border: 2px solid #fff;
  }
  .hh-user-info-area{
    float: left;height: 60px;width:220px;margin-top: 39px; margin-left: 20px;color: #fff;
  }
  .hh-user-info-name{
    font-size: 28px;line-height: 30px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;
  }
  .hh-user-info-role{
    font-size: 16px;line-height: 18px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;
  }
  .hh-login-time-area{
    height: 86px;width: 100%;background: rgb(248,248,244);padding: 16px 26px;
  }
  .hh-login-time{
    border-left: 1px solid rgb(227,226,221);border-right: 1px solid rgb(227,226,221);height: 52px;width: 100%;color: rgb(148,132,110);line-height: 28px;font-size: 16px;text-align: left;text-indent: 20px;
  }
  .hh-bottom-area{
    background: #fff;height: 92px;width: 100%;color: #fff;padding-top: 20px;padding-bottom: 10px;border-bottom-left-radius: 10px;border-bottom-right-radius: 10px;
  }
  .hh-button{
    width: 200px;display: inline-block;border-radius: 3px;cursor: pointer;
  }
  .change-pwd-icon{
    background: url("./assets/grey-pwd.png") no-repeat 17px 11px;
  }
  input.input-ele::-webkit-input-placeholder {
    color: #999999;
    font-size: 14px;
  }
  input.input-ele:-moz-placeholder {
    color: #999999;
    font-size: 14px;
  }
  input.input-ele:-ms-input-placeholder {
    color: #999999;
    font-size: 14px;
  }
  .hover-header{
    cursor: pointer;
  }

  .head-arrow{
    position: relative;
    top: -2px;
  }

  .person-dialog-wrap{
    width: 100%;
    height: 100%;
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background-color: rgba(0,0,0,.7);
    z-index: 1000;
  }
  .option-input-area .el-alert--error{
    background-color: #fff;
    border: 1px solid #f56c6c;
    margin-bottom: 20px;
  }
 /* .avatar-header{
    position:relative;
    margin-top: -20px;
  }*/
  .user-info-text{
    font-size: 20px;
  }
  .message-input-area{
    padding: 70px 35px 0;
    padding-top: 0px;
    margin-top: 45px;
  }
  .message-card-area{
    width: 100%;
    height: 83%;
  }
  .message-input-area .title{
    display: block;
    font-size: 14px;
    color: #666666;
  }
  .message-input-area .msg-detail p{
    line-height: 24px;
    font-size: 16px;
  }
  .message-input-area .bottom{
    display: flex;
    justify-content: space-between;
    font-size:14px;
    margin-bottom: 11px;
  }
  .message-input-area .bottom .tit{
    font-size: 16px;
    color: #222;
    display: flex;
  }
  .message-input-area .bottom .tit i{
    display: inline-block;
    width: 7px;
    height: 7px;
    background-color: rgba(0, 0, 0, 0.15);
    border-radius: 50%;
    margin-right: 5px;
    margin-top: 3px;
  }
  .message-input-area .bottom .tit i.active{
    background-color: #FF0000;
    margin-top: 3px;
  }
  .message-input-area .bottom .time{
    color: #999999;
    font-size: 14px;
  }
  .message-detail-area{
    height: 83%;
    position: relative;
    width: 100%;
  }
  .message-detail-area .el-row{
    height: 100%;
  }
  .message-detail-area .el-col-24{
    height: 100%;
  }
  .message-detail-area .el-card{
    height: 100%;
    overflow-y: auto;
  }
  .message-detail-area .back{
    position: absolute;
    top: -33px;
    left: 37px;
    font-size: 24px;
    cursor: pointer;
  }
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 48px;
    height:48px;
    line-height: 48px;
    text-align: center;
    border: 1px dashed #ccc;
  }
  .avatar {
    width: 80px;
    height: 80px;
    display: block;
  }
  /*.change-pwd-
  .option-ara>span{
    background: url("./assets/cpwd-option.png") no-repeat;
  }
  .change-pwd-option-area:hover > .change-pwd-option-icon-checked>span{
    background: url("./assets/cpwd-option-checked.png") no-repeat;
  }*/


 .hover-header-dialog{
   background: #fff;
 }
 .hover-header-dialog ul li{
   font-size: 14px;
   color: #444444;
 }
 .hover-header-dialog ul li:hover{
   color: #02b2b5;
 }
 .hover-header-dialog ul li .icon{
   background: url("./assets/icon_xiaoxi_n.png") no-repeat center;
   width: 22px;
 }
 .hover-header-dialog ul li:hover .icon{
   background: url("./assets/icon_xiaoxi_s.png") no-repeat center;
 }
 .hover-header-dialog ul li:nth-child(2) .icon{
   background: url("./assets/icon_geren_n.png") no-repeat center;
   width: 26px;
 }
 .hover-header-dialog ul li:nth-child(2):hover .icon{
   background: url("./assets/icon_geren_s.png") no-repeat center;
 }
 .hover-header-dialog ul li:nth-child(3) .icon{
   background: url("./assets/icon_mima_n.png") no-repeat center;
   width: 26px;
 }
 .hover-header-dialog ul li:nth-child(3):hover .icon{
   background: url("./assets/icon_mima_s.png") no-repeat center;
 }
 .hover-header-dialog ul li:nth-child(4) .icon{
   background: url("./assets/icon_tuichu_n.png") no-repeat center;
   width: 26px;
 }
 .hover-header-dialog ul li:nth-child(4):hover .icon{
   background: url("./assets/icon_tuichu_s.png") no-repeat center;
 }
 .home-container .container-body .school-list .school-header .icon-wrap span{
   color: #403635;
 }
 .home-container .container-body .school-list .school-header .icon-wrap .posi-icon i{
   color: #799af4;
 }
 .home-container .container-body .school-list .school-area .school-items-list .school-item{
   background-color: #78a2ff;
   color: #fff;
 }
 .home-container .container-body .school-list .school-area .school-items-list .school-item:hover{
   background-color: #a77afd;
   color: #fff;
 }
 .home-container .container-body .school-list .school-header .back-index{
   background-color: #78a2ff;
 }
 .home-container .container-body .school-list .school-header .back-index:hover{
   background-color: #a77afd;
 }

 ul,ol{list-style-type:none;}
ul {
  padding-inline-start:0px!important;
}
  .container{
    height: 54px;width: 100%;background: #02b2b5;
  }
  .appinfo-area{
    line-height: 54px;display: inline-block;height: 54px;padding: 0 24px;font-size: 20px;color: #fff;
  }
 .avatar {
   width: 40px;
   height: 40px;
   display: block;
 }
 .hover-header-dialog{
   top: 56px;
 }
  .hover-header-dialog ul{
    padding: 0;
  }
 .option-check-item span.txt{
   margin-left: -4px;
 }
</style>

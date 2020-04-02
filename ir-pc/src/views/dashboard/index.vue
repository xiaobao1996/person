<template>
  <div class="login-school-container login-school" >
    <div style="font-size: 36px;padding: 40px 100px;box-sizing:border-box;color: white;height: 10vh;">
      <div class="header-left" style="float: left;">欢迎您：{{form.realName}}</div>
      <div class="header-right" style="float: right;">
        <!--登录名：{{form.username}}-->
        <el-dropdown placement="top">
          <span class="el-dropdown-link" style="font-size: 36px;padding: 40px 100px;box-sizing:border-box;color: white;">
            登录名：{{form.username}} <i class="el-icon-caret-bottom"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item divided>
              <span @click="logout" style="display:block;padding: 0 20px;">退出</span>
            </el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
        <!--登录名：{{form.username}}
        <div style="display: inline-block;"><i class="el-icon-caret-bottom" ></i></div>-->
      </div>
    </div>
    <div style="font-size: 20px;color: #fff;margin: 20px 40px 10px ">请选中一个学校进行登录：</div>
    <div style="background: #f0f0f0;margin: 0 40px;height: 80vh;border-radius: 10px;padding: 20px;">
      <div v-for="item in form.schoolRoleVoList" class="school-item" @click="gotoSchool(item)">
        <div>
          <span>学校名称：</span><span style="color: blueviolet">{{item.schoolName}}</span>
        </div>
        <div style="margin-top: 10px;">
          <span>角色：</span><span style="color: blueviolet">{{item.roleNameList.join(",")}}</span>
        </div>
        <div style="margin-top: 10px;">
          <span>到期时间：</span><span style="color: blueviolet">{{item.expiredTime}}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import {userSchoolList,logout} from '@/api/login'
  import {getSchoolId} from '@/utils/index'
  import store from '@/store'
  import {constantRouterMap} from "@/router/index"
  import { mapGetters } from 'vuex'
  export default {
    name: 'login-school',
    data() {
      return {
        form : {}
      }
    },
    computed: {
      ...mapGetters([
        'visitHomeCount'
      ]),
    },
    methods: {
      gotoSchool(item) {
        if (item.expired) {
          this.$message.error("应用已过期，无法使用！");
          return;
        }
        sessionStorage.setItem("schoolId", item.schoolId);
        this.$router.push("/home");
      },
      fetchData() {
        userSchoolList().then((res) => {
          if (res.code === 200) {
            this.form = res.result;
          } else {
            this.$message.error(res.message);
          }
        });
      },
    logout() {
      logout().then((res) => {
        if (res.code === 200) {
          //sso单点登出
          window.location = res.result;
        } else {
          this.$message.error(res.message)
        }
      })
    }

    },
    mounted() {
      store.dispatch('PermissionGotten', false);
      if (this.visitHomeCount > 0) {
        location.reload();
      }
      store.dispatch('SET_VISITHOMECOUNT', 1);
      let schoolId = getSchoolId();
      if (schoolId && schoolId.trim() !== '') {
        sessionStorage.setItem("schoolId", schoolId);
        this.$router.push("/home");
      } else {
        this.fetchData();
      }
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  .login-school-container{
    background: #2d3a4b;
    min-height: 100vh;
  }
  .school-item{
    cursor:pointer;
    float: left;
    height: 150px;
    width: 400px;
    background: #fff;
    border-radius:8px;
    padding: 20px;
    font-size: 20px;
    line-height: 30px;
    font-weight: 600;
    margin-right: 20px;
  }
  .school-item:hover{
    background: #2d3a4b;
  }
</style>

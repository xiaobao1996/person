<template>
  <el-dialog  :title="title" :visible.sync="checkInUserDialog" top="2vh" width="1000px">
    <div class="filter-container">
      <el-input style="width: 200px;" class="filter-item" placeholder="姓名" v-model="listQuery.realName" size="small" @keyup.enter.native="fetchData(true)">
      </el-input>
      <el-select clearable class="filter-item" style="width: 200px" v-model="listQuery.roleId" placeholder="角色" size="small" filterable  @change="fetchData(true)">
        <el-option v-for="item in roleList" :key="item.id" :label="item.roleName" :value="item.id">
        </el-option>
      </el-select>
      <el-checkbox v-model="listQuery.filterChecked" size="small" @change="fetchData(true)">过滤已选的用户</el-checkbox>
      <el-button class="filter-item" type="primary" v-waves icon="el-icon-search" size="small" @click="fetchData(true)">查询</el-button>
      <el-button class="filter-item" icon="el-icon-remove-outline" size="small" @click="initListQuery">清除</el-button>
    </div>
    <span>您已选中<span style="color: red;">{{checkedUserData.length}}</span>位人员</span>
      <el-table
        :data="tableData"
        highlight-current-row
        style="width: 100%"
        size="small"
        @selection-change="userChecked">
        <el-table-column
          type="selection"
          width="55">
        </el-table-column>
        <el-table-column label="真实姓名" prop="realName">
        </el-table-column>
        <el-table-column label="用户名"  prop="username">
        </el-table-column>
        <el-table-column label="手机号"  prop="mobile">
        </el-table-column>
        <el-table-column label="邮箱" prop="email">
        </el-table-column>
        <el-table-column label="证件类型">
          <template slot-scope="scope">
            <span>{{scope.row.papersType === 0 ? "身份证" : scope.row.papersType === 1 ? "护照" : ""}}</span>
          </template>
        </el-table-column>
        <el-table-column label="证件号码">
          <template slot-scope="scope">
            <span>{{scope.row.papersType === 0 ? scope.row.idCard : scope.row.papersType === 1 ? scope.row.passport : ""}}</span>
          </template>
        </el-table-column>
        <el-table-column label="角色"  prop="roleNamesStr">
        </el-table-column>
        <el-table-column label="状态" >
          <template slot-scope="scope">
            <span>{{scope.row.status === 1 ? "正常" : "停用"}}</span>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageInfo.pageNum"
        :page-sizes="[5, 10, 20, 50]"
        :page-size="pageInfo.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="pageInfo.total">
      </el-pagination>
      <div style="margin-top: 22px;text-align: center;">
        <el-button v-if="checkedUserData && checkedUserData.length && checkedUserData.length > 0" type="primary" @click="confirmClick(checkedUserData)">选入</el-button>
        <el-button v-else type="primary" style="color: #99a9bf" disabled>选入</el-button>
      </div>
  </el-dialog>
</template>
<script>
  import {checkInSchoolUserList, roleListAll} from "@/api/api"
  import waves from '@/directive/waves'

  export default {
    directives: {waves},
    name: 'chooseApp',
    props: {
      title : {
        type : String,
        default : "选入人员"
      },
      schoolId : {
        type: String,
        required : true,
        default: ""
      },
      officeId : {
        type: String,
        required : true,
        default: ""
      },
      confirmClick : {
        type: Function,
        required : true,
      }
    },
    data() {
      return {
        checkInUserDialog : false,
        tableData : [],
        listQuery: {
          realName: '',
          roleId : '',
          filterChecked : false
        },
        pageInfo: {
          pageNum: 1,
          pageSize: 10,
          total: 0
        },
        roleList : [],
        checkedUserData : []
      }
    },
    methods: {
      userChecked(data) {
        this.checkedUserData = data;
      },
      initListQuery() {
        this.listQuery = {
          realName: '',
          roleId : '',
          filterChecked : false
        };
        this.fetchData(true);
      },
      initPageInfo() {
        this.pageInfo.pageNum = 1
      },
      fetchData(initPageInfo) {
        if (initPageInfo) {
          this.initPageInfo();
        }
        let params = {
          schoolId : this.schoolId,
          pageNum: this.pageInfo.pageNum,
          pageSize: this.pageInfo.pageSize,
          realName : this.listQuery.realName,
          roleId : this.listQuery.roleId
        };
        if (this.listQuery.filterChecked && this.officeId) {
          params.filterOfficeId = this.officeId;
        }
        checkInSchoolUserList(params).then(res => {
          if (res.code === 200) {
            if (res.result) {
              this.tableData = res.result.list;
              this.pageInfo.total = res.result.total;
            }
          } else {
            this.$message.error("获取用户信息失败！");
          }
        })
      },
      getRoleList() {
        roleListAll({schoolId : this.schoolId}).then(res => {
          if (res.code === 200) {
            this.roleList = res.result;
          } else {
            this.$message.error("获取角色信息失败！");
          }
        });
      },
      //显示组建
      showComponent() {
        this.initListQuery();
        this.checkInUserDialog = true;
      },
      closeComponent() {
        this.checkInUserDialog = false;
        this.initData();
      },
      initData() {
        this.tableData = [];
        this.pageInfo = {
          pageNum: 1,
          pageSize: 10,
          total: 0
        };
      },
      handleSizeChange(val) {
        let oldVal = this.pageInfo.pageSize;
        this.pageInfo.pageSize = val;
        if (this.checkedUserData && this.checkedUserData.length && this.checkedUserData.length > 0) {
          this.$message.warning("请将当前页面选中的用户先选入，否则会丢失已选择的数据！");
          this.$nextTick(() => {
            this.pageInfo.pageSize = oldVal;
          });
          return false;
        }
        this.fetchData();
      },
      handleCurrentChange(val) {
        let oldVal = this.pageInfo.pageNum;
        this.pageInfo.pageNum = val
        if (this.checkedUserData && this.checkedUserData.length && this.checkedUserData.length > 0) {
          this.$message.warning("请将当前页面选中的用户先选入，否则切换页面后会丢失已选择的数据！");
          this.$nextTick(() => {
            this.pageInfo.pageNum = oldVal;
          });
          return false;
        }
        this.fetchData();
      }
    },
    created() {
      this.getRoleList();
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>

</style>

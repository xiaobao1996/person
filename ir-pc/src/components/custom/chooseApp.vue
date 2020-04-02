<template>
  <el-dialog v-el-drag-dialog title="选择应用" :visible.sync="addAppDialog">
    <div class="filter-container">
      <el-input style="width: 200px;" class="filter-item" placeholder="应用名称" v-model="listQuery.name">
      </el-input>
      <el-button class="filter-item" type="primary" v-waves icon="el-icon-search" @click="getAppList">查询</el-button>
      <el-table
        ref="addAppTableData"
        :data="addAppTableData"
        highlight-current-row
        @current-change="addAppChecked"
        style="width: 100%">
        <el-table-column label="应用名称" prop="name">
        </el-table-column>
        <el-table-column label="版本号" prop="version">
        </el-table-column>
        <el-table-column label="创建时间" prop="createAt">
        </el-table-column>
      </el-table>
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageInfo.pageNum"
        :page-sizes="[5, 10, 20, 50]"
        :page-size="10"
        layout="total, sizes, prev, pager, next, jumper"
        :total="pageInfo.total">
      </el-pagination>
      <div style="margin-top: 22px;margin-left: 100px;">
        <el-button v-if="checkedAddApp && checkedAddApp.id" type="primary" @click="confirmClick(checkedAddApp)">确定</el-button>
        <el-button v-else disabled>确定</el-button>
        <el-button @click="addAppDialog = false">取消</el-button>
      </div>
    </div>
  </el-dialog>
</template>
<script>
  import {getAppList} from "@/api/api"
  import elDragDialog from '@/directive/el-dragDialog'
  import waves from '@/directive/waves'

  export default {
    directives: {elDragDialog, waves},
    name: 'chooseApp',
    props: {
      confirmClick: {
        type: Function,
        default: null
      }
    },
    data() {
      return {
        addAppDialog : false,
        addAppTableData: [],
        checkedAddApp: {},
        listQuery: {
          name: ''
        },
        notInIds : [],
        pageInfo: {
          pageNum: 1,
          pageSize: 10,
          total: 0
        },
      }
    },
    methods: {
      initData() {
        this.addAppTableData = [];
        this.checkedAddApp = {};
        this.listQuery = {
          name: ''
        };
        this.pageInfo = {
          pageNum: 1,
            pageSize: 10,
            total: 0
        };
      },
      //选中一行
      addAppChecked(curRow) {
        this.checkedAddApp = curRow;
      },
      //显示组建
      showComponent(notInIds) {
        this.notInIds = notInIds;
        this.getAppList();
        this.addAppDialog = true;
      },
      closeComponent() {
        this.addAppDialog = false;
        this.initData();
      },
      getAppList() {
        let params = {};
        if (this.listQuery.name && this.listQuery.name.trim() !== '') {
          params.name = this.listQuery.name;
        }
        params.notInIds = this.notInIds.join(",");
        getAppList(params).then((res) => {
          if (res.code === 200) {
            this.addAppTableData = res.result && res.result.list ? res.result.list : [];
            this.pageInfo.total = res.result.total;
          } else {
            this.$message.error(res.message);
          }
        });
      },
      handleSizeChange(val) {
        this.pageInfo.pageSize = val
        this.getAppList();
      },
      handleCurrentChange(val) {
        this.pageInfo.pageNum = val
        this.getAppList();
      },
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>

</style>

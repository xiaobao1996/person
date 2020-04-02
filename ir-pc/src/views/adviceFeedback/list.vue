<template>
  <div class="app-container advice">
    <div class="filter-container">
      <div class="filter-block">
        <span>关键词:</span>
        <el-input size="small" v-model="listQuery.keyWord" placeholder="请输入" @keyup.enter.native="fetchData(true)"/>
      </div>
      <div class="filter-block">
        <span>类型:</span>
        <el-select clearable size="small" v-model="listQuery.typeId" placeholder="请选择" @change="fetchData(true)">
          <el-option v-for="item in typeList" :key="item.name" :label="item.name" :value="item.value"/>
        </el-select>
      </div>
      <div class="filter-block">
        <span>状态:</span>
        <el-select clearable size="small" v-model="listQuery.state" placeholder="请选择" @change="fetchData(true)">
          <el-option v-for="item in statusList" :key="item.name" :label="item.name" :value="item.value"/>
        </el-select>
      </div>
      <div class="filter-block">
        <span >日期:</span>
        <el-date-picker
          value-format="yyyy-MM"
          v-model="listQuery.date"
          type="month"
          @change="fetchData(true)"
        >
        </el-date-picker>
      </div>
      <div class="filter-btn-area">
        <el-button size="small" type="primary" @click="fetchData(true)">查询</el-button>
        <el-button size="small" type="none" @click="initListQuery">清除</el-button>
      </div>

    </div>
    <div class="calc-area" style="font-size: 14px;margin-bottom: 18px;margin-top: 30px;;color: #909399">
      共计<span style="color: #02b2b5">{{pageInfo.total}}</span>条，待回复<span style="color: #02b2b5">{{pageInfo.feedBacked}}</span>条
    </div>
    <el-table ref="form" :data="tableData"  border show-overflow-tooltip style="width: 100%">
      <el-table-column label="主题" align="center">
        <template slot-scope="scope" >
          <span class="link" @click="$router.push({path : '/adviceFeedback/detail', query : {id : scope.row.id}})"  style="overflow: hidden;text-overflow: ellipsis;white-space: nowrap;">{{scope.row.theme}}</span>
        </template>
      </el-table-column>
      <el-table-column label="时间" prop="createAt" align="center"/>
      <el-table-column label="类型" align="center">
        <template slot-scope="scope">
         {{formatTypeNameByValue(scope.row.typeId)}}
        </template>
      </el-table-column>

      <el-table-column label="状态" align="center">
        <template slot-scope="scope">
          {{scope.row.state === 0 ? "待回复" : scope.row.state === 1 ? "已反馈" : ""}}
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        show-overflow-tooltip width="200" align="center">
        <template slot-scope="scope" >
            <el-link type="primary" :underline="false"
                     @click.stop="$router.push({path : '/adviceFeedback/detail', query : {id : scope.row.id}})"
                     >详情
            </el-link>
          <template v-if="permissions.indexOf('cf:manage:reply') !== -1">
            <el-link  v-if="scope.row.state == 0"
                      type="primary"
                      :underline="false"
                      @click.stop="$router.push({path : '/adviceFeedback/reply', query : {id : scope.row.id, todo : 1}})"
                      >回复
            </el-link>
          </template>
        </template>
      </el-table-column>
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
import {adviceList, adviceThemeType} from "@/api/api";
import store from '@/store'
import {mapGetters} from 'vuex'
export default {
  computed: {
    ...mapGetters([
      'permissions'
    ])
  },
  name: "list",
  data() {
    return {
      schoolId : sessionStorage.getItem("schoolId"),
      userInfo: '',
      tableData: [],
      pageInfo: {
        pageNum: 1,
        pageSize: 10,
        total: 0,
        feedBacked: '' //待反馈数量
      },
      listQuery: {
        keyWord: '',
        typeId: '',
        state: '',
        date: ''
      },
      typeList: [],
      statusList: [
        {
          name: '全部',
          value: null
        },
        {
          name: '待回复',
          value: 0
        },
        {
          name: '已反馈',
          value: 1
        }
      ]
    }
  },
  methods: {
    fetchData(initPageInfo) {
      if (initPageInfo) {
        this.pageInfo.pageNum = 1;
      }
      let params = {
        userId: this.userInfo.id,
        pageNum : this.pageInfo.pageNum,
        pageSize : this.pageInfo.pageSize,
        search : this.listQuery.keyWord,
        typeId : this.listQuery.typeId,
        state : this.listQuery.state,
        date : this.listQuery.date,
        operationType: 0, //pc端只有回复意见
      }
      adviceList(params).then(res => {
        if (res.code === 200) {
          this.tableData = res.result.pageInfo.list ? res.result.pageInfo.list || [] : [];
          this.pageInfo.total = res.result.pageInfo.total;
          if(this.pageInfo.total != null && this.pageInfo.total != '' && this.pageInfo.total != 0) {
            this.pageInfo.feedBacked = this.pageInfo.total  -  res.result.feedBacked
          }
        } else {
          this.$alert("获取教职工数据失败！" + res.message, '错误', {type : "error"});
        }
      })
    },
    initListQuery() {
      this.listQuery = {
        keyWord: '',
        typeId: '',
        state: '',
        date: ''
      };
      this.fetchData(true);
    },
    handleSizeChange(val) {
      this.pageInfo.pageSize = val
      this.fetchData()
    },
    handleCurrentChange(val) {
      this.pageInfo.pageNum = val
      this.fetchData()
    },
    formatTypeNameByValue(value) {
      let stmpType =  this.typeList.find(e => {
          if(value == e.value) {
            return e;
          }
        })
      return stmpType == null ? null: stmpType.name
    },
    initAdviceThemeType() {
      adviceThemeType().then(res => {
       if (res.code == 200) {
         this.typeList = res.result ? res.result || [] : [];
         this.typeList.unshift({name: '全部', value: null})
       } else {
         this.$alert("类型获取失败！" + res.message, '错误', {type : "error"});
       }
      })
    }
  },
  mounted(){
    this.userInfo = store.getters.userInfo
    this.initAdviceThemeType()
    this.fetchData()
  },
}
</script>

<template>
  <el-dialog v-el-drag-dialog :title="title" :visible.sync="dialogTableVisible">
    <el-input
      placeholder="输入关键字进行过滤"
      v-model="filterText">
    </el-input>

    <el-tree
      class="filter-tree"
      :data="officeTreeData"
      :props="defaultProps"
      default-expand-all
      expand-on-click-node
      :filter-node-method="filterNode"
      @node-click="nodeClick"
      ref="tree2">

    </el-tree>
  </el-dialog>
</template>
<script>
  import {schoolList} from "@/api/api"
  import elDragDialog from '@/directive/el-dragDialog'
  import {officeArrayToTree} from '@/utils/utils'

  export default {
    directives: {elDragDialog},
    name: 'chooseSchool',
    props: {
      nodeClick: {
        type: Function,
        default: null
      },
      title: {
        type : String,
        default: "请选择学校"
      }
    },
    data() {
      return {
        officeTreeData : [],
        dialogTableVisible: false,
        defaultProps : {
          children: 'children',
          label: 'officeName'
        },
        filterText : ''
      }
    },
    methods: {
      initData() {
        this.officeTreeData = [];
        this.filterText = '';
      },
      //显示组建
      showComponent(ignoreLevelId) {
        this.getSchoolList(ignoreLevelId);
        this.dialogTableVisible = true;
      },
      closeComponent() {
        this.dialogTableVisible = false;
        this.initData();
      },
      getSchoolList(ignoreLevelId) {
        schoolList({
          ignoreLevelId : ignoreLevelId
        }).then((res) => {
          this.officeTreeData = officeArrayToTree("0", res.result);
        })
      },
      filterNode(value, data) {
        if (!value) return true;
        return data.officeName.indexOf(value) !== -1;
      }
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>

</style>

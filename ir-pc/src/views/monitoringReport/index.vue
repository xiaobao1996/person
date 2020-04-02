<template>
  <div class="app-container">
    <div class="edit-nav">
      <el-radio-group v-model="roleType" @change="roleTypeChange">
        <el-radio-button :label="0"><span style="padding: 0 40px">学生</span></el-radio-button>
        <el-radio-button :label="1"><span style="padding: 0 33px">教职工</span></el-radio-button>
      </el-radio-group>
    </div>
    <!--统计面板-->
    <div class="statistics">
      <div class="statistics-list padding-top-22">
        <div class="statistics-item">
          <span class="statistics-item-icon statistics-item-icon-gray"></span>
          <span class="statistics-item-label">学生总数:</span>
          <span>{{calc.totalCount}}</span>
        </div>
        <div class="statistics-item ">
          <span class="statistics-item-icon statistics-item-icon-red"></span>
          <span class="statistics-item-label"> 今日体温异常:</span>
          <span style="color: #f00;">{{calc.todayAbnormalCount}}</span>
        </div>
        <div class="statistics-item ">
          <span class="statistics-item-icon statistics-item-icon-red"></span>
          <span class="statistics-item-label"> 昨日体温异常:</span>
          <span style="color: #f00;">{{calc.yesterdayAbnormalCount}}</span>
        </div>

      </div>

      <div class="statistics-list padding-bottom-22" style="margin-top: 10px">
        <div class="statistics-item">
          <span class="statistics-item-icon statistics-item-icon-orange"></span>
          <span class="statistics-item-label">较昨日:</span>
          <span v-if="calc.fromYesterday < 0"> <i class="el-icon-bottom down-color"></i> {{Math.abs(calc.fromYesterday)}}</span>
          <span v-else-if="calc.fromYesterday > 0"> <i class="el-icon-top up-color"></i> {{Math.abs(calc.fromYesterday)}}</span>
          <span v-else>0</span>
        </div>
        <div class="statistics-item">
          <span class="statistics-item-icon statistics-item-icon-purple"></span>
          <span class="statistics-item-label">今日体温异常占比:</span>
          <!--<span v-if="calc.fromYesterday < 0"> <i class="el-icon-bottom down-color"></i>{{toDecimal(calc.todayAbnormalRatio)}}%</span>-->
          <!--<span v-else-if="calc.fromYesterday > 0"> <i class="el-icon-top up-color"></i>{{toDecimal(calc.todayAbnormalRatio)}}%</span>-->
          <span>{{toDecimal(calc.todayAbnormalRatio)}}%</span>

        </div>
        <div class="statistics-item">
          <span class="statistics-item-icon statistics-item-icon-green"></span>
          <span class="statistics-item-label">昨日体温异常占比:</span>
         <!--<span v-if="calc.qianTian < 0"> <i class="el-icon-bottom down-color"></i>{{toDecimal(calc.yesterdayAbnormalRatio)}}%</span>-->
          <!--<span v-else-if="calc.qianTian > 0"> <i class="el-icon-top up-color"></i>{{toDecimal(calc.yesterdayAbnormalRatio)}}%</span>-->
          <span>{{toDecimal(calc.yesterdayAbnormalRatio)}}%</span>
        </div>
        <!--<div class="statistics-item">-->
        <!--</div>-->
      </div>
    </div>

    <div class="statistics-title" v-if="roleType === 0">学生体温异常人数趋势 </div>
    <div class="statistics-title" v-else>教职工体温异常人数趋势 </div>
    <!--统计图-->
    <div id="summary-graph"></div>
  </div>
</template>

<script>
  import moment from 'moment'
  import {getMonitorStudentCalc, getMonitorStaffCalc} from '@/api/api'
  import echarts from 'echarts'
    export default {
        name: "index",
        data () {
         return {
           roleType: 0,
           echartOption: {
             color: ['#0db5b8'],
             tooltip : {
               trigger: 'axis',
               formatter: '{b}/{c}人',
               axisPointer : {
                 type : 'none',
               }
             },
             xAxis: {
               type: 'category',
               name: '日期',
               boundaryGap: false,
               data: [],
               axisLine: {
                 lineStyle: {
                   type: 'solid',
                   color:'#999999',
                 }
               },
               axisLabel: {
                 textStyle: {
                   color: '#333333'
                 }
               }
             },
             yAxis: {
               type: 'value',
               name: '异常总人数',

               minInterval: 1,
               axisLine: {
                 lineStyle: {
                   type: 'solid',
                   color:'#999999',
                 }
               },
               axisLabel: {
                 textStyle: {
                   color: '#333333'
                 }
               }
             },
             series: [{
               data: [],
               type: 'line',
               smooth: true,
               label: {
                 normal: {
                   show: true,
                   position: 'top'
                 }
               },
             }]
           },
           studentCalc: {
             totalCount : 0,
             todayAbnormalCount : 0,
             yesterdayAbnormalCount : 0,
             fromYesterday : 0,  // 今日异常数与昨日相比
             todayAbnormalRatio: 0, // 今日异常比
             yesterdayAbnormalRatio : 0,//昨日异常比
             qianTian: 0
           },
           staffCalc: {
             totalCount : 0,
             todayAbnormalCount : 0,
             yesterdayAbnormalCount : 0,
             fromYesterday : 0,  // 今日异常数与昨日相比
             todayAbnormalRatio: 0, // 今日异常比
             yesterdayAbnormalRatio : 0,//昨日异常比
             qianTian: 0
           },
           calc : {
             totalCount : 0,
             todayAbnormalCount : 0,
             yesterdayAbnormalCount : 0,
             fromYesterday : 0,  // 今日异常数与昨日相比
             todayAbnormalRatio: 0, // 今日异常比
             yesterdayAbnormalRatio : 0,//昨日异常比
             qianTian: 0
           }
         }
        },
      methods : {
        roleTypeChange () {
          if (this.roleType === 1) {
            this.getMonitorStaffCalc()
          } else {
            this.getMonitorStudentCalc()
          }
          this.showChart()
        },
        getMonitorStudentCalc() {
          getMonitorStudentCalc().then(res => {
              if (res.code === 200) {
                this.echartOption.series[0].data = []
                this.echartOption.xAxis.data = []
                if (res.result) {
                  this.studentCalc.totalCount = res.result.totalCount || 0
                  this.studentCalc.todayAbnormalCount = res.result.todayAbnormalCount || 0
                  this.studentCalc.yesterdayAbnormalCount = res.result.yesterdayAbnormalCount || 0
                  this.studentCalc.fromYesterday = this.floatSub(this.studentCalc.todayAbnormalCount, this.studentCalc.yesterdayAbnormalCount)
                  this.studentCalc.qianTian = this.floatSub(this.studentCalc.yesterdayAbnormalCount, res.result.before3DaysAbnormalCount)
                  // console.log(this.studentCalc)
                  this.studentCalc.todayAbnormalRatio = this.floatDiv(this.studentCalc.todayAbnormalCount,  this.studentCalc.totalCount)
                  this.studentCalc.yesterdayAbnormalRatio = this.floatDiv(this.studentCalc.yesterdayAbnormalCount, this.studentCalc.totalCount)
                  // console.log(this.toDecimal(this.studentCalc.yesterdayAbnormalRatio))
                  if (this.roleType === 0) {
                    this.calc = this.studentCalc
                    if (Array.isArray(res.result.calcItemList) && res.result.calcItemList.length > 0) {
                      res.result.calcItemList.forEach(item => {
                        this.echartOption.series[0].data .push(item.abnormalCount)
                        // this.echartOption.series[0].data = [12, 11, 14, 1, 3, 4, 5, 12, 8, 8]
                        let time = moment(item.monitorDateStr).format("YYYY.M.D")
                        this.echartOption.xAxis.data.push(time)
                      })
                    }
                    this.showChart()
                  }
                }
              } else {
                this.$message.warning("获取学生统计信息失败！" + res.message);
              }
          })
        },
        getMonitorStaffCalc () {
          getMonitorStaffCalc().then(res => {
            if (res.code === 200) {
              this.echartOption.series[0].data = []
              this.echartOption.xAxis.data = []
              if (res.result) {
                this.staffCalc.totalCount = res.result.totalCount || 0
                this.staffCalc.todayAbnormalCount = res.result.todayAbnormalCount || 0
                this.staffCalc.yesterdayAbnormalCount = res.result.yesterdayAbnormalCount || 0
                this.staffCalc.qianTian = res.result.before3DaysAbnormalCount || 0
                this.staffCalc.fromYesterday = this.floatSub(this.staffCalc.todayAbnormalCount, this.staffCalc.yesterdayAbnormalCount)
                this.staffCalc.qianTian = this.floatSub(this.staffCalc.yesterdayAbnormalCount, this.staffCalc.qianTian)
                this.staffCalc.todayAbnormalRatio = this.floatDiv(this.staffCalc.todayAbnormalCount,  this.staffCalc.totalCount)
                this.staffCalc.yesterdayAbnormalRatio = this.floatDiv(this.staffCalc.yesterdayAbnormalCount, this.staffCalc.totalCount)

                if (this.roleType === 1) {
                  this.calc = this.staffCalc


                  if (Array.isArray(res.result.calcItemList) && res.result.calcItemList.length > 0) {
                    res.result.calcItemList.forEach(item => {
                      this.echartOption.series[0].data .push(item.abnormalCount)
                      // this.echartOption.series[0].data = [12, 11, 14, 1, 3, 4, 5, 12, 8, 8]
                      let time = moment(item.monitorDateStr).format("YYYY.M.D")
                      this.echartOption.xAxis.data.push(time)
                    })
                  }
                  this.showChart()
                }
              }
            } else {
              this.$message.warning("获取教职工统计信息失败！" + res.message);
            }
          })
        },
        showChart() {
          let this_ = this;
          let myChart = echarts.init(document.getElementById('summary-graph'));

          // this.getMonitorStudentCalc()

          let option = this.echartOption;
          myChart.setOption(option);
          //建议加上以下这一行代码，不加的效果图如下（当浏览器窗口缩小的时候）。超过了div的界限（红色边框）
          window.addEventListener('resize',function() {myChart.resize()});
        },
         floatSub(arg1,arg2){

          if (arg2 === 0 || arg2 === null) {
            return 0
          }
          let r1,r2,m,n;
          try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
          try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
          m=Math.pow(10,Math.max(r1,r2));
          //动态控制精度长度
          n=(r1>=r2)?r1:r2;
          return ((arg1*m-arg2*m)/m).toFixed(n);
           } ,
          toDecimal(num) {
            let result = parseFloat(num);
            if (isNaN(result)) {
              return 0.00;
            }
            result = Math.round(num * 100) / 100;
            let s_x = result.toString();
            let pos_decimal = s_x.indexOf('.');
            if (pos_decimal < 0) {
              pos_decimal = s_x.length;
              s_x += '.';
            }
            while (s_x.length <= pos_decimal + 2) {
              s_x += '0';
            }
            return s_x;
          },
          floatDiv(arg1,arg2){

          let  t1=0,t2=0,r1,r2;
          try{t1=arg1.toString().split(".")[1].length}catch(e){}
          try{t2=arg2.toString().split(".")[1].length}catch(e){}

          r1=Number(arg1.toString().replace(".",""));

          r2=Number(arg2.toString().replace(".",""));
         let res =  (r1/r2)*Math.pow(10,t2-t1);
         return res * 100
        }

      },
      mounted() {
        this.roleTypeChange()
      },

    }
</script>

<style scoped lang="scss">
  .statistics {
    width: 100%;
    height: 88px;
    background: #fcffff;
    margin-top: 32px;
    box-sizing: border-box;
    border-radius: 10px;
    border: 1px solid #C0C4CC;
  }
  .statistics-list {
    margin-left: 20px;
    display: flex;
    justify-content: space-between;
  }
  .statistics-item {
    width: 220px;
    height: 20px;
    line-height: 20px;
    display: flex;
    align-items: center;
  }
  .statistics-item-icon {
    margin-right: 4px;
    width: 8px;
    height: 8px;
    border-radius: 4px;
  }
  .statistics-item-icon-gray {
    background: #999999;
  }
  .statistics-item-icon-red {
      background: #da3120;
  }
  .statistics-item-icon-orange {
    background: #f6b375;
  }
  .statistics-item-icon-purple {
    background: #e663ee;
  }
  .statistics-item-icon-green {
    background: #53ca7f;
  }
  .down-color {
    color: #53ca7f !important;
  }
  .up-color {
    color: #da3120 !important;
  }
  .statistics-item-label {
    color: #999999;
    margin-right: 4px;
  }
  .padding-top-22 {
    padding-top: 22px;
  }
  .padding-bottom-22 {
    padding-bottom: 22px;
  }
  .statistics-title {
    width: 100%;
    margin: 95px auto 25px auto;
    text-align: center;
    font-weight: bold;
    font-size: 18px;
  }
  #summary-graph {
    width: 90%;
    height: 420px;
    margin: 0 auto;

  }
</style>

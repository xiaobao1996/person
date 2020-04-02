<template>
  <div class="app-container" >
    <div class="edit-nav">
    <div  class="cf-title-line">
      <svg-icon  icon-class="advice" style="fill: #02b2b5" />
      <span>意见</span>
    </div>
    <template  v-if="adviceDetail.anonymous != 0">
    <div>
      <div class="ele-form-item">
        <div class="cf-ele-form-label">姓名：</div>
        <div class="ele-form-content">{{adviceDetail.adviceName}}</div>
      </div>
    </div>
    <div>
      <div class="ele-form-item">
        <div class="cf-ele-form-label">联系电话：</div>
        <div class="ele-form-content">{{adviceDetail.phone }} </div>
      </div>
    </div>
    </template>

      <div>
        <div class="cf-ele-form-item">
          <div class="cf-ele-form-label">内容：</div>
          <div style="width: calc(100% - 100px);margin-left: 100px;">
            <div style="width: 100%;text-align: center;">
              <span style="font-weight: bold">{{adviceDetail.theme}}</span>
              <div style="color: #999999;line-height: 40px">{{adviceDetail.createAt | timeFormat}}</div>
            </div>
          </div>
        </div>
      </div>

      <div class="cf-ele-form-item">
        <div class="cf-ele-form-advice-content">
          <div class="cf-ele-form-advice-content-pre">
            <pre>{{adviceDetail.advice}}</pre>
          </div>
        </div>
        <div class="cf-ele-form-advice-imgList">
          <!--//   style="display: inline-block;margin-left: 20px"-->
          <div  v-for="(item, index) in imgList" :key="index"
                class="cf-photo-preview"
                @click="preImg(index)"
          >
            <img
              :src="item"
            />
          </div>
          <!--{{imgList}}-->
        </div>
        <el-image-viewer :urlList="imgList" ref="myPreview" v-show="showViewer" :on-close="preImgClose"/>

      </div>
      <div class="line"></div>
      <div  class="cf-title-line">
        <svg-icon  icon-class="feedback" style="fill: #02b2b5;width: 1.2em;height: 1.2em" />
        <span>反馈</span>
      </div>
    <template v-if="(adviceDetail.feedback == null || adviceDetail.feedback == '') && permissions.indexOf('cf:manage:reply') !== -1" >
      <template v-if="todo == 1">

        <div>
          <div class="ele-form-item-textarea">
            <div class="cf-ele-form-label">内容：</div>
            <div class="ele-form-content" style="margin-bottom: 20px;">
              <el-input type="textarea"
                        rows="8"
                        maxlength="200"
                        v-model="adviceForm.feedback"
                        size="medium"
                        show-word-limit
              ></el-input>
            </div>
          </div>
        </div>
        <div >
          <el-button type="primary" @click="saveAdviceFeedback">回复</el-button>
          <el-button style="margin-left: 44px" @click="handleCancel">取消</el-button>
        </div>
      </template>
    </template>
    <template v-else >
      <template v-if="(adviceDetail.feedback !== null && adviceDetail.feedback != '') ">
        <div>
          <div class="ele-form-item">
            <div class="cf-ele-form-label">回复人：</div>
            <div class="ele-form-content">{{adviceDetail.manageName}}</div>
          </div>
        </div>
        <div>
          <div class="ele-form-item">
            <div class="cf-ele-form-label">回复时间：</div>
            <div class="ele-form-content">{{adviceDetail.updateAt}} </div>
          </div>
        </div>
        <div>
          <div class="ele-form-item-textarea">
            <div class="cf-ele-form-label">内容：</div>
            <pre class="ele-form-content" >{{adviceDetail.feedback}}</pre>
          </div>
        </div>
      </template>
    </template>
    </div>
  </div>

</template>

<script>
  import {MessageBox} from 'element-ui'
  import {adviceDetail, saveAdviceFeedback} from '@/api/api'
  import {mapGetters} from 'vuex'
  import store from '@/store'
  import ElImageViewer from "element-ui/packages/image/src/image-viewer";
    export default {
        name: "reply",
      components: {ElImageViewer},
      computed: {
        ...mapGetters([
          'permissions'
        ])
      },
      mounted(){
        this.userInfo = store.getters.userInfo
      },
        data () {
          return {
            showViewer: false,
            userInfo: '',
            previewData: {
              imgDialog: false,
              imgUrlList: []
            },
            imgList: [
            ],
            todo: '', //0 详情 1 回复
            adviceDetail: {
              id : '',
              adviceName: '',
              phone: '',
              photo: '',
              advice: '',
              theme: '',
              createAt: '',
              feedback: '' ,//反馈，
              manageName: '',//回复人名称
              updateAt: '',// 回复时间
              anonymous: ''
            },
            adviceForm: {
              feedback: ''
            }
          }
        },
      methods: {
        preImgClose() {
          this.showViewer = false;
        },
        preImg(index) {
         if(this.imgList.length > 0) {
           this.$nextTick(() => {
             this.$refs.myPreview.deviceSupportInstall()
             this.$refs.myPreview.index = index
           })
           this.showViewer = true;
         }
        },
        previewImg(){
          this.previewData.imgDialog = true;
        },
        handleCancel() {
          this.$confirm('确定取消吗?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
           this.adviceForm.feedback = '',
             this.$router.push('/adviceFeedback/list')
          }).catch(() => {
          });
        },
        getAdviceDetail() {
          this.adviceDetail.id = this.$route.query.id  == null ? null : this.$route.query.id
          this.todo = this.$route.query.todo == null ? null  : this.$route.query.todo
          adviceDetail({id : this.adviceDetail.id}).then(res => {
           if (res.code == 200) {
             this.imgList = []
             this.adviceDetail = res.result == null ? null : res.result
             if(res.result.photo != null && res.result.photo != '') {
               this.imgList = res.result.photo.split(",");
               if(this.imgList.length != 0) {
                 let tem = [];
                 this.imgList.forEach(e =>{
                   tem.push(e + '?x-oss-process=image/resize,h_800')
                 })
                 this.imgList = [];
                 this.imgList = tem;
               }
             }
           } else {
             this.$alert("数据获取失败！" + res.message, '错误', {type : "error"});
           }
          })
        },
        saveAdviceFeedback(){
         if (this.adviceForm.feedback.length > 200) {
           this.$alert("反馈内容最大长度200个字符" , '提示', {type : "warning"});
         } else {
           let params = {
             id: this.adviceDetail.id,
             feedback: this.adviceForm.feedback,
             updateBy: this.userInfo.id,
             updateName: this.userInfo.realName == null ?  this.userInfo.userName == null ? null : this.userInfo.userName : this.userInfo.realName
           }
           saveAdviceFeedback(params).then(res => {
            if (res.code == 200) {
              this.$message({
                message: '回复成功！',
                type: 'success'
              });
              setTimeout(() => {
                this.$router.push('/adviceFeedback/list')
              }, 1000)
            } else {
              this.$alert("操作失败！" + res.message, '错误', {type : "error"});
              setTimeout(() => {
                this.$router.replace('/adviceFeedback/list')
              }, 1000)
            }
           })
         }
        }
      },
      watch:{
        'adviceForm.feedback' (newVal, oldVal) {
          if (newVal.length >= 200) {
            this.$alert("反馈内容最大长度200个字符" , '提示', {type : "warning"});
          }
        }
      },
      created() {
        this.getAdviceDetail()
      }
    }
</script>

<style scoped >

</style>



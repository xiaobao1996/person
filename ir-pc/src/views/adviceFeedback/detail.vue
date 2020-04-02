<template>

  <div class="app-container">
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
          <div>
            <pre>{{adviceDetail.advice}}</pre>
          </div>
        </div>
        <div class="cf-ele-form-advice-imgList">
          <div  v-for="(item, index) in imgList" :key="index"
                @click="preImg(index)"
                class="cf-photo-preview"
          >
            <img
              :src="item"
            />
          </div>
          <el-image-viewer :urlList="imgList" ref="myPreview" v-show="showViewer" :on-close="preImgClose"></el-image-viewer>
          <!--{{imgList}}-->
        </div>
      </div>

      <div class="line"></div>

      <div  class="cf-title-line">
        <svg-icon  icon-class="feedback" style="fill: #02b2b5;width: 1.2em;height: 1.2em" />
        <span>反馈</span>
      </div>

      <template v-if="adviceDetail.feedback != null && adviceDetail.feedback != ''" >
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
            <div class="cf-ele-form-label" style="line-height: 30px;margin-top: 10px">内容：</div>
            <div class="ele-form-content" style="text-indent: 2em"><pre>{{adviceDetail.feedback}}</pre></div>
          </div>
        </div>
      </template>
    </div>
  </div>
</template>

<script>
  import {adviceDetail, saveAdviceFeedback} from '@/api/api'
  import {mapGetters} from 'vuex'
  import store from '@/store'
  import ElImageViewer from "element-ui/packages/image/src/image-viewer";
  export default {
    name: "detail",
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
        userInfo: '',
        previewData: {
          imgDialog: false,
          imgUrlList: []
        },
        imgList: [],
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
        },

        showViewer : false,
      }
    },
    methods: {
      preImgClose() {
        this.showViewer = false;
      },
      preImg(index) {
        if(this.imgList.length > 0) {
          this.$nextTick(() => {
            //reset
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
    },
    created() {
      this.getAdviceDetail()
    }
  }
</script>

<style scoped >

</style>




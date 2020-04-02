<template>
  <div class="app-container">
    <div class="calc-area msg-line" >
      共收到<span style="color: #02b2b5">{{pageInfo.total}}</span>条消息，已读<span style="color: #02b2b5">{{pageInfo.read}}</span>条, 未读<span style="color:#f00">{{pageInfo.unread}}</span>条
    </div>
    <template  v-for="(item, index) in tableData" >
      <!--addressType 1: 手机端 2: pc-->
      <!--readStatus 0: 未读 1: 已读-->
      <!--type（0：角色审批 1:咨询反馈 2:流程管理）-->
      <div v-if="item.openType == 2" >
        <div
          class="ele-form-line-item"
          :class="{'ele-form-line-item-bg': index % 2 == 0}"
          >
          <div class="ele-msg-form-label">
            <span v-if="item.readStatus == 0" class="message-unread-color">未读</span>
            <span v-else class="message-read-color">已读</span>
          </div>
          <div class="ele-form-label" style="color: #bdbdbd;">{{item.time | timeFormat}}</div>
          <div  class="ele-message-content" >
            <span  @click.stop="goTo(item.address, item.id, item.templateId)" v-html="item.msg"></span>
          </div>
        </div>
      </div>
      <div v-else >
        <div class="ele-form-line-item"
             :class="{'ele-form-line-item-bg': index % 2 == 0}"
             >
          <div class="ele-msg-form-label">
            <span v-if="item.readStatus == 0" class="message-unread-color">未读</span>
            <span v-else class="message-read-color">已读</span>
          </div>
          <div class="ele-form-label" style="color: #bdbdbd;">{{item.time | timeFormat}}</div>
          <div  class="ele-message-content" >
            <span  @click.stop="showQrDialog(item.address, item.id)" v-html="item.msg"></span>
          </div>
        </div>
      </div>
    </template>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pageInfo.pageNum"
      :page-sizes="[5, 10, 20, 50]"
      :page-size="10"
      layout="total, sizes, prev, pager, next, jumper"
      :total="pageInfo.total">
    </el-pagination>
    <el-dialog :visible.sync="previewData.imgDialog" custom-class="preview-img">
      <img width="100%" :src="previewData.imgUrl" alt="">
    </el-dialog>
    <el-dialog
      width="400px"
      center
      class="message-qr"
      :visible.sync="previewQr.qrDialog" >
      <h3>请至 <span>微信端</span> 操作</h3>
      <div>
        <vue-qr :text="previewQr.qrUrl" :dotScale="1" ></vue-qr>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import {subjectList, userMsgList, readMsg} from '@/api/api'
  import store from '@/store'
  import VueQr from 'vue-qr'
  import {mapGetters} from 'vuex'
    export default {
      computed: {
        ...mapGetters([
          'permissions'
        ])
      },
      name: "list",
      components: {
        VueQr
      },
      data() {
        return {
          tableData: [],
          userInfo: '',
          previewData: {
            imgDialog: false,
            imgUrl: '',
          },
          previewQr: {
            qrDialog: false,
            qrUrl: '',
          },
          pageInfo: {
            pageNum: 1,
            pageSize: 10,
            total: 0,
            unread: 0,
            read: 0
          },
          msgInfo: {
            id: '',
            read: '',
            time: '',
            msg: '',
            openType: '',
            address: '',
            templateId: ''
          }
        }
      },
      methods: {
        initMsgInfo() {
          this.msgInfo = {
            id: '',
            readStatus: '',
            time: '',
            msg: '',
            openType: '',
            address: '',
            templateId: ''
          }
        },
        formatSysType(code) {
          for (let item of this.sysType) {
            if (item.value == code) {
              return item.label;
            }
          }
          return "";
        },
        goTo(url, id, templateId) {
          this.readMsg(id)

          if(templateId == 5 || templateId == 6) {
            if (this.permissions.indexOf('cf:common:list') == -1 && this.permissions.indexOf('cf:manage:reply') == -1  ) {
              this.$alert("暂无权限！", {type : "warning"});
              return ;
            } else {
              if (url != null && url != '') {
                window.open(url)
              }
            }
          } else {
            if (url != null && url != '') {
              window.open(url)
            }
          }
        },
        readMsg(id) {
          let userId = this.userInfo.id;
          let params = {
            userId: userId,
            id: id
          }
          readMsg(params).then(res => {
            if (res.code == 200) {
              this.changeUnreadNum()
              this.fetchData()
            }
          })
        },
        fetchData(initPageInfo) {
          if (initPageInfo) {
            this.pageInfo.pageNum = 1;
          }
          let userId = this.userInfo.id;
          let curSchoolId = sessionStorage.getItem("schoolId");
          if (curSchoolId != null && curSchoolId != '' && userId != null && userId != '') {
            let params = {
              userId: userId,
              schoolId: curSchoolId,
              pageNum: this.pageInfo.pageNum,
              pageSize: this.pageInfo.pageSize,
            }
            userMsgList(params).then(res => {
                if (res.code == 200) {
                  this.tableData = [];
                  if (res.result.pageInfo.list != null) {
                    res.result.pageInfo.list.forEach(e => {
                      this.initMsgInfo()
                      this.msgInfo.templateId = e.templateId;
                      this.msgInfo.id = e.id;
                      this.msgInfo.readStatus = e.readStatus
                      this.msgInfo.openType = e.addressType
                      this.msgInfo.address = e.address
                      this.msgInfo.time = e.createAt
                      this.msgInfo.msg = this.formatMsg(e.key1, e.key2, e.key3,e.key4,e.key5, e.sysMsgTemplate.template)
                      this.tableData.push(this.msgInfo)
                      // console.log(JSON.stringify(this.msgInfo));
                    })
                  }
                  this.pageInfo.total = res.result.pageInfo.total;
                  this.pageInfo.unread = res.result.unRead;
                  if (this.pageInfo.total != 0 && this.pageInfo.total != null) {
                    this.pageInfo.read = this.pageInfo.total - this.pageInfo.unread
                  }
                } else {
                  this.$alert("信息获取失败！" + res.message, '错误', {type : "error"});
                }
            }
            )
          }
        },
        //格式化模板消息
        formatMsg(key1, key2, key3,key4,key5, msg) {
          if (msg.indexOf("flag") != -1) {
            if (key1 != null && key1 != '' && msg.indexOf("${key1}$") != -1 ) {
              msg = msg.replace("${key1}$", key1);
            }
            if (key1 != null && key1 != '' && msg.indexOf("${key1:flag}$") != -1 ) {
              let newKey =  `<span>${key1}</span>`
              msg = msg.replace("${key1:flag}$", newKey);
            }
            if (key2 != null && key2 != '' && msg.indexOf("${key2:flag}$") != -1) {
              let newKey = `<span>${key2}</span>`
              msg = msg.replace("${key2:flag}$", newKey);
            }
            if (key2 != null && key2 != '' && msg.indexOf("${key2}$") != -1) {
              msg = msg.replace("${key2}$", key2);
            }
            if (key3 != null && key3 != '' && msg.indexOf("${key3:flag}$") != -1) {
              let newKey = `<span>${key3}</span>`
              msg = msg.replace("${key3:flag}$", newKey);
            }
            if (key3 != null && key3 != '' && msg.indexOf("${key3}$") != -1) {
              msg = msg.replace("${key3}$", key3);
            }
            if (key4 != null && key4 != '' && msg.indexOf("${key4:flag}$") != -1) {
              let newKey = `<span>${key4}</span>`
              msg = msg.replace("${key4:flag}$", newKey);
            }
            if (key4 != null && key4 != '' && msg.indexOf("${key4}$") != -1) {
              msg = msg.replace("${key4}$", key4);
            }
            if (key5 != null && key5 != '' && msg.indexOf("${key5:flag}$") != -1) {
              let newKey = `<span>${key5}</span>`
              msg = msg.replace("${key5:flag}$", newKey);
            }
            if (key5 != null && key5 != '' && msg.indexOf("${key5}$") != -1) {
              msg = msg.replace("${key5}$", key5);
            }
          } else {
            if (key1 != null && key1 != '' && msg.indexOf("${key1}$") != -1 ) {
              msg = msg.replace("${key1}$", key1);
            }
            if (key2 != null && key2 != '' && msg.indexOf("${key2}$") != -1) {
              let newKey2 = `<span>${key2}</span>`
              msg = msg.replace("${key2}$", newKey2);
            }
            if (key3 != null && key3 != '' && msg.indexOf("${key3}$") != -1) {
              msg = msg.replace("${key3}$", key3);
            }
            if (key4 != null && key4 != '' && msg.indexOf("${key4}$") != -1) {
              msg = msg.replace("${key4}$", key4);
            }
            if (key5 != null && key5 != '' && msg.indexOf("${key5}$") != -1) {
              msg = msg.replace("${key5}$", key5);
            }
          }
          return msg;
        },
        showQrDialog(url, id) {
          this.previewQr.qrUrl = ''
          this.previewQr.qrDialog = true
          this.previewQr.qrUrl = url
          this.readMsg(id)
        },
        handleSizeChange(val) {
          this.pageInfo.pageSize = val
          this.fetchData()
        },
        handleCurrentChange(val) {
          this.pageInfo.pageNum = val
          this.fetchData()
        },
        changeUnreadNum() {
          //.unreadMsgCountAction()
          if (this.$root.$children[0] && this.$root.$children[0].$children[0] && this.$root.$children[0].$children[0].$children) {
            let objList = this.$root.$children[0].$children[0].$children
            for(let obj of objList) {
              if ( obj.$el.id == "message-header") {
                obj.unreadMsgCountAction();
                break;
              }
            }
          }
        },
      },
      created() {
        this.userInfo = store.getters.userInfo
        this.fetchData()
      },
    }
</script>

<style scoped>

</style>

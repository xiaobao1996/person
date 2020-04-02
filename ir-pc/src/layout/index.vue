<template>
  <div :class="classObj" class="app-wrapper" style="min-width: 1000px;">
    <div v-if="device==='mobile'&&sidebar.opened" class="drawer-bg" @click="handleClickOutside" />
    <div class="layout-header">
      <top-header/>
    </div>
    <sidebar class="sidebar-container" style="float: left;"/>
    <div class="main-container" :style="{'margin-left' : sidebar.opened ? `247px` : `69px`}">
      <!--      <div class="navbar-area" :style="{'width' : sidebar.opened ? 'calc(100% - 247px)' : 'calc(100% - 69px)'}">-->
      <div class="navbar-area" style="position: relative;">
        <navbar />
      </div>
      <div style="margin-top:15px;background: #fff;height: calc(100vh - 187px);overflow: auto;">
        <app-main />
      </div>
      <div class="layout-footer">
        <span style="font-weight: 600;">Copyright © 2017 </span>
        <span style="margin-left: 80px;">.All rights reserved </span>
      </div>
    </div>
  </div>
</template>

<script>
  import { Navbar, Sidebar, AppMain } from './components'
  import TopHeader from '@/components/TopHeader'
  import ResizeMixin from './mixin/ResizeHandler'

  export default {
    name: 'Layout',
    components: {
      Navbar,
      Sidebar,
      AppMain,
      TopHeader
    },
    mixins: [ResizeMixin],
    computed: {
      sidebar() {
        return this.$store.state.app.sidebar
      },
      device() {
        return this.$store.state.app.device
      },
      fixedHeader() {
        return this.$store.state.settings.fixedHeader
      },
      classObj() {
        return {
          hideSidebar: !this.sidebar.opened,
          openSidebar: this.sidebar.opened,
          withoutAnimation: this.sidebar.withoutAnimation,
          mobile: this.device === 'mobile'
        }
      }
    },
    methods: {
      handleClickOutside() {
        this.$store.dispatch('app/closeSideBar', { withoutAnimation: false })
      }
    },
  }
</script>

<style lang="scss" scoped>
  @import "~@/styles/mixin.scss";
  @import "~@/styles/variables.scss";

  .app-wrapper {
    @include clearfix;
    position: relative;
    height: 100%;
    width: 100%;
    &.mobile.openSidebar{
      position: fixed;
      top: 0;
    }
  }
  .drawer-bg {
    background: #000;
    opacity: 0.3;
    width: 100%;
    top: 0;
    height: 100%;
    position: absolute;
    z-index: 999;
  }

  /*  .fixed-header {
      position: fixed;
      top: 0;
      right: 0;
      z-index: 9;
      width: calc(100% - #{$sideBarWidth});
      transition: width 0.28s;
    }*/

  /*  .hideSidebar .fixed-header {
      width: calc(100% - 54px)
    }

    .mobile .fixed-header {
      width: 100%;
    }*/
  .layout-header{
    height: 54px;width: 100%;position: fixed;top: 0;left: 0;z-index: 100
  }
  .layout-footer{
    height: 53px;background: #fff;margin-top: 15px;width: 100%;line-height: 53px;font-size: 13px;color: #333;text-align: center;
  }
  .navbar-area{
    top: 0;z-index: 48;
  }
</style>

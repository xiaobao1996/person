<template>
  <div class="home" >
    <h2 style="padding: 20px 40px;">欢迎使用{{title}}系统！</h2>

  </div>
</template>

<script>
  import {title} from  '../../settings'
  export default {
    name: 'home',
    data() {
      return {
        title
      }
    },
    methods: {
    },
    mounted() {
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>

</style>

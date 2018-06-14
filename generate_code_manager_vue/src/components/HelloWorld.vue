<template>

  <div class="hello">
    <h1 v-on:click="a()">{{ msg }}aa</h1>
        <img src="@/staticss/layui/images/face/66.gif">
  </div>
</template>





  
<script>
import '@/staticss/layui/layui.all.js'
export default {
  name: 'HelloWorld',
  data () {
    return {
      msg: 'Welcome to Your Vue.js App'
    }
  },methods:{
    a(){
      //alert("aaaaa");
console.log("aaaaa");  

  layer.msg('Hello World');
      layer.msg("aaaaaaaa");
    }
  }
}

</script>

<style>

</style>

<!-- Add "scoped" attribute to limit CSS to this component only -->

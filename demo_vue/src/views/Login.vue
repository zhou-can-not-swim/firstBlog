<template>
<!--    透明背景-->

  <div class="glass-container" id="glass">
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm" style="margin-left: -100px">
      <el-form-item prop="username">
        <el-input v-model="ruleForm.username" placeholder="用户名"></el-input>
      </el-form-item>

      <el-form-item prop="password">
        <el-input v-model="ruleForm.password" show-password="true" placeholder="密码"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="submitForm">登录</el-button>
        <el-button @click="resetForm">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import instance from "@/axios";
import {Message} from "element-ui";

export default {
  name: "Login",
  data() {
    return {
      loginBg: 'url(' + require('../../public/R.webp') + ')',
      ruleForm: {
        username: '',
        password: '',
      },
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '密码', trigger: 'blur' },
          { min: 5, max: 15, message: '长度在 5 到 15 个字符', trigger: 'blur' }
        ],
      }
    };
  },

  methods: {
    submitForm() {
      if (this.ruleForm.username===""||this.ruleForm.password==="") {
        const h = this.$createElement;
        this.$notify({
          title: '登录错误',
          message: h('i', { style: 'color: teal'},
              '提交表单错误')
        });

      } else {
        //  TODO 登录成功
          instance.post("/login",this.ruleForm).then(res=>{
            // console.log(res.headers.getAuthorization())
            this.$store.dispatch("ac_user",res.data.data)
            console.log(this.$store.state.userInfo)
            localStorage.setItem("token",res.headers.getAuthorization())
            localStorage.setItem("userInfo",this.$store.state.userInfo)

            this.$router.push("/blogs")
            Message.success("登陆成功")

          })

      }
    },
    resetForm() {
      this.ruleForm={}
    }
  }
}
</script>

<style lang="less" scoped>

/*透明背景*/
.glass-container{
  width: 600px;
  height: 470px;
  color: white;
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 20px;
  border-radius: 10px;
  backdrop-filter: blur(5px);
  background-color: rgba(0,191,255, 0.075);
  box-shadow: rgba(0, 0, 0, 0.3) 2px 8px 8px;
  border: 2px rgba(255,255,255,0.4) solid;
  border-bottom: 2px rgba(40,40,40,0.35) solid;
  border-right: 2px rgba(40,40,40,0.35) solid;

  position: absolute;
  top: 50%;
  left: 50%;
  transform:translate(-50%,-50%);
}

/deep/ .el-button+.el-button{
  margin-left: 90px;
}

</style>
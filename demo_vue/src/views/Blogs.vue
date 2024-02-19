<template>

  <el-container height="100vh">
    <el-header height="30vh">
      <el-card class="box-card" shadow="never">
        <h3 style="margin-top: 20px">欢迎来到我的Blog--></h3>
        <div style="margin-top: 20px">
          <el-avatar :src=userInfo.avatar size="80px"></el-avatar>
        </div>

        <div style="margin-top: 10px">
          {{userInfo.username}}
        </div>

        <div style="margin-top: 10px">
          <span><el-link href="/blogs">主页</el-link></span>

          <el-divider direction="vertical"></el-divider>
          <span>
            <el-link type="success" href="/blog/add">发表博客</el-link>
          </span>

          <el-divider direction="vertical"></el-divider>
          <span><el-link type="danger" @click="logout">退出</el-link></span>

        </div>
      </el-card>
    </el-header>

    <el-main class="scrollable">
      <el-timeline>
        <el-timeline-item timestamp="2018/4/12" placement="top" v-for="blog in blogs">

          <el-card>
            <h4>
              <router-link :to="{name: 'blogDetail', params: {blogId: blog.id}}">
                {{blog.title}}
              </router-link>
            </h4>
            <p>{{blog.description}}</p>
          </el-card>
        </el-timeline-item>
      </el-timeline>

      <div class="page_bar">
        <el-pagination
            layout="prev, pager, next"
            :total="total"
            :current-page="currentPage"
            :page-size="5"
            @current-change="handleCurrentChange">
        </el-pagination>
      </div>
    </el-main>


  </el-container>


</template>

<script>
import instance from "@/axios";

export default {
  name: "Blogs",
  data() {
    return {
      //bar
      total:0,
      currentPage:1,


      //blog
      blogs:{},
      userInfo:{}

    }
  },
  created() {
    this.load()

    instance.get("/getuser",{
      params:{
        token:localStorage.getItem("token")
      }
    }).then(res=>{
      this.userInfo = res.data.data

    })

  },
  methods:{
    load(){
      instance.get("/blogs/"+this.currentPage).then(res=>{
        // console.log(res.data.data.records)
        // console.log(this.$store)

        this.avatar_url=this.$store.state.userInfo.avatar
        this.username=this.$store.state.userInfo.username
        this.blogs=res.data.data.records

        // console.log("===================================res.data.data")
        // console.log(res.data.data)
        this.total=res.data.data.total


      })
    },
    handleCurrentChange(val) {
      this.currentPage=val
      this.load()
    },
    logout(){
      localStorage.removeItem("token")
      localStorage.removeItem("userInfo")
      this.$router.push("/login")
    }
  }
}
</script>

<style scoped lang="less">
//page_bar
.page_bar{
  text-align: right;
}


//滚动栏

/* 针对 Chrome, Edge 和 Safari */
::-webkit-scrollbar {
  width: 0;
  height: 0;
  display: none; /* Chrome, Safari, Opera */
}

.scrollable {
  overflow: auto;
  -ms-overflow-style: none;  /* IE 10+ */
  scrollbar-width: none;  /* Firefox */
}


//时间戳
/deep/ .el-timeline{

}
///deep/ .el-timeline>.el-timeline-item{
//  width: 500px;
//}


/*头像*/
/deep/ .el-avatar{
  width: 70px;
  height: 70px;
  line-height: 70px;
}

/*卡片*/
.text {
  font-size: 14px;
}

.item {
  padding: 18px 0;
}

.box-card {
  height: 250px;
  width: 400px;
  text-align: center;

  position: relative;
  top: 50%;
  left: 50%;
  transform:translate(-50%,-50%);
}



.el-header {
  color: #333;
}

.el-main {
  color: #333;
  height: 70vh;
  width: 60vw;

  position: relative;
  left: 50%;

  transform:translate(-50%,0);

}

</style>
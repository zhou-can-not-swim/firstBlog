<template>
  <div class="mblog">
    <h2> {{ blog.title }}</h2>
    <el-link icon="el-icon-edit">
      <router-link :to="{name: 'blogEdit', params: {blogId: blog.id}}" >
        编辑
      </router-link>
    </el-link>
    <el-divider></el-divider>
    <div class="markdown-body" v-html="blog.content"></div>

  </div>


</template>

<script>
import 'github-markdown-css'
import instance from "@/axios";

export default {
  name: "BlogDetail",
  data() {
    return {
      blog: {
        id: "",
        title: "",
        content: ""
      },
      ownBlog: true,
    }
  },
  methods: {},
  created() {
    const blogId = this.$route.params.blogId
    console.log(this.$route)

    instance.get('/blog/' + blogId).then(res => {
      const blog = res.data.data
      //拿到blog的信息
      console.log(blog)
      this.blog.id=blog.id
      this.blog.title=blog.title
      //对内容进行markdown


      const MardownIt = require("markdown-it")
      const md = new MardownIt()
      const result = md.render(blog.content)

      this.blog.content=result

    })
  }
}
</script>

<style scoped>
.mblog {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  width: available;
  height: inherit;

  padding: 20px 15px;




}
</style>
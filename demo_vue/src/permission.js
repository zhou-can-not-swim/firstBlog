import router from "./router";

// 路由判断登录 根据路由配置文件的参数
// 路由守卫
router.beforeEach((to, from, next) => {
    console.log("to.meta.requireToken-->"+to.meta.requireToken)
    if (to.meta.requireToken) { // 判断需要Token
        const token = localStorage.getItem("token")
        if (token) { //判断本地是否存在access_token
            next();
        } else {
            next({
                path: '/login'
            })
        }
    } else {
        next();
    }

})
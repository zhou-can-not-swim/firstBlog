import axios from "axios";
import {Message} from "element-ui";


// 使用由库提供的配置的默认值来创建实例
// 此时超时配置的默认值是 `0`
// 覆写库的超时默认值
// 现在，在超时前，所有请求都会等待 2.5 秒
const instance = axios.create({
    baseURL: "http://localhost:9090",
    timeout: 250000,
});
// 添加请求拦截器
instance.interceptors.request.use(
    function (config) {
        config.headers['Content-Type']="application/json;;charset=UTF-8"
        //TODO 添加token
        console.log("=====config======")
        console.log(config)
        console.log("=====结束======")
        // // 在发送请求之前做些什么
        // let token = getCookie("token");
        // config.headers["Authori-zation"] = "Bearer " + token;
        return config;
    },
    function (error) {
        // 对请求错误做些什么
        return Promise.reject(error);
    }
);
// 添加响应拦截器
instance.interceptors.response.use(
    function (response) {
        // 对响应数据做点什么
        console.log("=====response======")
        console.log(response)
        console.log("=====结束======")
        if (response.data.code !== 200) {
            Message.error(response.data.msg);
        }
        // res的返回信息
        return response;
    },
    function (error) {
        // 对响应错误做点什么
        return Promise.reject(error);
    }
);

export default instance



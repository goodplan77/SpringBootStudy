import axios from "axios";
import { getCookie } from "./Cookie";

// axios 전송시 header에 항상 access_token을 추가
const CustomAxios = axios.create({
    // headers : {
    //     "Authorization" : getCookie("accessToken")
    // }
    // 안되는 경우 : 요청을 보내고 쿠키를 받는 경우가 있음
})
CustomAxios.interceptors.request.use(function(request){
    // 끼어들 코드
    request.headers.Authorization = "Bearer " + getCookie("accessToken");

    return request;
})

export default CustomAxios;
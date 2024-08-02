import axios from "axios";
import { getCookie, removeCookie } from "./Cookie";

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
});

CustomAxios.interceptors.response.use(function(response){
    return response;
},
function (error){
    const {config , response:{status}} = error;

    // 403 -- 권한 없음 에러
    // 1) 실제로 권한이 없음 url을 요청하는 경우
    // 2) 로그인을 하지 않은 경우
    // 3) JWT 토큰이 만료된 경우
    if(status == 403){
        // 로그인 정보 지워주기
        removeCookie('accessToken'); // 비동기 함수
        removeCookie('user');
        window.location.href = '/';
    }
}
)

export default CustomAxios;
import KakaoLogin from "react-kakao-login";
import { LoginResponse } from "../type/user";
import axios from "../utils/CustomAxios";
import { setCookie } from "../utils/Cookie";

export default function KakaoLoginForm({setUser}:{setUser:(data:any)=>void}){
    const kakoJavascriptKey = process.env.REACT_APP_KAKAO_API_KEY as string;
    /*
        로그인 성공시 실행할 콜백함수
        인증 성공시 카카오서버에서 인가 코드를 발행
    */
    const kakaoOnSuccess = (data:{response:LoginResponse}) => {
        console.log('카카오에서 전달받은 토큰' , data);

        const ACCESS_TOKEN = data.response.access_token;

        // setCookie("accessToken" , ACCESS_TOKEN);

        // axios.get("https://kapi.kakao.com/v2/user/me")
        //     .then((res) => {
        //         console.log(res);
        //         const {properties} = res.data;
        //         const user = {
        //             nickName : properties.nickname,
        //             profile : properties.profile_image
        //         }
        //         setUser(user);
        //     })
        axios.post("http://localhost:8084/api/auth/login/kakao",{
            accessToken : ACCESS_TOKEN
        })
        .then(res => {
            console.log(res);
            const Jwtoken = res.data.jwtToken;
            setCookie("accessToken",Jwtoken);
            setCookie("user", JSON.stringify(res.data.user));
            setUser(res.data.user);
        })
    }

    const kakaoOnFail = (error:any) => {
        console.log(error);
    }
    
    return(
        <KakaoLogin token={kakoJavascriptKey}
        onSuccess={kakaoOnSuccess}
        onFail={kakaoOnFail}
        />
    )
}
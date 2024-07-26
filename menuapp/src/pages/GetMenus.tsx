import { useEffect } from 'react';
import Menus from '../components/Menus';
import axios from 'axios';
import { useDispatch } from 'react-redux';
import { selectAllMenu } from '../features/menuSlice';

const GetMenus = () => {

    const dispatch = useDispatch();

    useEffect(()=>{
        // Origin? 프로토콜 + ip 주소 + 포트번호
        // 브라우저상에서는 같은 Origin 끼리만 통신이 가능.
        axios.get("http://localhost:8082/api/menus") // rest 서버의 전체 주소를 적어야함
        .then((response) =>{
            dispatch(selectAllMenu(response.data));
        })
    },[])

    return (
        <>
            <div>
                <h4>전체 메뉴 조회(GET)</h4>
            </div>
            <Menus/>
        </>
    )
}
export default GetMenus;
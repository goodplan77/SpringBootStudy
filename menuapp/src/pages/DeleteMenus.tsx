import axios from "axios";
import { useState } from "react";

const DeleteMenus = () => {

    const [menuId , setMenuId] = useState('');

    const deleteMenu = () => {
        // CRUD
        // C (CREATE) => POST
        // R (READ) => GET
        // U (UPDATE) => PUT
        // D (DELETE) => DELETE
        axios.delete("http://localhost:8082/api/menu/" + menuId)
            .then((response) => {
                alert(response.data);
                setMenuId('');
            })
    }

    return (
        <div>
            <h4>메뉴 삭제하기(DELETE)</h4>
            <p>메뉴번호를 사용해 해당메뉴정보를 삭제함.</p>
            <input type="text" name="id" placeholder="메뉴번호" className="form-control" 
                value={menuId} onChange={(e)=>setMenuId(e.target.value)}
            /><br />
            <input type="button" onClick={deleteMenu} className="btn btn-block btn-outline-danger btn-send" value="삭제" />
        </div>
    )
}
export default DeleteMenus;
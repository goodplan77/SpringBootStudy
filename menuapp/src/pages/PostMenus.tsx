import { FormEvent, useState } from "react";
import { initialMenu, Menu } from "../type/menu";
import useInput from "../hook/useInput";
import axios from "axios";

const PostMenus = () => {

    const [newMenus , handleInputChange] = useInput<Menu>(initialMenu);

    const insertMenu = (e:FormEvent) => {
        e.preventDefault(); // 새로고침 막기
        axios.post("http://localhost:8082/api/menu",newMenus)
            .then((response) => {
                //console.log(response);
                alert(response.data.msg);
            })
            .catch((error) => {
                console.log(error);
            })
            .finally(() => {
                (e.target as HTMLFormElement).reset();
            })
    }

    return (
        <div className="menu-test">
            <h4>메뉴 등록하기(POST)</h4>
            <form id="menuEnrollFrm" onSubmit={insertMenu} >
                <input type="text" 
                name="restaurant" 
                placeholder="음식점" 
                className="form-control" 
                onChange={handleInputChange} />
                <input type="text" 
                name="name" 
                placeholder="메뉴" 
                className="form-control" 
                onChange={handleInputChange}/>
                <input type="number" 
                name="price" 
                placeholder="가격" 
                className="form-control" 
                onChange={handleInputChange}/>
                <div className="form-check form-check-inline">
                    <input type="radio" 
                    className="form-check-input" 
                    name="type" 
                    id="post-kr" 
                    value="kr" 
                    onChange={handleInputChange}/>
                    <label htmlFor="post-kr" className="form-check-label">한식</label>
                </div>
                <div className="form-check form-check-inline">    
                    <input type="radio" 
                    className="form-check-input" 
                    name="type" 
                    id="post-ch" 
                    value="ch" 
                    onChange={handleInputChange}/>
                    <label htmlFor="post-ch" className="form-check-label">중식</label>
                </div>
                <div className="form-check form-check-inline">    
                    <input type="radio" 
                    className="form-check-input" 
                    name="type" 
                    id="post-jp" 
                    value="jp" 
                    onChange={handleInputChange}/>
                    <label htmlFor="post-jp" className="form-check-label">일식</label>
                </div>
                <br />
                <div className="form-check form-check-inline">
                    <input type="radio" 
                    className="form-check-input" 
                    name="taste" 
                    id="post-hot" 
                    value="hot" 
                    onChange={handleInputChange}/>
                    <label htmlFor="post-hot" className="form-check-label">매운맛</label>
                </div>
                <div className="form-check form-check-inline">    
                    <input type="radio" 
                    className="form-check-input" 
                    name="taste" 
                    id="post-mild" 
                    value="mild" 
                    onChange={handleInputChange}/>
                    <label htmlFor="post-mild" className="form-check-label">순한맛</label>
                </div>
                <br />
                <input type="submit" className="btn btn-block btn-outline-success btn-send" value="등록"/>
            </form>
        </div>
    )

}
export default PostMenus;
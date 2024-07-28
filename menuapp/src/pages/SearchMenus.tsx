import { FormEvent } from "react";
import Menus from "../components/Menus";
import useInput from "../hook/useInput";
import { initialMenu, Menu } from "../type/menu";
import axios from "axios";
import { useDispatch } from "react-redux";
import { selectMenu } from '../features/menuSlice';

const SearchMenus = () => {

    const [searchMenu , handleInputChange] = useInput<Menu>([]);

    const dispatch = useDispatch();

    const searchMenus = (e:FormEvent) => {
        e.preventDefault(); // 새로고침 막기

        if(searchMenu.type == undefined || searchMenu.taste == undefined){
            alert("값을 다시 입력하세요.");
            return;
        }

        let queryData = "?type=" + (String)(searchMenu.type) + "&taste=" + (String)(searchMenu.taste)
        
        axios.get("http://localhost:8082/api/searchMenu" + queryData)
            .then((response) => {
                dispatch(selectMenu(response.data));
            })
            .catch((error) => {
                console.log(error);
            })
            .finally(() => {
                (e.target as HTMLFormElement).reset();
            })
    }
    
    return (
        <>
            <div>
                <h4>추천 메뉴(GET)</h4>
            </div>
            <form id="menuEnrollFrm" onSubmit={searchMenus} >
                    {/* <div className="form-check form-check-inline">
                        <input type="radio" 
                        className="form-check-input" 
                        name="type" 
                        id="post-all" 
                        value="all" 
                        onChange={handleInputChange}/>
                        <label htmlFor="post-kr" className="form-check-label">모두</label>
                    </div> */}
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
                    {/* <div className="form-check form-check-inline">
                        <input type="radio" 
                        className="form-check-input" 
                        name="taste" 
                        id="post-all" 
                        value="all" 
                        onChange={handleInputChange}/>
                        <label htmlFor="post-hot" className="form-check-label">모두</label>
                    </div> */}
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
            <Menus/>
        </>
    )
}
export default SearchMenus;
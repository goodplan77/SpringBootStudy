import { useSelector } from "react-redux";
import { RootState } from "../store/store";

const Menus = () => {
    const menus = useSelector((state:RootState) => state.menus);

    return (
        <div className="result">
            <table className='table'>
              <thead>
              <tr>
                  <th>번호</th>
                  <th>음식점</th>
                  <th>메뉴</th>
                  <th>가격</th>
                  <th>타입</th>
                  <th>맛</th>
              </tr>  
            </thead>
            <tbody>
                {
                    menus.map((menu) => {
                        return (
                            <tr key={menu.id}>
                                <td>{menu.id}</td>
                                <td>{menu.restaurant}</td>
                                <td>{menu.name}</td>
                                <td>{menu.price}</td>
                                <td>{menu.type}</td>
                                <td>{menu.taste}</td>
                            </tr>
                        )
                    })
                }
            </tbody> 
            </table>
        </div>
    )
}
export default Menus;
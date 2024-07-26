import { Link } from "react-router-dom";
const Header = () => {
    return(
        <header>
                    <div id="header-container">
                <h1 style={{textAlign:"center"}}>Menu App</h1>
                <div className="navbar bg-light navbar-expand ">
                    <ul className="navbar-nav">
                    <li className="nav-item">
                        <Link to="/" className="nav-link">메뉴 전체 조회</Link>
                    </li>
                    <li className="nav-item">
                        <Link to="/search" className="nav-link">메뉴 검색</Link>
                    </li>
                    <li className="nav-item">
                        <Link to="/insert" className="nav-link">메뉴 추가</Link>
                    </li>
                    <li className="nav-item">
                        <Link to="/update" className="nav-link">메뉴 수정</Link>
                    </li>
                    <li className="nav-item">
                        <Link to="/delete" className="nav-link">메뉴 삭제</Link>
                    </li>
                    </ul>
                </div>
            </div>   
        </header>
    )
}
export default Header;
import React, { useEffect, useState } from 'react';
import logo from './logo.svg';
import './App.css';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from './store/store';
import axios from 'axios';
import { Member } from './type/type';
import { logout, setUser } from './features/userSlice';
import { Route, Routes, useNavigate } from 'react-router-dom';
import ChattingRoomList from './pages/ChattingRoomList';
import ChattingRoom from './pages/ChattingRoom';

function App() {

  const user = useSelector((state:RootState) => state.user);
  const [userList , setUserList] = useState<Member[]>([]); // 화면상 관리되는 데이터는 state 사용?
  const [userNo , setUserNo] = useState(0);
  const dispatch = useDispatch();
  const navi = useNavigate();

  // 처음 컴포넌트 실행시 1회만 실행
  useEffect(() => {
    axios
    .get("http://localhost:8083/chatApp/allUsers")
    .then((response) => {
      setUserList(response.data); // Member[]
    })
  },[])

  const selectUser = () => {
    if(userNo == 0) return;
    let user = userList.find(u => u.userNo == userNo) as Member;
    dispatch(setUser(user));
  }

  return (
    <div className="App">
      {
        user.userNo == 0 ? (
          <>
            <h2>채팅 계정 선택</h2>
            <div className='card-wrapper'>
              {
                userList.map((user) => {
                  return (
                    <div className='card' key={user.userNo}>
                      <label>
                        <img src={user.profile}/>
                        <div className='user-info'>
                          <span className='user-nickname'>{user.nickName}</span>
                        </div>
                        <input type='radio' name='userNo' 
                        onChange={(e) => setUserNo(Number(e.target.value))}
                        value = {user.userNo}/>
                      </label>
                    </div>
                  )
                })
              }
            </div>
            <button onClick={selectUser}>선택</button>
          </>
        ) : (
        <>
          <div className="header">
            <div className='header-2'>
              <div className='user-info'>
                <img src={user.profile}/>
                <span className='user-nickname'>{user.nickName}</span>                
              </div>
              <button className='logout-btn' onClick={() => {
                dispatch(logout());
                navi("/");
              }}>로그아웃</button>
            </div>
          </div>
          <Routes>
            <Route path='/' element={<ChattingRoomList/>}></Route>
            <Route path='/list' element={<ChattingRoomList/>}></Route>
            <Route path='/detail/:chatRoomNo' element={<ChattingRoom/>}></Route>
          </Routes>
        </>
        ) 
      }
    </div>
  );
}

export default App;

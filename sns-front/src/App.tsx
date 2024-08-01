import React, { useState } from 'react';
import logo from './logo.svg';
import './App.css';
import { User } from './type/user';
import KakaoLoginForm from './component/KakaoLoginForm';


function App() {
  // 로그인 정보를 저장할 state
  const [user , setUser] = useState<User | null>(null);
  return (
    <div className="App">
      {
        user ? (
          <>
            <img src={user.profile}/>
            <div>
              <span>{user.nickName}</span>
            </div>
            <button onClick={() =>setUser(null)}>로그아웃</button>
          </>
        ) : (
          <KakaoLoginForm setUser={setUser}/>
        )
      }
    </div>
  );
}

export default App;

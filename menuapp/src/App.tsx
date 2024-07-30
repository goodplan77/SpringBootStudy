import './App.css';
import 'bootstrap/dist/css/bootstrap.css'
import Header from './components/Header'
import { Route, Routes } from 'react-router-dom';
import GetMenus from './pages/GetMenus';
import PostMenus from './pages/PostMenus';
import SearchMenus from './pages/SearchMenus';
import UpdateMenus from './pages/UpdateMenus';
import DeleteMenus from './pages/DeleteMenus';

function App() {
  return (
    <div className="App">
      <Header/>
      <section id='content'>
        <div id = 'menu-container' className='text-center'>
            <Routes>
              <Route path="/" element={<GetMenus/>}/>
              <Route path="/search" element={<SearchMenus/>}/>
              <Route path="/insert" element={<PostMenus/>}/>
              <Route path="/update" element={<UpdateMenus/>}/>
              <Route path="/delete" element={<DeleteMenus/>}/>
            </Routes>
        </div>
      </section>
    </div>
  );
}

export default App;

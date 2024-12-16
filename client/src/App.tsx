import React, { Children, ReactNode } from 'react';
import logo from './logo.svg';
import './App.css';
import { Routes, Route } from 'react-router-dom';
import Login from './Components/Pages/Login';
import Navbar from './Components/Navbar/Navbar';
import { UserProvider } from './Components/Context/UserContext';
import Ticket from './Components/Pages/Ticket';
import Logout from './Components/Pages/Logout';
import Leftbar from './Components/Leftbar/Leftbar';
import Account from './Components/Pages/Account';

function App() {
  return (
    <div className="App">
      <UserProvider>
        <Navbar/>
        
        <div className="content">
          <Leftbar/>
          <div className="page-content">
            <div className="page-content-data">
              <Routes>
                <Route path="/" element={<Ticket/>} />
                <Route path="/account" element={<Account/>} />
              </Routes>
            </div>
          </div>
        </div>

        <Routes>
          <Route path="/login" element={<Login bLogin={true} />} />
          <Route path="/register" element={<Login bLogin={false} />} />
          <Route path="/logout" element={<Logout/>} />
        </Routes>

      </UserProvider>
    </div>
  );
}

export default App;

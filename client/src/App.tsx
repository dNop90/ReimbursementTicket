import React from 'react';
import logo from './logo.svg';
import './App.css';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Login from './Components/Pages/Login';
import Navbar from './Components/Navbar/Navbar';
import { UserProvider } from './Components/Context/UserContext';
import Home from './Components/Pages/Home';
import Logout from './Components/Pages/Logout';

function App() {
  return (
    <div className="App">
      <UserProvider>
        <Navbar/>

        <Routes>
          <Route path="/" element={<Home/>} />

          <Route path="/login" element={<Login bLogin={true} />} />
          <Route path="/register" element={<Login bLogin={false} />} />
          <Route path="/logout" element={<Logout/>} />
          
        </Routes>
      </UserProvider>
    </div>
  );
}

export default App;

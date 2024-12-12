import React from 'react';
import logo from './logo.svg';
import './App.css';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Login from './Components/Login/Login';
import Navbar from './Components/Navbar/Navbar';

function App() {
  return (
    <BrowserRouter>
      <Navbar/>

      <Routes>
        <Route path="/login" element={<Login bLogin={true} />} />
        <Route path="/register" element={<Login bLogin={false} />} />
      </Routes>
    </BrowserRouter>
    
  );
}

export default App;

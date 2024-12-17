import React, { Children, ReactNode } from 'react';
import logo from './logo.svg';
import './App.css';
import { Routes, Route } from 'react-router-dom';
import Login from './Components/Pages/Login';
import Navbar from './Components/Navbar/Navbar';
import { UserProvider } from './Context/UserContext';
import Ticket from './Components/Pages/Ticket';
import Logout from './Components/Pages/Logout';
import Account from './Components/Pages/Account';
import UserRoute from './Components/RouteGuard/UserRoute';
import ManagerRoute from './Components/RouteGuard/ManagerRoute';
import Password from './Components/Pages/Password';
import UserList from './Components/Pages/UserList';
import NewTicket from './Components/Pages/NewTicket';
import TicketList from './Components/Pages/TicketList';

function App() {
  return (
    <div className="App">
      <UserProvider>
        <Navbar/>
        <Routes>
          <Route path="/login" element={<Login bLogin={true} />} />
          <Route path="/register" element={<Login bLogin={false} />} />
          <Route path="/logout" element={<Logout/>} />

          <Route element={<UserRoute/>}>
            <Route path="/" element={<Ticket/>} />
            <Route path="/ticket/new" element={<NewTicket/>} />
            <Route path="/account" element={<Account/>} />
            <Route path="/account/password" element={<Password/>} />

            <Route element={<ManagerRoute/>}>
              <Route path="/ticket/list" element={<TicketList/>} />
              <Route path="/user/list" element={<UserList/>} />
            </Route>
          </Route>
        </Routes>

      </UserProvider>
    </div>
  );
}

export default App;

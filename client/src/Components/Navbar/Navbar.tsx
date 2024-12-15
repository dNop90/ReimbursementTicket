import React, { useContext } from 'react'
import './Navbar.css'
import { Link } from 'react-router-dom'
import { UserContext } from '../Context/UserContext'
import {User} from '../../Data/User'

function Navbar() {
  const userContext = useContext(UserContext);
  const user = userContext?.user;

  return (
    <>
      <nav>
          <div className="left">
              <Link to="/">Home</Link>

              {
                user && user.role >= 0 &&
                <Link to="/new-ticket">New Ticket</Link>
              }

              {
                user && user.role >= 1 &&
                <Link to="/user-management">User Management</Link>
              }

          </div>
          
          {
            user &&
            <div className="right">
              <Link to="/logout">{(user as User).username}</Link>
            </div>
          }
      </nav>
    </>
  )
}

export default Navbar
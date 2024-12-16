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
      {
        user &&
        <nav>
          <div className="right">
            <ul>
              <span>{(user as User).username}</span>
              <li><Link to="/account">Account Information</Link></li>
              <li><Link to="/set-password">Change Password</Link></li>
              <li><Link to="/logout">Logout</Link></li>
            </ul>
            
          </div>
        </nav>
      }
    </>
  )
}

export default Navbar
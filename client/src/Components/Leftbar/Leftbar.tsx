import React, { useContext, useEffect } from 'react'
import './Leftbar.css'
import { Link, useNavigate } from 'react-router-dom'
import { UserContext } from '../Context/UserContext';

function Leftbar() {
  const userContext = useContext(UserContext);
  const user = userContext?.user;

  return (
    <>
        {
            user && 
            <div className="leftbar">
                <ul>
                    <span className="leftbar-category">Ticket</span>
                    <li><Link to="/">My Tickets</Link></li>
                    <li><Link to="/ticket/new">New Ticket</Link></li>
                    {
                        user.role > 0 &&
                        <>
                            <li><Link to="/ticket/list">Ticket List</Link></li>

                            <span className="leftbar-category">User</span>
                            <li><Link to="/user/list">User List</Link></li>
                        </>
                    }
                </ul>
            </div>
        }
    </>
  )
}

export default Leftbar
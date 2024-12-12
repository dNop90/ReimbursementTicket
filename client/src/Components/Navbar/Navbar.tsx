import React from 'react'
import './Navbar.css'
import { Link } from 'react-router-dom'

function Navbar() {
  return (
    <nav>
        <div className="left">
            <Link to="/">Home</Link>
        </div>
        <div className="right">
            <Link to="/login">Login</Link>
        </div>
    </nav>
  )
}

export default Navbar
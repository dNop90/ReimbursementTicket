import React, { useState } from 'react'
import './Login.css'
import { Link } from 'react-router-dom'

function Login(props: {bLogin: boolean})
{
  function onSubmitForm(event: any)
  {
    event.preventDefault();
    console.log("WORKING");
  }

  return (
    <form className="login-form" onSubmit={onSubmitForm}>
      <label htmlFor="username">Username</label>
      <input type="text" id="username" name="username" required/>
      <label htmlFor="password">Password</label>
      <input type="password" id="password" name="password" required/>

      {
        !props.bLogin &&
        <><label htmlFor="password2">Re-type Password</label>
        <input type="password" id="password2" name="password2" required/></>
      }

      {
        props.bLogin
        ? <><span className="login-form-info">Don't have an account? <Link to="/register">Switch to Register</Link></span>
        <button type="submit">Login</button></>
        : <><span className="login-form-info">Already have an account? <Link to="/login">Switch to Login</Link></span>
        <button type="submit">Register</button></>
      }
      
    </form>
  )
}

export default Login
import React, { useState } from 'react'
import './Login.css'
import { Link } from 'react-router-dom'

const API_DOMAIN = process.env.REACT_APP_API_DOMAIN;

function Login(props: {bLogin: boolean})
{
  const [state, setState] = useState(
    {
      error: false,
      error_messsage: ""
    }
  );

  /**
   * When login/register form is submit
   * We will grab the form data and determine whenever it's login or register
   * @param event The event of the form
   */
  function onSubmitForm(event: any)
  {
    event.preventDefault();
    const formData = new FormData(event.currentTarget);

    if(props.bLogin)
    {
      userLogin(formData)
    }
    else
    {
      userRegister(formData);
    }
  }

  /**
   * When submit a form and its for login
   * @param formData The formdata containing user login information
   */
  async function userLogin(formData: FormData)
  {
    let reponse = await fetch("")
  }

  /**
   * When submit form and its for register new user
   * @param formData The formdata containing register information
   */
  async function userRegister(formData: FormData)
  {
    //If password didn't match
    if(formData.get("password") !== formData.get("password2"))
    {
      setState({
        ...state,
        error: true,
        error_messsage: "Password didn't match."
      });

      return;
    }

    //Send to API to register the user
    try
    {
      let response = await fetch(`${API_DOMAIN}/register`,{
        method: "POST",
        headers: {
          "Content-Type": "application/json; charset=UTF-8"
        },
        body: JSON.stringify({
          username: formData.get("username"),
          password: formData.get("password")
        })
      });
  
      //Check status
      let status = response.status;
      if(status != 200)
      {
        throw status;
      }

      //Get data as json and check if there's error
      let data = await response.json();
      if(data.hasOwnProperty("error"))
      {
        setState({
          ...state,
          error: true,
          error_messsage: data["error"]
        });

        return;
      }
      
    }
    catch(e)
    {
      setState({
        ...state,
        error: true,
        error_messsage: "Unknown error. Failed to register an account."
      });
    }
  }

  return (
    <form className="login-form" onSubmit={onSubmitForm}>
      {
        state.error &&
        <>
          <span className="login-form-error">{state.error_messsage}</span>
        </>
      }

      <label htmlFor="username">Username</label>
      <input type="text" id="username" name="username" required/>
      <label htmlFor="password">Password</label>
      <input type="password" id="password" name="password" required/>

      {
        !props.bLogin &&
        <> 
          <label htmlFor="password2">Re-type Password</label>
          <input type="password" id="password2" name="password2" required/>
        </>
      }

      {
        props.bLogin
        ? <>
          <span className="login-form-info">Don't have an account? <Link to="/register">Register</Link></span>
          <button type="submit">Login</button>
        </>
        : <>
          <span className="login-form-info">Already have an account? <Link to="/login">Login</Link></span>
          <button type="submit">Register</button>
        </>
      }
      
    </form>
  )
}

export default Login
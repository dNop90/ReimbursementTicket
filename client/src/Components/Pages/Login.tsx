import React, { useContext, useEffect, useState } from 'react'
import './Login.css'
import { Link, useNavigate } from 'react-router-dom'
import { UserContext } from '../../Context/UserContext';

const API_DOMAIN = process.env.REACT_APP_API_USER;

function Login(props: {bLogin: boolean})
{
  const userContext = useContext(UserContext);
  const navigate = useNavigate();

  /**
   * We will check if the user has login or not in useEffect
   */
  useEffect(function()
  {
    if(userContext?.user)
    {
      //If login then we will go to homepage
      navigate("/");
    }
  });


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
    {
      //Check username and password
      let username = formData.get("username");
      let password = formData.get("password");
      if(!username && !password)
      {
        setState({
          ...state,
          error: true,
          error_messsage: "Missing username or password."
        });
  
        return;
      }
  
      //Send to API to login the user
      try
      {
        let response = await fetch(`${API_DOMAIN}/login`,{
          method: "POST",
          headers: {
            "Content-Type": "application/json; charset=UTF-8"
          },
          body: JSON.stringify({
            username,
            password
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

        //Set user login
        userContext?.login(data.userID, data.username, data.role);
        
        //Back to home page if successfully login
        navigate("/");
      }
      catch(e)
      {
        setState({
          ...state,
          error: true,
          error_messsage: "Unknown error. Failed to login."
        });
      }
    }
  }

  /**
   * When submit form and its for register new user
   * @param formData The formdata containing register information
   */
  async function userRegister(formData: FormData)
  {
    //Check username and password
    let username = formData.get("username");
    let password = formData.get("password");
    if(!username && !password)
    {
      setState({
        ...state,
        error: true,
        error_messsage: "Missing username or password."
      });

      return;
    }
    //If password didn't match
    else if(password !== formData.get("password2"))
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
          username,
          password
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
      
      //Login after register
      userContext?.login(data.userID, data.username, data.role);

      //Back to home page if successfully register
      navigate("/");
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
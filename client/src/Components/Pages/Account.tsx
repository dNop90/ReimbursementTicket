import React, { useContext, useEffect, useState } from 'react'
import './Account.css'
import { UserContext } from '../../Context/UserContext';

const API_DOMAIN = process.env.REACT_APP_API_USER;

function Account() {
  const userContext = useContext(UserContext);

  const [state, setState] = useState(
    {
      loaded: false,
      error: false,
      success: false,
      error_message: "",
      formData: {
        email: "",
        firstname: "",
        lastname: "",
        address: ""
      }
    }
  );

  useEffect(function()
  {
    if(!state.loaded)
    {
      getUserAccountInfo();
    }
    
  });

  /**
   * Get the user account information
   * This should execute once after page loaded to fill the form
   * @returns 
   */
  async function getUserAccountInfo()
  {
    try
    {
      let response = await fetch(`${API_DOMAIN}/account`,{
        method: "POST",
        headers: {
          "Content-Type": "application/json; charset=UTF-8"
        },
        body: JSON.stringify({
          username: userContext?.user?.username
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
          loaded: true,
          error: true,
          success: false,
          error_message: data["error"] as string
        });

        return;
      }
      
      setState({
        ...state,
        loaded: true,
        formData: data
      });
    }
    catch(e)
    {
      setState({
        ...state,
        loaded: true,
        error: true,
        success: false,
        error_message: "Unknown error. Failed to retrieve your account information."
      });
    }
  }

  function onSubmitAccountForm(event: any)
  {
    event.preventDefault();
    const formData = new FormData(event.currentTarget);

    updateUserAccountInfo(formData);
  }

  /**
   * Update user information
   * @param formData The formdata containing the user information
   * @returns 
   */
  async function updateUserAccountInfo(formData: FormData)
  {
    try
    {
      let email = formData.get("email");
      let firstname = formData.get("firstname");
      let lastname = formData.get("lastname");
      let address = formData.get("address");

      let response = await fetch(`${API_DOMAIN}/account`,{
        method: "PATCH",
        headers: {
          "Content-Type": "application/json; charset=UTF-8"
        },
        body: JSON.stringify({
          username: userContext?.user?.username,
          email, firstname, lastname, address
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
          success: false,
          error_message: data["error"] as string
        });

        return;
      }
      else if(data.hasOwnProperty("success"))
      {
        setState({
          ...state,
          error: true,
          success: true,
          error_message: "Successfully updated.",
          formData: data
        });

        return;
      }
    }
    catch(e)
    {
      setState({
        ...state,
        error: true,
        success: false,
        error_message: "Unknown error. Failed to retrieve your account information."
      });
    }
  }

  return (
    <form className="account-form" onSubmit={onSubmitAccountForm}>
      {
        state.error && !state.success &&
        <>
          <span className="account-form-error">{state.error_message}</span>
        </>
      }

      {
        state.error && state.success &&
        <>
          <span className="account-form-error success">{state.error_message}</span>
        </>
      }

      <label htmlFor="username">Username</label>
      <input type="text" id="username" name="username" defaultValue={userContext?.user?.username} disabled/>
      <label htmlFor="email">Email</label>
      <input type="email" id="email" name="email" defaultValue={state.formData.email || ""} />
      <label htmlFor="firstname">First Name</label>
      <input type="text" id="firstname" name="firstname" defaultValue={state.formData.firstname || ""}/>
      <label htmlFor="lastname">Last Name</label>
      <input type="text" id="lastname" name="lastname" defaultValue={state.formData.lastname || ""}/>
      <label htmlFor="address">Address</label>
      <input type="text" id="address" name="address" defaultValue={state.formData.address || ""}/>
      <button type="submit">Update</button>
    </form>
  )
}

export default Account
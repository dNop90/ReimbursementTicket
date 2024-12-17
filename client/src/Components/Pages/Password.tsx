import React, { useContext, useState } from 'react'
import './Account.css'
import { UserContext } from '../../Context/UserContext';

const API_DOMAIN = process.env.REACT_APP_API_USER;

function Password() {
  const userContext = useContext(UserContext);
  
  const [state, setState] = useState(
    {
      error: false,
      success: false,
      error_message: ""
    }
  );

  /**
   * Trigger when set password form is submitted
   * @param event Event of the form
   */
  function onSubmitAccountSetPasswordForm(event: any)
  {
    event.preventDefault();
    const formData = new FormData(event.currentTarget);

    changeUserAccountPassword(formData);
  }

  /**
   * Change the user password
   * @param formData The form data with new password to change
   * @returns 
   */
  async function changeUserAccountPassword(formData: FormData)
  {
    try
    {
      let currentpassword = formData.get("currentpassword");
      let newpassword = formData.get("newpassword");
      let newpassword2 = formData.get("newpassword2");

      if(newpassword != newpassword2)
      {
        setState({
          error: true,
          success: false,
          error_message: "New password does not match."
        });

        return;
      }

      let response = await fetch(`${API_DOMAIN}/password`,{
        method: "PATCH",
        headers: {
          "Content-Type": "application/json; charset=UTF-8"
        },
        body: JSON.stringify({
          username: userContext?.user?.username,
          current: currentpassword, new: newpassword
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
          error: true,
          success: false,
          error_message: data["error"] as string
        });

        return;
      }
      else if(data.hasOwnProperty("success"))
      {
        setState({
          error: true,
          success: true,
          error_message: "Successfully updated."
        });

        return;
      }
    }
    catch(e)
    {
      setState({
        error: true,
        success: false,
        error_message: "Unknown error. Failed to retrieve your account information."
      });
    }
  }

  return (
    <form className="account-form" onSubmit={onSubmitAccountSetPasswordForm}>
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

      <label htmlFor="currentpassword">Current Password</label>
      <input type="password" id="currentpassword" name="currentpassword"/>
      <label htmlFor="newpassword">New Password</label>
      <input type="password" id="newpassword" name="newpassword"/>
      <label htmlFor="newpassword2">Re-type New Password</label>
      <input type="password" id="newpassword2" name="newpassword2"/>
      <button type="submit">Update</button>
    </form>
  )
}

export default Password
import React, { useContext, useEffect } from 'react'
import { UserContext } from '../../Context/UserContext';
import {useNavigate } from 'react-router-dom';

function Logout() {
  const userContext = useContext(UserContext);
  const navigate = useNavigate();

  /**
  * We will check if the user has login or not in useEffect
  */
  useEffect(function()
  {
    if(userContext?.user)
    {
      //If login then we will remove user from the usercontext
      userContext.logout();
    }

    navigate("/");
  });

  return (
    <></>
  )
}

export default Logout
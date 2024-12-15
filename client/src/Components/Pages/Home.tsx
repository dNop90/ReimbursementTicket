import React, { useContext, useEffect } from 'react'
import { useNavigate } from 'react-router-dom'
import { UserContext } from '../Context/UserContext';

function Home() {
  const userContext = useContext(UserContext);
  const navigate = useNavigate();

  /**
   * We will check if the user has login or not in useEffect
   */
  useEffect(function()
  {
    if(!userContext?.user)
    {
      //If not login then we will go to the login page
      navigate("/login");
    }
  });

  return (
    <div>Home</div>
  )

}

export default Home
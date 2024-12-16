import React, {useContext } from 'react'
import { UserContext } from '../Context/UserContext';
import { Navigate, Outlet } from 'react-router-dom';
import Leftbar from '../Leftbar/Leftbar';

function UserRoute() {
  const userContext = useContext(UserContext);
  const user = userContext?.user;
  
  if(!user)
  {
    return <Navigate to="/login" replace/>;
  }

  return(
    <div className="content">
      <Leftbar/>
      <div className="page-content">
        <div className="page-content-data">
          <Outlet/>
        </div>
      </div>
    </div>
  );
}

export default UserRoute
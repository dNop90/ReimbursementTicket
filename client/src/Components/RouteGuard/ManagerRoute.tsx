import React, { useContext } from 'react'
import { Navigate, Outlet } from 'react-router-dom';
import { UserContext } from '../Context/UserContext';

function ManagerRoute() {
    const userContext = useContext(UserContext);
    const user = userContext?.user;

    //Check if user has role
    if(!user || (user && user.role < 1))
    {
        return <Navigate to="/" replace/>;
    }
    
    return (
        <Outlet/>
    )
}

export default ManagerRoute
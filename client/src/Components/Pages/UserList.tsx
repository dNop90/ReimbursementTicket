import React, { useEffect, useRef, useState } from 'react'
import './Table.css'

const API_DOMAIN = process.env.REACT_APP_API_USER;

function UserList() {
    const [state, setState] = useState<any>({
        loaded: false,
        data: {}
    });

    //Tablesort
    const usertablelist = useRef(null);
    let tablesort_usertable : any = null;

    useEffect(function()
    {
        if(!state.loaded)
        {
            getUserList();

            //Tablesort
            const TableSort = (window as any).Tablesort;

            if(!tablesort_usertable)
            {
                tablesort_usertable = new TableSort(usertablelist.current);
            }
        }
    });

    /**
     * Get the user list when the page load
     */
    async function getUserList()
    {
        try
        {
            let response = await fetch(`${API_DOMAIN}/list`);
    
            //Check status
            let status = response.status;
            if(status != 200)
            {
                throw status;
            }

            let data = await response.json();
            
            setState({loaded: true, data});
            tablesort_usertable?.refresh();
        }
        catch(e)
        {
            setState({loaded: false, data: {}});
        }
    }

    async function updateUserRole(id: number, role: number)
    {
        try
        {
            let response = await fetch(`${API_DOMAIN}/role`,{
                method: "PATCH",
                headers: {
                  "Content-Type": "application/json; charset=UTF-8"
                },
                body: JSON.stringify({id, role})
            });

            //Check status
            let status = response.status;
            if(status != 200)
            {
            throw status;
            }
    
            //Get data as json
            let data = await response.json();
        }
        catch(e)
        {

        }
    }

    /**
     * On role change from the selection in the table
     * We will update the role
     * @param event The event of the element
     */
    function onRoleChange(event: any)
    {
        let userID = event.currentTarget.getAttribute("data-user-id");
        let option = event.currentTarget.value;
        
        updateUserRole(userID, option);
    }

    return (
        <table ref={usertablelist}>
            <thead>
                <tr data-sort-method="none">
                    <th>ID</th>
                    <th>Username</th>
                    <th>Email</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Address</th>
                    <th>Role</th>
                </tr>
            </thead>
            
            {state.loaded &&
            <tbody>
                {
                    state.data.users.map((user: any, index: number) => (
                        <tr>
                            <td>{user.userID}</td>
                            <td>{user.username}</td>
                            <td>{user.email || ""}</td>
                            <td>{user.firstname || ""}</td>
                            <td>{user.lastname || ""}</td>
                            <td>{user.address || ""}</td>
                            <td>
                                {
                                    user.role > 1 ?
                                    (<>Owner</>)
                                    :
                                    (<select data-user-id={user.userID} onChange={onRoleChange}>
                                        <option value="0" selected={user.role == 0}>Employee</option>
                                        <option value="1" selected={user.role == 1}>Manager</option>
                                    </select>)
                                }
                            </td>
                        </tr>
                    ))
                }
            </tbody>
            }
        </table>
    )
}

export default UserList
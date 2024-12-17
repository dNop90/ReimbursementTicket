import React, { useContext, useEffect, useState } from 'react'
import './Account.css'
import { UserContext } from '../../Context/UserContext';
import { useNavigate } from 'react-router-dom';

const API_TICKET = process.env.REACT_APP_API_TICKET;
const API_REIMBURSEMENT = process.env.REACT_APP_API_REIMBURSEMENT;

function NewTicket() {
    const userContext = useContext(UserContext);
    const navigate = useNavigate();
      
    const [state, setState] = useState(
        {
            error: false,
            success: false,
            error_message: "",
            reimbursement_types: {} as any
        }
    );

    useEffect(function()
    {
        if(!Object.keys(state.reimbursement_types).length)
        {
            getReimbursementTypes();
        }
    });

    async function getReimbursementTypes()
    {
        try
        {
            let response = await fetch(`${API_REIMBURSEMENT}/types`);
            
            let status = response.status;
            if(status != 200)
            {
                throw status;
            }
    
            let data = await response.json();

            setState({...state, reimbursement_types: data});
        }
        catch(e)
        {

        }
    }

    async function createNewTicket(formData: FormData)
    {
        try
        {
            let description = formData.get("description");
            let amount = formData.get("amount");
            let reimbursement_type = formData.get("reimbursement_type");

            if(!reimbursement_type)
            {
                setState({
                    ...state,
                    error: true,
                    success: false,
                    error_message: "Missing reimburstment type."
                });

                return;
            }

            let response = await fetch(`${API_TICKET}/new`,{
                method: "POST",
                headers: {
                    "Content-Type": "application/json; charset=UTF-8"
                },
                body: JSON.stringify({
                    description,
                    amount: String(amount),
                    typeid: String(reimbursement_type),
                    submitterid: String(userContext?.user?.id)
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
            
            navigate("/");
        }
        catch(e)
        {
            setState({
                ...state,
                error: true,
                success: false,
                error_message: "Unknown error. Failed to create new ticket."
            });
        }
    }

    function onSubmitNewTicketForm(event: any)
    {
        event.preventDefault();
        const formData = new FormData(event.currentTarget);
  
        createNewTicket(formData);
    }

    return (
        <form className="account-form" onSubmit={onSubmitNewTicketForm}>
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

            <label htmlFor="description">Description</label>
            <input type="text" id="description" name="description" required/>
            <label htmlFor="amount">Amount</label>
            <input type="number" step="0.01" id="amount" name="amount" min="0.01" required/>
            <label htmlFor="reimbursement_type">Reimburstment Type</label>
            <select name="reimbursement_type">
                {
                    state.reimbursement_types.types?.map((reimbtype: any, index: number) => (
                        (<option value={reimbtype.typeID}>{reimbtype.typename}</option>)
                    ))
                }
            </select>
            <button type="submit">Create New Ticket</button>
        </form>
    )
}

export default NewTicket
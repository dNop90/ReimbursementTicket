import React, { useContext, useEffect, useRef, useState } from 'react'
import { UserContext } from '../../Context/UserContext';
import './Table.css'

const API_TICKET = process.env.REACT_APP_API_TICKET;
const API_REIMBURSEMENT = process.env.REACT_APP_API_REIMBURSEMENT;

function TicketList() {
  const userContext = useContext(UserContext);
  
    const [state, setState] = useState<any>({
        loaded: false,
        data: {},
        active: [],
        archived: []
    });
  
    //Tablesort
    const currentactivetickets = useRef(null);
    const archivedtickets = useRef(null);
    let tablesort_activetickets : any = null;
    let tablesort_archivedtickets: any = null;
    
    useEffect(function()
    {
        if(!state.loaded)
        {
            getAllTickets();
  
            //Tablesort
            const TableSort = (window as any).Tablesort;
  
            if(!tablesort_activetickets)
            {
              tablesort_activetickets = new TableSort(currentactivetickets.current);
            }
            
            if(!tablesort_archivedtickets)
            {
              tablesort_archivedtickets = new TableSort(archivedtickets.current);
            }
        }
    });
  
    /**
     * Get user tickets
     * Separate them into 2 tables
     * The active and the achived
     */
    async function getAllTickets()
    {
      try
      {
        let responseType = await fetch(`${API_REIMBURSEMENT}/types`);
              
        let statusType = responseType.status;
        if(statusType != 200)
        {
            throw statusType;
        }
  
        let reimbursementData = await responseType.json();
  
        //Convert reimbursementType to object
        let reimbursementType: any = {};
        for(const type of reimbursementData.types)
        {
          reimbursementType[type.typeID] = type.typename;
        }
  
  
        //Get user tickets
        let response = await fetch(`${API_TICKET}/all`);
  
        //Check status
        let status = response.status;
        if(status != 200)
        {
            throw status;
        }
  
        let data = await response.json();
  
        //Separate them into active and archived
        let active_tickets = [];
        let archived_tickets = [];
  
        for(const ticket of data.tickets)
        {
          ticket.typeID = reimbursementType[ticket.typeID];
  
          if(ticket.status == 0)
          {
            ticket['statusname'] = 'In Progress';
            active_tickets.push(ticket);
            continue;
          }
  
          switch(ticket.status)
          {
            case 1:
              ticket['statusname'] = 'Denied';
              break;
            case 2:
              ticket['statusname'] = 'Approved';
              break;
            default:
              ticket['statusname'] = 'Unknown';
          }
  
          archived_tickets.push(ticket);
        }
        
        setState({loaded: true, data, active: active_tickets, archived: archived_tickets});
  
        tablesort_activetickets?.refresh();
        tablesort_archivedtickets?.refresh();
      }
      catch(e)
      {
        setState({loaded: false, data: {}, active: [], archived: []});
      }
    }

    /**
     * Update the ticket status to the API
     * @param ticketid The ID of the ticket we will be update
     * @param status The new status for the ticket
     */
    async function updateTicketStatus(ticketid: number, ticketstatus: number)
    {
        try
        {
            let response = await fetch(`${API_TICKET}/status`,{
                method: "PATCH",
                headers: {
                  "Content-Type": "application/json; charset=UTF-8"
                },
                body: JSON.stringify({ticketid, status: ticketstatus, userid: userContext?.user?.id})
            });

            //Check status
            let status = response.status;
            if(status != 200)
            {
                throw status;
            }
    
            //Get data as json
            let data = await response.json();

            if(data?.success)
            {
                //Refresh tables
                getAllTickets();
            }
        }
        catch(e)
        {

        }
    }

    /**
     * When ticket status change then we will update that ticket
     * @param event The select event
     */
    function onTicketStatusChange(event: any)
    {
        let ticketid = event.currentTarget.getAttribute("data-ticket-id");
        let status = event.currentTarget.value;

        updateTicketStatus(ticketid, status);
    }
  
    return (
      <>
        <span className="tabletitle">Active</span>
        <table ref={currentactivetickets}>
            <thead>
                <tr data-sort-method="none">
                    <th>ID</th>
                    <th>Status</th>
                    <th>Description</th>
                    <th>Amount</th>
                    <th>Type</th>
                    <th>Submitter</th>
                    <th data-sort-default>Created At</th>
                </tr>
            </thead>
            
            {state.loaded &&
            <tbody>
                {
                    state.active.map((ticket: any, index: number) => (
                        <tr>
                            <td>{ticket.ticketID}</td>
                            <td>
                                <select data-ticket-id={ticket.ticketID} onChange={onTicketStatusChange}>
                                    <option value="0" selected={ticket.status == 0}>In Progress</option>
                                    <option value="1" selected={ticket.status == 1}>Denied</option>
                                    <option value="2" selected={ticket.status == 2}>Approved</option>
                                </select>
                            </td>
                            <td>{ticket.description || ""}</td>
                            <td>{ticket.amount || ""}</td>
                            <td>{ticket.typeID || ""}</td>
                            <td>{ticket.submitterName || ""}</td>
                            <td>{ticket.createdAt ? (new Date(ticket.createdAt)).toLocaleString() : ""}</td>
                        </tr>
                    ))
                }
            </tbody>
            }
        </table>
  
        <span className="tabletitle">Archived</span>
        <table ref={archivedtickets}>
            <thead>
                <tr data-sort-method="none">
                    <th>ID</th>
                    <th>Status</th>
                    <th>Description</th>
                    <th>Amount</th>
                    <th>Type</th>
                    <th>Submitter</th>
                    <th>Created At</th>
                    <th>Assignee</th>
                    <th data-sort-default>Completed At</th>
                </tr>
            </thead>
            
            {state.loaded &&
            <tbody>
                {
                    state.archived.map((ticket: any, index: number) => (
                        <tr>
                            <td>{ticket.ticketID}</td>
                            <td>{ticket.statusname}</td>
                            <td>{ticket.description || ""}</td>
                            <td>{ticket.amount || ""}</td>
                            <td>{ticket.typeID || ""}</td>
                            <td>{ticket.submitterName || ""}</td>
                            <td>{ticket.createdAt ? (new Date(ticket.createdAt)).toLocaleString() : ""}</td>
                            <td>{ticket.assigneeName || ""}</td>
                            <td>{ticket.completedAt ? (new Date(ticket.completedAt)).toLocaleString() : ""}</td>
                        </tr>
                    ))
                }
            </tbody>
            }
        </table>
      </>
    )
}

export default TicketList
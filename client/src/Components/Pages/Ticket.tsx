import React, { useContext, useEffect, useRef, useState } from 'react'
import { UserContext } from '../../Context/UserContext';
import './Table.css'

const API_TICKET = process.env.REACT_APP_API_TICKET;
const API_REIMBURSEMENT = process.env.REACT_APP_API_REIMBURSEMENT;

function Ticket() {
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
          getUserTickets();

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
  async function getUserTickets()
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
      let response = await fetch(`${API_TICKET}/user`,{
        method: "POST",
        headers: {
          "Content-Type": "application/json; charset=UTF-8"
        },
        body: JSON.stringify({userid: userContext?.user?.id})
      });

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
          ticket.status = 'In Progress';
          active_tickets.push(ticket);
          continue;
        }

        switch(ticket.status)
        {
          case 1:
            ticket.status = 'Denied';
            break;
          case 2:
            ticket.status = 'Approved';
            break;
          default:
            ticket.status = 'Unknown';
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
                  <th data-sort-default>Created At</th>
              </tr>
          </thead>
          
          {state.loaded &&
          <tbody>
              {
                  state.active.map((ticket: any, index: number) => (
                      <tr>
                          <td>{ticket.ticketID}</td>
                          <td>{ticket.status}</td>
                          <td>{ticket.description || ""}</td>
                          <td>{ticket.amount || ""}</td>
                          <td>{ticket.typeID || ""}</td>
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
                  <th data-sort-default>Created At</th>
              </tr>
          </thead>
          
          {state.loaded &&
          <tbody>
              {
                  state.archived.map((ticket: any, index: number) => (
                      <tr>
                          <td>{ticket.ticketID}</td>
                          <td>{ticket.status}</td>
                          <td>{ticket.description || ""}</td>
                          <td>{ticket.amount || ""}</td>
                          <td>{ticket.typeID || ""}</td>
                          <td>{ticket.createdAt ? (new Date(ticket.createdAt)).toLocaleString() : ""}</td>
                      </tr>
                  ))
              }
          </tbody>
          }
      </table>
    </>
  )

}

export default Ticket
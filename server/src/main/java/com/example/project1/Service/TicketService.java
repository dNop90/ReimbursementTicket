package com.example.project1.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.project1.Entity.Ticket;
import com.example.project1.Repository.TicketRepository;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository)
    {
        this.ticketRepository = ticketRepository;
    }

    /**
     * Create a new ticket to the database
     * @param description The description of the ticket
     * @param amount The cost
     * @param typeID What type of imbursement
     * @param submitterID The userID
     * @return The ticket that created
     */
    public Ticket createNewTicket(String description, Double amount, Integer typeID, Integer submitterID)
    {
        Ticket newticket = new Ticket(description, amount, 0, typeID, submitterID);
        return ticketRepository.save(newticket);
    }

    /**
     * Get a list of ticket that was submitted by specific user
     * @param submitterID The userID to look for
     * @return A list of tickets from the submitter
     */
    public List<Ticket> getTickets(Integer submitterID)
    {
        return ticketRepository.findBySubmitterIDOrderByCreatedAtDesc(submitterID);
    }

    /**
     * Get all the ticket
     * The order of the ticket will be based on the created time desc
     * @return A list of ticket
     */
    public List<Ticket> getTickets()
    {
        return ticketRepository.findAllByOrderByCreatedAtDesc();
    }

    /**
     * Get a ticket based on the ticket ID
     * @param id The ticket ID to search for
     * @return The ticket if exist otherwise null
     */
    public Ticket getTicketByID(Integer id)
    {
        return ticketRepository.findById(id).orElse(null);
    }

    /**
     * Update a ticket data
     * @param ticket The ticket to update
     * @return The ticket that updated
     */
    public Ticket updateTicket(Ticket ticket)
    {
        return ticketRepository.save(ticket);
    }
}

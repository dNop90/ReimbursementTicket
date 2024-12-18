package com.example.project1.Controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project1.Data.TicketAndUser;
import com.example.project1.Entity.ReimbursementType;
import com.example.project1.Entity.Ticket;
import com.example.project1.Entity.User;
import com.example.project1.Service.ReimbursementTypeService;
import com.example.project1.Service.TicketService;
import com.example.project1.Service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;



@CrossOrigin
@RestController
@RequestMapping("/api/ticket")
public class TicketController {
    private final UserService userService;
    private final TicketService ticketService;
    private final ReimbursementTypeService reimbursementTypeService;

    public TicketController(UserService userService, TicketService ticketService, ReimbursementTypeService reimbursementTypeService)
    {
        this.userService = userService;
        this.ticketService = ticketService;
        this.reimbursementTypeService = reimbursementTypeService;
    }

    /**
     * Create new ticket
     * @param payload data for creating new ticket
     * @return success or error
     */
    @PostMapping("/new")
    public ResponseEntity<Object> createNewTicket(@RequestBody Map<String, Object> payload) {
        if(payload == null)
        {
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("error", "Invalid data.");
            return ResponseEntity.ok(data);
        }

        if(payload.get("description") == null || payload.get("amount") == null || payload.get("typeid") == null || payload.get("submitterid") == null)
        {
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("error", "Missing data.");
            return ResponseEntity.ok(data);
        }

        String description = payload.get("description").toString();
        String strAmount = payload.get("amount").toString();
        String strTypeID = payload.get("typeid").toString();
        String SubmitterID = payload.get("submitterid").toString();

        Double amount = Double.parseDouble(strAmount);
        Integer typeid = Integer.parseInt(strTypeID);
        Integer submitterid = Integer.parseInt(SubmitterID);

        if(amount <= 0.0f)
        {
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("error", "Amount must be greater than 0.");
            return ResponseEntity.ok(data);
        }

        //Check if typeID exist
        ReimbursementType type = reimbursementTypeService.getType(typeid);
        if(type == null)
        {
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("error", "Invalid reimbursement type.");
            return ResponseEntity.ok(data);
        }

        ticketService.createNewTicket(description, amount, typeid, submitterid);

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("success", true);
        return ResponseEntity.ok(data);
    }

    @PostMapping("/user")
    public ResponseEntity<Object> getTickets(@RequestBody Map<String, Object> payload) {
        if(payload == null)
        {
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("error", "Invalid data.");
            return ResponseEntity.ok(data);
        }
        
        if(payload.get("userid") == null)
        {
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("error", "Missing data.");
            return ResponseEntity.ok(data);
        }

        String strUserID = payload.get("userid").toString();
        Integer userid = Integer.parseInt(strUserID);
        
        List<Ticket> tickets = ticketService.getTickets(userid);
        
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("tickets", tickets);
        return ResponseEntity.ok(data);
    }
    
    /**
     * Get all the tickets
     * @return A list of ticket
     */
    @GetMapping("/all")
    public ResponseEntity<Object> getAllTickets() {
        List<Ticket> tickets = ticketService.getTickets();
        List<TicketAndUser> ticketdata = new ArrayList<TicketAndUser>();

        //Loop through each ticket data
        for(Ticket ticket : tickets)
        {
            //Create new TicketAndUser data
            TicketAndUser tempdata = new TicketAndUser(ticket.getTicketID(), ticket.getDescription(), ticket.getAmount(),
                ticket.getStatus(), ticket.getTypeID(), ticket.getCreatedAt(), ticket.getCompletedAt());

            
            //Search for the submitter user and the assignee user
            User submitterUser = userService.getUser(ticket.getSubmitterID());
            if(submitterUser == null)
            {
                continue;
            }
            tempdata.setSubmitterName(submitterUser.getUsername());

            if(ticket.getAssigneeID() != null)
            {
                User assignUser = userService.getUser(ticket.getAssigneeID());
                tempdata.setAssigneeName(assignUser.getUsername());
            }

            //Add to the over all data
            ticketdata.add(tempdata);
        }

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("tickets", ticketdata);
        return ResponseEntity.ok(data);
    }

    /**
     * Update the ticket status
     * @param payload The payload contain the ticket ID, the new status, and the assignee
     * @return Success or failure
     */
    @PatchMapping("/status")
    public ResponseEntity<Object> updateTicketStatus(@RequestBody Map<String, Object> payload) {
        if(payload == null)
        {
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("error", "Invalid data.");
            return ResponseEntity.ok(data);
        }
        
        if(payload.get("ticketid") == null || payload.get("status") == null ||payload.get("userid") == null)
        {
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("error", "Missing data.");
            return ResponseEntity.ok(data);
        }

        Integer ticketid = Integer.valueOf(payload.get("ticketid").toString());
        Integer status = Integer.valueOf(payload.get("status").toString());
        Integer userid = Integer.valueOf(payload.get("userid").toString());

        Ticket ticket = ticketService.getTicketByID(ticketid);
        if(ticket == null)
        {
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("error", "Invalid ticket.");
            return ResponseEntity.ok(data);
        }

        User user = userService.getUser(userid);
        if(user == null)
        {
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("error", "Invalid assigneeID.");
            return ResponseEntity.ok(data);
        }

        ticket.setStatus(status);
        ticket.setAssigneeID(userid);
        ticket.setCompletedAt(LocalDateTime.now());
        ticketService.updateTicket(ticket);

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("success", true);
        return ResponseEntity.ok(data);
    }
    
}

package com.example.project1.Controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project1.Entity.ReimbursementType;
import com.example.project1.Service.ReimbursementTypeService;
import com.example.project1.Service.TicketService;
import com.example.project1.Service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


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
    public ResponseEntity<Object> postMethodName(@RequestBody Map<String, Object> payload) {
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
}

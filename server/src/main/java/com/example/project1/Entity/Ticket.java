package com.example.project1.Entity;

import jakarta.persistence.*;


@Entity
@Table(name="ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer ticketID;

    private String description;
    private Double amount;
    private Integer status;
    private Integer typeID;
    private Integer submitterID;
    private Integer assigneeID;

    public Ticket()
    {
        
    }

    public Ticket(String description, Double amount, Integer status, Integer typeID, Integer submitterID, Integer assigneeID)
    {
        this.description = description;
        this.amount = amount;
        this.status = status;
        this.typeID = typeID;
        this.submitterID = submitterID;
        this.assigneeID = assigneeID;
    }
    
    public Integer getTicketID()
    {
        return ticketID;
    }

    public String getDescription()
    {
        return description;
    }

    public Double getAmount()
    {
        return amount;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public Integer getTypeID()
    {
        return typeID;
    }

    public Integer getSubmitterID()
    {
        return submitterID;
    }

    public Integer getAssigneeID()
    {
        return assigneeID;
    }

    public void setAssigneeID(Integer assigneeID)
    {
        this.assigneeID = assigneeID;
    }
}

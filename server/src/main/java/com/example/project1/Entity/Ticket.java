package com.example.project1.Entity;

import jakarta.persistence.*;


@Entity
@Table(name="ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public Integer getTicketID() {
        return ticketID;
    }

    public void setTicketID(Integer ticketID) {
        this.ticketID = ticketID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTypeID() {
        return typeID;
    }

    public void setTypeID(Integer typeID) {
        this.typeID = typeID;
    }

    public Integer getSubmitterID() {
        return submitterID;
    }

    public void setSubmitterID(Integer submitterID) {
        this.submitterID = submitterID;
    }

    public Integer getAssigneeID() {
        return assigneeID;
    }

    public void setAssigneeID(Integer assigneeID) {
        this.assigneeID = assigneeID;
    }
}

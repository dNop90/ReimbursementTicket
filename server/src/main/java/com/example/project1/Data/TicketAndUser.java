package com.example.project1.Data;

import java.time.LocalDateTime;

/**
 * This class isn't entity or model
 * This is only use to combine the ticket and the user data
 */
public class TicketAndUser {
    private Integer ticketID;
    private String description;
    private Double amount;
    private Integer status;
    private Integer typeID;
    private String submitterName;
    private String assigneeName;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;
    
    public TicketAndUser(){
        
    }

    public TicketAndUser(Integer ticketID, String description, Double amount, Integer status, Integer typeID,
            LocalDateTime createdAt, LocalDateTime completedAt) {
        this.ticketID = ticketID;
        this.description = description;
        this.amount = amount;
        this.status = status;
        this.typeID = typeID;
        this.createdAt = createdAt;
        this.completedAt = completedAt;
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

    public String getSubmitterName() {
        return submitterName;
    }

    public void setSubmitterName(String submitterName) {
        this.submitterName = submitterName;
    }

    public String getAssigneeName() {
        return assigneeName;
    }

    public void setAssigneeName(String assigneeName) {
        this.assigneeName = assigneeName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }
}

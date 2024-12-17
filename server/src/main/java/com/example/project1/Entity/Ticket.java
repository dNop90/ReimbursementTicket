package com.example.project1.Entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;


@Entity
@Table(name="ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ticketID;

    private String description;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Integer status;

    @Column(nullable = false)
    private Integer typeID;

    @Column(nullable = false)
    private Integer submitterID;
    
    private Integer assigneeID;

    @CreationTimestamp
    private LocalDateTime createdAt;


    public Ticket()
    {
        
    }

    public Ticket(String description, Double amount, Integer status, Integer typeID, Integer submitterID)
    {
        this.description = description;
        this.amount = amount;
        this.status = status;
        this.typeID = typeID;
        this.submitterID = submitterID;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

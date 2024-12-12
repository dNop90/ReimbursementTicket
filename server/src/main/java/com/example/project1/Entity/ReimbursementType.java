package com.example.project1.Entity;

import jakarta.persistence.*;


@Entity
@Table(name = "ticket_type")
public class ReimbursementType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer typeID;

    @Column(unique = true, nullable = false)
    private String typename;

    public ReimbursementType()
    {
        
    }

    public ReimbursementType(String typename)
    {
        this.typename = typename;
    }
}

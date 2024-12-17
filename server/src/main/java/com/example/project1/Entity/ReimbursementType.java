package com.example.project1.Entity;

import jakarta.persistence.*;


@Entity
@Table(name = "ticket_type")
public class ReimbursementType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public Integer getTypeID() {
        return typeID;
    }

    public void setTypeID(Integer typeID) {
        this.typeID = typeID;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }
}

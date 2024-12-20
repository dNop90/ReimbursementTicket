package com.example.project1.Entity;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.*;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userID;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @ColumnDefault("0")
    private Integer role;

    private String firstname;
    private String lastname;
    private String email;
    private String address;

    public User()
    {
        
    }

    public User(String username, String password)
    {
        this.username = username;
        this.password = password;
        this.role = 0;
    }

    public Integer getUserID()
    {
        return userID;
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getRole()
    {
        return role;
    }

    public void setRole(Integer role)
    {
        this.role = role;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

package com.example.project1.Entity;

import jakarta.persistence.*;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userID;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private Integer role;


    public User(String username, String password)
    {
        this.username = username;
        this.password = password;
        this.role = 0;
    }

    public User(String username, String password, Integer role)
    {
        this.username = username;
        this.password = password;
        this.role = role;
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

    public Integer getRole()
    {
        return role;
    }

    public void setRole(Integer role)
    {
        this.role = role;
    }

    @Override
    public boolean equals(Object o)
    {
        if(this == o) return true;
        if(o == null) return false;
        if(getClass() != o.getClass()) return false;

        User other = (User)o;

        if(userID == null && other.userID != null)
        {
            return false;
        }
        else if(!userID.equals(other.userID))
        {
            return false;
        }

        if(username == null && other.username != null)
        {
            return false;
        }
        else if(!username.equals(other.username))
        {
            return false;
        }

        if(password == null && other.password != null)
        {
            return false;
        }
        else if(!password.equals(other.password))
        {
            return false;
        }

        return true;
    }
}

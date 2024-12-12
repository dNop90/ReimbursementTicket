package com.example.project1.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.project1.Entity.User;
import com.example.project1.Service.UserService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api")
public class UserController
{
    private final UserService userService;

    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody User user)
    {
        if(user == null)
        {
            return ResponseEntity.badRequest().build();
        }

        if(user.getUsername().isEmpty() || user.getPassword().isEmpty())
        {
            return ResponseEntity.badRequest().build();
        }

        User newuser = userService.registerUser(user);
        if(newuser == null)
        {
            return ResponseEntity.badRequest().build();
        }

        Map<String, Object> data = new HashMap<>();
        data.put("userID", newuser.getUserID());
        data.put("username", newuser.getUsername());
        data.put("role", newuser.getRole());


        return ResponseEntity.ok(data);
    }
}

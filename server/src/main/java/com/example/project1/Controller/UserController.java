package com.example.project1.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.project1.Entity.User;
import com.example.project1.Service.UserService;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;


@CrossOrigin
@RestController
@RequestMapping("/api")
public class UserController
{
    private final UserService userService;

    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    /**
     * On user register
     * It will check all the username and password and return the error
     * If there's no error then user information will be return
     * @param payload The payload that the user send to us
     * @return The error or the user information if successfully register
     */
    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody Map<String, Object> payload)
    {
        if(payload == null)
        {
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("error", "Invalid data.");
            return ResponseEntity.ok(data);
        }

        if(payload.get("username") == null || payload.get("password") == null)
        {
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("error", "Missing username or password.");
            return ResponseEntity.ok(data);
        }

        String username = payload.get("username").toString();
        String password = payload.get("password").toString();

        if(username.isEmpty() || password.isEmpty())
        {
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("error", "Invalid username or password.");
            return ResponseEntity.ok(data);
        }

        User newuser = userService.registerUser(username, password);
        if(newuser == null)
        {
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("error", "Username already exists.");
            return ResponseEntity.ok(data);
        }

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("userID", newuser.getUserID());
        data.put("username", newuser.getUsername());
        data.put("role", newuser.getRole());

        return ResponseEntity.ok(data);
    }
}

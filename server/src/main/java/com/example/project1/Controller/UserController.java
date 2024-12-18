package com.example.project1.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.project1.Entity.User;
import com.example.project1.Service.UserService;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;



@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class UserController
{
    private final UserService userService;

    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    /**
     * On user login
     * Check if username and password match
     * @param payload The payload that the user send to us for login
     * @return The user information if successful, otherwise error message
     */
    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody Map<String, Object> payload) {
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
            data.put("error", "Empty username or password.");
            return ResponseEntity.ok(data);
        }
        
        User user = userService.getUser(username, password);
        if(user == null)
        {
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("error", "Invalid username or password.");
            return ResponseEntity.ok(data);
        }

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("userID", user.getUserID());
        data.put("username", user.getUsername());
        data.put("role", user.getRole());

        return ResponseEntity.ok(data);
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
            data.put("error", "Empty username or password.");
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

    /**
     * Get user account information
     * @param payload The payload containing username
     * @return The user information
     */
    @PostMapping("/account")
    public ResponseEntity<Object> getUser(@RequestBody Map<String, Object> payload)
    {
        if(payload == null)
        {
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("error", "Invalid data.");
            return ResponseEntity.ok(data);
        }

        String username = payload.get("username").toString();

        User user = userService.getUser(username);
        if(user == null)
        {
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("error", "Invalid user.");
            return ResponseEntity.ok(data);
        }

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("email", user.getEmail());
        data.put("firstname", user.getFirstname());
        data.put("lastname", user.getLastname());
        data.put("address", user.getAddress());

        return ResponseEntity.ok(data);
    }

    /**
     * Update user account information
     * @param payload The payload containing user information
     * @return The status if it working or not
     */
    @PatchMapping("/account")
    public ResponseEntity<Object> patchUser(@RequestBody Map<String, Object> payload)
    {
        if(payload == null)
        {
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("error", "Invalid data.");
            return ResponseEntity.ok(data);
        }

        String username = payload.get("username").toString();

        User user = userService.getUser(username);
        if(user == null)
        {
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("error", "Invalid user.");
            return ResponseEntity.ok(data);
        }

        //Set the new data
        String email = payload.get("email").toString();
        String firstname = payload.get("firstname").toString();
        String lastname = payload.get("lastname").toString();
        String address = payload.get("address").toString();

        user.setEmail(email);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setAddress(address);

        //Update
        User newuserinfo = userService.updateUser(user);
        if(newuserinfo == null)
        {
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("error", "Failed to update account information.");
            return ResponseEntity.ok(data);
        }

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("success", true);
        return ResponseEntity.ok(data);
    }

    /**
     * Change the user password
     * @param payload Including username, current password, and new password
     * @return The reponse success or error message
     */
    @PatchMapping("/password")
    public ResponseEntity<Object> patchPassword(@RequestBody Map<String, Object> payload)
    {
        if(payload == null)
        {
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("error", "Invalid data.");
            return ResponseEntity.ok(data);
        }

        String username = payload.get("username").toString();

        User user = userService.getUser(username);
        if(user == null)
        {
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("error", "Invalid user.");
            return ResponseEntity.ok(data);
        }

        //New password data
        String currentpassword = payload.get("current").toString();
        String newpassword = payload.get("new").toString();

        if(currentpassword.isEmpty() || newpassword.isEmpty())
        {
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("error", "Missing current password or new password.");
            return ResponseEntity.ok(data);
        }
        
        if(!user.getPassword().equals(currentpassword))
        {
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("error", "Invalid current password.");
            return ResponseEntity.ok(data);
        }

        user.setPassword(newpassword);

        //Update
        User newuserinfo = userService.updateUser(user);
        if(newuserinfo == null)
        {
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("error", "Failed to update password.");
            return ResponseEntity.ok(data);
        }

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("success", true);
        return ResponseEntity.ok(data);
    }

    /**
     * Get all users information except the password
     * @return A list of users
     */
    @GetMapping("/list")
    public ResponseEntity<Object> getAllUsers()
    {
        List<User> users = userService.getAllUsers();

        //Remove password
        for(int i = 0; i < users.size(); i++)
        {
            users.get(i).setPassword(null);
        }

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("users", users);
        return ResponseEntity.ok(data);
    }
    
    /**
     * Update the user role
     * @param payload The payload contains the user ID and the role for update
     * @return Nothing
     */
    @PatchMapping("/role")
    public ResponseEntity<Object> patchRole(@RequestBody Map<String, Object> payload)
    {
        if(payload == null)
        {
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("error", "Invalid data.");
            return ResponseEntity.ok(data);
        }

        Integer userid = Integer.valueOf(payload.get("id").toString());
        Integer role = Integer.valueOf(payload.get("role").toString());

        User user = userService.getUser(userid);
        if(user == null)
        {
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("error", "Invalid user.");
            return ResponseEntity.ok(data);
        }

        user.setRole(role);
        userService.updateUser(user);

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("success", true);
        return ResponseEntity.ok(data);
    }
}

package com.example.project1.Service;

import org.springframework.stereotype.Service;

import com.example.project1.Entity.User;
import com.example.project1.Repository.UserRepository;

@Service
public class UserService
{
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    /**
     * Find user by their username
     * @param username The username to search for
     * @return The user that found or null
     */
    public User getUser(String username)
    {
        return userRepository.findFirstByUsername(username);
    }

    /**
     * Find user by their username and password
     * @param username The username to search for
     * @param password The password to search for
     * @return The user that found or null
     */
    public User getUser(String username, String password)
    {
        return userRepository.findFirstByUsernameAndPassword(username, password);
    }

    /**
     * Register a user if the username didn't exist
     * @param username The username to register
     * @param password The password to register
     * @return The user if successfully register, otherwise null if username exist
     */
    public User registerUser(String username, String password)
    {
        if(getUser(username) != null)
        {
            return null;
        }

        User user = new User(username, password);
        return userRepository.save(user);
    }

    public int deleteUser(String username)
    {
        return userRepository.deleteByUsername(username);
    }
}

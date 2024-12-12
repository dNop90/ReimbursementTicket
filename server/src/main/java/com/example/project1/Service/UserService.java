package com.example.project1.Service;

import org.springframework.stereotype.Service;

import com.example.project1.Entity.User;
import com.example.project1.Repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    public User registerUser(User user)
    {
        User temp = new User(user.getUsername(), user.getPassword());
        return userRepository.save(temp);
    }
}

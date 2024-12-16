package com.example.project1.Init;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.example.project1.Entity.User;
import com.example.project1.Repository.UserRepository;

@Component
public class DefaultUserInit implements ApplicationRunner{
    private final UserRepository userRepository;
    
    public DefaultUserInit(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    public void run(final ApplicationArguments args)
    {
        //Create the first default admin account if it doesn't exist
        User usertemp = userRepository.findFirstByUsername("admin");
        if(usertemp == null)
        {
            User user = new User("admin", "admin");
            user.setRole(2);

            userRepository.save(user);
        }
    }
}

package com.example.Library2.service;

import com.example.Library2.model.User;
import com.example.Library2.model.UserRole;
import com.example.Library2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

 //to register user
    public User register(String firstname,
                         String lastname, String email,
                         String password, UserRole role){
        User newUser=new User();
        newUser.setFirstname(firstname);
        newUser.setLastname(lastname);
        newUser.setEmail(email);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setRole(role);
        return userRepository.save(newUser);
    }

}

package com.example.Library2.controller;

import com.example.Library2.DTO.UserLoginDTO;
import com.example.Library2.DTO.UserRegistrationDTO;
import com.example.Library2.jwtSecurity.JwtHelper;
import com.example.Library2.model.User;
import com.example.Library2.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody UserRegistrationDTO userDto){
     try{
         User newUser=userService.register(
                 userDto.getFirstname(),
                 userDto.getLastname(),
                 userDto.getEmail(),
                 userDto.getPassword(),
                 userDto.getRole()
         );
         return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
     }catch (Exception ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
     }
    }
    //user login
    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody UserLoginDTO userloginDTO){
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userloginDTO.getEmail(),userloginDTO.getPassword())
            );
            UserDetails userDetails= userDetailsService.loadUserByUsername(userloginDTO.getEmail());
            String token=jwtHelper.generateToken(userDetails);
            return ResponseEntity.ok(token);
        }catch(Exception ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credential");
        }
    }
}

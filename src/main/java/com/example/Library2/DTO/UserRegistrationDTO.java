package com.example.Library2.DTO;

import com.example.Library2.model.UserRole;
import lombok.Data;

@Data
public class UserRegistrationDTO {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private UserRole role;
}

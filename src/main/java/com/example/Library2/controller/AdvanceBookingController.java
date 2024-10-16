package com.example.Library2.controller;

import com.example.Library2.DTO.AdvanceBookingDTO;
import com.example.Library2.model.AdvanceBooking;
import com.example.Library2.model.User;
import com.example.Library2.repository.UserRepository;
import com.example.Library2.service.AdvanceBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.plaf.PanelUI;

@RestController
@RequestMapping("/booking")
public class AdvanceBookingController {

    @Autowired
    private AdvanceBookingService bookingService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/reserve")
    public ResponseEntity<AdvanceBooking> reserveBook(@RequestBody AdvanceBookingDTO bookingDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User user = userRepository.findByEmail(currentUsername);

        AdvanceBooking booking=bookingService.reserveBook(bookingDTO.getBookId(),
                                                           bookingDTO.getUserEmail(),
                                                           bookingDTO.getReservedFromDate(),
                                                           bookingDTO.getReservedToDate());
        return ResponseEntity.status(HttpStatus.CREATED).body(booking);
    }
}

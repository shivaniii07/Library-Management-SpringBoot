package com.example.Library2.DTO;

import com.example.Library2.model.User;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AdvanceBookingDTO {
    private Long bookId;
    private String userEmail;
    private LocalDate reservedFromDate;
    private LocalDate reservedToDate;
}

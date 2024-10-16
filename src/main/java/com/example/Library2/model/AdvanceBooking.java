package com.example.Library2.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name="booking_shivani")
public class AdvanceBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;
    private LocalDate bookingDate; //date when user booked
    private LocalDate reservedFromDate; //date when user can pick the book
    private LocalDate reservedToDate;  // date until when the book is reserved
}

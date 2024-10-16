package com.example.Library2.service;

import com.example.Library2.model.AdvanceBooking;
import com.example.Library2.model.Book;
import com.example.Library2.model.Borrowing;
import com.example.Library2.model.User;
import com.example.Library2.repository.AdvanceBookingRepository;
import com.example.Library2.repository.BookRepository;
import com.example.Library2.repository.BorrowingRepository;
import com.example.Library2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class AdvanceBookingService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BorrowingRepository borrowingRepository;
    @Autowired
    private AdvanceBookingRepository advanceBookingRepository;
    @Autowired
    private UserRepository userRepository;

    public AdvanceBooking reserveBook(Long bookId,
                                      String userEmail,
                                      LocalDate reservedFromDate,
                                      LocalDate reservedToDate){

        Book book=bookRepository.findById(bookId)
                .orElseThrow(()-> new IllegalArgumentException("Book not found"));

        User user=userRepository.findByEmail(userEmail);

        //check if book is available during requested period
        Optional<Borrowing> currentBorrowing=borrowingRepository.findCurrentBorrowingByBookId(bookId);
        if(currentBorrowing.isPresent() && !currentBorrowing.get().isReturned())
        {
            //book is currently borrowed,proceed with advance booking
            AdvanceBooking booking=new AdvanceBooking();
            booking.setBook(book);
            booking.setUser(user);
            booking.setReservedFromDate(reservedFromDate);
            booking.setReservedToDate(reservedToDate);
            return advanceBookingRepository.save(booking);
        }

     throw new IllegalStateException("Book is available,no need of advance booking");
    }
}

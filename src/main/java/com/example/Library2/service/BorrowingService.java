package com.example.Library2.service;

import com.example.Library2.exception.BookAlreadyBorrowed;
import com.example.Library2.model.AdvanceBooking;
import com.example.Library2.model.Book;
import com.example.Library2.model.Borrowing;
import com.example.Library2.model.User;
import com.example.Library2.repository.AdvanceBookingRepository;
import com.example.Library2.repository.BookRepository;
import com.example.Library2.repository.BorrowingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class BorrowingService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BorrowingRepository borrowingRepository;
    @Autowired
    private AdvanceBookingRepository advanceBookingRepository;
    @Autowired
    private EmailService emailService;

    //borrow book
    public Borrowing borrowBook(Long book_id, User user) throws BookAlreadyBorrowed {
        Book book = bookRepository.findById(book_id)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        // Check if the book is currently borrowed
        Optional<Borrowing> currentBorrowing = borrowingRepository.findCurrentBorrowingByBookId(book_id);
        if (currentBorrowing.isPresent()) {
            throw new BookAlreadyBorrowed("Book is currently borrowed, consider making an advance booking");
        }

        Borrowing borrowing = new Borrowing();
        borrowing.setBook(book);
        borrowing.setUser(user);
        borrowing.setBorrowedDate(LocalDate.now());
        borrowing.setReturnDate(LocalDate.now().plusDays(15));  // Set return date to 15 days
        borrowing.setReturned(false);

        return borrowingRepository.save(borrowing);
    }
    //return book
    public void returnBook(Long borrowingId){
     Borrowing borrowing=borrowingRepository.findById(borrowingId)
             .orElseThrow(()-> new IllegalArgumentException("Borrowing record not found"));
        borrowing.setReturned(true);
        borrowingRepository.save(borrowing);

        //notify user with advance booking
        List<AdvanceBooking> bookings=advanceBookingRepository.findAllByBookIdAndReservedFromDateAfter(
                borrowing.getBook().getId(),LocalDate.now());

        if(!bookings.isEmpty()){
            //get the next booking after this return date\
            AdvanceBooking nextBooking=bookings.stream()
                    .min(Comparator.comparing(AdvanceBooking::getReservedFromDate)) //get the earlier upcoming booking
                    .orElse(null);

            LocalDate availableFrom=LocalDate.now();
            LocalDate availableFor15Days=availableFrom.plusDays(15);
            LocalDate availableUntil=null;

            // Check if the next booking happens before 15 days, and adjust the availableUntil date
            if (nextBooking != null && nextBooking.getReservedFromDate().isBefore(availableFor15Days)) {
                availableUntil = nextBooking.getReservedFromDate().minusDays(1);  // Available until the day before the next booking
            } else {
                availableUntil = availableFor15Days;  // Available for the full 15 days
            }

            LocalDate finalAvailableUntil = availableUntil;
            bookings.forEach(booking ->{
                String email = booking.getUser().getEmail();
                String subject = "Book Available: " + booking.getBook().getTitle();
                String message = String.format("The book '%s' is now available earlier than expected. "
                                + "You can pick it up from %s to %s.",
                        booking.getBook().getTitle(),
                        availableFrom,
                        finalAvailableUntil);

                emailService.sendMail(email, subject, message);
            });
        }
    }
}

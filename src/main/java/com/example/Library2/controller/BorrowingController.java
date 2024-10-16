package com.example.Library2.controller;

import com.example.Library2.exception.BookAlreadyBorrowed;
import com.example.Library2.model.Borrowing;
import com.example.Library2.model.User;
import com.example.Library2.repository.UserRepository;
import com.example.Library2.service.BorrowingService;
import com.example.Library2.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/borrowing")
public class BorrowingController {
    @Autowired
    private BorrowingService borrowingService;
    @Autowired
    private CustomUserDetailsService custom;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/borrow/{book_id}")
    public ResponseEntity<Borrowing> borrowBook(@PathVariable Long book_id) throws BookAlreadyBorrowed {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User user = userRepository.findByEmail(currentUsername);// Use your UserRepository to find the User
                //.orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Borrowing borrowing=borrowingService.borrowBook(book_id,user);
        return ResponseEntity.status(HttpStatus.CREATED).body(borrowing);
    }

    //return book
    @PostMapping("/{borrowingId}/return")
    public ResponseEntity<String> returnBook(@PathVariable Long borrowingId){
      borrowingService.returnBook(borrowingId);
        return ResponseEntity.ok("Book returned successfully and notifications sent if applicable.");
    }
}

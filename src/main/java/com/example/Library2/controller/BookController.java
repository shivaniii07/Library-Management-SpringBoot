package com.example.Library2.controller;

import com.example.Library2.DTO.BookDTO;
import com.example.Library2.model.Book;
import com.example.Library2.model.User;
import com.example.Library2.repository.BookRepository;
import com.example.Library2.repository.UserRepository;
import com.example.Library2.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    UserRepository userRepository;

    //add book
    @PostMapping("/add")
    public ResponseEntity<String > addBook(@RequestBody BookDTO bookDto, @AuthenticationPrincipal UserDetails userDetails)
    {
        User author=userRepository.findByEmail(userDetails.getUsername());
        Book newBook=new Book();
        newBook.setTitle(bookDto.getTitle());
        newBook.setCategory(bookDto.getCategory());
        newBook.setPrice(bookDto.getPrice());
        newBook.setPdfFile(bookDto.getPdfFile());
        newBook.setAuthor(author);

//        try {
//            // Convert MultipartFile to byte array and set it in the book
//            newBook.setPdfFile(bookDto.getPdfFile().getBytes());
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to upload PDF file.");
//        }
        bookService.addBook(newBook);
        return ResponseEntity.status(HttpStatus.CREATED).body("Book added successfully");
    }

    //FIND BOOK BY AUTHOR
    @GetMapping("/author")
    public ResponseEntity<List<Book>> findBookByAuthor(@AuthenticationPrincipal UserDetails userDetails){
       User author=userRepository.findByEmail(userDetails.getUsername());
       List<Book> bookByAuthor=bookService.getAllBookByAuthor(author);
       return ResponseEntity.ok(bookByAuthor);
    }

    // get all the books
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks(){
        List<Book> allBooks=bookService.getAllBook();
        return ResponseEntity.ok(allBooks);
    }
}

package com.example.Library2.service;

import com.example.Library2.model.Book;
import com.example.Library2.model.User;
import com.example.Library2.repository.BookRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    //add book
    public Book addBook(Book book){
        return bookRepository.save(book);
    }

    //get all books by author
    public List<Book> getAllBookByAuthor(User author){
      return bookRepository.findByAuthor(author);
    }

    // get all the book
    public List<Book> getAllBook(){
        return bookRepository.findAll();
    }
}

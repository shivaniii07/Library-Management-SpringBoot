package com.example.Library2.repository;

import com.example.Library2.model.Book;
import com.example.Library2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    List<Book> findByAuthor(User author);
    Optional<Book> findById(Long id);
}

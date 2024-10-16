package com.example.Library2.repository;

import com.example.Library2.model.Borrowing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BorrowingRepository extends JpaRepository<Borrowing,Long> {
    @Query("SELECT b FROM Borrowing b WHERE b.book.id = :bookId AND b.isReturned = false")
    Optional<Borrowing> findCurrentBorrowingByBookId(@Param("bookId") Long bookId);
    Optional<Borrowing> findById(Long borrow_id);
}

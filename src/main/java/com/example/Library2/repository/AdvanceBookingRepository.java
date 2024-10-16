package com.example.Library2.repository;

import com.example.Library2.model.AdvanceBooking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AdvanceBookingRepository extends JpaRepository<AdvanceBooking,Long> {
    List<AdvanceBooking> findAllByBookIdAndReservedFromDateAfter(Long bookId, LocalDate date);
}

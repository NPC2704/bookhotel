package com.example.bookhotel.repositories;

import com.example.bookhotel.entities.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookingRepository extends MongoRepository<Booking, String> {
    Page<Booking> findByIdContainingOrEmailContainingAllIgnoreCase(String search, String search1, Pageable pageable);
}

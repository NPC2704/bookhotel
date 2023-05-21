package com.example.bookhotel.repositories;

import com.example.bookhotel.entities.RoomHotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoomRepository extends MongoRepository<RoomHotel,String> {
    boolean existsByMaRoom(String maRoom);

    Page<RoomHotel> findByMaRoomContainingOrTenRoomContainingAllIgnoreCase(String maRoom, String tenRoom, Pageable pageable);

    Optional<RoomHotel> findRoomByMaRoom(String maRoom);
}

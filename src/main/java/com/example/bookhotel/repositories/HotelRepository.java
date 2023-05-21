package com.example.bookhotel.repositories;

import com.example.bookhotel.entities.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HotelRepository extends MongoRepository<Hotel,String> {
    @Override
    boolean existsById(String s);

    boolean existsByMaHotel(String maLoai);

    Page<Hotel> findByMaHotelContainingOrTenHotelContainingAllIgnoreCase(String search, String search1, Pageable pageable);

    Hotel findLoaiThuCungByMaHotel(String maHotel);

}

package com.example.bookhotel.service.Booking;

import com.example.bookhotel.dtos.booking.BookingDTO;
import com.example.bookhotel.dtos.roomhotel.RoomHotelDTO;
import com.example.bookhotel.entities.Booking;
import com.example.bookhotel.entities.embedded.ThongTinBooking;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface BookingService {
    Page<Booking> filter(String search, int page, int size, String sort, String column);
    Booking getBookingByMaBooking(String maDatCho);
    Optional<Booking> findById(String Id);
    List<Booking> getAllBooking(String search);
    List<Booking> finfAll();
    Booking addThongTinBooking(String id, ThongTinBooking thongTinDatCho);
    Booking create(BookingDTO dto);

    Booking update(String id, BookingDTO dto);

    Booking changeStatus(String id);
    void deleteById(String id);

}

package com.example.bookhotel.service.Hotel;

import com.example.bookhotel.dtos.booking.BookingDTO;
import com.example.bookhotel.dtos.hotel.HotelDTO;
import com.example.bookhotel.dtos.roomhotel.RoomHotelDTO;
import com.example.bookhotel.dtos.user.UserDto;
import com.example.bookhotel.entities.Hotel;
import org.springframework.data.domain.Page;

import java.util.List;

public interface HotelService {
    Page<Hotel> filter(String search, int page, int size, String sort, String column);
    Hotel getHotelByMHotel(String maHotel);
    List<Hotel> getAllHotel(String search);
    List<Hotel> finfAll();

    Hotel create(HotelDTO dto);

    Hotel update(String id, HotelDTO dto);
    Hotel changeStatus(String maHotel);
    void deleteById(String id);
    HotelDTO findById(String id);
    HotelDTO addRoomToHotel(String hotelId, RoomHotelDTO roomHotelDTO);
}

package com.example.bookhotel.service.Room;

import com.example.bookhotel.dtos.hotel.HotelDTO;
import com.example.bookhotel.dtos.roomhotel.RoomHotelDTO;
import com.example.bookhotel.entities.RoomHotel;
import com.example.bookhotel.entities.embedded.GiaRoom;
import org.springframework.data.domain.Page;

import java.security.Principal;
import java.util.List;

public interface RoomService {
    Page<RoomHotel> filter(String search, int page, int size, String sort, String column);
    RoomHotel getRoomByMaRoom(String maRoomHotel);
    List<RoomHotel> getAllRoom(String search);
    List<RoomHotel> finfAll();
    RoomHotel addGiaRoom(String maRoom, GiaRoom giaRoom);
    RoomHotel create(RoomHotelDTO dto, Principal principal);

    RoomHotel update(String id, RoomHotelDTO dto, Principal principal);

    RoomHotel changeStatus(String maRoom);
    void deleteById(String id);
    RoomHotelDTO findById(String id);
}

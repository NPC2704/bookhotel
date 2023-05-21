package com.example.bookhotel.dtos.hotel;

import com.example.bookhotel.dtos.booking.BookingDTO;
import com.example.bookhotel.entities.RoomHotel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HotelDTO {
    private String maHotel;
    private String tenHotel;
    private List<RoomHotel> room;
}

package com.example.bookhotel.dtos.user;

import com.example.bookhotel.dtos.booking.BookingDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String Id;
    private List<BookingDTO> booking;
    private String email;

    private String password;

    private String name;

    private List<String> roles = new ArrayList<>();
}

package com.example.bookhotel.service.User;

import com.example.bookhotel.dtos.booking.BookingDTO;
import com.example.bookhotel.dtos.user.RegisterDto;
import com.example.bookhotel.dtos.user.SigupDto;
import com.example.bookhotel.dtos.user.UpdateUserDto;
import com.example.bookhotel.dtos.user.UserDto;
import com.example.bookhotel.entities.TaiKhoan;
import org.springframework.data.domain.Page;

import java.security.Principal;
import java.util.List;

public interface UserService {
    Page<TaiKhoan> filter(String search,
                          int page, int size, String sort, String column);

    List<TaiKhoan> finAll();
    UserDto findById(String id);
    TaiKhoan getUser(String id);

    void deleteById(String id);
    TaiKhoan getUserByEmail(String email);

    TaiKhoan create(SigupDto dto, Principal principal);


    TaiKhoan update(String id, UpdateUserDto dto, Principal principal);

    TaiKhoan changeStatus(String id);

    UserDto addBookingToUser(String userId, BookingDTO bookingDTO);
    TaiKhoan signup(RegisterDto registerDto);
}

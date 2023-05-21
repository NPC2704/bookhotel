package com.example.bookhotel.service.User;

import com.example.bookhotel.dtos.booking.BookingDTO;
import com.example.bookhotel.dtos.user.RegisterDto;
import com.example.bookhotel.dtos.user.SigupDto;
import com.example.bookhotel.dtos.user.UpdateUserDto;
import com.example.bookhotel.dtos.user.UserDto;
import com.example.bookhotel.entities.Booking;
import com.example.bookhotel.entities.TaiKhoan;
import com.example.bookhotel.exceptions.InvalidException;
import com.example.bookhotel.exceptions.NotFoundException;
import com.example.bookhotel.repositories.BookingRepository;
import com.example.bookhotel.repositories.UserRepository;
import com.example.bookhotel.utils.EnumRole;
import com.example.bookhotel.utils.PageUtils;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private ModelMapper modelMapper;
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Page<TaiKhoan> filter(String search, int page, int size, String sort, String column) {
        Pageable pageable = PageUtils.createPageable(page, size, sort, column);
        return userRepository.findByNameContainingOrEmailContainingAllIgnoreCase(search,search, pageable);
    }

    @Override
    public List<TaiKhoan> finAll() {
        return userRepository.getAlls();
    }

    @Override
    public TaiKhoan getUser(String id) {
        return userRepository.findById(id)
                .orElseThrow(()-> new NotFoundException(String.format("User with id %s does not exist",id)));
    }


    @Override
    public TaiKhoan getUserByEmail(String email) {
        return null;
    }

    @Override
    public TaiKhoan create(SigupDto dto, Principal principal) {
        TaiKhoan taiKhoan = new TaiKhoan();

        if (ObjectUtils.isEmpty(dto.getName())) {
            throw new InvalidException("Tên Không được để trống");
        }
        if (ObjectUtils.isEmpty(dto.getEmail())) {
            throw new InvalidException("Email không được để trống");
        }
        if (ObjectUtils.isEmpty(dto.getPassword())) {
            throw new InvalidException("Nhập password đi!");
        }
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new InvalidException(String.format("Email đã tồn tại, vui lòng nhập email khác",
                    dto.getEmail()));
        }
        taiKhoan.setName(dto.getName());
        taiKhoan.setEmail(dto.getEmail());
        taiKhoan.setPassword(dto.getPassword());
        taiKhoan.setDienThoai(dto.getDienThoai());
        taiKhoan.setRoles(Arrays.asList(EnumRole.ROLE_USER.name()));

        return userRepository.save(taiKhoan);

    }




    @Override
    public TaiKhoan update(String id, UpdateUserDto dto, Principal principal) {

        TaiKhoan taiKhoan = userRepository.findById(id).orElse(null);
        if (taiKhoan==null)
            throw new NotFoundException("Khong tim thay tai khoan");

        if (ObjectUtils.isEmpty(dto.getName())) {
            throw new InvalidException("Tên Không được bỏ trống");
        }
        if (ObjectUtils.isEmpty(dto.getPassword())) {
            throw new InvalidException("Mật khẩu không được để trống");
        }


        taiKhoan.setName(dto.getName());
        //taiKhoan.setEmail(dto.getEmail());
        taiKhoan.setPassword(dto.getPassword());

        return userRepository.save(taiKhoan);
    }


    @Override
    public TaiKhoan changeStatus(String id) {

        Optional<TaiKhoan> taiKhoan = userRepository.findById(id);
        if(!taiKhoan.isPresent())
            throw new NotFoundException(String.format("Tài khoản có id %s không tồn tại", id));
        taiKhoan.get().setTrangThai(!taiKhoan.get().isTrangThai());
        return userRepository.save(taiKhoan.get());
    }
    @Override
    public UserDto addBookingToUser(String userId, BookingDTO bookingDTO) {
        TaiKhoan user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found with id: " + userId));

        // Check if the reservation ID already exists in the user's list of reservations
        if (user.getBooking() != null && user.getBooking().stream().anyMatch(r -> r != null && r.getId().equals(bookingDTO.getId()))) {
            throw new IllegalArgumentException("Reservation with ID " + bookingDTO.getId() + " already exists for user with ID " + userId);
        }

        Booking booking = modelMapper.map(bookingDTO, Booking.class);
      //  booking = bookingRepository.save(booking);
        if (user.getBooking() == null) {
            user.setBooking(new ArrayList<>());
        }
        user.getBooking().add(booking);
        userRepository.save(user);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public void deleteById(String id) {
        userRepository.deleteById(id);
    }
    @Override
    public UserDto findById(String id) {
        TaiKhoan user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));
        return modelMapper.map(user, UserDto.class);
    }
    @Override
    public TaiKhoan signup(RegisterDto registerDto) {
        TaiKhoan taiKhoan = new TaiKhoan();
        if(userRepository.existsByEmail(registerDto.getEmail()))
            throw new InvalidException(String.format("User có email %s đã tồn tại", registerDto.getEmail()));
        taiKhoan.setName(registerDto.getName());
        taiKhoan.setEmail(registerDto.getEmail());
        taiKhoan.setRoles(Arrays.asList(EnumRole.ROLE_USER.name()));
        taiKhoan.setTrangThai(true);
        taiKhoan.setPassword(registerDto.getPassword());
        taiKhoan.setDienThoai(registerDto.getDienThoai());
        return userRepository.save(taiKhoan);
    }
}

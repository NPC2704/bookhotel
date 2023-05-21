package com.example.bookhotel.service.Hotel;

import com.example.bookhotel.dtos.booking.BookingDTO;
import com.example.bookhotel.dtos.hotel.HotelDTO;
import com.example.bookhotel.dtos.roomhotel.RoomHotelDTO;
import com.example.bookhotel.dtos.user.UserDto;
import com.example.bookhotel.entities.Booking;
import com.example.bookhotel.entities.Hotel;
import com.example.bookhotel.entities.RoomHotel;
import com.example.bookhotel.entities.TaiKhoan;
import com.example.bookhotel.exceptions.InvalidException;
import com.example.bookhotel.exceptions.NotFoundException;
import com.example.bookhotel.repositories.HotelRepository;
import com.example.bookhotel.repositories.RoomRepository;
import com.example.bookhotel.utils.PageUtils;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class HotelServiceImpl implements HotelService{
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private ModelMapper modelMapper;
    public final HotelRepository hotelRepository;
    @Override
    public Page<Hotel> filter(String search, int page, int size, String sort, String column) {
        Pageable pageable = PageUtils.createPageable(page, size, sort, column);
        return hotelRepository.findByMaHotelContainingOrTenHotelContainingAllIgnoreCase(search,search, pageable);
    }

    @Override
    public Hotel getHotelByMHotel(String maHotel) {
        return hotelRepository.findLoaiThuCungByMaHotel(maHotel);
    }

    @Override
    public List<Hotel> getAllHotel(String search) {
        return null;
    }

    @Override
    public List<Hotel> finfAll() {
        return hotelRepository.findAll();
    }

    @Override
    public Hotel create(HotelDTO dto) {
        Hotel hotel = new Hotel();
        if (ObjectUtils.isEmpty(dto.getMaHotel())) {
            throw new InvalidException("Mã hotel Không được để trống");
        }
        if (ObjectUtils.isEmpty(dto.getTenHotel())) {
            throw new InvalidException("Tên hotel không được để trống");
        }
        if (hotelRepository.existsByMaHotel(dto.getMaHotel())) {
            throw new InvalidException(String.format("Mã hotel đã tồn tại, vui lòng nhập mã khác",
                    dto.getMaHotel()));
        }
        hotel.setMaHotel(dto.getMaHotel());
        hotel.setTenHotel(dto.getTenHotel());
        hotel.setTrangThai(true);
        hotelRepository.save(hotel);
        return hotel;
    }

    @Override
    public Hotel update(String id, HotelDTO dto) {
        Optional<Hotel> hotel =hotelRepository.findById(id);
        if(!hotel.isPresent())
            throw new NotFoundException(String.format("ID %s Không tồn tại",id));
        if (!hotel.get().getMaHotel().equals(dto.getMaHotel())){
            // Nếu mã không giống mã cũ thì kiểm tra mã mới đã tồn tại trong database hay chưa
            if (hotelRepository.existsByMaHotel(dto.getMaHotel())){
                throw new InvalidException(String.format("Mã hotel %s đã tồn tại", dto.getMaHotel()));
            }
        }
        hotel.get().setTenHotel(dto.getTenHotel());
        hotel.get().setMaHotel(dto.getMaHotel());
        return hotelRepository.save(hotel.get());
    }

    @Override
    public Hotel changeStatus(String maHotel) {
        Hotel hotel = hotelRepository.findLoaiThuCungByMaHotel(maHotel);
        if(hotel==null)
            throw new NotFoundException(String.format("Mã hotel %s không tồn tại", maHotel));
        hotel.setTrangThai(!hotel.isTrangThai());
        return hotelRepository.save(hotel);
    }
    @Override
    public HotelDTO findById(String id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Hotel not found with id: " + id));
        return modelMapper.map(hotel, HotelDTO.class);
    }
    @Override
    public void deleteById(String id) {
        hotelRepository.deleteById(id);
    }
    @Override
    public HotelDTO addRoomToHotel(String hotelId, RoomHotelDTO roomHotelDTO) {
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new NotFoundException("Hotel not found with id: " + hotelId));
        if (hotel.getRoom() != null && hotel.getRoom().stream().anyMatch(r -> r != null && r.getId().equals(roomHotelDTO.getId()))) {
            throw new IllegalArgumentException("Hotel with ID " + roomHotelDTO.getId() + " already exists for user with ID " + hotelId);
        }
        RoomHotel roomHotel = modelMapper.map(roomHotelDTO, RoomHotel.class);
      //  roomHotel = roomRepository.save(roomHotel);
        if (hotel.getRoom() == null) {
            hotel.setRoom(new ArrayList<>());
        }
        hotel.getRoom().add(roomHotel);
        hotelRepository.save(hotel);
        return modelMapper.map(hotel, HotelDTO.class);
    }
}

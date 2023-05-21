package com.example.bookhotel.service.Booking;


import com.example.bookhotel.dtos.booking.BookingDTO;
import com.example.bookhotel.entities.Booking;
import com.example.bookhotel.entities.embedded.ThongTinBooking;
import com.example.bookhotel.exceptions.InvalidException;
import com.example.bookhotel.exceptions.NotFoundException;
import com.example.bookhotel.repositories.BookingRepository;
import com.example.bookhotel.utils.EnumTrangThaiBooking;
import com.example.bookhotel.utils.PageUtils;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService{
    @Autowired
    private ModelMapper modelMapper;
    public final BookingRepository bookingRepository;
    @Override
    public Page<Booking> filter(String search, int page, int size, String sort, String column) {

        Pageable pageable = PageUtils.createPageable(page, size, sort, column);
        return bookingRepository.findByIdContainingOrEmailContainingAllIgnoreCase(search,search, pageable);
    }

    @Override
    public Booking getBookingByMaBooking(String maBooking) {
        return null;
    }

    @Override
    public Optional<Booking> findById(String Id) {
        return bookingRepository.findById(Id);
    }

    @Override
    public List<Booking> getAllBooking(String search) {
        return null;
    }

    @Override
    public List<Booking> finfAll() {
        return bookingRepository.findAll();
    }

    @Override
    public Booking addThongTinBooking(String id, ThongTinBooking thongTinBooking) {
        Optional<Booking> booking=bookingRepository.findById(id);

        if(!booking.isPresent())
        {
            throw new NotFoundException(String.format("Id  %s không tồn tại",id));
        }
        booking.get().getThongTinBookings().add(thongTinBooking);
        return bookingRepository.save(booking.get());
    }

    @Override
    public Booking create(BookingDTO dto) {
        Booking booking=new Booking();

        if (ObjectUtils.isEmpty(dto.getEmail())) {
            throw new InvalidException("Email Không được để trống");
        }

        if (ObjectUtils.isEmpty(dto.getThoiGiannhan())) {
            throw new InvalidException("Thoi gian nhan Không được để trống!");
        }
        if (ObjectUtils.isEmpty(dto.getThoiGianTra())) {
            throw new InvalidException("Thoi gian tra Không được để trống!");
        }
        booking.setEmail(dto.getEmail());
        booking.setThoiGiannhan(dto.getThoiGiannhan());
        booking.setThoiGianTra(dto.getThoiGianTra());
        booking.setTrangThaiBooking(EnumTrangThaiBooking.DAT_CHO.name());
        booking.setTrangThai(true);

        bookingRepository.save(booking);

        return booking;
    }

    @Override
    public Booking update(String id, BookingDTO dto) {
        Optional<Booking> booking=bookingRepository.findById(id);
        if(!booking.isPresent())
            throw new NotFoundException(String.format("Không tìm thấy Booking có ID %s",id));

        booking.get().setEmail(dto.getEmail());
        booking.get().setThoiGiannhan(dto.getThoiGiannhan());
        booking.get().setThoiGianTra(dto.getThoiGianTra());
        return bookingRepository.save(booking.get());
    }

    @Override
    public Booking changeStatus(String id)
    {
        Optional<Booking> booking = bookingRepository.findById(id);
        if(!booking.isPresent())
            throw new NotFoundException(String.format("Mã booking %s không tồn tại",id));
        booking.get().setTrangThai(!booking.get().isTrangThai());
        return bookingRepository.save(booking.get());
    }
    @Override
    public void deleteById(String id) {
        bookingRepository.deleteById(id);
    }
}

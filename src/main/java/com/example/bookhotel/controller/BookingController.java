package com.example.bookhotel.controller;

import com.example.bookhotel.dtos.booking.BookingDTO;
import com.example.bookhotel.dtos.hotel.HotelDTO;
import com.example.bookhotel.dtos.user.UserDto;
import com.example.bookhotel.entities.Booking;
import com.example.bookhotel.entities.embedded.ThongTinBooking;
import com.example.bookhotel.service.Booking.BookingService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("rest/booking")
public class BookingController {
    public final BookingService bookingService;
    private final int size = 5;
    private final String sort = "asc";
    private final String search = "true";
    private final String column = "email";


    @ApiOperation(value ="Create Booking")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping("/create")
    public ResponseEntity<Booking> create(@Valid @RequestBody BookingDTO dto){
        return new ResponseEntity<>(bookingService.create(dto), HttpStatus.OK);
    }
    @ApiOperation(value ="Add Thong tin Booking")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/addThongTinBooing/{id}")
    public ResponseEntity<Booking> addBooking(@PathVariable String id,
                                                @RequestBody ThongTinBooking thongTinBooking){
        return new ResponseEntity<>(bookingService.addThongTinBooking(id,thongTinBooking), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/filter")
    public ResponseEntity<Page<Booking>> filter(
            @RequestParam(required = false, defaultValue = "") String search,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "ASC") String sort,
            @RequestParam(required = false, defaultValue = "id") String column) {
        Page<Booking> roomPage = bookingService.filter(search, page, size, sort, column);
        return ResponseEntity.ok(roomPage);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @ApiOperation(value = "Get All Booking")
    @GetMapping("/all")
    public List<Booking> getall() {

        return bookingService.finfAll();
    }
    @ApiOperation(value ="Update Booking")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PutMapping("/update/{id}")
    public ResponseEntity<Booking> update(@PathVariable String id,
                                         @RequestBody BookingDTO dto){
        return new ResponseEntity<>(bookingService.update(id,dto), HttpStatus.OK);
    }
    @PostMapping("/change-status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> changeStatus(@RequestParam String id){
        bookingService.changeStatus(id);
        Optional<Booking> datCho=bookingService.findById(id);
        return new ResponseEntity<>(String.format("Booking có Id %s da duoc thay doi trang thai thanh %s"
                , datCho.get().getId(), datCho.get().isTrangThai()), HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        bookingService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}

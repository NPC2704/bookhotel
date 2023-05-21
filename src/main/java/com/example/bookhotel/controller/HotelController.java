package com.example.bookhotel.controller;

import com.example.bookhotel.dtos.booking.BookingDTO;
import com.example.bookhotel.dtos.hotel.HotelDTO;
import com.example.bookhotel.dtos.roomhotel.RoomHotelDTO;
import com.example.bookhotel.dtos.user.UserDto;
import com.example.bookhotel.entities.Hotel;
import com.example.bookhotel.service.Hotel.HotelService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("rest/hotel")
public class HotelController {
    private final int size = 5;
    private final String sort = "asc";
    private final String search = "true";
    private final String column = "maHotel";
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/filter")
    public ResponseEntity<Page<Hotel>> filter(
            @RequestParam(required = false, defaultValue = "") String search,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "ASC") String sort,
            @RequestParam(required = false, defaultValue = "id") String column) {
        Page<Hotel> roomPage = hotelService.filter(search, page, size, sort, column);
        return ResponseEntity.ok(roomPage);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @ApiOperation(value = "Get All Hotel")
    @GetMapping("/all")
    public List<Hotel> all() {

        return hotelService.finfAll();
    }
    public final HotelService hotelService;
    @ApiOperation(value ="Create Hotel")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<Hotel> create(@Valid @RequestBody HotelDTO dto){
        return new ResponseEntity<>(hotelService.create(dto), HttpStatus.OK);
    }

    @ApiOperation(value ="Update LoaiThuCung")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<Hotel> update(@PathVariable String id,
                                              @RequestBody HotelDTO dto){
        return new ResponseEntity<>(hotelService.update(id,dto), HttpStatus.OK);
    }
    @PutMapping("/change-status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> changeStatus(@RequestParam String maLoaiThuCung){
        hotelService.changeStatus(maLoaiThuCung);
        Hotel hotel=hotelService.getHotelByMHotel(maLoaiThuCung);
        return new ResponseEntity<>(String.format("Mã Hotel %s da duoc thay doi trang thai thanh %s"
                , hotel.getMaHotel(), hotel.isTrangThai()), HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        hotelService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/find/{id}")
    public ResponseEntity<HotelDTO> findById(@PathVariable String id) {
        HotelDTO hotelDTO = hotelService.findById(id);
        return ResponseEntity.ok(hotelDTO);
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/{hotelId}/Room")
    public ResponseEntity<HotelDTO> addRoomToHotel(@PathVariable String hotelId, @RequestBody RoomHotelDTO roomHotelDTO) {
        HotelDTO hotelDTO = hotelService.addRoomToHotel(hotelId, roomHotelDTO);
        return ResponseEntity.ok(hotelDTO);
    }
}

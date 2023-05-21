package com.example.bookhotel.controller;

import com.example.bookhotel.dtos.hotel.HotelDTO;
import com.example.bookhotel.dtos.roomhotel.RoomHotelDTO;
import com.example.bookhotel.entities.RoomHotel;
import com.example.bookhotel.entities.embedded.GiaRoom;
import com.example.bookhotel.service.Room.RoomService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("rest/room")
public class RoomController {
    public final RoomService roomService;
    private final int size = 5;
    private final String sort = "asc";
    private final String search = "true";
    private final String column = "tenRoom";


    @ApiOperation(value ="Create Room")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<RoomHotel> create(@Valid @RequestBody RoomHotelDTO dto, Principal principal){
        return new ResponseEntity<>(roomService.create(dto,principal), HttpStatus.OK);
    }


    @ApiOperation(value ="Update Gia Room theo maHotel")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<RoomHotel> update(@PathVariable String id,
                                            @RequestBody RoomHotelDTO dto,Principal principal){
        return new ResponseEntity<>(roomService.update(id,dto,principal), HttpStatus.OK);
    }

    @ApiOperation(value ="Update Room")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/addGiaDV/{maRoom}")
    public ResponseEntity<RoomHotel> addGiaRoom(@PathVariable String maRoom,
                                                @RequestBody GiaRoom giaRoom){
        return new ResponseEntity<>(roomService.addGiaRoom(maRoom,giaRoom), HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @ApiOperation(value = "Get All Room")
    @GetMapping("/all")
    public List<RoomHotel> getRoom() {

        return roomService.finfAll();
    }
    @PostMapping("/change-status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> changeStatus(@RequestParam String maRoom){
        roomService.changeStatus(maRoom);
        RoomHotel roomHotel=roomService.getRoomByMaRoom(maRoom);
        return new ResponseEntity<>(String.format("Room %s da duoc thay doi trang thai thanh %s"
                , roomHotel.getTenRoom(), roomHotel.isTrangThai()), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @ApiOperation(value = "Get All USER Paging")
    @GetMapping("allPaging")
    public ResponseEntity<Page<RoomHotel>> getRoomPaging(@RequestParam(defaultValue = "") String search,
                                                        @RequestParam(defaultValue = "0") int page){
        return new ResponseEntity<>(roomService.filter(search,page,size,sort,column), HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        roomService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/find/{id}")
    public ResponseEntity<RoomHotelDTO> findById(@PathVariable String id) {
        RoomHotelDTO roomHotelDTO = roomService.findById(id);
        return ResponseEntity.ok(roomHotelDTO);
    }
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/filter")
    public ResponseEntity<Page<RoomHotel>> filter(
            @RequestParam(required = false, defaultValue = "") String search,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "ASC") String sort,
            @RequestParam(required = false, defaultValue = "id") String column) {
        Page<RoomHotel> roomPage = roomService.filter(search, page, size, sort, column);
        return ResponseEntity.ok(roomPage);
    }

}

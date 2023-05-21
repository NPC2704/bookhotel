package com.example.bookhotel.service.Room;

import com.example.bookhotel.dtos.hotel.HotelDTO;
import com.example.bookhotel.dtos.roomhotel.RoomHotelDTO;
import com.example.bookhotel.entities.Hotel;
import com.example.bookhotel.entities.RoomHotel;
import com.example.bookhotel.entities.embedded.GiaRoom;
import com.example.bookhotel.exceptions.InvalidException;
import com.example.bookhotel.exceptions.NotFoundException;
import com.example.bookhotel.repositories.RoomRepository;
import com.example.bookhotel.utils.PageUtils;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class RoomServiceImpl  implements RoomService{
    @Autowired
    private ModelMapper modelMapper;
    public final RoomRepository roomRepository;
    @Override
    public Page<RoomHotel> filter(String search, int page, int size, String sort, String column) {
        Pageable pageable = PageUtils.createPageable(page, size, sort, column);
        return roomRepository.findByMaRoomContainingOrTenRoomContainingAllIgnoreCase(search,search, pageable);
    }

    @Override
    public RoomHotel getRoomByMaRoom(String maRoomHotel) {
        return roomRepository.findRoomByMaRoom(maRoomHotel)
                .orElseThrow(()-> new NotFoundException(String.format("User with id %s does not exist",maRoomHotel)));
    }

    @Override
    public List<RoomHotel> getAllRoom(String search) {
        return null;
    }

    @Override
    public List<RoomHotel> finfAll() {
        return roomRepository.findAll();
    }

    @Override
    public RoomHotel addGiaRoom(String maRoom, GiaRoom giaRoom) {
        Optional<RoomHotel> room= roomRepository.findRoomByMaRoom(maRoom);
        if(room==null)
        {
            throw new NotFoundException(String.format("Mã room %s không tồn tại",maRoom));
        }
        room.get().getGiaRoom().add(giaRoom);
        return roomRepository.save(room.get());
    }

    @Override
    public RoomHotel create(RoomHotelDTO dto, Principal principal) {
        RoomHotel roomHotel=new RoomHotel();

        if (ObjectUtils.isEmpty(dto.getMaRoom())) {
            throw new InvalidException("Mã room Không được để trống");
        }

        if (ObjectUtils.isEmpty(dto.getTenRoom())) {
            throw new InvalidException("Tên room Không được để trống!");
        }
        if (roomRepository.existsByMaRoom(dto.getMaRoom())) {
            throw new InvalidException(String.format("Mã room đã tồn tại",
                    dto.getMaRoom()));
        }

        roomHotel.setMaRoom(dto.getMaRoom());
        roomHotel.setTenRoom(dto.getTenRoom());
        roomHotel.setTrangThai(true);
        roomHotel.setNoiDung(dto.getNoiDung());
        roomRepository.save(roomHotel);

        return roomHotel;
    }

    @Override
    public RoomHotel update(String id, RoomHotelDTO dto, Principal principal) {
        Optional<RoomHotel> roomHotel=roomRepository.findById(id);
        if(!roomHotel.isPresent())
            throw new NotFoundException(String.format("Không tìm thấy room có ID %s",id));
        if (!roomHotel.get().getMaRoom().equals(dto.getMaRoom())){
            // Nếu mã không giống mã cũ thì kiểm tra mã mới đã tồn tại trong database hay chưa
            if (roomRepository.existsByMaRoom(dto.getMaRoom())){
                throw new InvalidException(String.format("Mã room %s đã tồn tại", dto.getMaRoom()));
            }
            roomHotel.get().setMaRoom(dto.getMaRoom());
        }
        roomHotel.get().setTenRoom(dto.getTenRoom());
        roomHotel.get().setNoiDung(dto.getNoiDung());
        return roomRepository.save(roomHotel.get());
    }

    @Override
    public RoomHotel changeStatus(String maRoom) {
        Optional<RoomHotel> roomHotel = roomRepository.findRoomByMaRoom(maRoom);
        if(!roomHotel.isPresent())
            throw new NotFoundException(String.format("Mã room %s không tồn tại", maRoom));
        roomHotel.get().setTrangThai(!roomHotel.get().isTrangThai());
        return roomRepository.save(roomHotel.get());
    }
    @Override
    public RoomHotelDTO findById(String id) {
        RoomHotel roomHotel = roomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Room not found with id: " + id));
        return modelMapper.map(roomHotel, RoomHotelDTO.class);
    }
    @Override
    public void deleteById(String id) {
        roomRepository.deleteById(id);
    }
}

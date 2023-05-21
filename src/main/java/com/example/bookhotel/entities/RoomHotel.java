package com.example.bookhotel.entities;


import com.example.bookhotel.entities.embedded.GiaRoom;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "room-hotel")
public class RoomHotel {
    @Id
    private String id;

    // mã dịch vụ không được trùng
    private String maRoom;

    private String tenRoom;

    private String noiDung;

    public RoomHotel(String maDichVu, String tenDichVu, String noiDung, List<GiaRoom> giaRoom, boolean trangThai) {
        this.maRoom = maRoom;
        this.tenRoom = tenRoom;
        this.noiDung = noiDung;
        this.giaRoom = giaRoom;
        this.trangThai = trangThai;
    }
    // Giá Room phụ thuộc vào loại khach san và so sao của khach san
    private List<GiaRoom> giaRoom = new ArrayList<>();
    private boolean trangThai = true;
}

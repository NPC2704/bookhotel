package com.example.bookhotel.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "hotel")
public class Hotel {
    @Id
    private String id;
    // mã không được trùng
    private String maHotel;
    @DBRef
    private List<RoomHotel> room;
    private String tenHotel;

    private boolean trangThai = true;
}

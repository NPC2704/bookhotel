package com.example.bookhotel.entities;

import com.example.bookhotel.entities.embedded.ThongTinBooking;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "booking")
public class Booking {
    @Id
    private String id;

    // email người đặt chỗ
    private String email;

    private List<ThongTinBooking> thongTinBookings = new ArrayList<>();

    // Thời gian nhan phong
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm")
    private Date thoiGiannhan;

    // Thời gian tra phong
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm")
    private Date thoiGianTra;

    // lấy từ enum trạng thái đặt chỗ
    private String trangThaiBooking;

    private boolean trangThai = true;
}

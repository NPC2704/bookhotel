package com.example.bookhotel.entities.embedded;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GiaRoom {
    // Id loại hotel
    private String maHotel;

    // Loai khach san ( 5 sao, 4 sao ,...) (kg)
    private double soSao;

    private double gia;
}

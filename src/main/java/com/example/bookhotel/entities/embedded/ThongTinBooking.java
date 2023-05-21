package com.example.bookhotel.entities.embedded;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ThongTinBooking {
    // Room id
    private String room;

    // lấy giá từ bảng dịch vụ tại thời điểm hiện tại bỏ vào đây
    private String giaRoom;
}

package com.example.bookhotel;

import com.example.bookhotel.entities.TaiKhoan;
import com.example.bookhotel.repositories.RoomRepository;
import com.example.bookhotel.repositories.UserRepository;
import com.example.bookhotel.utils.EnumRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class BookhotelApplication implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;
    private RoomRepository roomRepository2;

    public static void main(String[] args) {
        SpringApplication.run(BookhotelApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if(userRepository.count()==0){
            TaiKhoan taiKhoan1 = new TaiKhoan("user", "user@gmail.com", "123456", "08691546",
                    Arrays.asList(EnumRole.ROLE_USER.name()));
            TaiKhoan taiKhoan2 = new TaiKhoan("admin", "admin@gmail.com", "123456", "08691546",
                    Arrays.asList(EnumRole.ROLE_ADMIN.name()));
            TaiKhoan taiKhoan3 = new TaiKhoan("adminuser", "adminuser@gmail.com", "123456", "08691546",
                    Arrays.asList(EnumRole.ROLE_ADMIN.name(),EnumRole.ROLE_USER.name() ));
            userRepository.save(taiKhoan1);
            userRepository.save(taiKhoan2);
            userRepository.save(taiKhoan3);
            System.out.println("Done!");
        }
    }
}

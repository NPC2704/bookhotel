package com.example.bookhotel.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDto {
  //  private String email;

    private String password;

    private String name;
//
//    private List<String> roles = new ArrayList<>();
}

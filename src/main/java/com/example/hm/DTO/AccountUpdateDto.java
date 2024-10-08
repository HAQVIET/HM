package com.example.hm.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountUpdateDto {
    private String name;
    private String phone;
    private String address;
    private String image;
}

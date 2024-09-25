package com.example.hm.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomCreateDto {
    private String numberRoom;
    private Long typeRoom;;
    private Long price;
    private Long idAccount;
}

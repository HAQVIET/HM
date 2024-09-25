package com.example.hm.DTO.Response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Roomdto {
    private String numberRoom;
    private Long typeRoom;;
    private Long price;
    private Boolean isAvailable;
    private Long idAccount;


}

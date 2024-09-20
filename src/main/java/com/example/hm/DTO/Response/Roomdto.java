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
    private Long numberRoom;
    private String typeRoom;;
    private Long price;
    private Boolean isAvailable;
    private Long idAccount;


}

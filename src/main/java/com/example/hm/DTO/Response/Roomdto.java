package com.example.hm.DTO.Response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Roomdto {
    private String numberRoom;
    private Long typeRoom;;
    private BigDecimal price;
    private Boolean isAvailable;
    private Long idAccount;


}

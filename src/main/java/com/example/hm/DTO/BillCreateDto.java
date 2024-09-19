package com.example.hm.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillCreateDto {
    private Long numberRoom;
    private String typeRoom;;
    private String nameGuest;
    private String phone;
    private String email;
    private Timestamp timeIn;
    private Timestamp timeOut;
    private Long totalPrice;
    private String service;
    private Long price;
    private Long quantity;
    private BigDecimal totalAmount;


    public BillCreateDto(Long numberRoom, String typeRoom, String nameGuest,String phone,String email, Timestamp timeIn, Timestamp timeOut, Long totalPrice, String service, Long price,Long quantity) {
        this.numberRoom = numberRoom;
        this.typeRoom = typeRoom;
        this.nameGuest = nameGuest;
        this.phone = phone;
        this.email = email;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
        this.totalPrice = totalPrice;
        this.service = service;
        this.price = price;
        this.quantity = quantity;
        this.totalAmount = BigDecimal.valueOf(totalPrice).add(BigDecimal.valueOf(price*quantity));

    }
}

package com.example.hm.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServiceCreateDto {
    private Long id;
    private String nameService;
    private Long price;
    private Long quantity;
    private BigDecimal amount;

    public ServiceCreateDto(Long id, String nameService, Long price, Long quantity) {
        this.id = id;
        this.nameService = nameService;
        this.price = price;
        this.quantity = quantity;
        this.amount = BigDecimal.valueOf(price).multiply(BigDecimal.valueOf(quantity));
    }
}

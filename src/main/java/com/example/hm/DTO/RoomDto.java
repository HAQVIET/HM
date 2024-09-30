package com.example.hm.DTO;

import com.example.hm.Entity.RoomEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomDto {
    private Long id;
    private String numberRoom;
    private Long typeRoom;;
    private BigDecimal price;
    private Boolean available;

    public RoomDto(RoomEntity roomEntity){
        this.id = roomEntity.getId();
        this.numberRoom = roomEntity.getNumberRoom();
        this.typeRoom = roomEntity.getTypeRoom();
        this.price = roomEntity.getPrice();
        this.available = roomEntity.getIsAvailabile();
    }
}

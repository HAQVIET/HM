package com.example.hm.DTO;

import com.example.hm.Entity.BookingEntity;
import com.example.hm.Entity.RoomEntity;
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
public class BookingDto {
    private Long id;
    private RoomDto room;
    private Timestamp timeIn;
    private Timestamp timeOut;
    private BigDecimal totalPrice;
    private Boolean isPaid;


    public RoomDto convertRoomDtotoRoomEntity(RoomEntity roomEntity) {
        RoomDto roomDto = new RoomDto();
        roomDto.setId(roomEntity.getId());
        roomDto.setNumberRoom(roomEntity.getNumberRoom());
        roomDto.setTypeRoom(roomEntity.getTypeRoom());
        roomDto.setPrice(roomEntity.getPrice());
        roomDto.setAvailable(roomEntity.getIsAvailabile());
        return roomDto;
    }

   public BookingDto (BookingEntity bookingEntity,RoomEntity roomEntity) {
        this.id = bookingEntity.getId();
       this.room = convertRoomDtotoRoomEntity(roomEntity);
        this.timeOut = bookingEntity.getTimeOut();
        this.timeIn = bookingEntity.getTimeIn();
        this.totalPrice = bookingEntity.getTotalPrice();
        this.isPaid = bookingEntity.getIsPaid();
    }
}

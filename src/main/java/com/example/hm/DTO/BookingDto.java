package com.example.hm.DTO;

import com.example.hm.Entity.BookingEntity;
import com.example.hm.Entity.RoomEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingDto {
    private Long id;
    private RoomDto room;
    private String name;
    private String phone;
    private String email;
    private Timestamp timeIn;
    private Timestamp timeOut;
    private Long totalPrice;
    private Boolean isPaid;


    public RoomDto convertRoomDtotoRoomEntity(RoomEntity roomEntity) {
        RoomDto roomDto = new RoomDto();
        roomDto.setId(roomEntity.getId());
        roomDto.setNumberRoom(roomEntity.getNumberRoom());
        roomDto.setTypeRoom(roomEntity.getTypeRoom());
        roomDto.setPrice(roomEntity.getPrice());
        roomDto.setAvailable(false);
        return roomDto;
    }

   public BookingDto (BookingEntity bookingEntity,RoomEntity roomEntity) {
        this.id = bookingEntity.getId();
       this.room = convertRoomDtotoRoomEntity(roomEntity);
        this.name = bookingEntity.getNameGuest();
        this.phone = bookingEntity.getPhone();
        this.email = bookingEntity.getEmail();
        this.timeOut = bookingEntity.getTimeOut();
        this.timeIn = bookingEntity.getTimeIn();
        this.totalPrice = bookingEntity.getTotalPrice();
        this.isPaid = bookingEntity.getIsPaid();
    }
}

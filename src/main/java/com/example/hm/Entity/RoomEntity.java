package com.example.hm.Entity;


import com.example.hm.DTO.RoomCreateDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "room", schema = "hm", catalog = "postgres")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "number_room", nullable = false)
    private String numberRoom;
    @Column(name = "type_room", nullable = false)
    private Long typeRoom;
    @Column(name = "price", nullable = false)
    private BigDecimal price;
    @Column(name = "is_availibe",nullable = false)
    private Boolean isAvailabile;
    @Column(name = "id_account")
    private Long idAccount;
    public RoomEntity(RoomCreateDto roomCreateDto) {
        this.numberRoom = roomCreateDto.getNumberRoom();
        this.typeRoom = roomCreateDto.getTypeRoom();
        this.price = roomCreateDto.getPrice();
        this.isAvailabile = true;
        this.idAccount = roomCreateDto.getIdAccount();
    }
}

package com.example.hm.Entity;


import com.example.hm.DTO.BookingCreateDto;
import com.example.hm.utils.DateUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "booking", schema = "hm", catalog = "postgres")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "id_room", nullable = false)
    private Long idRoom;
    @Column(name = "time_in", nullable = false)
    private Timestamp timeIn;
    @Column(name = "time_out")
    private Timestamp timeOut;
    @Column(name ="total_price")
    private BigDecimal totalPrice;
    @Column(name = "is_paid",nullable = false)
    private Boolean isPaid;
    @Column(name = "id_account")
    private Long idAccount;


    public BookingEntity(BookingCreateDto bookingCreateDto){
        this.idRoom = bookingCreateDto.getIdRoom();
        this.timeIn = DateUtils.convertToTimestamp(bookingCreateDto.getTimeIn());
        this.isPaid = bookingCreateDto.getIsPaid();
        this.idAccount = bookingCreateDto.getIdAccount();
    }

}

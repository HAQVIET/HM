package com.example.hm.Entity;


import com.example.hm.DTO.BookingServiceDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table (name = "booking_service", schema = "hm",catalog = "postgres")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BookingServiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "id_booking", nullable = false)
    private Long idBooking;
    @Column(name = "id_service", nullable = false)
    private Long idService;
    @Column(name ="quantity",nullable = false)
    private Long quantity;
    @Column(name ="id_account")
    private Long idAccount;

    public BookingServiceEntity(BookingServiceDto bookingServiceDto) {
        this.idBooking = bookingServiceDto.getIdBooking();
        this.idService = bookingServiceDto.getIdService();
        this.quantity = bookingServiceDto.getQuantity();
        this.idAccount = bookingServiceDto.getIdAccount();
    }

}

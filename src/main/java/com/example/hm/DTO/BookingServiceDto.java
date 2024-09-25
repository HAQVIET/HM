package com.example.hm.DTO;

import com.example.hm.Entity.BookingServiceEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingServiceDto {
    private Long id;
    private Long idBooking;
    private Long idService;
    private Long quantity;
    private Long idAccount;

    public BookingServiceDto(BookingServiceEntity bookingServiceEntity) {
        this.id = bookingServiceEntity.getId();
        this.idBooking = bookingServiceEntity.getIdBooking();
        this.idService = bookingServiceEntity.getIdService();
        this.quantity = bookingServiceEntity.getQuantity();
    }
}

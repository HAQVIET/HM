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
    private Long idBooking;
    private Long idService;
    private Long quantity;

    public BookingServiceDto(BookingServiceEntity bookingServiceEntity) {
        this.idBooking = bookingServiceEntity.getIdBooking();
        this.idService = bookingServiceEntity.getIdService();
        this.quantity = bookingServiceEntity.getQuantity();
    }
}

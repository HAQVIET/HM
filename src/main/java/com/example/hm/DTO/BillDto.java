package com.example.hm.DTO;

import com.example.hm.Entity.BookingServiceEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BillDto {
    private InforHotelDto inforHotel;
    private BookingDto bookingDto;
    private List<ServiceCreateDto>  serviceDtoList;
    private BigDecimal totalAmount;
}

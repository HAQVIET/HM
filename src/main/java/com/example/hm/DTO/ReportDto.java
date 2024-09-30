package com.example.hm.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportDto {
    private Long totalBooking;
    private BigDecimal revenue;
//    private List<BillDto> bills;


}

package com.example.hm.DTO;


import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingCreateDto {
    private Long idRoom;
    private String timeIn;
    private String timeOut;
    private Boolean isPaid;
    private Long idAccount;
}

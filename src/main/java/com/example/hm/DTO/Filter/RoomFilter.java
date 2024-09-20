package com.example.hm.DTO.Filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomFilter {
    private Long numberRoom;
    private String typeRoom;;
    private Long price;
    private Boolean available;
    private Long idAccount;
    private Integer page;
    private Integer pageSize;
}

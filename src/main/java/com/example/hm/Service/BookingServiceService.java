package com.example.hm.Service;


import com.example.hm.DTO.BillDto;
import com.example.hm.DTO.BookingServiceDto;

import java.util.List;

public interface BookingServiceService {
    BillDto getbills(Long id);
    BillDto updatebills(Long id, BookingServiceDto bookingServiceDto);

}

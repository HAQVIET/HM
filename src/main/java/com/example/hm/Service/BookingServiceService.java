package com.example.hm.Service;


import com.example.hm.DTO.BillDto;
import com.example.hm.DTO.BookingServiceDto;

import java.util.List;

public interface BookingServiceService {
    BillDto getbills(Long idBooking,Long idAccount);
    BillDto updatebills(Long id, BookingServiceDto bookingServiceDto);
    BookingServiceDto addService(BookingServiceDto bookingServiceDto);
List<BillDto> getAllBills(Long idAccount);
    void deleteBill(Long id);
}

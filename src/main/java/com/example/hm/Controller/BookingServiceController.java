package com.example.hm.Controller;


import com.example.hm.DTO.BillDto;
import com.example.hm.DTO.BookingServiceDto;
import com.example.hm.Service.BookingService;
import com.example.hm.Service.BookingServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bill")
public class BookingServiceController {
    @Autowired
    private BookingServiceService bookingServiceService;

    @GetMapping()
    BillDto getBills(@Param("idBooking")Long idBooking,@Param("idAccount") Long idAccount) {
        return bookingServiceService.getbills(idBooking,idAccount);
    }
    @PutMapping("/updatebill/{id}")
    BillDto updatebill(@PathVariable ("id") Long id, @RequestBody BookingServiceDto bookingServiceDto) {
        return bookingServiceService.updatebills(id,bookingServiceDto);
    }
    @PostMapping("/addservice")
    BookingServiceDto addservice(@RequestBody BookingServiceDto bookingServiceDto) {
        return bookingServiceService.addService(bookingServiceDto);
    }
}

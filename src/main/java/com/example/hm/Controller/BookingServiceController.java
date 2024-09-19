package com.example.hm.Controller;


import com.example.hm.DTO.BillDto;
import com.example.hm.Service.BookingService;
import com.example.hm.Service.BookingServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bill")
public class BookingServiceController {
    @Autowired
    private BookingServiceService bookingServiceService;

    @GetMapping()
    BillDto getBills(@Param("id")Long id) {
        return bookingServiceService.getbills(id);
    }
}

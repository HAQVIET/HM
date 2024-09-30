package com.example.hm.Controller;


import com.example.hm.DTO.BookingCreateDto;
import com.example.hm.DTO.BookingDto;
import com.example.hm.DTO.ReportDto;
import com.example.hm.Service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/booking")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @PostMapping("/add")
    BookingDto addBooking(@RequestBody BookingCreateDto bookingCreateDto) {
        return bookingService.addBooking(bookingCreateDto);
    }
    @PutMapping("/update/{id}")
    BookingDto updateCustomer(@PathVariable ("id")Long id, @RequestBody BookingCreateDto bookingCreateDto) {
        return bookingService.updateBooking(id,bookingCreateDto);
    }
    @DeleteMapping("/delete/{id}")
    void deleteCustomer(@PathVariable("id") Long id) {
        bookingService.deleteBooking(id);
    }

    @GetMapping("/getlist")
    List<BookingDto> getBookingList(@Param("idAccount") Long idAccount) {
        return bookingService.getBookings(idAccount);
    }
    @GetMapping("/getbooking")
    BookingDto getBooking(@Param("idAccount") Long idAccount, @Param("idBooking") Long idBooking) {
        return bookingService.getBooking(idAccount,idBooking);
    }
    @GetMapping("/report")
    ReportDto getReport (@Param("idAccount") Long idAccount) {
        return bookingService.getReport(idAccount);
    }



}

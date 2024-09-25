package com.example.hm.Controller;


import com.example.hm.DTO.BillDto;
import com.example.hm.DTO.BookingCreateDto;
import com.example.hm.DTO.BookingDto;
import com.example.hm.DTO.Filter.RoomFilter;
import com.example.hm.DTO.ReportDto;
import com.example.hm.DTO.Response.PageDataDto;
import com.example.hm.DTO.Response.Roomdto;
import com.example.hm.Entity.BookingEntity;
import com.example.hm.Service.BookingService;
import com.example.hm.Service.RoomService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
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

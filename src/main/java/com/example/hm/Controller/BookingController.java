package com.example.hm.Controller;


import com.example.hm.DTO.BookingCreateDto;
import com.example.hm.DTO.BookingDto;
import com.example.hm.DTO.Filter.RoomFilter;
import com.example.hm.DTO.Response.PageDataDto;
import com.example.hm.DTO.Response.Roomdto;
import com.example.hm.Entity.BookingEntity;
import com.example.hm.Service.BookingService;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/booking")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @GetMapping("/list")
    List<BookingEntity> getBookings() {
        return bookingService.findAll();
    }
    @GetMapping("/{id}")
    Optional<BookingEntity> getCustomerById(@PathVariable("id") Long id) {
        return bookingService.findById(id);
    }
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



}

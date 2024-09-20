package com.example.hm.Service;

import com.example.hm.DTO.BookingCreateDto;
import com.example.hm.DTO.BookingDto;
import com.example.hm.Entity.BookingEntity;

import java.util.List;
import java.util.Optional;

public interface BookingService {
    List<BookingEntity> findAll();
    Optional<BookingEntity> findById(long id);
    BookingDto addBooking(BookingCreateDto bookingCreateDto);
    BookingDto updateBooking(Long id,BookingCreateDto bookingCreateDto);
    void deleteBooking(long id);
    List<BookingDto> getBookings(Long idAccount);
}

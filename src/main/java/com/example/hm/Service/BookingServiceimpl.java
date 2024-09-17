package com.example.hm.Service;


import com.example.hm.DTO.BookingCreateDto;
import com.example.hm.DTO.BookingDto;
import com.example.hm.Entity.BookingEntity;
import com.example.hm.Entity.RoomEntity;
import com.example.hm.Respository.BookingRespository;
import com.example.hm.Respository.RoomRespository;
import com.example.hm.handler_exception.CustomException;
import com.example.hm.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class BookingServiceimpl implements BookingService {
    @Autowired
    private BookingRespository bookingRespository;
    @Autowired
    private RoomRespository roomRespository;

    @Override
    public List<BookingEntity> findAll() {
        return bookingRespository.findAll();
    }

    @Override
    public Optional<BookingEntity> findById(long id) {
        return bookingRespository.findById(id);
    }

    @Override
    public BookingDto addBooking(BookingCreateDto bookingCreateDto) {
        if (bookingCreateDto.getIdRoom() == null) {
            throw new CustomException("400", "IdRoom is null");
        }
        Optional<RoomEntity> roomEntity = roomRespository.findById(bookingCreateDto.getIdRoom());
        if (roomEntity.isEmpty()) {
            throw new CustomException("400", "Room not found");
        }
        RoomEntity room = roomEntity.get();
        if (!room.getIsAvailabile()) {
            throw new CustomException("400", "Room is not available.");
        }
        if (bookingCreateDto.getTimeIn() == null || bookingCreateDto.getTimeOut() == null) {
            throw new CustomException("400", "TimeOut and TimeIn is null");
        }
        if (bookingCreateDto.getName() == null) {
            throw new CustomException("400", "Name cannot be empty");
        }
        if (bookingCreateDto.getPhone() == null) {
            throw new CustomException("400", "Phone cannot be empty");
        }

        if (bookingCreateDto.getPhone().length() < 10 || bookingCreateDto.getPhone().length() > 11) {
            throw new CustomException("400", "Phone is valid");
        }

        if (bookingCreateDto.getEmail() == null) {
            throw new CustomException("400", "Email cannot be empty");
        }
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        if (!pattern.matcher(bookingCreateDto.getEmail()).matches()) {
            throw new CustomException("400", "Email is invalid");
        }

        Long totalPrice = calculateTotalPrice(room.getPrice(), DateUtils.convertToTimestamp(bookingCreateDto.getTimeIn()), DateUtils.convertToTimestamp(bookingCreateDto.getTimeOut()));
        BookingEntity bookingEntity = new BookingEntity();
        bookingEntity.setTotalPrice(totalPrice);
        bookingEntity.setIsPaid(bookingCreateDto.getIsPaid());
        bookingEntity.setName(bookingCreateDto.getName());
        bookingEntity.setPhone(bookingCreateDto.getPhone());
        bookingEntity.setEmail(bookingCreateDto.getEmail());
        bookingEntity.setTimeIn(DateUtils.convertToTimestamp(bookingCreateDto.getTimeIn()));
        bookingEntity.setTimeOut(DateUtils.convertToTimestamp(bookingCreateDto.getTimeOut()));
        bookingEntity.setIdRoom(bookingCreateDto.getIdRoom());
        room.setIsAvailabile(false);


        return new BookingDto(bookingRespository.save(bookingEntity), room);
    }

    @Override
    public BookingDto updateBooking(Long id,BookingCreateDto bookingCreateDto) {
        Optional<BookingEntity> bookingEntity = bookingRespository.findById(id);
        if (bookingEntity.isEmpty()) {
            throw new CustomException("400", "Booking not found");
        }
        BookingEntity booking = bookingEntity.get();
            booking.setIsPaid(bookingCreateDto.getIsPaid());
            booking.setEmail(bookingCreateDto.getEmail());


        return new BookingDto(bookingRespository.save(booking), RoomEntity.builder().build()) ;
    }

    @Override
    public void deleteBooking(long id) {
        bookingRespository.deleteById(id);

    }

    private Long calculateTotalPrice(Long pricePerNight, Timestamp checkInTimestamp, Timestamp checkOutTimestamp) {
        // Calculate the number of milliseconds between the two timestamps
        LocalDate dateIn = checkInTimestamp.toLocalDateTime().toLocalDate();
        LocalDate dateOut = checkOutTimestamp.toLocalDateTime().toLocalDate();
        // Calculate total price based on the price per night
        Long days = ChronoUnit.DAYS.between(dateIn, dateOut);
        return days * pricePerNight;
    }
}

package com.example.hm.Service;


import com.example.hm.DTO.*;
import com.example.hm.Entity.AccountEntity;
import com.example.hm.Entity.BookingEntity;
import com.example.hm.Entity.RoomEntity;
import com.example.hm.Respository.AccountRepository;
import com.example.hm.Respository.BookingRespository;
import com.example.hm.Respository.RoomRespository;
import com.example.hm.Respository.spec.SpecBillRepository;
import com.example.hm.handler_exception.CustomException;
import com.example.hm.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class BookingServiceimpl implements BookingService {
    @Autowired
    private BookingRespository bookingRespository;
    @Autowired
    private RoomRespository roomRespository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private SpecBillRepository specBillRepository;


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
        if (bookingCreateDto.getTimeIn() == null ) {
            throw new CustomException("400", "TimeIn is null");
        }
        if(bookingCreateDto.getIdAccount() == null) {
            throw new CustomException("400", "IdAccount is null");
        }
        if(accountRepository.findById(bookingCreateDto.getIdAccount()).isEmpty()) {
            throw new CustomException("400", "Account not found");
        }
        if (!bookingCreateDto.getIsPaid()){
            if(bookingCreateDto.getTimeOut() == null){
                room.setIsAvailabile(false);
            }
        }


        return new BookingDto(bookingRespository.save(new BookingEntity(bookingCreateDto)), room);
    }

    @Override
    public BookingDto updateBooking(Long id, BookingCreateDto bookingCreateDto) {
        Optional<BookingEntity> bookingEntity = bookingRespository.findById(id);
        if (bookingEntity.isEmpty()) {
            throw new CustomException("400", "Booking not found");
        }

        RoomEntity room = roomRespository.findById(bookingCreateDto.getIdRoom()).get();
        BigDecimal totalPrice = calculateTotalPrice(room.getPrice(), DateUtils.convertToTimestamp(bookingCreateDto.getTimeIn()), DateUtils.convertToTimestamp(bookingCreateDto.getTimeOut()));
        BookingEntity booking = bookingEntity.get();

        booking.setTotalPrice(totalPrice);
        booking.setIsPaid(bookingCreateDto.getIsPaid());
        booking.setTimeIn(DateUtils.convertToTimestamp(bookingCreateDto.getTimeIn()));
        booking.setTimeOut(DateUtils.convertToTimestamp(bookingCreateDto.getTimeOut()));
        booking.setIdRoom(bookingCreateDto.getIdRoom());
        room.setIsAvailabile(true);

        return new BookingDto(bookingRespository.save(booking), RoomEntity.builder()
                .id(room.getId())
                .numberRoom(room.getNumberRoom())
                .typeRoom(room.getTypeRoom())
                .price(room.getPrice())
                .isAvailabile(true)
                .build());
    }

    @Override
    public void deleteBooking(Long id) {
        bookingRespository.deleteById(id);

    }

    @Override
    public List<BookingDto> getBookings(Long idAccount) {
        if(idAccount == null){
            throw new CustomException("400","Id of account is required");
        }
        Optional<AccountEntity> account = accountRepository.findById(idAccount);
        if(account.isEmpty()){
            throw new CustomException("404","Account not found");
        }
        List<Object[]> rawBookings = bookingRespository.getBookingsByAccount(idAccount);
        return mapBookingsToDto(rawBookings);
    }

    @Override
    public BookingDto getBooking(Long idAccount, Long idBooking) {
        if(idAccount == null){
            throw new CustomException("400","Id of account is required");
        }
        Optional<AccountEntity> account = accountRepository.findById(idAccount);
        if(account.isEmpty()){
            throw new CustomException("404","Account not found");
        }
        if(idBooking == null){
            throw new CustomException("400","IdBooking is required");
        }
        Optional<BookingEntity> booking = bookingRespository.findById(idBooking);
        if(booking.isEmpty()){
            throw new CustomException("404","Booking not found");
        }

        List<Object[]> result = bookingRespository.findByIdAndIdAccount(idBooking, idAccount);

        // Assuming only one result should be returned:
        if (!result.isEmpty()) {
            return mapToBookingDto(result.get(0));
        }
        return null; // Or handle if no booking is found
    }

    @Override
    public ReportDto getReport(Long idAccount)  {
        List<BillDto> billDtos = getBillDtosForAccount(idAccount);
        ReportDto reportDto = new ReportDto();
        reportDto.setRevenue(calculateTotalRevenue(billDtos));
//        reportDto.setBills(billDtos);
        reportDto.setTotalBooking(bookingRespository.getReport(idAccount));

        return reportDto;
    }

    private BigDecimal calculateTotalPrice(BigDecimal pricePerNight, Timestamp checkInTimestamp, Timestamp checkOutTimestamp) {
        // Calculate the number of milliseconds between the two timestamps
        LocalDate dateIn = checkInTimestamp.toLocalDateTime().toLocalDate();
        LocalDate dateOut = checkOutTimestamp.toLocalDateTime().toLocalDate();
        // Calculate total price based on the price per night
        Long days = ChronoUnit.DAYS.between(dateIn, dateOut);
        if(days == 0){
           days = 1L;
        }
        return pricePerNight.multiply(BigDecimal.valueOf(days));
    }

    public List<BookingDto> mapBookingsToDto(List<Object[]> rawBookings) {
        List<BookingDto> bookingDtos = new ArrayList<>();

        for (Object[] rawBooking : rawBookings) {
            Long bookingId = (Long) rawBooking[0];
            Long roomId = (Long) rawBooking[1];
            String roomNumber = (String) rawBooking[2];
            Long roomType = (Long) rawBooking[3];
            BigDecimal roomPrice = (BigDecimal) rawBooking[4];
            Boolean isAvailable = (Boolean) rawBooking[5];
            Timestamp timeIn = (Timestamp) rawBooking[6];
            Timestamp timeOut = (Timestamp) rawBooking[7];
            BigDecimal totalPrice = (BigDecimal) rawBooking[8];
            Boolean isPaid = (Boolean) rawBooking[9];


            // Create RoomDto
            RoomDto roomDto = new RoomDto(roomId, roomNumber, roomType, roomPrice, isAvailable);

            // Create BookingDto
            BookingDto bookingDto = new BookingDto(bookingId, roomDto, timeIn, timeOut, totalPrice, isPaid);

            // Add to list
            bookingDtos.add(bookingDto);
        }

        return bookingDtos;
    }
    public BookingDto mapToBookingDto(Object[] result) {
        Long bookingId = (Long) result[0];
        Long roomId = (Long) result[1];
        String numberRoom = (String) result[2];
        Long typeRoom = (Long) result[3];
        BigDecimal price = (BigDecimal) result[4];
        Boolean isAvailable = (Boolean) result[5];
        Timestamp timeIn = (Timestamp) result[6];
        Timestamp timeOut = (Timestamp) result[7];
        BigDecimal totalPrice = (BigDecimal) result[8];
        Boolean isPaid = (Boolean) result[9];
        // Create RoomDto
        RoomDto roomDto = new RoomDto(roomId, numberRoom, typeRoom, price, isAvailable);

        // Create BookingDto
        return new BookingDto(bookingId, roomDto, timeIn, timeOut, totalPrice, isPaid);
    }

    private BigDecimal calculateTotalRevenue(List<BillDto> billDtos) {
        // Use Java Streams to sum all totalAmount from BillDto, converting BigDecimal to long
        return billDtos.stream()
                .map(BillDto::getTotalAmount)          // Get BigDecimal totalAmount from each BillDto
                .reduce(BigDecimal.ZERO, BigDecimal::add); // Sum all totalAmount values
    }
    private List<BillDto> getBillDtosForAccount(Long idAccount) {
        List<BillDto> billDtos = specBillRepository.getSalary(idAccount);
        return billDtos; // Return actual list from the repository
    }

}


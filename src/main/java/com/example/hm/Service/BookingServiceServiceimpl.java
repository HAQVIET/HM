package com.example.hm.Service;


import com.example.hm.DTO.*;
import com.example.hm.Entity.AccountEntity;
import com.example.hm.Entity.BookingEntity;
import com.example.hm.Entity.BookingServiceEntity;
import com.example.hm.Entity.RoomEntity;
import com.example.hm.Respository.*;
import com.example.hm.Respository.spec.SpecBillRepository;
import com.example.hm.handler_exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceServiceimpl implements BookingServiceService {
    @Autowired
    BookingServiceRepository bookingServiceRepository;
    @Autowired
    BookingRespository bookingRespository;

    @Autowired
    RoomRespository roomRespository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    ServiceRespository serviceRespository;
    @Autowired
    SpecBillRepository specBillRepository;


    @Override
    public BillDto getbills( Long idBooking,Long idAccount) {
        if(bookingRespository.findById(idBooking).isEmpty()){
            throw new CustomException("400", "Bill Not Found");
        }

     List<ServiceCreateDto> serviceCreateDtos = bookingServiceRepository.getServiceCreateDto(idBooking,idAccount);
        BookingEntity bookingEntity = bookingRespository.findById(idBooking).get();

        RoomEntity roomEntity = roomRespository.findById(bookingEntity.getIdRoom()).get();
        AccountEntity account = accountRepository.findById(bookingEntity.getIdAccount()).get();
        InforHotelDto inforHotelDto = new InforHotelDto();
        inforHotelDto.setHotelAddress(account.getAddress());
        inforHotelDto.setHotelName(account.getName());
        inforHotelDto.setHotline(account.getPhone());
        inforHotelDto.setImage(account.getImage());

        return BillDto.builder()
                .inforHotel(inforHotelDto)
                .bookingDto(new BookingDto(bookingEntity,roomEntity))
                .serviceDtoList(serviceCreateDtos)
                .totalAmount(serviceCreateDtos.stream().map(ServiceCreateDto::getAmount).reduce((bookingEntity.getTotalPrice()),BigDecimal::add))
                .build();
    }

    @Override
    public BillDto updatebills(Long id, BookingServiceDto bookingServiceDto) {
        if(!bookingServiceRepository.existsByIdBooking(id)) {
            throw new CustomException("400", "Bill Not Found");
        }
        if(bookingServiceDto.getIdService() == null){
            throw new CustomException("400", "Service Not Found");
        }
        BookingServiceEntity bookingService = bookingServiceRepository.findByIdBookingAndIdService(id, bookingServiceDto.getIdService());
        if (bookingService == null) {
            bookingServiceRepository.save(BookingServiceEntity.builder().idBooking(id).idService(bookingService.getIdService()).quantity(bookingServiceDto.getQuantity()).build());
        }else {
            bookingService.setQuantity(bookingService.getQuantity() + bookingServiceDto.getQuantity());
            bookingServiceRepository.save(bookingService);
        }
        return getbills(bookingService.getIdBooking(), bookingServiceDto.getIdService());
    }

    @Override
    public BookingServiceDto addService(BookingServiceDto bookingServiceDto) {
        if(bookingServiceDto.getIdBooking() == null){
            throw new CustomException("400", "Booking is required");
        }
        if(bookingRespository.findById(bookingServiceDto.getIdBooking()).isEmpty()){
            throw new CustomException("400", "Booking Not Found");
        }
        if(bookingServiceDto.getIdService() == null){
            throw new CustomException("400", "Service is required");
        }
        if(serviceRespository.findById(bookingServiceDto.getIdService()).isEmpty()){
            throw new CustomException("400", "Service Not Found");
        }
    if(bookingServiceDto.getQuantity() == null || bookingServiceDto.getQuantity() <= 0){
        throw new CustomException("400", "Quantity is required");
    }
            return new BookingServiceDto(bookingServiceRepository.save(new BookingServiceEntity(bookingServiceDto)));
    }

    @Override
    public List<BillDto> getAllBills(Long idAccount) {
        if(idAccount == null){
            throw new CustomException("400", "Account is required");
        }
        if(!accountRepository.existsById(idAccount)){
            throw new CustomException("400", "Account Not Found");
        }
        List<BillDto> billDtos = specBillRepository.getSalary(idAccount);
        return billDtos;
    }

}

package com.example.hm.Respository.spec;


import com.example.hm.DTO.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class SpecBillRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<BillDto> getSalary(Long idAccount) {
        String sql = "SELECT " +
                "    a.name AS hotelName, " +
                "    a.address AS hotelAddress, " +
                "    a.phone AS hotline, " +
                "    b.id AS bookingId, " +
                "    r.id AS roomId, " +
                "    r.number_room AS numberRoom, " +
                "    r.type_room AS typeRoom, " +
                "    r.price AS roomPrice, " +
                "    b.time_in AS timeIn, " +
                "    b.time_out AS timeOut, " +
                "    b.total_price AS totalPrice, " +
                "    b.is_paid AS isPaid, " +
                "    s.id AS serviceId, " +
                "    s.name AS serviceName, " +
                "    s.price AS servicePrice, " +
                "    bs.quantity AS serviceQuantity " +
                "FROM " +
                "    account a " +
                "    INNER JOIN booking b ON a.id = b.id_account " +
                "    INNER JOIN room r ON r.id = b.id_room " +
                "    LEFT JOIN booking_service bs ON bs.id_booking = b.id " +
                "    LEFT JOIN service s ON s.id = bs.id_service "
               ;

        Query query = entityManager.createNativeQuery(sql);

        // Get the results as a List of Object arrays
        List<Object[]> results = query.getResultList();

        // Map results to BillDto manually
        Map<Long, BillDto> billDtoMap = new HashMap<>();

        for (Object[] row : results) {
            // Extract values from each row
            String hotelName = (String) row[0];
            String hotelAddress = (String) row[1];
            String hotline = (String) row[2];
            Long bookingId = ((Number) row[3]).longValue();
            Long roomId = ((Number) row[4]).longValue();
            String numberRoom = (String) row[5];
            Long typeRoom = (Long) row[6];
            Long roomPrice = (Long) row[7];
            Timestamp timeIn = (Timestamp) row[8];
            Timestamp timeOut = (Timestamp) row[9];
            Long totalPrice = (Long) row[10];
            Boolean isPaid = (Boolean) row[11];
            Long serviceId = row[12] != null ? ((Number) row[12]).longValue() : null;
            String serviceName = (String) row[13];
            Long servicePrice = (Long) row[14];
            Long serviceQuantity = row[15] != null ? ((Number) row[15]).longValue() : null;

            // Create or update BillDto based on bookingId
            BillDto billDto = billDtoMap.get(bookingId);
            if (billDto == null) {
                billDto = new BillDto();

                // Set InforHotelDto
                InforHotelDto inforHotelDto = new InforHotelDto();
                inforHotelDto.setHotelName(hotelName);
                inforHotelDto.setHotelAddress(hotelAddress);
                inforHotelDto.setHotline(hotline);
                billDto.setInforHotel(inforHotelDto);

                // Set BookingDto
                BookingDto bookingDto = new BookingDto();
                bookingDto.setId(bookingId);
                RoomDto roomDto = new RoomDto();
                roomDto.setId(roomId);
                roomDto.setNumberRoom(numberRoom);
                roomDto.setTypeRoom(typeRoom);
                roomDto.setPrice((roomPrice));
                bookingDto.setRoom(roomDto);
                bookingDto.setTimeIn(timeIn);
                bookingDto.setTimeOut(timeOut);
                bookingDto.setTotalPrice(totalPrice);
                bookingDto.setIsPaid(isPaid);
                billDto.setBookingDto(bookingDto);

                billDto.setServiceDtoList(new ArrayList<>());

                // Add to the map
                billDtoMap.put(bookingId, billDto);
            }

            // Add service to the list if exists
            if (serviceId != null) {
                ServiceCreateDto serviceCreateDto = new ServiceCreateDto();
                serviceCreateDto.setId(serviceId);
                serviceCreateDto.setNameService(serviceName);
                serviceCreateDto.setPrice(servicePrice);
                serviceCreateDto.setQuantity(serviceQuantity);
                serviceCreateDto.setAmount(BigDecimal.valueOf(servicePrice).multiply(BigDecimal.valueOf(serviceQuantity)));

                billDto.getServiceDtoList().add(serviceCreateDto);
            }
        }

        // Calculate totalAmount for each BillDto
        for (BillDto billDto : billDtoMap.values()) {
            BigDecimal totalAmount = BigDecimal.valueOf(billDto.getBookingDto().getTotalPrice());
            for (ServiceCreateDto service : billDto.getServiceDtoList()) {
                totalAmount = totalAmount.add(service.getAmount());
            }
            billDto.setTotalAmount(totalAmount);
        }

        // Return the list of BillDto
        return new ArrayList<>(billDtoMap.values());
    }
}

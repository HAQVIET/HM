package com.example.hm.Respository.spec;


import com.example.hm.DTO.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

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
                "    LEFT JOIN service s ON s.id = bs.id_service " +
                "WHERE a.id = ?" +
                "and b.is_paid = true" +
                " order by b.id desc  ";

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, idAccount);  // Ensure parameter binding

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
            BigDecimal roomPrice = row[7] != null ? BigDecimal.valueOf(((Number) row[7]).longValue()) : BigDecimal.ZERO;  // Cast to BigDecimal
            Timestamp timeIn = (Timestamp) row[8];
            Timestamp timeOut = (Timestamp) row[9];
            BigDecimal totalPrice = row[10] != null ? BigDecimal.valueOf(((Number) row[10]).longValue()) : BigDecimal.ZERO;  // Handle null
            Boolean isPaid = (Boolean) row[11];
            Long serviceId = row[12] != null ? ((Number) row[12]).longValue() : null;
            String serviceName = (String) row[13];
            BigDecimal servicePrice = row[14] != null ? BigDecimal.valueOf(((Number) row[14]).longValue()) : BigDecimal.ZERO;
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
                roomDto.setPrice(roomPrice);  // Set roomPrice as BigDecimal
                bookingDto.setRoom(roomDto);
                bookingDto.setTimeIn(timeIn);
                bookingDto.setTimeOut(timeOut);
                bookingDto.setTotalPrice(totalPrice);  // Set totalPrice as BigDecimal
                bookingDto.setIsPaid(isPaid);
                billDto.setBookingDto(bookingDto);

                billDto.setServiceDtoList(new ArrayList<>());

                // Add to the map
                billDtoMap.put(bookingId, billDto);
            }

            // Add service to the list if it exists
            if (serviceId != null) {
                ServiceCreateDto serviceCreateDto = new ServiceCreateDto();
                serviceCreateDto.setId(serviceId);
                serviceCreateDto.setNameService(serviceName);
                serviceCreateDto.setPrice(servicePrice);  // Set servicePrice as BigDecimal
                serviceCreateDto.setQuantity(serviceQuantity);
                if (serviceQuantity != null) {
                    serviceCreateDto.setAmount(servicePrice.multiply(BigDecimal.valueOf(serviceQuantity)));  // Correct amount calculation
                } else {
                    serviceCreateDto.setAmount(BigDecimal.ZERO);  // Handle null quantity
                }

                billDto.getServiceDtoList().add(serviceCreateDto);
            }
        }

        // Calculate totalAmount for each BillDto
        for (BillDto billDto : billDtoMap.values()) {
            BigDecimal totalAmount = billDto.getBookingDto().getTotalPrice();  // Initialize with booking's totalPrice
            for (ServiceCreateDto service : billDto.getServiceDtoList()) {
                totalAmount = totalAmount.add(service.getAmount());  // Add service amounts to total
            }
            billDto.setTotalAmount(totalAmount);  // Set final totalAmount
        }

        // Return the list of BillDto
        return new ArrayList<>(billDtoMap.values().stream().sorted(Comparator.comparing(billDto -> billDto.getBookingDto().getId(), Comparator.nullsLast(Comparator.reverseOrder()))).toList());
    }
}


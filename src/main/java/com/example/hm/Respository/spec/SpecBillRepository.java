package com.example.hm.Respository.spec;

import com.example.hm.DTO.BillDto;
import com.example.hm.DTO.BookingDto;
import com.example.hm.DTO.InforHotelDto;
import com.example.hm.DTO.ServiceCreateDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SpecSalaryRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<BillDto> getSalary(Long idAccount) {
        String sql = "SELECT " +
                "    COALESCE(" +
                "        jsonb_build_object(" +
                "            'hotelName', a.name," +
                "            'hotelAddress', a.address," +
                "            'hotline', a.phone" +
                "        ), " +
                "        '{}'::jsonb" +
                "    ) AS inforHotel," +
                "    COALESCE(" +
                "        jsonb_build_object(" +
                "            'id', b.id," +
                "            'room', COALESCE(" +
                "                jsonb_build_object(" +
                "                    'id', r.id," +
                "                    'numberRoom', r.number_room," +
                "                    'typeRoom', r.type_room," +
                "                    'price', r.price" +
                "                ), " +
                "                '{}'::jsonb" +
                "            )," +
                "            'timeIn', b.time_in," +
                "            'timeOut', b.time_out," +
                "            'totalPrice', b.total_price," +
                "            'isPaid', b.is_paid" +
                "        ), " +
                "        '{}'::jsonb" +
                "    ) AS bookingDto," +
                "   COALESCE" +
                "            SELECT jsonb_agg(" +
                "                jsonb_build_object(" +
                "                    'id', s.id," +
                "                    'nameService', s.name," +
                "                    'price', s.price," +
                "                    'quantity', bs.quantity," +
                "                    'amount', (s.price * bs.quantity)" +
                "                )" +
                "            )" +
                "            FROM booking_service bs" +
                "            INNER JOIN service s ON s.id = bs.id_service" +
                "            WHERE bs.id_booking = b.id" +
                "        )," +
                "        '[]'::jsonb" +
                "    ) AS serviceDtoList" +
                "FROM " +
                "    account a" +
                "    INNER JOIN booking b ON a.id = b.id_account" +
                "    INNER JOIN room r ON r.id = b.id_room" +
                "WHERE " +
                "    a.id = 2;";

        Query query = entityManager.createNativeQuery(sql);

        // Get the results as a List of Object arrays
        List<Object[]> results = query.getResultList();

        // Map the results to SalaryDto objects
        List<BillDto> billDtoList = new ArrayList<>();
        for (Object[] row : results) {
            BillDto billDto = new BillDto();
            billDto.setInforHotel((InforHotelDto) row[0]);
            billDto.setBookingDto((BookingDto) row[1]);
            billDto.setServiceDtoList((List<ServiceCreateDto>) row[2]);

            // Optionally calculate totalAmount
            BigDecimal totalAmount = BigDecimal.valueOf(billDto.getBookingDto().getTotalPrice());
            for (ServiceCreateDto service : billDto.getServiceDtoList()) {
                totalAmount = totalAmount.add(service.getAmount());
            }
            billDto.setTotalAmount(totalAmount);

            billDtoList.add(billDto);
        }
        return billDtoList;
    }
}

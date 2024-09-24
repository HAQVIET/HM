package com.example.hm.Respository;


import com.example.hm.DTO.BookingDto;
import com.example.hm.DTO.ReportDto;
import com.example.hm.DTO.RoomDto;
import com.example.hm.Entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRespository extends JpaRepository<BookingEntity, Long>, JpaSpecificationExecutor<BookingEntity> {
    @Query(value = "select b.id, r.id, r.numberRoom, r.typeRoom, r.price, r.isAvailabile ," +
            " b.nameGuest,b.phone, b.email, b.timeIn, b.timeOut, b.totalPrice, b.isPaid " +
            "from BookingEntity b " +
            "inner join RoomEntity r on r.id = b.idRoom " +
            "where b.idAccount = :id_account")
    List<Object[]> getBookingsByAccount(@Param("id_account") Long id_account);

    @Query(value = "select b.id, r.id, r.numberRoom, r.typeRoom, r.price, r.isAvailabile ," +
            " b.nameGuest,b.phone, b.email, b.timeIn, b.timeOut, b.totalPrice, b.isPaid " +
            "from BookingEntity b " +
            "inner join RoomEntity r on r.id = b.idRoom " +
            "where  b.idAccount = :id_account and  b.id = :id_booking " )
    List<Object[]> findByIdAndIdAccount(@Param("id_booking")Long id_booking,@Param("id_account") Long id_account);

    @Query(value = " select count (*)" +
            " from BookingEntity b " +
            " where  b.idAccount =:id_account" +
            " and b.isPaid =true ")
    Long getReport(@Param("id_account") Long id_account);




}

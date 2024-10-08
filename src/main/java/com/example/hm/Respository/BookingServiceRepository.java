package com.example.hm.Respository;

import com.example.hm.DTO.ServiceCreateDto;
import com.example.hm.Entity.BookingServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingServiceRepository extends JpaRepository<BookingServiceEntity,Long> , JpaSpecificationExecutor<BookingServiceEntity> {
   @Query(value = "select new com.example.hm.DTO.ServiceCreateDto (s.id, s.name ,s.price,bs.quantity )" +
           "   from BookingServiceEntity bs inner join ServiceEntity s on s.id = bs.idService " +
           "   where bs.idAccount = :idAccount"+
           "   and bs.idBooking = :idBooking")
   List<ServiceCreateDto> getServiceCreateDto(@Param("idBooking") Long idBooking,@Param("idAccount")Long idAccount);


    Boolean existsByIdBooking(Long idBooking);
    BookingServiceEntity findByIdBookingAndIdService(Long idBooking,Long idService);




}

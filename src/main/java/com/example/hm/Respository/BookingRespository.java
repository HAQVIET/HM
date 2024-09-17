package com.example.hm.Respository;


import com.example.hm.Entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRespository extends JpaRepository<BookingEntity, Long>, JpaSpecificationExecutor<BookingEntity> {
}

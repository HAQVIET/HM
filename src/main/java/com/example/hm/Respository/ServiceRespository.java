package com.example.hm.Respository;


import com.example.hm.DTO.ServiceDto;
import com.example.hm.Entity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRespository extends JpaRepository<ServiceEntity, Long>, JpaSpecificationExecutor<ServiceEntity> {
    @Query(value = "select new com.example.hm.DTO.ServiceDto(s.id ,s.name ,s.price ,s.image) " +
            " from ServiceEntity s " +
            " where s.idAccount =:id_account ")
    List<ServiceDto> getServiceDtoByAccount(@Param("id_account") Long id_account);
}

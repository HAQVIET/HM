package com.example.hm.Respository;


import com.example.hm.Entity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRespository extends JpaRepository<ServiceEntity, Long>, JpaSpecificationExecutor<ServiceEntity> {
}

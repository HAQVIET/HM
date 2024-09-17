package com.example.hm.Service;

import com.example.hm.DTO.ServiceDto;
import com.example.hm.Entity.ServiceEntity;

import java.util.List;
import java.util.Optional;

public interface ServiceService {
    List<ServiceEntity> getAllServices();
    Optional<ServiceEntity> getServiceById(Long id);
    ServiceDto addService(ServiceDto serviceDto);
    ServiceDto updateService(Long id,ServiceDto serviceDto);
    void deleteService(Long id);
}

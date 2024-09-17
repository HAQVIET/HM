package com.example.hm.Service;


import com.example.hm.DTO.ServiceDto;
import com.example.hm.Entity.ServiceEntity;
import com.example.hm.Respository.ServiceRespository;
import com.example.hm.handler_exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceServiceimpl implements ServiceService {
    @Autowired
    ServiceRespository serviceRespository;

    @Override
    public List<ServiceEntity> getAllServices() {
        return serviceRespository.findAll();
    }

    @Override
    public Optional<ServiceEntity> getServiceById(Long id) {
        return serviceRespository.findById(id);
    }

    @Override
    public ServiceDto addService(ServiceDto serviceDto) {
        if (serviceDto.getName() == null ){
            throw new CustomException("400","Name is required");
        }
        if(serviceDto.getPrice() == null || serviceDto.getPrice() < 0){
            throw new CustomException("400","Price is required");
        }
        return new ServiceDto(serviceRespository.save(new ServiceEntity(serviceDto)));
    }

    @Override
    public ServiceDto updateService(Long id,ServiceDto serviceDto) {
    Optional<ServiceEntity> serviceEntity = serviceRespository.findById(id);
    if (serviceEntity.isEmpty()){
        throw new CustomException("400","Service not found");
        }
    ServiceEntity service = serviceEntity.get();
    service.setName(serviceDto.getName());
    service.setPrice(serviceDto.getPrice());

        return new ServiceDto(serviceRespository.save(service));
    }

    @Override
    public void deleteService(Long id) {
        serviceRespository.deleteById(id);

    }
}

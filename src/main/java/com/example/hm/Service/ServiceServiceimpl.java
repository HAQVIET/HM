package com.example.hm.Service;


import com.example.hm.DTO.ServiceDto;
import com.example.hm.Entity.AccountEntity;
import com.example.hm.Entity.ServiceEntity;
import com.example.hm.Respository.AccountRepository;
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
    @Autowired
    AccountRepository accountRepository;

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
        if(serviceDto.getIdAccount() == null || serviceDto.getIdAccount() < 0){
            throw new CustomException("400","IdAccount is required");
        }
        if(accountRepository.findById(serviceDto.getIdAccount()).isEmpty()){
            throw new CustomException("400","Account not found");
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
    public List<ServiceDto> getServiceByAccountId(Long idAccount) {
        if(idAccount == null){
            throw new CustomException("400","Id of account is required");
        }
        Optional<AccountEntity> account = accountRepository.findById(idAccount);
        if(account.isEmpty()){
            throw new CustomException("404","Account not found");
        }
        return serviceRespository.getServiceDtoByAccount(idAccount);
    }

    @Override
    public void deleteService(Long id) {
        serviceRespository.deleteById(id);

    }
}

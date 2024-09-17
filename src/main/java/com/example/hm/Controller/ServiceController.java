package com.example.hm.Controller;


import com.example.hm.DTO.ServiceDto;
import com.example.hm.Entity.ServiceEntity;
import com.example.hm.Service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/service")
public class ServiceController {
    @Autowired
    private ServiceService serviceService;

    @GetMapping("/list")
    List<ServiceEntity> getServiceList() {
        return serviceService.getAllServices();
    }

    @GetMapping("/{id}")
    Optional<ServiceEntity> getServiceById(@PathVariable ("id") Long id) {
        return serviceService.getServiceById(id);
    }

    @PostMapping("/add")
    ServiceDto addService(@RequestBody ServiceDto serviceDto) {
        return serviceService.addService(serviceDto);
    }

    @PutMapping("/update/{id}")
    ServiceDto updateService(@PathVariable("id") Long id,@RequestBody ServiceDto serviceDto) {
        return serviceService.updateService(id,serviceDto);
    }
    @DeleteMapping("/delete/{id}")
    void deleteService(@PathVariable ("id") Long id) {
        serviceService.deleteService(id);

    }
}

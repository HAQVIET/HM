package com.example.hm.DTO;


import com.example.hm.Entity.ServiceEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServiceDto {
    private String name;
    private Long price;

    public ServiceDto(ServiceEntity serviceEntity) {
        this.name = serviceEntity.getName();
        this.price = serviceEntity.getPrice();
    }
}

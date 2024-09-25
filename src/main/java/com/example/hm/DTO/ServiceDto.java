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
    private Long id ;
    private String name;
    private Long price;
    private String image;
    private Long idAccount;

    public ServiceDto(ServiceEntity serviceEntity) {
        this.id = serviceEntity.getId();
        this.name = serviceEntity.getName();
        this.price = serviceEntity.getPrice();
        this.image = serviceEntity.getImage();
    }
    public ServiceDto(Long id, String name, Long price, String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
    }
}

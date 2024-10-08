package com.example.hm.Entity;


import com.example.hm.DTO.ServiceDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "service", schema = "hm", catalog = "postgres")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ServiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "price", nullable = false)
    private BigDecimal price;
    @Column(name = "image")
    private String image;
    @Column(name ="id_account",nullable = false)
    private Long idAccount;

    public ServiceEntity(ServiceDto serviceDto) {
        this.name = serviceDto.getName();
        this.price = serviceDto.getPrice();
        this.image = serviceDto.getImage();
        this.idAccount = serviceDto.getIdAccount();
    }
}

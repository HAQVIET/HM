package com.example.hm.Entity;

import com.example.hm.DTO.AccountCreateDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "account", schema = "hm", catalog = "postgres")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name="name",nullable = false)
    private String name;
    @Column(name = "phone", nullable = false)
    private String phone;
    @Column(name="address", nullable = false)
    private String address;

    public AccountEntity(AccountCreateDto accountCreateDto) {
        this.email = accountCreateDto.getEmail();
        this.password = accountCreateDto.getPassword();
        this.name = accountCreateDto.getName();
        this.phone = accountCreateDto.getPhone();
        this.address = accountCreateDto.getAddress();
    }

}

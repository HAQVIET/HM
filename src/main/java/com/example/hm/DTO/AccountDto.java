package com.example.hm.DTO;

import com.example.hm.Entity.AccountEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDto {
    private Long id;
    private String email;
    private String name;
    private String phone;
    private String address;
    private String image;

    public AccountDto(AccountEntity accountEntity) {
        this.id = accountEntity.getId();
        this.email = accountEntity.getEmail();
        this.name = accountEntity.getName();
        this.phone = accountEntity.getPhone();
        this.address = accountEntity.getAddress();
        this.image = accountEntity.getImage();

    }
}

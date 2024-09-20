package com.example.hm.Service;

import com.example.hm.DTO.AccountCreateDto;
import com.example.hm.DTO.AccountDto;
import com.example.hm.DTO.AccountUpdateDto;
import com.example.hm.DTO.LoginDto;

public interface AccountService {
    AccountDto addAccount(AccountCreateDto accountCreateDto);
    AccountDto updateAccount(Long id ,AccountUpdateDto accountUpdateDto);
    AccountDto login(LoginDto loginDto);
    void deleteAccount(Long id);
}

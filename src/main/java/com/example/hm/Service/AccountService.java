package com.example.hm.Service;

import com.example.hm.DTO.*;

public interface AccountService {
    AccountDto addAccount(AccountCreateDto accountCreateDto);
    AccountDto updateAccount(Long id ,AccountUpdateDto accountUpdateDto);
    AccountId login(LoginDto loginDto);
    void deleteAccount(Long id);
}

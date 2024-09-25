package com.example.hm.Service;

import com.example.hm.DTO.*;
import com.example.hm.Entity.AccountEntity;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    List<AccountEntity> getAllAccounts();
    Optional<AccountEntity> getAccountById(Long id);
    AccountDto addAccount(AccountCreateDto accountCreateDto);
    AccountDto updateAccount(Long id ,AccountUpdateDto accountUpdateDto);
    AccountId login(LoginDto loginDto);
    void deleteAccount(Long id);
}

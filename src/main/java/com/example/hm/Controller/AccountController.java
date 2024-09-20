package com.example.hm.Controller;

import com.example.hm.DTO.*;
import com.example.hm.Service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping("/register")
    AccountDto register(@RequestBody AccountCreateDto accountCreateDto) {
        return accountService.addAccount(accountCreateDto);
    }

    @PutMapping("/update/{id}")
    AccountDto update(@PathVariable("id") Long id, @RequestBody AccountUpdateDto accountUpdateDto) {
        return accountService.updateAccount(id, accountUpdateDto);
    }


    @DeleteMapping("/{id}")
    void delete(@PathVariable("id") Long id) {
        accountService.deleteAccount(id);
    }

    @PostMapping("/login")
    public AccountId login(@RequestBody LoginDto loginDto) {
       return accountService.login(loginDto);

    }
}

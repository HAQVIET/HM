package com.example.hm.Service;


import com.example.hm.DTO.*;
import com.example.hm.Entity.AccountEntity;
import com.example.hm.Respository.AccountRepository;
import com.example.hm.handler_exception.CustomException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class AccountServiceimpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;


    @Override
    public List<AccountEntity> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Optional<AccountEntity> getAccountById(Long id) {
        if(accountRepository.findById(id).isEmpty()){
            throw new CustomException("400","Account Not Found");
        }
        return accountRepository.findById(id);
    }

    @Override
    public AccountDto addAccount(AccountCreateDto accountCreateDto) {
        if(accountCreateDto.getEmail() == null){
            throw new CustomException("400", "Email is required");
        }
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        if (!pattern.matcher(accountCreateDto.getEmail()).matches()) {
            throw new CustomException("400", "Email is invalid");
        }
        if(accountRepository.existsByEmail(accountCreateDto.getEmail())){
            throw new CustomException("400", "Email already exists");
        }

        if(accountCreateDto.getPassword() == null){
            throw new CustomException("400", "Password is required");
        }
        if(accountCreateDto.getName() == null|| accountCreateDto.getPhone() == null||accountCreateDto.getAddress() == null){
            throw new CustomException("400", "Information are required");
        }
        if (accountCreateDto.getPhone().length() < 10 || accountCreateDto.getPhone().length() > 11) {
            throw new CustomException("400", "Phone is valid");
        }
        AccountEntity accountEntity = new AccountEntity();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        accountEntity.setPassword(passwordEncoder.encode(accountCreateDto.getPassword()));
        accountEntity.setEmail(accountCreateDto.getEmail());
        accountEntity.setPhone(accountCreateDto.getPhone());
        accountEntity.setAddress(accountCreateDto.getAddress());
        accountEntity.setName(accountCreateDto.getName());
        return new AccountDto(accountRepository.save(accountEntity));
    }

    @Override
    public AccountDto updateAccount(Long id,AccountUpdateDto accountUpdateDto) {
        if(accountRepository.findById(id).isEmpty()){
            throw new CustomException("400", "Account not found");
        }
        AccountEntity accountEntity = accountRepository.findById(id).get();
        accountEntity.setName(accountUpdateDto.getName());
        accountEntity.setPhone(accountUpdateDto.getPhone());
        accountEntity.setAddress(accountUpdateDto.getAddress());
        accountEntity.setImage(accountUpdateDto.getImage());
        accountRepository.save(accountEntity);
        return new AccountDto(accountEntity);
    }

    @Override
    public AccountId login(LoginDto loginDto) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (org.apache.commons.lang3.StringUtils.isBlank(loginDto.getEmail()) || StringUtils.isBlank(loginDto.getPassword())) {
            try {
                throw new CustomException("500", "Email and Password must be not null");
            } catch (Exception e) {
                throw  e;
            }
        }
        AccountEntity account = accountRepository.findByEmail(loginDto.getEmail());
        if(account == null || !passwordEncoder.matches(loginDto.getPassword(), account.getPassword())) {
            try {
                throw new CustomException("500","Account or password is incorrect");
            } catch (Exception e) {
                throw e;
            }
        }
        return AccountId.builder()
                .accountId(account.getId())
                .build();
    }


    @Override
    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);

    }
}

package com.example.hm.Respository;

import com.example.hm.Entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity,Long> , JpaSpecificationExecutor<AccountEntity> {
Boolean existsByEmail(String email);
AccountEntity findByEmail(String email);

}


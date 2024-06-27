package com.example.ojt.repository;

import com.example.ojt.model.entity.Account;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface IAccountRepository extends JpaRepository<Account,Integer> {
    ;
    boolean existsByEmail(String email);

    Optional <Account> findByEmail(String email);

}

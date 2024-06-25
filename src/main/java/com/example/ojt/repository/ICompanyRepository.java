package com.example.ojt.repository;

import com.example.ojt.model.entity.Company;
import com.example.ojt.model.entity.TypeCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICompanyRepository extends JpaRepository<Company,Integer> {

    boolean existsByName(String name);
    boolean existsByEmailCompany(String emailCompany);
    boolean existsByPhone(String phone);
}

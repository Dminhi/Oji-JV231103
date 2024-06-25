package com.example.ojt.repository;

import com.example.ojt.model.entity.TypeCompany;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ITypeCompanyRepository extends JpaRepository<TypeCompany,Integer> {
    Optional<TypeCompany> findByName(String name);

    Page<TypeCompany> findAllByNameContainingIgnoreCase(Pageable pageable, String keyword);

}

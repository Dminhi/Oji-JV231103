package com.example.ojt.repository;

import com.example.ojt.model.entity.AddressCompany;
import com.example.ojt.model.entity.Location;
import jakarta.mail.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAddressRepository extends JpaRepository<AddressCompany,Integer> {
        boolean existsByAddress (String address);
        @Query("SELECT a FROM AddressCompany a WHERE LOWER(a.address) LIKE LOWER(CONCAT('%', :keyword, '%'))")
        Page<AddressCompany> findAllByAddressContainingKeyword(Pageable pageable,String keyword);

}

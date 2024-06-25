package com.example.ojt.repository;

import com.example.ojt.model.entity.AddressCompany;
import com.example.ojt.model.entity.Location;
import jakarta.mail.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAddressRepository extends JpaRepository<AddressCompany,Integer> {

}

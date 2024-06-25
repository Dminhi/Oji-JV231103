package com.example.ojt.repository;

import com.example.ojt.model.entity.Location;
import com.example.ojt.model.entity.TypeCompany;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ILocationRepository extends JpaRepository<Location,Integer> {
    Optional<Location> findByNameCity(String name);

    Page<Location> findAllByNameCityContainingIgnoreCase(Pageable pageable, String keyword);



}

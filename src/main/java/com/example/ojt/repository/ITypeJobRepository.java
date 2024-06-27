package com.example.ojt.repository;

import com.example.ojt.model.entity.TypeJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ITypeJobRepository extends JpaRepository<TypeJob,Integer> {

    Optional<TypeJob> findByName(String name);

}

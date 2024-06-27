package com.example.ojt.repository;

import com.example.ojt.model.entity.LevelJob;
import com.example.ojt.model.entity.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ILeveJobRepository extends JpaRepository<LevelJob,Integer> {
    Optional<LevelJob> findByName(String name);
    boolean existsByName(String name);
    @Query("SELECT a FROM LevelJob a WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<LevelJob> findAllByNameLevelJob(Pageable pageable, String keyword);
}

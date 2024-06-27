package com.example.ojt.repository;

import com.example.ojt.model.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job,Integer> {
    @Query("SELECT c FROM Job c WHERE LOWER(c.title) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Job> findByName(Pageable pageable, String keyword);
}

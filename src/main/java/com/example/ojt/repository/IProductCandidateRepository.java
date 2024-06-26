package com.example.ojt.repository;

import com.example.ojt.model.entity.ExperienceCandidate;
import com.example.ojt.model.entity.ProjectCandidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductCandidateRepository extends JpaRepository<ProjectCandidate,Integer> {
    @Query("SELECT a FROM ProjectCandidate a WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<ProjectCandidate> findByName(Pageable pageable, String keyword);
}

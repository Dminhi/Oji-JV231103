package com.example.ojt.repository;

import com.example.ojt.model.entity.EducationCandidate;
import com.example.ojt.model.entity.ExperienceCandidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IExperienceCandidateRepository extends JpaRepository<ExperienceCandidate,Integer> {
    @Query("SELECT a FROM ExperienceCandidate a WHERE LOWER(a.position) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<ExperienceCandidate> findByName(Pageable pageable, String keyword);
}

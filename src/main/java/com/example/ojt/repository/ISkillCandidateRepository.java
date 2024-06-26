package com.example.ojt.repository;

import com.example.ojt.model.dto.response.SkillCandidateResponse;
import com.example.ojt.model.entity.SkillsCandidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ISkillCandidateRepository extends JpaRepository<SkillsCandidate,Integer> {
    boolean existsByName(String name);
    @Query("SELECT a FROM SkillsCandidate a WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<SkillsCandidate> findByName(Pageable pageable, String keyword);
}

package com.example.ojt.repository;

import com.example.ojt.model.entity.EducationCandidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEduCandidateRepository extends JpaRepository<EducationCandidate,Integer> {
}

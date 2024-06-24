package com.example.ojt.repository;

import com.example.ojt.model.entity.ProjectCandidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductCandidateRepository extends JpaRepository<ProjectCandidate,Integer> {
}

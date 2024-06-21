package com.example.ojt.repository;

import com.example.ojt.model.entity.ExperienceCandidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IExperienceCandidateRepository extends JpaRepository<ExperienceCandidate,Integer> {
}

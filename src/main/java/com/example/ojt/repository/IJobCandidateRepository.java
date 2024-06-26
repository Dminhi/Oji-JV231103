package com.example.ojt.repository;

import com.example.ojt.model.entity.JobCandidates;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface IJobCandidateRepository extends JpaRepository<JobCandidates,Integer> {
    @Query("SELECT jc FROM JobCandidates jc WHERE LOWER(jc.content) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<JobCandidates> findByInterview(Pageable pageable, String keyword);
}

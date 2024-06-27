package com.example.ojt.repository;

import com.example.ojt.model.entity.AddressCompany;
import com.example.ojt.model.entity.EducationCandidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IEduCandidateRepository extends JpaRepository<EducationCandidate,Integer> {
    @Query("SELECT a FROM EducationCandidate a WHERE LOWER(a.nameEducation) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<EducationCandidate> findAllByAddressContainingKeyword(Pageable pageable, String keyword);
}

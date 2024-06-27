package com.example.ojt.repository;

import com.example.ojt.model.entity.CertificateCandidate;
import com.example.ojt.model.entity.EducationCandidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ICertificateCandidateRepository extends JpaRepository<CertificateCandidate,Integer> {
    @Query("SELECT a FROM CertificateCandidate a WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<CertificateCandidate> findByName(Pageable pageable, String keyword);
}

package com.example.ojt.repository;

import com.example.ojt.model.entity.CertificateCandidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICertificateCandidateRepository extends JpaRepository<CertificateCandidate,Integer> {
}

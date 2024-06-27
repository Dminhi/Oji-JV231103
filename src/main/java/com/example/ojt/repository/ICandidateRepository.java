
package com.example.ojt.repository;

import com.example.ojt.model.entity.Candidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ICandidateRepository extends JpaRepository<Candidate,Integer> {
    @Query("SELECT c FROM Candidate c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Candidate> findAndSort(Pageable pageable, String keyword);
}

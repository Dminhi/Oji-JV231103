package com.example.ojt.service.jobCandidate;

import com.example.ojt.exception.CustomException;
import com.example.ojt.model.dto.mapper.PageDataDTO;
import com.example.ojt.model.dto.request.JobCandidateRequestDTO;
import com.example.ojt.model.dto.response.JobCandidateListResponseDTO;
import com.example.ojt.model.dto.response.JobCandidateResponseDTO;
import com.example.ojt.model.entity.JobCandidates;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IJobCandidateService {
    boolean save(JobCandidateRequestDTO jobCandidateRequestDTO) throws CustomException;

    boolean update(JobCandidateRequestDTO jobCandidateRequestDTO, Integer id) throws CustomException;

    boolean removeJobCandidate(Integer id) throws CustomException;
    Page<JobCandidateResponseDTO> findAllWithPaginationAndSort(Pageable pageable);

    Page<JobCandidateResponseDTO> searchByNameWithPaginationAndSort(Pageable pageable, String keyword) ;
    PageDataDTO<JobCandidateResponseDTO> getJobCandidate(String keyword, int page, int limit, String sort, String order) throws CustomException;

    JobCandidates findById(Integer id) throws CustomException;
}

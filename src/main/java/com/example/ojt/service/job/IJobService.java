package com.example.ojt.service.job;

import com.example.ojt.exception.CustomException;
import com.example.ojt.model.dto.mapper.PageDataDTO;
import com.example.ojt.model.dto.request.JobRequestDTO;
import com.example.ojt.model.dto.response.JobCandidateResponseDTO;
import com.example.ojt.model.dto.response.JobListResponseDTO;
import com.example.ojt.model.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IJobService {
    boolean save(JobRequestDTO jobRequestDTO) throws CustomException;

    boolean update(JobRequestDTO jobRequestDTO, Integer id) throws CustomException;

    Job findById(Integer id) throws CustomException;
    Page<JobListResponseDTO> findAllWithPaginationAndSort(Pageable pageable);

    Page<JobListResponseDTO> searchByNameWithPaginationAndSort(Pageable pageable, String keyword) ;
    PageDataDTO<JobListResponseDTO> getJob(String keyword, int page, int limit, String sort, String order) throws CustomException;
}

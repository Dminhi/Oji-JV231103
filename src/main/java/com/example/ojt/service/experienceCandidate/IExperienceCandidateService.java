package com.example.ojt.service.experienceCandidate;

import com.example.ojt.exception.CustomException;
import com.example.ojt.model.dto.mapper.PageDataDTO;
import com.example.ojt.model.dto.request.ExperienceCandidateRequestDTO;
import com.example.ojt.model.dto.response.ExperienceCandidateResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IExperienceCandidateService {
    boolean save(ExperienceCandidateRequestDTO experienceCandidateRequestDTO) throws CustomException;
    boolean update(ExperienceCandidateRequestDTO experienceCandidateRequestDTO,Integer id) throws CustomException;

    boolean removeExCandidate(Integer id) throws CustomException;

    PageDataDTO<ExperienceCandidateResponseDTO> getExCandidate(String keyword, int page, int limit, String sort, String order) throws CustomException;
     Page<ExperienceCandidateResponseDTO> findAllWithPaginationAndSort(Pageable pageable);
     Page<ExperienceCandidateResponseDTO> searchByNameWithPaginationAndSort(Pageable pageable, String keyword);
}

package com.example.ojt.service.eduCandidate;

import com.example.ojt.exception.CustomException;
import com.example.ojt.model.dto.mapper.PageDataDTO;
import com.example.ojt.model.dto.request.EduCandidateRequestDTO;
import com.example.ojt.model.dto.response.EducationCandidateResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IEduCandidateService {
    boolean saveOrUpdate(EduCandidateRequestDTO eduCandidateRequestDTO) throws CustomException;

    boolean removeEduCandidate(Integer id) throws CustomException;

    PageDataDTO<EducationCandidateResponseDTO> getProCandidate(String keyword, int page, int limit, String sort, String order) throws CustomException;

    Page<EducationCandidateResponseDTO> findAllWithPaginationAndSort(Pageable pageable);

    Page<EducationCandidateResponseDTO> searchByNameWithPaginationAndSort(Pageable pageable, String keyword);

}

package com.example.ojt.service.productCandidate;

import com.example.ojt.exception.CustomException;
import com.example.ojt.model.dto.mapper.PageDataDTO;
import com.example.ojt.model.dto.request.ProjectCandidateRequestDTO;
import com.example.ojt.model.dto.response.ProjectCandidateResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProductCandidateService {
    boolean saveOrUpdate(ProjectCandidateRequestDTO projectCandidateRequestDTO) throws CustomException;

    boolean removeProCandidate(Integer id) throws CustomException;

    PageDataDTO<ProjectCandidateResponseDTO> getProCandidate(String keyword, int page, int limit, String sort, String order) throws CustomException;
    Page<ProjectCandidateResponseDTO> findAllWithPaginationAndSort(Pageable pageable) ;

    Page<ProjectCandidateResponseDTO> searchByNameWithPaginationAndSort(Pageable pageable, String keyword);

}

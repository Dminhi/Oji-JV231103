package com.example.ojt.service.cerCandidate;

import com.example.ojt.exception.CustomException;
import com.example.ojt.model.dto.mapper.PageDataDTO;
import com.example.ojt.model.dto.request.CertificateCandidateRequestDTO;
import com.example.ojt.model.dto.response.CertificateCandidateResponseDTO;
import com.example.ojt.model.entity.CertificateCandidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICertificateCandidateService {
    Page<CertificateCandidateResponseDTO> findAllWithPaginationAndSort(Pageable pageable);
    Page<CertificateCandidateResponseDTO> searchByNameWithPaginationAndSort(Pageable pageable, String keyword);
    boolean save(CertificateCandidateRequestDTO certificateCandidateRequestDTO) throws CustomException;
    boolean update(CertificateCandidateRequestDTO certificateCandidateRequestDTO, Integer id) throws CustomException;

    boolean removeCerCandidate(Integer id) throws CustomException;

    PageDataDTO<CertificateCandidateResponseDTO> getCerCandidate(String keyword, int page, int limit, String sort, String order) throws CustomException;

   }


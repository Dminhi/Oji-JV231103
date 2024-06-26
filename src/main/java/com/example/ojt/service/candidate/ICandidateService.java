
package com.example.ojt.service.candidate;

import com.example.ojt.exception.CustomException;
import com.example.ojt.model.dto.mapper.PageDataDTO;
import com.example.ojt.model.dto.request.CandidateRequestDTO;
import com.example.ojt.model.dto.response.CandidateResponseDTO;
import com.example.ojt.model.entity.Candidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICandidateService {
    boolean saveOrUpdate(CandidateRequestDTO candidateRequestDTO, Integer id) throws CustomException;
    boolean save(CandidateRequestDTO candidateRequestDTO) throws CustomException;

    boolean removeCandidate(Integer id) throws CustomException;
   CandidateResponseDTO getCandidate() throws CustomException;

}


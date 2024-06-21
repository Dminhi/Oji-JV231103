package com.example.ojt.service.candidate;

import com.example.ojt.exception.CustomException;
import com.example.ojt.model.dto.request.CandidateRequestDTO;

public interface ICandidateService {
    boolean saveOrUpdate(CandidateRequestDTO candidateRequestDTO) throws CustomException;
}

package com.example.ojt.service.productCandidate;

import com.example.ojt.exception.CustomException;
import com.example.ojt.model.dto.request.ProjectCandidateRequestDTO;

public interface IProductCandidateService {
    boolean saveOrUpdate(ProjectCandidateRequestDTO projectCandidateRequestDTO) throws CustomException;
}

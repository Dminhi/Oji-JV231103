package com.example.ojt.service.projectCandidatde;

import com.example.ojt.exception.CustomException;
import com.example.ojt.model.dto.request.ProjectCandidateRequestDTO;

public interface IProjectCandidateService {
    boolean saveOrUpdate(ProjectCandidateRequestDTO projectCandidateRequestDTO) throws CustomException;
}

package com.example.ojt.service.experienceCandidate;

import com.example.ojt.exception.CustomException;
import com.example.ojt.model.dto.request.ExperienceCandidateRequestDTO;

public interface IExperienceCandidateService {
    boolean saveOrUpdate(ExperienceCandidateRequestDTO experienceCandidateRequestDTO) throws CustomException;
}

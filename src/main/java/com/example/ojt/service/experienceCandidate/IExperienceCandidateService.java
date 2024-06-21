package com.example.ojt.service.experienceCandidate;

import com.example.ojt.exception.CustomException;
import com.example.ojt.model.dto.request.ExperienceCandidateRequestDTO;
import com.example.ojt.model.entity.ExperienceCandidate;

public interface IExperienceCandidateService {
    boolean saveOrUpdate(ExperienceCandidateRequestDTO experienceCandidateRequestDTO) throws CustomException;
}

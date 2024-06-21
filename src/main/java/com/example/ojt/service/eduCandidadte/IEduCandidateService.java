package com.example.ojt.service.eduCandidadte;

import com.example.ojt.exception.CustomException;
import com.example.ojt.model.dto.request.EduCandidateRequestDTO;

public interface IEduCandidateService {
    boolean saveOrUpdate(EduCandidateRequestDTO eduCandidateRequestDTO) throws CustomException;
}

package com.example.ojt.service.certificateCandidate;

import com.example.ojt.exception.CustomException;
import com.example.ojt.model.dto.request.CertificateCandidateRequestDTO;

public interface ICertificateCandidateService {
    boolean saveOrUpdate(CertificateCandidateRequestDTO certificateCandidateRequestDTO) throws CustomException;
}

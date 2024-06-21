package com.example.ojt.service.cerCandidate;

import com.example.ojt.exception.CustomException;
import com.example.ojt.model.entity.CertificateCandidate;

public interface ICertificateCandidateService {
    boolean saveOrUpdate(CertificateCandidate certificateCandidate) throws CustomException;
}

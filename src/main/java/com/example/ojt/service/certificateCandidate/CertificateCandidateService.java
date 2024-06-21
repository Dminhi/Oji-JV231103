package com.example.ojt.service.certificateCandidate;

import com.example.ojt.exception.CustomException;
import com.example.ojt.model.dto.request.CertificateCandidateRequestDTO;
import com.example.ojt.model.entity.Account;
import com.example.ojt.model.entity.CertificateCandidate;
import com.example.ojt.model.entity.EducationCandidate;
import com.example.ojt.repository.IAccountRepository;
import com.example.ojt.repository.ICertificateCandidateRepository;
import com.example.ojt.security.principle.AccountDetailsCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CertificateCandidateService implements ICertificateCandidateService {
    @Autowired
    private IAccountRepository accountRepository;
    @Autowired
    private ICertificateCandidateRepository certificateCandidateRepository;
    @Override
    public boolean saveOrUpdate(CertificateCandidateRequestDTO certificateCandidateRequestDTO) throws CustomException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof AccountDetailsCustom accountDetailsCustom) {
            Account account = accountRepository.findById(accountDetailsCustom.getId())
                    .orElseThrow(() -> new CustomException("Account is not found with this id " + accountDetailsCustom.getId(), HttpStatus.NOT_FOUND));

            if (certificateCandidateRequestDTO.getId() != null) {
                // Update existing certificate candidate
                CertificateCandidate certificateCandidate = certificateCandidateRepository.findById(certificateCandidateRequestDTO.getId())
                        .orElseThrow(() -> new CustomException("Certificate candidate not found with this id " + certificateCandidateRequestDTO.getId(), HttpStatus.NOT_FOUND));

                certificateCandidate.setName(certificateCandidateRequestDTO.getName());
                certificateCandidate.setOrganization(certificateCandidateRequestDTO.getOrganization());
                certificateCandidate.setStartAt(certificateCandidateRequestDTO.getStartAt());
                certificateCandidate.setEndAt(certificateCandidateRequestDTO.getEndAt());
                certificateCandidate.setInfo(certificateCandidateRequestDTO.getInfo());
                certificateCandidate.setStatus(certificateCandidateRequestDTO.getStatus());
                certificateCandidateRepository.save(certificateCandidate);
            } else {
                // Save new Certificate candidate
                CertificateCandidate certificateCandidate = new CertificateCandidate();
                certificateCandidate.setName(certificateCandidateRequestDTO.getName());
                certificateCandidate.setOrganization(certificateCandidateRequestDTO.getOrganization());
                certificateCandidate.setStartAt(certificateCandidateRequestDTO.getStartAt());
                certificateCandidate.setEndAt(certificateCandidateRequestDTO.getEndAt());
                certificateCandidate.setInfo(certificateCandidateRequestDTO.getInfo());
                certificateCandidate.setStatus(certificateCandidateRequestDTO.getStatus());
                certificateCandidate.setCandidate(account.getCandidate());
                certificateCandidateRepository.save(certificateCandidate);
            }
            return true;
        }
        return false;
    }
}

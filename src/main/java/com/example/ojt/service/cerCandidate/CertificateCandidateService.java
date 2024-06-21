package com.example.ojt.service.cerCandidate;

import com.example.ojt.exception.CustomException;
import com.example.ojt.model.entity.Account;
import com.example.ojt.model.entity.CertificateCandidate;
import com.example.ojt.model.entity.EducationCandidate;
import com.example.ojt.repository.IAccountRepository;
import com.example.ojt.repository.ICertificateCandidateRepository;
import com.example.ojt.repository.IEduCandidateRepository;
import com.example.ojt.security.principle.AccountDetailsCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CertificateCandidateService implements ICertificateCandidateService{
    @Autowired
    private IAccountRepository accountRepository;
    @Autowired
    private ICertificateCandidateRepository certificateCandidateRepository;
    @Override
    public boolean saveOrUpdate(CertificateCandidate certificateCandidate) throws CustomException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof AccountDetailsCustom accountDetailsCustom) {
            Account account = accountRepository.findById(accountDetailsCustom.getId())
                    .orElseThrow(() -> new CustomException("Account is not found with this id " + accountDetailsCustom.getId(), HttpStatus.NOT_FOUND));

            if (certificateCandidate.getId() != null) {
                // Update existing education candidate
                CertificateCandidate certificateCandidate1 = certificateCandidateRepository.findById(certificateCandidate.getId())
                        .orElseThrow(() -> new CustomException("Education candidate not found with this id " + certificateCandidate.getId(), HttpStatus.NOT_FOUND));

                certificateCandidate1.setName(certificateCandidate.getName());
                certificateCandidate1.setOrganization(certificateCandidate.getOrganization());
                certificateCandidate1.setStartAt(certificateCandidate.getStartAt());
                certificateCandidate1.setEndAt(certificateCandidate.getEndAt());
                certificateCandidate1.setInfo(certificateCandidate.getInfo());
                certificateCandidate1.setStatus(1);
                certificateCandidateRepository.save(certificateCandidate1);
            } else {
                // Save new education candidate
                CertificateCandidate certificateCandidate1 = new CertificateCandidate();
                certificateCandidate1.setName(certificateCandidate.getName());
                certificateCandidate1.setOrganization(certificateCandidate.getOrganization());
                certificateCandidate1.setStartAt(certificateCandidate.getStartAt());
                certificateCandidate1.setEndAt(certificateCandidate.getEndAt());
                certificateCandidate1.setInfo(certificateCandidate.getInfo());
                certificateCandidate1.setStatus(1);
                certificateCandidate1.setCandidate(account.getCandidate());
                certificateCandidateRepository.save(certificateCandidate1);
            }
            return true;
        }
        return false;
    }
}

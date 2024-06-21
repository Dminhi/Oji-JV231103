package com.example.ojt.service.eduCandidadte;

import com.example.ojt.exception.CustomException;
import com.example.ojt.model.dto.request.EduCandidateRequestDTO;
import com.example.ojt.model.dto.response.CandidateResponseDTO;
import com.example.ojt.model.entity.Account;
import com.example.ojt.model.entity.Candidate;
import com.example.ojt.model.entity.EducationCandidate;
import com.example.ojt.repository.IAccountRepository;
import com.example.ojt.repository.ICandidateRepository;
import com.example.ojt.repository.IEduCandidateRepository;
import com.example.ojt.security.principle.AccountDetailsCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class EduCandidateService implements IEduCandidateService {
    @Autowired
    private IAccountRepository accountRepository;
    @Autowired
    private IEduCandidateRepository eduCandidateRepository;

    @Override
    public boolean saveOrUpdate(EduCandidateRequestDTO eduCandidateRequestDTO) throws CustomException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof AccountDetailsCustom accountDetailsCustom) {
            Account account = accountRepository.findById(accountDetailsCustom.getId())
                    .orElseThrow(() -> new CustomException("Account is not found with this id " + accountDetailsCustom.getId(), HttpStatus.NOT_FOUND));

            if (eduCandidateRequestDTO.getId() != null) {
                // Update existing education candidate
                EducationCandidate eduCandidate = eduCandidateRepository.findById(eduCandidateRequestDTO.getId())
                        .orElseThrow(() -> new CustomException("Education candidate not found with this id " + eduCandidateRequestDTO.getId(), HttpStatus.NOT_FOUND));

                eduCandidate.setNameEducation(eduCandidateRequestDTO.getNameEducation());
                eduCandidate.setMajor(eduCandidateRequestDTO.getMajor());
                eduCandidate.setStartAt(eduCandidateRequestDTO.getStartAt());
                eduCandidate.setEndAt(eduCandidateRequestDTO.getEndAt());
                eduCandidate.setInfo(eduCandidateRequestDTO.getInfo());
                eduCandidate.setStatus(eduCandidateRequestDTO.getStatus());
                eduCandidateRepository.save(eduCandidate);
            } else {
                // Save new education candidate
                EducationCandidate eduCandidate = new EducationCandidate();
                eduCandidate.setNameEducation(eduCandidateRequestDTO.getNameEducation());
                eduCandidate.setMajor(eduCandidateRequestDTO.getMajor());
                eduCandidate.setStartAt(eduCandidateRequestDTO.getStartAt());
                eduCandidate.setEndAt(eduCandidateRequestDTO.getEndAt());
                eduCandidate.setInfo(eduCandidateRequestDTO.getInfo());
                eduCandidate.setStatus(eduCandidateRequestDTO.getStatus());
                eduCandidate.setCandidate(account.getCandidate());
                eduCandidateRepository.save(eduCandidate);
            }
            return true;
        }
        return false;
    }


}

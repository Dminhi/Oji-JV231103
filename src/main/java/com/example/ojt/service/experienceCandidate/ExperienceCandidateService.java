package com.example.ojt.service.experienceCandidate;

import com.example.ojt.exception.CustomException;
import com.example.ojt.model.dto.request.ExperienceCandidateRequestDTO;
import com.example.ojt.model.entity.Account;
import com.example.ojt.model.entity.EducationCandidate;
import com.example.ojt.model.entity.ExperienceCandidate;
import com.example.ojt.repository.IAccountRepository;
import com.example.ojt.repository.IEduCandidateRepository;
import com.example.ojt.repository.IExperienceCandidateRepository;
import com.example.ojt.security.principle.AccountDetailsCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ExperienceCandidateService implements IExperienceCandidateService{
    @Autowired
    private IAccountRepository accountRepository;
    @Autowired
    private IExperienceCandidateRepository experienceCandidateRepository;
    @Override
    public boolean saveOrUpdate(ExperienceCandidateRequestDTO experienceCandidateRequestDTO) throws CustomException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof AccountDetailsCustom accountDetailsCustom) {
            Account account = accountRepository.findById(accountDetailsCustom.getId())
                    .orElseThrow(() -> new CustomException("Account is not found with this id " + accountDetailsCustom.getId(), HttpStatus.NOT_FOUND));

            if (experienceCandidateRequestDTO.getId() != null) {
                // Update existing education candidate
                ExperienceCandidate experienceCandidate = experienceCandidateRepository.findById(experienceCandidateRequestDTO.getId())
                        .orElseThrow(() -> new CustomException("Education candidate not found with this id " + experienceCandidateRequestDTO.getId(), HttpStatus.NOT_FOUND));

                experienceCandidate.setCompany(experienceCandidateRequestDTO.getCompany());
                experienceCandidate.setPosition(experienceCandidateRequestDTO.getPosition());
                experienceCandidate.setStartAt(experienceCandidateRequestDTO.getStartAt());
                experienceCandidate.setEndAt(experienceCandidateRequestDTO.getEndAt());
                experienceCandidate.setInfo(experienceCandidateRequestDTO.getInfo());
                experienceCandidate.setStatus(1);
                experienceCandidateRepository.save(experienceCandidate);
            } else {
                // Save new education candidate
                ExperienceCandidate experienceCandidate = new ExperienceCandidate();
                experienceCandidate.setCompany(experienceCandidateRequestDTO.getCompany());
                experienceCandidate.setPosition(experienceCandidateRequestDTO.getPosition());
                experienceCandidate.setStartAt(experienceCandidateRequestDTO.getStartAt());
                experienceCandidate.setEndAt(experienceCandidateRequestDTO.getEndAt());
                experienceCandidate.setInfo(experienceCandidateRequestDTO.getInfo());
                experienceCandidate.setStatus(1);
                experienceCandidate.setCandidate(account.getCandidate());
                experienceCandidateRepository.save(experienceCandidate);
            }
            return true;
        }
        return false;
    }
}

package com.example.ojt.service.projectCandidatde;

import com.example.ojt.exception.CustomException;
import com.example.ojt.model.dto.request.ProjectCandidateRequestDTO;
import com.example.ojt.model.entity.Account;
import com.example.ojt.model.entity.CertificateCandidate;
import com.example.ojt.model.entity.ProjectCandidate;
import com.example.ojt.repository.IAccountRepository;
import com.example.ojt.repository.ICertificateCandidateRepository;
import com.example.ojt.repository.IProjectCandidateRepository;
import com.example.ojt.security.principle.AccountDetailsCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ProjectCandidateService implements IProjectCandidateService {
    @Autowired
    private IAccountRepository accountRepository;
    @Autowired
    private IProjectCandidateRepository projectCandidateRepository;
    @Override
    public boolean saveOrUpdate(ProjectCandidateRequestDTO projectCandidateRequestDTO) throws CustomException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof AccountDetailsCustom accountDetailsCustom) {
            Account account = accountRepository.findById(accountDetailsCustom.getId())
                    .orElseThrow(() -> new CustomException("Account is not found with this id " + accountDetailsCustom.getId(), HttpStatus.NOT_FOUND));

            if (projectCandidateRequestDTO.getId() != null) {
                // Update existing certificate candidate
                ProjectCandidate projectCandidate = projectCandidateRepository.findById(projectCandidateRequestDTO.getId())
                        .orElseThrow(() -> new CustomException("Project candidate not found with this id " + projectCandidateRequestDTO.getId(), HttpStatus.NOT_FOUND));

                projectCandidate.setName(projectCandidateRequestDTO.getName());
                projectCandidate.setLink(projectCandidateRequestDTO.getLink());
                projectCandidate.setStartAt(projectCandidateRequestDTO.getStartAt());
                projectCandidate.setEndAt(projectCandidateRequestDTO.getEndAt());
                projectCandidate.setInfo(projectCandidateRequestDTO.getInfo());
                projectCandidate.setStatus(1);
                projectCandidateRepository.save(projectCandidate);
            } else {
                // Save new Certificate candidate
                ProjectCandidate projectCandidate = new ProjectCandidate();
                projectCandidate.setName(projectCandidateRequestDTO.getName());
                projectCandidate.setLink(projectCandidateRequestDTO.getLink());
                projectCandidate.setStartAt(projectCandidateRequestDTO.getStartAt());
                projectCandidate.setEndAt(projectCandidateRequestDTO.getEndAt());
                projectCandidate.setInfo(projectCandidateRequestDTO.getInfo());
                projectCandidate.setStatus(1);
                projectCandidate.setCandidate(account.getCandidate());
                projectCandidateRepository.save(projectCandidate);
            }
            return true;
        }
        return false;
    }
}

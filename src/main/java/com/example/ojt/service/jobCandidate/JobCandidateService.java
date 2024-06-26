package com.example.ojt.service.jobCandidate;

import com.example.ojt.exception.CustomException;
import com.example.ojt.model.dto.mapper.PageDataDTO;
import com.example.ojt.model.dto.request.JobCandidateRequestDTO;
import com.example.ojt.model.dto.response.JobCandidateResponseDTO;
import com.example.ojt.model.entity.Account;
import com.example.ojt.model.entity.JobCandidates;
import com.example.ojt.repository.IAccountRepository;
import com.example.ojt.repository.IJobCandidateRepository;
import com.example.ojt.repository.JobRepository;
import com.example.ojt.security.principle.AccountDetailsCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.util.Objects;

@Service
public class JobCandidateService implements IJobCandidateService {
    @Autowired
    private IAccountRepository accountRepository;
    @Autowired
    private IJobCandidateRepository jobCandidateRepository;
    @Autowired
    private JobRepository jobRepository;

    @Override
    public boolean save(JobCandidateRequestDTO jobCandidateRequestDTO) throws CustomException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof AccountDetailsCustom accountDetailsCustom) {
            Account account = accountRepository.findById(accountDetailsCustom.getId())
                    .orElseThrow(() -> new CustomException("Account is not found with this id " + accountDetailsCustom.getId(), HttpStatus.NOT_FOUND));
            // add new existing education candidate
            JobCandidates jobCandidates = new JobCandidates();
            jobCandidates.setCvUrl(jobCandidateRequestDTO.getCvUrl());
            jobCandidates.setInterviewDay(jobCandidateRequestDTO.getInterviewDay());
            jobCandidates.setContent(jobCandidateRequestDTO.getContent());
            jobCandidates.setCandidate(account.getCandidate());
            jobCandidates.setJob(jobRepository.findById(jobCandidateRequestDTO.getJobId()).orElseThrow(() -> new CustomException("JOb is not found with this id " + jobCandidateRequestDTO.getJobId(), HttpStatus.NOT_FOUND)));
            jobCandidates.setStatus(true);
            jobCandidateRepository.save(jobCandidates);
            return true;
        }
        return false;
    }

    @Override
    public boolean update(JobCandidateRequestDTO jobCandidateRequestDTO, Integer id) throws CustomException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof AccountDetailsCustom accountDetailsCustom) {
            Account account = accountRepository.findById(accountDetailsCustom.getId())
                    .orElseThrow(() -> new CustomException("Account is not found with this id " + accountDetailsCustom.getId(), HttpStatus.NOT_FOUND));
            // Update existing education candidate
            JobCandidates jobCandidates = jobCandidateRepository.findById(id)
                    .orElseThrow(() -> new CustomException("Jbb candidate not found with this id " + id, HttpStatus.NOT_FOUND));
            jobCandidates.setContent(jobCandidateRequestDTO.getContent());
            jobCandidates.setCvUrl(jobCandidateRequestDTO.getCvUrl());
            jobCandidates.setInterviewDay(jobCandidateRequestDTO.getInterviewDay());
            jobCandidates.setJob(jobRepository.findById(jobCandidateRequestDTO.getJobId()).orElseThrow(() -> new CustomException("JOb is not found with this id " + jobCandidateRequestDTO.getJobId(), HttpStatus.NOT_FOUND)));
            jobCandidates.setStatus(true);
            jobCandidateRepository.save(jobCandidates);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeJobCandidate(Integer id) throws CustomException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof AccountDetailsCustom accountDetailsCustom) {
            JobCandidates jobCandidates = jobCandidateRepository.findById(id).orElseThrow(() -> new CustomException("Job Candidate not found with this id " + id, HttpStatus.NOT_FOUND));
            if (Objects.equals(jobCandidates.getCandidate().getAccount().getId(), accountDetailsCustom.getId())) {
                jobCandidates.setStatus(false);
                jobCandidateRepository.save(jobCandidates);
                return true;
            }
            throw new CustomException("Job Candidate is not found with this id " + id, HttpStatus.NOT_FOUND);
        }
        return false;
    }

    @Override
    public PageDataDTO<JobCandidateResponseDTO> getJobCandidate(String keyword, int page, int limit, String sort, String order) throws CustomException {
        Sort.Direction direction = order.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(direction, sort));
        Page<JobCandidateResponseDTO> jobCandidateResponseDTOS;
        if (keyword != null && !keyword.isEmpty()) {
            jobCandidateResponseDTOS = searchByNameWithPaginationAndSort(pageable, keyword);
        } else {
            jobCandidateResponseDTOS = findAllWithPaginationAndSort(pageable);
        }
        if (jobCandidateResponseDTOS == null || jobCandidateResponseDTOS.isEmpty()) {
            throw new CustomException("Job Candidate is not found", HttpStatus.NOT_FOUND);

        }
        PageDataDTO<JobCandidateResponseDTO> pageDataDTO = new PageDataDTO<>();
        pageDataDTO.setCurrentPage(jobCandidateResponseDTOS.getNumber());
        pageDataDTO.setTotalPage(jobCandidateResponseDTOS.getTotalPages());
        pageDataDTO.setLimit(jobCandidateResponseDTOS.getSize());
        pageDataDTO.setSort(sort);
        pageDataDTO.setTotalElement(jobCandidateResponseDTOS.getTotalElements());
        pageDataDTO.setSearchName(keyword == null ? "" : keyword);
        pageDataDTO.setContent(jobCandidateResponseDTOS.getContent());
        return pageDataDTO;
    }

    @Override
    public Page<JobCandidateResponseDTO> findAllWithPaginationAndSort(Pageable pageable) {
        Page<JobCandidates> list = jobCandidateRepository.findAll(pageable);
        return list.map(JobCandidateResponseDTO::new);
    }

    @Override
    public Page<JobCandidateResponseDTO> searchByNameWithPaginationAndSort(Pageable pageable, String keyword) {
        Page<JobCandidates> list = jobCandidateRepository.findByInterview(pageable,keyword);
        return list.map(JobCandidateResponseDTO::new);
    }
}

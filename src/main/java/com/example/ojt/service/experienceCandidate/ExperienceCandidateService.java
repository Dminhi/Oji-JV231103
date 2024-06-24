package com.example.ojt.service.experienceCandidate;

import com.example.ojt.exception.CustomException;
import com.example.ojt.model.dto.mapper.PageDataDTO;
import com.example.ojt.model.dto.request.ExperienceCandidateRequestDTO;
import com.example.ojt.model.dto.response.ExperienceCandidateResponseDTO;
import com.example.ojt.model.entity.Account;
import com.example.ojt.model.entity.ExperienceCandidate;
import com.example.ojt.repository.IAccountRepository;
import com.example.ojt.repository.IExperienceCandidateRepository;
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
public class ExperienceCandidateService implements IExperienceCandidateService {
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

    @Override
    public boolean removeExCandidate(Integer id) throws CustomException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof AccountDetailsCustom accountDetailsCustom) {
            ExperienceCandidate experienceCandidate = experienceCandidateRepository.findById(id).orElseThrow(() -> new CustomException("Certificate Candidate not found with this id " + id, HttpStatus.NOT_FOUND));
            if (Objects.equals(experienceCandidate.getCandidate().getAccount().getId(), accountDetailsCustom.getId())) {
                experienceCandidate.setStatus(2);
                experienceCandidateRepository.save(experienceCandidate);
                return true;
            }
            throw new CustomException("Experience Candidate is not found with this id " + id, HttpStatus.NOT_FOUND);
        }
        return false;
    }

    @Override
    public PageDataDTO<ExperienceCandidateResponseDTO> getExCandidate(String keyword, int page, int limit, String sort, String order) throws CustomException {
        Sort.Direction direction = order.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(direction, sort));
        Page<ExperienceCandidateResponseDTO> experienceCandidateResponseDTOS;
        if (keyword != null && !keyword.isEmpty()) {
            experienceCandidateResponseDTOS = searchByNameWithPaginationAndSort(pageable, keyword);
        } else {
            experienceCandidateResponseDTOS = findAllWithPaginationAndSort(pageable);
        }
        if (experienceCandidateResponseDTOS == null || experienceCandidateResponseDTOS.isEmpty()) {
            throw new CustomException("Certificate Candidate is not found", HttpStatus.NOT_FOUND);

        }
        PageDataDTO<ExperienceCandidateResponseDTO> pageDataDTO = new PageDataDTO<>();
        pageDataDTO.setCurrentPage(experienceCandidateResponseDTOS.getNumber());
        pageDataDTO.setTotalPage(experienceCandidateResponseDTOS.getTotalPages());
        pageDataDTO.setLimit(experienceCandidateResponseDTOS.getSize());
        pageDataDTO.setSort(sort);
        pageDataDTO.setTotalElement(experienceCandidateResponseDTOS.getTotalElements());
        pageDataDTO.setSearchName(keyword == null ? "" : keyword);
        pageDataDTO.setContent(experienceCandidateResponseDTOS.getContent());
        return pageDataDTO;
    }

    @Override
    public Page<ExperienceCandidateResponseDTO> findAllWithPaginationAndSort(Pageable pageable) {
        Page<ExperienceCandidate> list = experienceCandidateRepository.findAll(pageable);
        return list.map(ExperienceCandidateResponseDTO::new);
    }

    @Override
    public Page<ExperienceCandidateResponseDTO> searchByNameWithPaginationAndSort(Pageable pageable, String keyword) {
        Page<ExperienceCandidate> list = experienceCandidateRepository.findAll(pageable);
        return list.map(ExperienceCandidateResponseDTO::new);
    }

}

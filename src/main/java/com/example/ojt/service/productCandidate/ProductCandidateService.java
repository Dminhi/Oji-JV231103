package com.example.ojt.service.productCandidate;

import com.example.ojt.exception.CustomException;
import com.example.ojt.model.dto.mapper.PageDataDTO;
import com.example.ojt.model.dto.request.ProjectCandidateRequestDTO;
import com.example.ojt.model.dto.response.ProjectCandidateResponseDTO;
import com.example.ojt.model.entity.Account;
import com.example.ojt.model.entity.ProjectCandidate;
import com.example.ojt.repository.IAccountRepository;
import com.example.ojt.repository.IProductCandidateRepository;
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
public class ProductCandidateService implements IProductCandidateService {
    @Autowired
    private IAccountRepository accountRepository;
    @Autowired
    private IProductCandidateRepository productCandidateRepository;

    @Override
    public boolean save(ProjectCandidateRequestDTO projectCandidateRequestDTO) throws CustomException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof AccountDetailsCustom accountDetailsCustom) {
            Account account = accountRepository.findById(accountDetailsCustom.getId()).orElseThrow(() -> new CustomException("Account is not found with this id " + accountDetailsCustom.getId(), HttpStatus.NOT_FOUND));
                // Save new education candidate
                ProjectCandidate projectCandidate = new ProjectCandidate();
                projectCandidate.setName(projectCandidateRequestDTO.getName());
                projectCandidate.setLink(projectCandidateRequestDTO.getLink());
                projectCandidate.setStartAt(projectCandidateRequestDTO.getStartAt());
                projectCandidate.setEndAt(projectCandidateRequestDTO.getEndAt());
                projectCandidate.setInfo(projectCandidateRequestDTO.getInfo());
                projectCandidate.setStatus(true);
                projectCandidate.setCandidate(account.getCandidate());
                productCandidateRepository.save(projectCandidate);
            return true;
        }
        return false;
    }

    @Override
    public boolean update(ProjectCandidateRequestDTO projectCandidateRequestDTO, Integer id) throws CustomException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof AccountDetailsCustom accountDetailsCustom) {
            Account account = accountRepository.findById(accountDetailsCustom.getId())
                    .orElseThrow(() -> new CustomException("Account is not found with this id " + accountDetailsCustom.getId(), HttpStatus.NOT_FOUND));
                ProjectCandidate projectCandidate = productCandidateRepository.findById(id)
                        .orElseThrow(() -> new CustomException("Education candidate not found with this id " +id, HttpStatus.NOT_FOUND));
                projectCandidate.setName(projectCandidateRequestDTO.getName());
                projectCandidate.setLink(projectCandidateRequestDTO.getLink());
                projectCandidate.setStartAt(projectCandidateRequestDTO.getStartAt());
                projectCandidate.setEndAt(projectCandidateRequestDTO.getEndAt());
                projectCandidate.setInfo(projectCandidateRequestDTO.getInfo());
                projectCandidate.setStatus(true);
                productCandidateRepository.save(projectCandidate);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeProCandidate(Integer id) throws CustomException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof AccountDetailsCustom accountDetailsCustom) {
            ProjectCandidate projectCandidate = productCandidateRepository.findById(id).orElseThrow(() -> new CustomException("Certificate Candidate not found with this id " + id, HttpStatus.NOT_FOUND));
            if (Objects.equals(projectCandidate.getCandidate().getAccount().getId(), accountDetailsCustom.getId())) {
                projectCandidate.setStatus(false);
                productCandidateRepository.save(projectCandidate);
                return true;
            }
            throw new CustomException("Project Candidate is not found with this id " + id, HttpStatus.NOT_FOUND);
        }
        return false;
    }

    @Override
    public PageDataDTO<ProjectCandidateResponseDTO> getProCandidate(String keyword, int page, int limit, String sort, String order) throws CustomException {
        Sort.Direction direction = order.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(direction, sort));
        Page<ProjectCandidateResponseDTO> projectCandidateResponseDTOS;
        if (keyword != null && !keyword.isEmpty()) {
            projectCandidateResponseDTOS = searchByNameWithPaginationAndSort(pageable, keyword);
        } else {
            projectCandidateResponseDTOS = findAllWithPaginationAndSort(pageable);
        }
        if (projectCandidateResponseDTOS == null || projectCandidateResponseDTOS.isEmpty()) {
            throw new CustomException("Project Candidate is not found", HttpStatus.NOT_FOUND);

        }
        PageDataDTO<ProjectCandidateResponseDTO> pageDataDTO = new PageDataDTO<>();
        pageDataDTO.setCurrentPage(projectCandidateResponseDTOS.getNumber());
        pageDataDTO.setTotalPage(projectCandidateResponseDTOS.getTotalPages());
        pageDataDTO.setLimit(projectCandidateResponseDTOS.getSize());
        pageDataDTO.setSort(sort);
        pageDataDTO.setTotalElement(projectCandidateResponseDTOS.getTotalElements());
        pageDataDTO.setSearchName(keyword == null ? "" : keyword);
        pageDataDTO.setContent(projectCandidateResponseDTOS.getContent());
        return pageDataDTO;
    }

    @Override
    public Page<ProjectCandidateResponseDTO> findAllWithPaginationAndSort(Pageable pageable) {
        Page<ProjectCandidate> list = productCandidateRepository.findAll(pageable);
        return list.map(ProjectCandidateResponseDTO::new);
    }

    @Override
    public Page<ProjectCandidateResponseDTO> searchByNameWithPaginationAndSort(Pageable pageable, String keyword) {
        Page<ProjectCandidate> list = productCandidateRepository.findByName(pageable,keyword);
        return list.map(ProjectCandidateResponseDTO::new);
    }

}

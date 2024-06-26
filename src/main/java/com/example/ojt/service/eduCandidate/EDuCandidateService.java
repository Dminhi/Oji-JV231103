package com.example.ojt.service.eduCandidate;

import com.example.ojt.exception.CustomException;
import com.example.ojt.model.dto.mapper.PageDataDTO;
import com.example.ojt.model.dto.request.EduCandidateRequestDTO;

import com.example.ojt.model.dto.response.CertificateCandidateResponseDTO;
import com.example.ojt.model.dto.response.EducationCandidateResponseDTO;
import com.example.ojt.model.dto.response.ProjectCandidateResponseDTO;
import com.example.ojt.model.entity.Account;
import com.example.ojt.model.entity.CertificateCandidate;
import com.example.ojt.model.entity.EducationCandidate;
import com.example.ojt.model.entity.ProjectCandidate;
import com.example.ojt.repository.IAccountRepository;
import com.example.ojt.repository.IEduCandidateRepository;
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
public class EDuCandidateService implements IEduCandidateService{
    @Autowired
    private IAccountRepository accountRepository;
    @Autowired
    private IEduCandidateRepository eduCandidateRepository;
    @Override
    public boolean save(EduCandidateRequestDTO eduCandidateRequestDTO) throws CustomException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof AccountDetailsCustom accountDetailsCustom) {
            Account account = accountRepository.findById(accountDetailsCustom.getId())
                    .orElseThrow(() -> new CustomException("Account is not found with this id " + accountDetailsCustom.getId(), HttpStatus.NOT_FOUND));
                // Save new education candidate
                EducationCandidate eduCandidate = new EducationCandidate();
                eduCandidate.setNameEducation(eduCandidateRequestDTO.getNameEducation());
                eduCandidate.setMajor(eduCandidateRequestDTO.getMajor());
                eduCandidate.setStartAt(eduCandidateRequestDTO.getStartAt());
                eduCandidate.setEndAt(eduCandidateRequestDTO.getEndAt());
                eduCandidate.setInfo(eduCandidateRequestDTO.getInfo());
                eduCandidate.setStatus(true);
                eduCandidate.setCandidate(account.getCandidate());
                eduCandidateRepository.save(eduCandidate);
            return true;
        }
        return false;
    }
    @Override
    public boolean update(EduCandidateRequestDTO eduCandidateRequestDTO,Integer id) throws CustomException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof AccountDetailsCustom accountDetailsCustom) {
            Account account = accountRepository.findById(accountDetailsCustom.getId())
                    .orElseThrow(() -> new CustomException("Account is not found with this id " + accountDetailsCustom.getId(), HttpStatus.NOT_FOUND));

                // Update existing education candidate
                EducationCandidate eduCandidate = eduCandidateRepository.findById(id)
                        .orElseThrow(() -> new CustomException("Education candidate not found with this id " + id, HttpStatus.NOT_FOUND));

                eduCandidate.setNameEducation(eduCandidateRequestDTO.getNameEducation());
                eduCandidate.setMajor(eduCandidateRequestDTO.getMajor());
                eduCandidate.setStartAt(eduCandidateRequestDTO.getStartAt());
                eduCandidate.setEndAt(eduCandidateRequestDTO.getEndAt());
                eduCandidate.setInfo(eduCandidateRequestDTO.getInfo());
                eduCandidate.setStatus(true);
                eduCandidateRepository.save(eduCandidate);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeEduCandidate(Integer id) throws CustomException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof AccountDetailsCustom accountDetailsCustom) {
          EducationCandidate educationCandidate = eduCandidateRepository.findById(id).orElseThrow(() -> new CustomException("Education Candidate not found with this id " + id, HttpStatus.NOT_FOUND));
            if (Objects.equals(educationCandidate.getCandidate().getAccount().getId(), accountDetailsCustom.getId())) {
                educationCandidate.setStatus(false);
                eduCandidateRepository.save(educationCandidate);
                return true;
            }
            throw new CustomException("Education Candidate is not found with this id " + id, HttpStatus.NOT_FOUND);
        }
        return false;
    }

    @Override
    public PageDataDTO<EducationCandidateResponseDTO> getProCandidate(String keyword, int page, int limit, String sort, String order) throws CustomException {
        Sort.Direction direction = order.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(direction, sort));
        Page<EducationCandidateResponseDTO> educationCandidateResponseDTOS;
        if (keyword != null && !keyword.isEmpty()) {
            educationCandidateResponseDTOS = searchByNameWithPaginationAndSort(pageable, keyword);
        } else {
            educationCandidateResponseDTOS = findAllWithPaginationAndSort(pageable);
        }
        if (educationCandidateResponseDTOS == null || educationCandidateResponseDTOS.isEmpty()) {
            throw new CustomException("Education Candidate is not found", HttpStatus.NOT_FOUND);

        }
        PageDataDTO<EducationCandidateResponseDTO> pageDataDTO = new PageDataDTO<>();
        pageDataDTO.setCurrentPage(educationCandidateResponseDTOS.getNumber());
        pageDataDTO.setTotalPage(educationCandidateResponseDTOS.getTotalPages());
        pageDataDTO.setLimit(educationCandidateResponseDTOS.getSize());
        pageDataDTO.setSort(sort);
        pageDataDTO.setTotalElement(educationCandidateResponseDTOS.getTotalElements());
        pageDataDTO.setSearchName(keyword == null ? "" : keyword);
        pageDataDTO.setContent(educationCandidateResponseDTOS.getContent());
        return pageDataDTO;
    }
@Override
    public Page<EducationCandidateResponseDTO> findAllWithPaginationAndSort(Pageable pageable) {
    Page<EducationCandidate> list = eduCandidateRepository.findAll(pageable);
    return list.map(EducationCandidateResponseDTO::new);
    }
@Override
public Page<EducationCandidateResponseDTO> searchByNameWithPaginationAndSort(Pageable pageable, String keyword) {
    Page<EducationCandidate> list = eduCandidateRepository.findAllByAddressContainingKeyword(pageable,keyword);
    return list.map(EducationCandidateResponseDTO::new);
    }


}

package com.example.ojt.service.skillCandidate;

import com.example.ojt.exception.CustomException;
import com.example.ojt.model.dto.mapper.PageDataDTO;
import com.example.ojt.model.dto.request.SkillCandidateRequestDTO;
import com.example.ojt.model.dto.response.SkillCandidateResponse;
import com.example.ojt.model.entity.Account;
import com.example.ojt.model.entity.SkillsCandidate;
import com.example.ojt.repository.IAccountRepository;
import com.example.ojt.repository.ILeveJobRepository;
import com.example.ojt.repository.ISkillCandidateRepository;
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
public class SkillCandidateService implements ISkillCandidateService {
    @Autowired
    private IAccountRepository accountRepository;
    @Autowired
    private ISkillCandidateRepository skillCandidateRepository;
    @Autowired
    private ILeveJobRepository leveJobRepository;

    @Override
    public boolean save(SkillCandidateRequestDTO skillCandidateRequestDTO) throws CustomException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof AccountDetailsCustom accountDetailsCustom) {
            Account account = accountRepository.findById(accountDetailsCustom.getId())
                    .orElseThrow(() -> new CustomException("Account is not found with this id " + accountDetailsCustom.getId(), HttpStatus.NOT_FOUND));
            // add new existing skill candidate
            SkillsCandidate skillsCandidate = new SkillsCandidate();
            skillsCandidate.setName(skillCandidateRequestDTO.getName());
            skillsCandidate.setCandidate(account.getCandidate());
            skillsCandidate.setLevelJob(leveJobRepository.findById(skillCandidateRequestDTO.getLevelJobId()).orElseThrow(() -> new CustomException("LevelJob is not found", HttpStatus.NOT_FOUND)));
            skillsCandidate.setStatus(true);
            skillCandidateRepository.save(skillsCandidate);
            return true;
        }
        return false;
    }

    @Override
    public boolean update(SkillCandidateRequestDTO skillCandidateRequestDTO, Integer id) throws CustomException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof AccountDetailsCustom accountDetailsCustom) {
            Account account = accountRepository.findById(accountDetailsCustom.getId())
                    .orElseThrow(() -> new CustomException("Account is not found with this id " + accountDetailsCustom.getId(), HttpStatus.NOT_FOUND));
            // Update existing education candidate
            SkillsCandidate skillsCandidate = skillCandidateRepository.findById(id)
                    .orElseThrow(() -> new CustomException("Skill candidate not found with this id " + id, HttpStatus.NOT_FOUND));

            skillsCandidate.setName(skillCandidateRequestDTO.getName());
            skillsCandidate.setLevelJob(leveJobRepository.findById(skillCandidateRequestDTO.getLevelJobId()).orElseThrow(() -> new CustomException("LevelJob is not found", HttpStatus.NOT_FOUND)));
            skillsCandidate.setStatus(true);
            skillCandidateRepository.save(skillsCandidate);
            return true;
        }
        return false;
    }

    @Override
    public PageDataDTO<SkillCandidateResponse> getSkillCandidate(String keyword, int page, int limit, String sort, String order) throws CustomException {
        Sort.Direction direction = order.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(direction, sort));
        Page<SkillCandidateResponse> skillCandidateResponses;
        if (keyword != null && !keyword.isEmpty()) {
            skillCandidateResponses = searchByNameWithPaginationAndSort(pageable, keyword);
        } else {
            skillCandidateResponses = findAllWithPaginationAndSort(pageable);
        }
        if (skillCandidateResponses == null || skillCandidateResponses.isEmpty()) {
            throw new CustomException("Skill Candidate is not found", HttpStatus.NOT_FOUND);

        }
        PageDataDTO<SkillCandidateResponse> pageDataDTO = new PageDataDTO<>();
        pageDataDTO.setCurrentPage(skillCandidateResponses.getNumber());
        pageDataDTO.setTotalPage(skillCandidateResponses.getTotalPages());
        pageDataDTO.setLimit(skillCandidateResponses.getSize());
        pageDataDTO.setSort(sort);
        pageDataDTO.setTotalElement(skillCandidateResponses.getTotalElements());
        pageDataDTO.setSearchName(keyword == null ? "" : keyword);
        pageDataDTO.setContent(skillCandidateResponses.getContent());
        return pageDataDTO;
    }

    @Override
    public Page<SkillCandidateResponse> findAllWithPaginationAndSort(Pageable pageable) {
        Page<SkillsCandidate> list = skillCandidateRepository.findAll(pageable);
        return list.map(SkillCandidateResponse::new);
    }

    @Override
    public Page<SkillCandidateResponse> searchByNameWithPaginationAndSort(Pageable pageable, String keyword) {
        Page<SkillsCandidate> list = skillCandidateRepository.findByName(pageable,keyword);
        return list.map(SkillCandidateResponse::new);
    }

    @Override
    public boolean removeSkillCandidate(Integer id) throws CustomException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof AccountDetailsCustom accountDetailsCustom) {
            SkillsCandidate skillsCandidate = skillCandidateRepository.findById(id).orElseThrow(() -> new CustomException("Skill Candidate not found with this id " + id, HttpStatus.NOT_FOUND));
            if (Objects.equals(skillsCandidate.getCandidate().getAccount().getId(), accountDetailsCustom.getId())) {
                skillsCandidate.setStatus(false);
                skillCandidateRepository.save(skillsCandidate);
                return true;
            }
            throw new CustomException("Skill Candidate is not found with this id " + id, HttpStatus.NOT_FOUND);
        }
        return false;
    }
}

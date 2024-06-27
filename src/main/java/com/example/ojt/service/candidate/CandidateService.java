
package com.example.ojt.service.candidate;

import com.example.ojt.exception.CustomException;
import com.example.ojt.model.dto.mapper.PageDataDTO;
import com.example.ojt.model.dto.request.CandidateRequestDTO;
import com.example.ojt.model.dto.response.CandidateListResponseDTO;
import com.example.ojt.model.dto.response.CandidateResponseDTO;
import com.example.ojt.model.dto.response.CompanyResponseDTO;
import com.example.ojt.model.entity.Account;
import com.example.ojt.model.entity.Candidate;
import com.example.ojt.model.entity.Company;
import com.example.ojt.repository.IAccountRepository;
import com.example.ojt.repository.ICandidateRepository;
import com.example.ojt.repository.ICompanyRepository;
import com.example.ojt.security.principle.AccountDetailsCustom;
import com.example.ojt.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

@Service
public class CandidateService implements ICandidateService {

    @Autowired
    private UploadService uploadService;

    @Autowired
    private ICandidateRepository candidateRepository;

    @Autowired
    private IAccountRepository accountRepository;


    @Override
    public boolean saveOrUpdate(CandidateRequestDTO candidateRequestDTO, Integer id) throws CustomException {
        String fileName;
            CandidateResponseDTO candidateResponseDTO = findById(id);
            // updateCandidate
            // kiểm tra có upload ảnh không
            if (candidateRequestDTO.getAvatar() != null && candidateRequestDTO.getAvatar().getSize() > 0) {
                fileName = uploadService.uploadFileToServer(candidateRequestDTO.getAvatar());
            } else {
                fileName = candidateResponseDTO.getAvatar();
            }


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof AccountDetailsCustom accountDetailsCustom) {
            Account account = accountRepository.findById(accountDetailsCustom.getId())
                    .orElseThrow(() -> new CustomException("Account is not found with this id " + accountDetailsCustom.getId(), HttpStatus.NOT_FOUND));

            Candidate candidate = Candidate.builder()
                    .id(id)
                    .name(candidateRequestDTO.getName())
                    .status(candidateRequestDTO.isStatus())
                    .birthday(candidateRequestDTO.getBirthday())
                    .address(candidateRequestDTO.getAddress())
                    .phone(candidateRequestDTO.getPhone())
                    .gender(candidateRequestDTO.getGender())
                    .linkLinkedin(candidateRequestDTO.getLinkLinkedin())
                    .linkGit(candidateRequestDTO.getLinkGit())
                    .position(candidateRequestDTO.getPosition())
                    .createdAt(candidateRequestDTO.getCreateAt())
                    .updatedAt(new Date())
                    .avatar(fileName)
                    .aboutme(candidateRequestDTO.getAboutme())
                    .account(account)
                    .build();

            candidateRepository.save(candidate);
            return true;
        } else {
            throw new CustomException("Principal is not an instance of AccountDetailsCustom", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean save(CandidateRequestDTO candidateRequestDTO) throws CustomException {
        String fileName;
            // addCandidate
            // check image
            if (candidateRequestDTO.getAvatar() == null || candidateRequestDTO.getAvatar().getSize() == 0) {
                throw new CustomException("File image is not found", HttpStatus.NOT_FOUND);
            }
            fileName = uploadService.uploadFileToServer(candidateRequestDTO.getAvatar());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof AccountDetailsCustom accountDetailsCustom) {
            Account account = accountRepository.findById(accountDetailsCustom.getId())
                    .orElseThrow(() -> new CustomException("Account is not found with this id " + accountDetailsCustom.getId(), HttpStatus.NOT_FOUND));

            Candidate candidate = Candidate.builder()
                    .name(candidateRequestDTO.getName())
                    .status(candidateRequestDTO.isStatus())
                    .birthday(candidateRequestDTO.getBirthday())
                    .address(candidateRequestDTO.getAddress())
                    .phone(candidateRequestDTO.getPhone())
                    .gender(candidateRequestDTO.getGender())
                    .linkLinkedin(candidateRequestDTO.getLinkLinkedin())
                    .linkGit(candidateRequestDTO.getLinkGit())
                    .position(candidateRequestDTO.getPosition())
                    .createdAt(candidateRequestDTO.getCreateAt())
                    .updatedAt(new Date())
                    .avatar(fileName)
                    .aboutme(candidateRequestDTO.getAboutme())
                    .account(account)
                    .build();

            candidateRepository.save(candidate);
            return true;
        } else {
            throw new CustomException("Principal is not an instance of AccountDetailsCustom", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public boolean removeCandidate(Integer id) throws CustomException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof AccountDetailsCustom accountDetailsCustom) {
            Account account = accountRepository.findById(accountDetailsCustom.getId())
                    .orElseThrow(() -> new CustomException("Account is not found with this id " + accountDetailsCustom.getId(), HttpStatus.NOT_FOUND));
            Candidate candidate = account.getCandidate();
            if (Objects.equals(candidate.getId(), id)) {
                candidate.setStatus(false);
                candidateRepository.save(candidate);
                return true;
            }
            throw new CustomException("Candidate is not found with this id " + id, HttpStatus.NOT_FOUND);
        }
        return false;
    }

    @Override
    public CandidateResponseDTO getCandidate() throws CustomException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof AccountDetailsCustom accountDetailsCustom) {
            Account account = accountRepository.findById(accountDetailsCustom.getId())
                    .orElseThrow(() -> new CustomException("Account is not found with this id " + accountDetailsCustom.getId(), HttpStatus.NOT_FOUND));Candidate candidate = account.getCandidate();
            CandidateResponseDTO candidateResponseDTO = findById(account.getCandidate().getId());
            if (candidate == null) {
                throw new CustomException("Candidate not found for this account", HttpStatus.NOT_FOUND);
            }
            return candidateResponseDTO;
        }
        throw new CustomException("You need to login", HttpStatus.UNAUTHORIZED);
    }

@Override
    public CandidateResponseDTO findById(Integer id) throws CustomException {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new CustomException("Candidate is not found with this id " + id, HttpStatus.NOT_FOUND));
        return new CandidateResponseDTO(candidate);
    }

    @Override
    public Page<CandidateListResponseDTO> findAllWithPaginationAndSort(Pageable pageable) {
        Page<Candidate> list = candidateRepository.findAll(pageable);
        return list.map(CandidateListResponseDTO::new);
    }

    @Override
    public Page<CandidateListResponseDTO> searchByNameWithPaginationAndSort(Pageable pageable, String keyword) {
        Page<Candidate> list = candidateRepository.findAndSort(pageable,keyword);
        return list.map(CandidateListResponseDTO::new);
    }

    @Override
    public PageDataDTO<CandidateListResponseDTO> getAllCandidate(String keyword, int page, int limit, String sort, String order) throws CustomException {
        Sort.Direction direction = order.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(direction, sort));
        Page<CandidateListResponseDTO> candidateResponseDTOS;
        if (keyword != null && !keyword.isEmpty()) {
            candidateResponseDTOS = searchByNameWithPaginationAndSort(pageable, keyword);
        } else {
            candidateResponseDTOS = findAllWithPaginationAndSort(pageable);
        }
        if (candidateResponseDTOS == null || candidateResponseDTOS.isEmpty()) {
            throw new CustomException("Candidate  is not found", HttpStatus.NOT_FOUND);

        }
        PageDataDTO<CandidateListResponseDTO> pageDataDTO = new PageDataDTO<>();
        pageDataDTO.setCurrentPage(candidateResponseDTOS.getNumber());
        pageDataDTO.setTotalPage(candidateResponseDTOS.getTotalPages());
        pageDataDTO.setLimit(candidateResponseDTOS.getSize());
        pageDataDTO.setSort(sort);
        pageDataDTO.setTotalElement(candidateResponseDTOS.getTotalElements());
        pageDataDTO.setSearchName(keyword == null ? "" : keyword);
        pageDataDTO.setContent(candidateResponseDTOS.getContent());
        return pageDataDTO;
    }
}


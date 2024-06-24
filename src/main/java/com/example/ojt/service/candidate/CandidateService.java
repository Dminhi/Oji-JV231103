package com.example.ojt.service.candidate;

import com.example.ojt.exception.CustomException;
import com.example.ojt.model.dto.mapper.PageDataDTO;
import com.example.ojt.model.dto.request.CandidateRequestDTO;
import com.example.ojt.model.dto.response.CandidateResponseDTO;
import com.example.ojt.model.entity.Account;
import com.example.ojt.model.entity.Candidate;
import com.example.ojt.repository.IAccountRepository;
import com.example.ojt.repository.ICandidateRepository;
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
    public boolean saveOrUpdate(CandidateRequestDTO candidateRequestDTO) throws CustomException {
        String fileName;
        if (candidateRequestDTO.getId() == null) {
            // addCandidate
            // check image
            if (candidateRequestDTO.getAvatar() == null || candidateRequestDTO.getAvatar().getSize() == 0) {
                throw new CustomException("File image is not found", HttpStatus.NOT_FOUND);
            }
            fileName = uploadService.uploadFileToServer(candidateRequestDTO.getAvatar());
        } else {
            CandidateResponseDTO candidateResponseDTO = findById(candidateRequestDTO.getId());
            // updateCandidate
            // kiểm tra có upload ảnh không
            if (candidateRequestDTO.getAvatar() != null && candidateRequestDTO.getAvatar().getSize() > 0) {
                fileName = uploadService.uploadFileToServer(candidateRequestDTO.getAvatar());
            } else {
                fileName = candidateResponseDTO.getAvatar();
            }
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof AccountDetailsCustom accountDetailsCustom) {
            Account account = accountRepository.findById(accountDetailsCustom.getId())
                    .orElseThrow(() -> new CustomException("Account is not found with this id " + accountDetailsCustom.getId(), HttpStatus.NOT_FOUND));

            Candidate candidate = Candidate.builder()
                    .id(candidateRequestDTO.getId())
                    .name(candidateRequestDTO.getName())
                    .status(candidateRequestDTO.getStatus())
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
                candidate.setStatus(2);
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


    private CandidateResponseDTO findById(Integer id) throws CustomException {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new CustomException("Candidate is not found with this id " + id, HttpStatus.NOT_FOUND));
        return new CandidateResponseDTO(candidate);
    }
}

package com.example.ojt.service.cerCandidate;

import com.example.ojt.exception.CustomException;
import com.example.ojt.model.dto.mapper.PageDataDTO;
import com.example.ojt.model.dto.response.CertificateCandidateResponseDTO;
import com.example.ojt.model.entity.Account;
import com.example.ojt.model.entity.CertificateCandidate;
import com.example.ojt.repository.IAccountRepository;
import com.example.ojt.repository.ICertificateCandidateRepository;
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
public class CertificateCandidateService implements ICertificateCandidateService{

    @Autowired
    private IAccountRepository accountRepository;
    @Autowired
    private ICertificateCandidateRepository certificateCandidateRepository;
    @Override
    public boolean saveOrUpdate(CertificateCandidate certificateCandidate) throws CustomException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof AccountDetailsCustom accountDetailsCustom) {
            Account account = accountRepository.findById(accountDetailsCustom.getId())
                    .orElseThrow(() -> new CustomException("Account is not found with this id " + accountDetailsCustom.getId(), HttpStatus.NOT_FOUND));

            if (certificateCandidate.getId() != null) {
                // Update existing education candidate
                CertificateCandidate certificateCandidate1 = certificateCandidateRepository.findById(certificateCandidate.getId())
                        .orElseThrow(() -> new CustomException("Education candidate not found with this id " + certificateCandidate.getId(), HttpStatus.NOT_FOUND));

                certificateCandidate1.setName(certificateCandidate.getName());
                certificateCandidate1.setOrganization(certificateCandidate.getOrganization());
                certificateCandidate1.setStartAt(certificateCandidate.getStartAt());
                certificateCandidate1.setEndAt(certificateCandidate.getEndAt());
                certificateCandidate1.setInfo(certificateCandidate.getInfo());
                certificateCandidate1.setStatus(1);
                certificateCandidateRepository.save(certificateCandidate1);
            } else {
                // Save new education candidate
                CertificateCandidate certificateCandidate1 = new CertificateCandidate();
                certificateCandidate1.setName(certificateCandidate.getName());
                certificateCandidate1.setOrganization(certificateCandidate.getOrganization());
                certificateCandidate1.setStartAt(certificateCandidate.getStartAt());
                certificateCandidate1.setEndAt(certificateCandidate.getEndAt());
                certificateCandidate1.setInfo(certificateCandidate.getInfo());
                certificateCandidate1.setStatus(1);
                certificateCandidate1.setCandidate(account.getCandidate());
                certificateCandidateRepository.save(certificateCandidate1);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean removeCerCandidate(Integer id) throws CustomException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof AccountDetailsCustom accountDetailsCustom) {
            CertificateCandidate certificateCandidate = certificateCandidateRepository.findById(id).orElseThrow(() -> new CustomException("Certificate Candidate not found with this id " + id, HttpStatus.NOT_FOUND));
            if (Objects.equals(certificateCandidate.getCandidate().getAccount().getId(), accountDetailsCustom.getId())) {
                certificateCandidate.setStatus(2);
                certificateCandidateRepository.save(certificateCandidate);
                return true;
            }
            throw new CustomException("Certificate Candidate is not found with this id " + id, HttpStatus.NOT_FOUND);
        }
        return false;
    }

    @Override
    public PageDataDTO<CertificateCandidateResponseDTO> getCerCandidate(String keyword, int page, int limit, String sort, String order) throws CustomException {
        Sort.Direction direction = order.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(direction, sort));
        Page<CertificateCandidateResponseDTO> CerCandidatePage;
        if (keyword != null && !keyword.isEmpty()) {
            CerCandidatePage = searchByNameWithPaginationAndSort(pageable, keyword);
        } else {
            CerCandidatePage = findAllWithPaginationAndSort(pageable);
        }
        if (CerCandidatePage == null || CerCandidatePage.isEmpty()) {
            throw new CustomException("Certificate Candidate is not found", HttpStatus.NOT_FOUND);

        }
        PageDataDTO<CertificateCandidateResponseDTO> pageDataDTO = new PageDataDTO<>();
        pageDataDTO.setCurrentPage(CerCandidatePage.getNumber());
        pageDataDTO.setTotalPage(CerCandidatePage.getTotalPages());
        pageDataDTO.setLimit(CerCandidatePage.getSize());
        pageDataDTO.setSort(sort);
        pageDataDTO.setTotalElement(CerCandidatePage.getTotalElements());
        pageDataDTO.setSearchName(keyword == null ? "" : keyword);
        pageDataDTO.setContent(CerCandidatePage.getContent());
        return pageDataDTO;
    }

    @Override
    public Page<CertificateCandidateResponseDTO> findAllWithPaginationAndSort(Pageable pageable) {
        Page<CertificateCandidate> list = certificateCandidateRepository.findAll(pageable);
        return list.map(CertificateCandidateResponseDTO::new);
    }

    @Override
    public Page<CertificateCandidateResponseDTO> searchByNameWithPaginationAndSort(Pageable pageable, String keyword) {
        Page<CertificateCandidate> list = certificateCandidateRepository.findAll(pageable);
        return list.map(CertificateCandidateResponseDTO::new);
    }

}

package com.example.ojt.service.company;

import com.example.ojt.exception.CustomException;
import com.example.ojt.model.dto.request.CompanyRequestDTO;
import com.example.ojt.model.dto.request.EditCompanyRequestDTO;
import com.example.ojt.model.entity.*;
import com.example.ojt.repository.*;
import com.example.ojt.security.principle.AccountDetailsCustom;
import com.example.ojt.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CompanyService implements ICompanyService {
    @Autowired
    private IAccountRepository accountRepository;
    @Autowired
    private ICompanyRepository companyRepository;
    @Autowired
    private IAddressRepository addressRepository;
    @Autowired
    private ILocationRepository locationRepository;
    @Autowired
    private UploadService uploadService;
    @Autowired
    private ITypeCompanyRepository typeCompanyRepository;

    @Override
    public boolean save(CompanyRequestDTO companyRequestDTO) throws CustomException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof AccountDetailsCustom accountDetailsCustom) {
            Account account = accountRepository.findById(accountDetailsCustom.getId())
                    .orElseThrow(() -> new CustomException("Account is not found with this id " + accountDetailsCustom.getId(), HttpStatus.NOT_FOUND));

            Location location = locationRepository.findByNameCity(companyRequestDTO.getLocation())
                    .orElseThrow(() -> new CustomException("Location is not found with this name " + companyRequestDTO.getLocation(), HttpStatus.NOT_FOUND));

            // Check for duplicate company based on unique constraints
            if (companyRepository.existsByName(companyRequestDTO.getNameCompany())) {
                throw new CustomException("Company with name " + companyRequestDTO.getNameCompany() + " already exists", HttpStatus.CONFLICT);
            }
            if (companyRepository.existsByEmailCompany(companyRequestDTO.getEmail())) {
                throw new CustomException("Company with email " + companyRequestDTO.getEmail() + " already exists", HttpStatus.CONFLICT);
            }

            AddressCompany addressCompany = new AddressCompany();
            addressCompany.setLocation(location);
            Company company = new Company();
            company.setName(companyRequestDTO.getNameCompany());
            company.setEmailCompany(companyRequestDTO.getEmail());
            company.setPhone(companyRequestDTO.getPhone());
            company.setAccount(account);
            companyRepository.save(company);
            addressCompany.setCompany(company);
            addressRepository.save(addressCompany);
            return true;
        }
        return false;
    }

    @Override
    public boolean update(EditCompanyRequestDTO companyRequestDTO) throws CustomException {
        Company company1 = findById(companyRequestDTO.getId());
        if(companyRepository.existsByName(companyRequestDTO.getNameCompany()) && !company1.getName().equalsIgnoreCase(companyRequestDTO.getNameCompany())) {
            throw new CustomException("Name company exist", HttpStatus.CONFLICT);
        }
        if(companyRepository.existsByEmailCompany(companyRequestDTO.getEmail()) && !company1.getEmailCompany().equalsIgnoreCase(companyRequestDTO.getEmail())) {
            throw new CustomException("Email company exist", HttpStatus.CONFLICT);
        }
        if(companyRepository.existsByPhone(companyRequestDTO.getPhone()) && !company1.getPhone().equalsIgnoreCase(companyRequestDTO.getPhone())) {
            throw new CustomException("Phone company exist", HttpStatus.CONFLICT);
        }
        String fileName;
        // update Company
        // kiểm tra có upload ảnh không
        if (companyRequestDTO.getLogo() != null && companyRequestDTO.getLogo().getSize() > 0) {
             fileName = uploadService.uploadFileToServer(companyRequestDTO.getLogo());
        } else {
            fileName = company1.getLogo();
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof AccountDetailsCustom accountDetailsCustom) {
            Account account = accountRepository.findById(accountDetailsCustom.getId())
                    .orElseThrow(() -> new CustomException("Account is not found with this id " + accountDetailsCustom.getId(), HttpStatus.NOT_FOUND));

            Company company = Company.builder()
                    .id(companyRequestDTO.getId())
                    .name(companyRequestDTO.getNameCompany())
                    .logo(fileName)
                    .website(companyRequestDTO.getWebsite())
                    .linkFacebook(companyRequestDTO.getLinkFacebook())
                    .linkLinkedin(companyRequestDTO.getLinkLinkedin())
                    .linkGithub(companyRequestDTO.getLinkGithub())
                    .followers(company1.getFollowers())
                    .size(companyRequestDTO.getSize())
                    .description(companyRequestDTO.getDescription())
                    .phone(companyRequestDTO.getPhone())
                    .emailCompany(companyRequestDTO.getEmail())
                    .policy(companyRequestDTO.getPolicy())
                    .createdAt(company1.getCreatedAt())
                    .updatedAt(new Date())
                    .typeCompany(company1.getTypeCompany())
                    .addressCompanySet(company1.getAddressCompanySet())
                    .policy(companyRequestDTO.getPolicy())
                    .account(account)
                    .typeCompany(typeCompanyRepository.findByName(companyRequestDTO.getTypeCompany()).orElseThrow(()->new CustomException("type Company is not founed with name " + companyRequestDTO.getTypeCompany(),HttpStatus.NOT_FOUND)))
                    .build();
            companyRepository.save(company);
            return true;
        } else {
            throw new CustomException("Principal is not an instance of AccountDetailsCustom", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    private Company findById(Integer id) throws CustomException {
        return  companyRepository.findById(id)
                .orElseThrow(() -> new CustomException("Company is not found with this id " + id, HttpStatus.NOT_FOUND));

    }
}

package com.example.ojt.service.address;

import com.example.ojt.config.LocalDateFormatter;
import com.example.ojt.exception.CustomException;
import com.example.ojt.model.dto.mapper.PageDataDTO;
import com.example.ojt.model.dto.request.AddressCompanyRequestDTO;
import com.example.ojt.model.dto.response.AddressCompanyResponseDTO;
import com.example.ojt.model.dto.response.EducationCandidateResponseDTO;
import com.example.ojt.model.dto.response.LocationResponse;
import com.example.ojt.model.entity.*;
import com.example.ojt.repository.IAccountRepository;
import com.example.ojt.repository.IAddressRepository;
import com.example.ojt.repository.ICompanyRepository;
import com.example.ojt.repository.ILocationRepository;
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

import java.util.Date;

@Service
public class AddressService implements IAddressService {
    @Autowired
    private IAccountRepository accountRepository;
    @Autowired
    private IAddressRepository addressRepository;
    @Autowired
    private ILocationRepository locationRepository;

    @Override
    public boolean save(AddressCompanyRequestDTO addressCompanyRequestDTO) throws CustomException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof AccountDetailsCustom accountDetailsCustom) {
            Account account = accountRepository.findById(accountDetailsCustom.getId())
                    .orElseThrow(() -> new CustomException("Account is not found with this id " + accountDetailsCustom.getId(), HttpStatus.NOT_FOUND));
            Location location = locationRepository.findByNameCity(addressCompanyRequestDTO.getLocation())
                    .orElseThrow(() -> new CustomException("Location is not found with this name " + addressCompanyRequestDTO.getLocation(), HttpStatus.NOT_FOUND));
            if (addressRepository.existsByAddress(addressCompanyRequestDTO.getAddress())) {
                throw new CustomException("Address is exist with this name " + addressCompanyRequestDTO.getAddress(), HttpStatus.CONFLICT);
            }
            LocalDateFormatter localDateFormatter = new LocalDateFormatter();
            AddressCompany addressCompany = new AddressCompany();
            addressCompany.setLocation(location);
            addressCompany.setCompany(account.getCompany());
            addressCompany.setAddress(addressCompanyRequestDTO.getAddress());
            addressCompany.setMapUrl(addressCompanyRequestDTO.getMapUrl());
            addressCompany.setStatus(true);
            addressCompany.setCreatedAt(localDateFormatter.dateToString(new Date(), "dd/MM/yyyy HH:mm:ss"));
            addressRepository.save(addressCompany);
            return true;
        }
        return false;

    }

    @Override
    public boolean update(AddressCompanyRequestDTO addressCompanyRequestDTO, Integer id) throws CustomException {
        AddressCompany addressCompany = findById(id);
        if (addressRepository.existsByAddress(addressCompanyRequestDTO.getAddress()) && !addressCompany.getAddress().equalsIgnoreCase(addressCompanyRequestDTO.getAddress())) {
            throw new CustomException("Address is exist with this name " + addressCompanyRequestDTO.getAddress(), HttpStatus.CONFLICT);
        }
        Location location = locationRepository.findByNameCity(addressCompanyRequestDTO.getLocation())
                .orElseThrow(() -> new CustomException("Location is not found with this name " + addressCompanyRequestDTO.getLocation(), HttpStatus.NOT_FOUND));
        LocalDateFormatter localDateFormatter = new LocalDateFormatter();
        addressCompany.setLocation(location);
        addressCompany.setAddress(addressCompanyRequestDTO.getAddress());
        addressCompany.setMapUrl(addressCompanyRequestDTO.getMapUrl());
        addressCompany.setStatus(true);
        addressCompany.setCreatedAt(localDateFormatter.dateToString(new Date(), "dd/MM/yyyy HH:mm:ss"));
        addressRepository.save(addressCompany);
        return true;
    }

    @Override
    public boolean delete(Integer id) throws CustomException {
        // Get current authentication context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Check if the principal is an instance of AccountDetailsCustom
        if (authentication.getPrincipal() instanceof AccountDetailsCustom accountDetailsCustom) {
            // Find the AddressCompany entity by ID
            AddressCompany addressCompany = findById(id);
            if (!addressCompany.isStatus()) {
                throw new CustomException("Address is deleted", HttpStatus.NOT_FOUND);
            }
            // Set status to 2 for logical deletion
            addressCompany.setStatus(false);
            // Save the updated AddressCompany entity
            addressRepository.save(addressCompany);

            // Return true indicating successful deletion
            return true;
        } else {
            // Throw CustomException if authentication principal is not of expected type
            throw new CustomException("Unauthorized access", HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public PageDataDTO<AddressCompanyResponseDTO> getAddress(String keyword, int page, int limit, String sort, String order) throws CustomException {
        Sort.Direction direction = order.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(direction, sort));
        Page<AddressCompanyResponseDTO> addressCompanyResponseDTOS;
        if (keyword != null && !keyword.isEmpty()) {
            addressCompanyResponseDTOS = searchByNameWithPaginationAndSort(pageable, keyword);
        } else {
            addressCompanyResponseDTOS = findAllWithPaginationAndSort(pageable);
        }
        if (addressCompanyResponseDTOS == null || addressCompanyResponseDTOS.isEmpty()) {
            throw new CustomException("Address is not found", HttpStatus.NOT_FOUND);

        }
        PageDataDTO<AddressCompanyResponseDTO> pageDataDTO = new PageDataDTO<>();
        pageDataDTO.setCurrentPage(addressCompanyResponseDTOS.getNumber());
        pageDataDTO.setTotalPage(addressCompanyResponseDTOS.getTotalPages());
        pageDataDTO.setLimit(addressCompanyResponseDTOS.getSize());
        pageDataDTO.setSort(sort);
        pageDataDTO.setTotalElement(addressCompanyResponseDTOS.getTotalElements());
        pageDataDTO.setSearchName(keyword == null ? "" : keyword);
        pageDataDTO.setContent(addressCompanyResponseDTOS.getContent());
        return pageDataDTO;
    }

    @Override
    public Page<AddressCompanyResponseDTO> findAllWithPaginationAndSort(Pageable pageable) {
        Page<AddressCompany> list = addressRepository.findAll(pageable);
        return list.map(AddressCompanyResponseDTO::new);
    }

    @Override
    public Page<AddressCompanyResponseDTO> searchByNameWithPaginationAndSort(Pageable pageable, String keyword) {
        Page<AddressCompany> list = addressRepository.findAllByAddressContainingKeyword(pageable, keyword);
        return list.map(AddressCompanyResponseDTO::new);
    }

    private AddressCompany findById(Integer id) throws CustomException {
        return addressRepository.findById(id)
                .orElseThrow(() -> new CustomException("Address is not found with this id " + id, HttpStatus.NOT_FOUND));

    }
}

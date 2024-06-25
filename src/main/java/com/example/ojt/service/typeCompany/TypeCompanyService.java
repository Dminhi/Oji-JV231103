package com.example.ojt.service.typeCompany;

import com.example.ojt.exception.CustomException;
import com.example.ojt.model.dto.mapper.PageDataDTO;
import com.example.ojt.model.dto.request.TypeCompanyRequestDTO;
import com.example.ojt.model.dto.response.EducationCandidateResponseDTO;
import com.example.ojt.model.dto.response.TypeCompanyResponse;
import com.example.ojt.model.entity.EducationCandidate;
import com.example.ojt.model.entity.TypeCompany;
import com.example.ojt.repository.IAccountRepository;
import com.example.ojt.repository.ITypeCompanyRepository;
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
public class TypeCompanyService implements ITypeCompanyService {
    @Autowired
    private ITypeCompanyRepository typeCompanyRepository;

    @Override
    public boolean saveOrUpdate(TypeCompanyRequestDTO typeCompanyRequestDTO) throws CustomException {
        if (typeCompanyRequestDTO.getId() != null) {
            // edit type Company
            TypeCompany typeCompany = typeCompanyRepository.findById(typeCompanyRequestDTO.getId())
                    .orElseThrow(() -> new CustomException("TypeCompany not found with this id " + typeCompanyRequestDTO.getId(), HttpStatus.NOT_FOUND));
            typeCompany.setName(typeCompanyRequestDTO.getName());
            typeCompanyRepository.save(typeCompany);
        } else {
            // Save new type company
            // check name
            if (typeCompanyRepository.findByName(typeCompanyRequestDTO.getName()).isPresent()) {
                throw new CustomException("TypeCompany with name " + typeCompanyRequestDTO.getName() + " already exists", HttpStatus.CONFLICT);
            }
            TypeCompany typeCompany = new TypeCompany();
            typeCompany.setName(typeCompanyRequestDTO.getName());
            typeCompanyRepository.save(typeCompany);
        }
        return true;
    }

    @Override
    public PageDataDTO<TypeCompanyResponse> getTypeCompany(String keyword, int page, int limit, String sort, String order) throws CustomException {
        Sort.Direction direction = order.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(direction, sort));
        Page<TypeCompanyResponse> typeCompanyResponses;
        if (keyword != null && !keyword.isEmpty()) {
            typeCompanyResponses = searchByNameWithPaginationAndSort(pageable, keyword);
        } else {
            typeCompanyResponses = findAllWithPaginationAndSort(pageable);
        }
        if (typeCompanyResponses == null || typeCompanyResponses.isEmpty()) {
            throw new CustomException("Type Company is not found", HttpStatus.NOT_FOUND);

        }
        PageDataDTO<TypeCompanyResponse> pageDataDTO = new PageDataDTO<>();
        pageDataDTO.setCurrentPage(typeCompanyResponses.getNumber());
        pageDataDTO.setTotalPage(typeCompanyResponses.getTotalPages());
        pageDataDTO.setLimit(typeCompanyResponses.getSize());
        pageDataDTO.setSort(sort);
        pageDataDTO.setTotalElement(typeCompanyResponses.getTotalElements());
        pageDataDTO.setSearchName(keyword == null ? "" : keyword);
        pageDataDTO.setContent(typeCompanyResponses.getContent());
        return pageDataDTO;
    }

    @Override
    public Page<TypeCompanyResponse> searchByNameWithPaginationAndSort(Pageable pageable, String keyword) {
        Page<TypeCompany> list = typeCompanyRepository.findAllByNameContainingIgnoreCase(pageable, keyword);
        return list.map(TypeCompanyResponse::new);
    }

    @Override
    public Page<TypeCompanyResponse> findAllWithPaginationAndSort(Pageable pageable) {
        Page<TypeCompany> list = typeCompanyRepository.findAll(pageable);
        return list.map(TypeCompanyResponse::new);
    }

    @Override
    public boolean removeTypeCompany(Integer id) throws CustomException {

        TypeCompany typeCompany = typeCompanyRepository.findById(id).orElseThrow(() -> new CustomException("Type Company not found with this id " + id, HttpStatus.NOT_FOUND));
        typeCompanyRepository.delete(typeCompany);
        return true;
    }
}

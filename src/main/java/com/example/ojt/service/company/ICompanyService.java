package com.example.ojt.service.company;

import com.example.ojt.exception.CustomException;
import com.example.ojt.model.dto.mapper.PageDataDTO;
import com.example.ojt.model.dto.request.CompanyRequestDTO;
import com.example.ojt.model.dto.request.EditCompanyRequestDTO;
import com.example.ojt.model.dto.response.CompanyResponseDTO;
import com.example.ojt.model.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICompanyService {
    boolean save(CompanyRequestDTO companyRequestDTO) throws CustomException;

    boolean update(EditCompanyRequestDTO companyRequestDTO,Integer id) throws CustomException;

    Company findById(Integer id) throws CustomException;

    PageDataDTO<CompanyResponseDTO> getCompany(String keyword, int page, int limit, String sort, String order) throws CustomException;
     Page<CompanyResponseDTO> findAllWithPaginationAndSort(Pageable pageable) ;

     Page<CompanyResponseDTO> searchByNameWithPaginationAndSort(Pageable pageable, String keyword);
}

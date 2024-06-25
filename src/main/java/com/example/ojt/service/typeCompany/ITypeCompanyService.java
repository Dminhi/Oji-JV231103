package com.example.ojt.service.typeCompany;

import com.example.ojt.exception.CustomException;
import com.example.ojt.model.dto.mapper.PageDataDTO;
import com.example.ojt.model.dto.request.TypeCompanyRequestDTO;
import com.example.ojt.model.dto.response.TypeCompanyResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ITypeCompanyService {
    boolean saveOrUpdate(TypeCompanyRequestDTO typeCompanyRequestDTO) throws CustomException;

    PageDataDTO<TypeCompanyResponse> getTypeCompany(String keyword, int page, int limit, String sort, String order) throws CustomException;

    boolean removeTypeCompany(Integer id) throws CustomException;
    Page<TypeCompanyResponse> searchByNameWithPaginationAndSort(Pageable pageable, String keyword);
    Page<TypeCompanyResponse> findAllWithPaginationAndSort(Pageable pageable);
}

package com.example.ojt.service.company;

import com.example.ojt.exception.CustomException;
import com.example.ojt.model.dto.request.CompanyRequestDTO;
import com.example.ojt.model.dto.request.EditCompanyRequestDTO;

public interface ICompanyService {
    boolean save(CompanyRequestDTO companyRequestDTO) throws CustomException;

    boolean update(EditCompanyRequestDTO companyRequestDTO,Integer id) throws CustomException;
}

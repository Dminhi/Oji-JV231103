package com.example.ojt.service.address;

import com.example.ojt.exception.CustomException;
import com.example.ojt.model.dto.mapper.PageDataDTO;
import com.example.ojt.model.dto.request.AddressCompanyRequestDTO;
import com.example.ojt.model.dto.response.AddressCompanyResponseDTO;
import com.example.ojt.model.dto.response.EducationCandidateResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IAddressService {
    boolean save(AddressCompanyRequestDTO addressCompanyRequestDTO) throws CustomException;

    boolean update(AddressCompanyRequestDTO addressCompanyRequestDTO, Integer id) throws CustomException;

    boolean delete(Integer id) throws CustomException;

    PageDataDTO<AddressCompanyResponseDTO> getAddress(String keyword, int page, int limit, String sort, String order) throws CustomException;
    Page<AddressCompanyResponseDTO> findAllWithPaginationAndSort(Pageable pageable);

    Page<AddressCompanyResponseDTO> searchByNameWithPaginationAndSort(Pageable pageable, String keyword);
}

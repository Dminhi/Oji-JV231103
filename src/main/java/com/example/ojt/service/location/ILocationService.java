package com.example.ojt.service.location;

import com.example.ojt.exception.CustomException;
import com.example.ojt.model.dto.mapper.PageDataDTO;
import com.example.ojt.model.dto.request.LocationRequestDTO;
import com.example.ojt.model.dto.response.LocationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ILocationService {
    boolean save(LocationRequestDTO locationRequestDTO) throws CustomException;
    boolean update(LocationRequestDTO locationRequestDTO,Integer id) throws CustomException;

    PageDataDTO<LocationResponse> getLocation(String keyword, int page, int limit, String sort, String order) throws CustomException;

    boolean removeLocation(Integer id) throws CustomException;
    Page<LocationResponse> searchByNameWithPaginationAndSort(Pageable pageable, String keyword);
    Page<LocationResponse> findAllWithPaginationAndSort(Pageable pageable);

}

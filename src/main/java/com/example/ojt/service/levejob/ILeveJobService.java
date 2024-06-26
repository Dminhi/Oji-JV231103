package com.example.ojt.service.levejob;

import com.example.ojt.exception.CustomException;
import com.example.ojt.model.dto.mapper.PageDataDTO;
import com.example.ojt.model.dto.request.LeveJobRequestDTO;
import com.example.ojt.model.dto.response.LevelJobResponse;
import com.example.ojt.model.dto.response.LocationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ILeveJobService {
    boolean save(LeveJobRequestDTO leveJobRequestDTO) throws CustomException;

    PageDataDTO<LevelJobResponse> getLevelJob(String keyword, int page, int limit, String sort, String order) throws CustomException;

    boolean removeLevelJob(Integer id) throws CustomException;

    boolean update(LeveJobRequestDTO leveJobRequestDTO, Integer id) throws CustomException;
     Page<LevelJobResponse> findAllWithPaginationAndSort(Pageable pageable);

 Page<LevelJobResponse> searchByNameWithPaginationAndSort(Pageable pageable, String keyword);

}

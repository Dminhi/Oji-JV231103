package com.example.ojt.service.job;

import com.example.ojt.exception.CustomException;
import com.example.ojt.model.dto.request.JobRequestDTO;

public interface IJobService {
    boolean save(JobRequestDTO jobRequestDTO) throws CustomException;

    boolean update(JobRequestDTO jobRequestDTO, Integer id) throws CustomException;
}

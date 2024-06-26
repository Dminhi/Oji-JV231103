package com.example.ojt.service.skillCandidate;

import com.example.ojt.exception.CustomException;
import com.example.ojt.model.dto.mapper.PageDataDTO;
import com.example.ojt.model.dto.request.SkillCandidateRequestDTO;
import com.example.ojt.model.dto.response.SkillCandidateResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ISkillCandidateService {
    boolean save(SkillCandidateRequestDTO skillCandidateRequestDTO) throws CustomException;

    boolean update(SkillCandidateRequestDTO skillCandidateRequestDTO, Integer id) throws CustomException;

    PageDataDTO<SkillCandidateResponse> getSkillCandidate(String keyword, int page, int limit, String sort, String order) throws CustomException;

    boolean removeSkillCandidate(Integer id) throws CustomException;
     Page<SkillCandidateResponse> findAllWithPaginationAndSort(Pageable pageable);

    Page<SkillCandidateResponse> searchByNameWithPaginationAndSort(Pageable pageable, String keyword);
}

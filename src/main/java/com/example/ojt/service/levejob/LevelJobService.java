package com.example.ojt.service.levejob;

import com.example.ojt.exception.CustomException;
import com.example.ojt.model.dto.mapper.PageDataDTO;
import com.example.ojt.model.dto.request.LeveJobRequestDTO;
import com.example.ojt.model.dto.response.LevelJobResponse;
import com.example.ojt.model.dto.response.LocationResponse;
import com.example.ojt.model.entity.LevelJob;
import com.example.ojt.model.entity.Location;
import com.example.ojt.repository.ILeveJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class LevelJobService implements ILeveJobService {
    @Autowired
    private ILeveJobRepository leveJobRepository;

    @Override
    public boolean save(LeveJobRequestDTO leveJobRequestDTO) throws CustomException {
        // Save new type company
        // check name
        if (leveJobRepository.findByName(leveJobRequestDTO.getLeveJobName()).isPresent()) {
            throw new CustomException("LevelJob with name " + leveJobRequestDTO.getLeveJobName() + " already exists", HttpStatus.CONFLICT);
        }
        LevelJob levelJob = new LevelJob();
        levelJob.setName(leveJobRequestDTO.getLeveJobName());
        leveJobRepository.save(levelJob);
        return true;
    }

    @Override
    public PageDataDTO<LevelJobResponse> getLevelJob(String keyword, int page, int limit, String sort, String order) throws CustomException {
        Sort.Direction direction = order.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(direction, sort));
        Page<LevelJobResponse> levelJobResponses;
        if (keyword != null && !keyword.isEmpty()) {
            levelJobResponses = searchByNameWithPaginationAndSort(pageable, keyword);
        } else {
            levelJobResponses = findAllWithPaginationAndSort(pageable);
        }
        if (levelJobResponses == null || levelJobResponses.isEmpty()) {
            throw new CustomException("LevelJob is not found", HttpStatus.NOT_FOUND);

        }
        PageDataDTO<LevelJobResponse> pageDataDTO = new PageDataDTO<>();
        pageDataDTO.setCurrentPage(levelJobResponses.getNumber());
        pageDataDTO.setTotalPage(levelJobResponses.getTotalPages());
        pageDataDTO.setLimit(levelJobResponses.getSize());
        pageDataDTO.setSort(sort);
        pageDataDTO.setTotalElement(levelJobResponses.getTotalElements());
        pageDataDTO.setSearchName(keyword == null ? "" : keyword);
        pageDataDTO.setContent(levelJobResponses.getContent());
        return pageDataDTO;
    }

    @Override
    public Page<LevelJobResponse> findAllWithPaginationAndSort(Pageable pageable) {
        Page<LevelJob> list = leveJobRepository.findAll(pageable);
        return list.map(LevelJobResponse::new);
    }

    @Override
    public Page<LevelJobResponse> searchByNameWithPaginationAndSort(Pageable pageable, String keyword) {
        Page<LevelJob> list = leveJobRepository.findAllByNameLevelJob(pageable, keyword);
        return list.map(LevelJobResponse::new);
    }

    @Override
    public boolean removeLevelJob(Integer id) throws CustomException {
        LevelJob levelJob = leveJobRepository.findById(id).orElseThrow(() -> new CustomException("LeveJob not found with this id " + id, HttpStatus.NOT_FOUND));
        leveJobRepository.delete(levelJob);
        return true;
    }

    @Override
    public boolean update(LeveJobRequestDTO leveJobRequestDTO, Integer id) throws CustomException {
        // Find LevelJob by id
        LevelJob levelJob = leveJobRepository.findById(id)
                .orElseThrow(() -> new CustomException("LevelJob not found with this id " + id, HttpStatus.NOT_FOUND));

        // Check if the new name exists in the database and is not the same as the current name of the object
        if (leveJobRepository.existsByName(leveJobRequestDTO.getLeveJobName()) &&
                !levelJob.getName().equalsIgnoreCase(leveJobRequestDTO.getLeveJobName())) {
            throw new CustomException("levelJobName is exists", HttpStatus.CONFLICT);
        }
        // Update the name of the LevelJob
        levelJob.setName(leveJobRequestDTO.getLeveJobName());
        leveJobRepository.save(levelJob);

        return true;
    }

}

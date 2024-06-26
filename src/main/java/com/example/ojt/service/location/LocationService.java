package com.example.ojt.service.location;

import com.example.ojt.exception.CustomException;
import com.example.ojt.model.dto.mapper.PageDataDTO;
import com.example.ojt.model.dto.request.LocationRequestDTO;
import com.example.ojt.model.dto.response.LocationResponse;
import com.example.ojt.model.dto.response.TypeCompanyResponse;
import com.example.ojt.model.entity.Location;
import com.example.ojt.model.entity.TypeCompany;
import com.example.ojt.repository.ILocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class LocationService implements ILocationService {
    @Autowired
    private ILocationRepository locationRepository;

    @Override
    public boolean save(LocationRequestDTO locationRequestDTO) throws CustomException {
        // Save new type company
        // check name
        if (locationRepository.findByNameCity(locationRequestDTO.getLocation()).isPresent()) {
            throw new CustomException("Location with name " + locationRequestDTO.getLocation() + " already exists", HttpStatus.CONFLICT);
        }
        Location location1 = new Location();
        location1.setNameCity(locationRequestDTO.getLocation());
        locationRepository.save(location1);

        return true;
    }

    @Override
    public boolean update(LocationRequestDTO locationRequestDTO, Integer id) throws CustomException {
        // edit type Company
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new CustomException("Location not found with this id " + id, HttpStatus.NOT_FOUND));
        // Check if the new name exists in the database and is not the same as the current name of the object
        if (locationRepository.existsByNameCity(locationRequestDTO.getLocation()) &&
                !location.getNameCity().equalsIgnoreCase(locationRequestDTO.getLocation())) {
            throw new CustomException("Location is exists", HttpStatus.CONFLICT);
        }
        location.setNameCity(locationRequestDTO.getLocation());
        locationRepository.save(location);

        return true;
    }

    @Override
    public PageDataDTO<LocationResponse> getLocation(String keyword, int page, int limit, String sort, String order) throws CustomException {
        Sort.Direction direction = order.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(direction, sort));
        Page<LocationResponse> locationResponses;
        if (keyword != null && !keyword.isEmpty()) {
            locationResponses = searchByNameWithPaginationAndSort(pageable, keyword);
        } else {
            locationResponses = findAllWithPaginationAndSort(pageable);
        }
        if (locationResponses == null || locationResponses.isEmpty()) {
            throw new CustomException("Location is not found", HttpStatus.NOT_FOUND);

        }
        PageDataDTO<LocationResponse> pageDataDTO = new PageDataDTO<>();
        pageDataDTO.setCurrentPage(locationResponses.getNumber());
        pageDataDTO.setTotalPage(locationResponses.getTotalPages());
        pageDataDTO.setLimit(locationResponses.getSize());
        pageDataDTO.setSort(sort);
        pageDataDTO.setTotalElement(locationResponses.getTotalElements());
        pageDataDTO.setSearchName(keyword == null ? "" : keyword);
        pageDataDTO.setContent(locationResponses.getContent());
        return pageDataDTO;
    }

    @Override
    public boolean removeLocation(Integer id) throws CustomException {
        Location location = locationRepository.findById(id).orElseThrow(() -> new CustomException("Location not found with this id " + id, HttpStatus.NOT_FOUND));
        locationRepository.delete(location);
        return true;
    }

    @Override
    public Page<LocationResponse> searchByNameWithPaginationAndSort(Pageable pageable, String keyword) {
        Page<Location> list = locationRepository.findAllByNameLocation(pageable, keyword);
        return list.map(LocationResponse::new);
    }

    @Override
    public Page<LocationResponse> findAllWithPaginationAndSort(Pageable pageable) {
        Page<Location> list = locationRepository.findAll(pageable);
        return list.map(LocationResponse::new);
    }
}

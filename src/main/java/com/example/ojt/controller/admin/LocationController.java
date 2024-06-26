package com.example.ojt.controller.admin;

import com.example.ojt.exception.CustomException;
import com.example.ojt.model.dto.mapper.HttpResponse;
import com.example.ojt.model.dto.mapper.PageDataDTO;
import com.example.ojt.model.dto.mapper.ResponseMapper;
import com.example.ojt.model.dto.request.LocationRequestDTO;
import com.example.ojt.model.dto.response.APIResponse;
import com.example.ojt.model.dto.response.LocationResponse;
import com.example.ojt.service.location.ILocationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api.myservice.com/v1/admin/location")
public class LocationController {
    @Autowired
    private ILocationService locationService;
    @RequestMapping("")
    public ResponseEntity<?> addLocation(@Valid @RequestBody LocationRequestDTO locationRequestDTO) throws CustomException {
        boolean check = locationService.save(locationRequestDTO);
        if (check) {
            APIResponse apiResponse = new APIResponse(200, "Create Location success");
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        } else {
            throw new CustomException("Lack of compulsory registration information or invalid information.", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
    @PutMapping("/{id}")
    ResponseEntity<?> EditLocation(@Valid @RequestBody @PathVariable Integer id, LocationRequestDTO locationRequestDTO) throws CustomException {
        boolean check = locationService.update(locationRequestDTO,id);
        if (check) {
            APIResponse apiResponse = new APIResponse(200, "Update Location success");
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        } else {
            throw new CustomException("Lack of compulsory registration information or invalid information.", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<?> removeLocation(@PathVariable Integer id) throws CustomException {
        boolean check = locationService.removeLocation(id);
        if (check) {
            APIResponse apiResponse = new APIResponse(200, "Delete Location success");
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        } else {
            throw new CustomException("Lack of compulsory registration information or invalid information.", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
    @GetMapping("")
    public ResponseEntity<?> getLocation(@RequestParam(name = "keyword", required = false) String keyword,
                                             @RequestParam(defaultValue = "5", name = "limit") int limit,
                                             @RequestParam(defaultValue = "0", name = "page") int page,
                                             @RequestParam(defaultValue = "id", name = "sort") String sort,
                                             @RequestParam(defaultValue = "asc", name = "order") String order) throws CustomException {
        PageDataDTO<LocationResponse> typeCompany = locationService.getLocation(keyword, page, limit, sort, order);
        return new ResponseEntity<>(new ResponseMapper<>(
                HttpResponse.SUCCESS,
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                typeCompany
        ), HttpStatus.OK);
    }
}

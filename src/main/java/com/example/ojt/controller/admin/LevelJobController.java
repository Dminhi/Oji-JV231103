package com.example.ojt.controller.admin;

import com.example.ojt.exception.CustomException;
import com.example.ojt.model.dto.mapper.HttpResponse;
import com.example.ojt.model.dto.mapper.PageDataDTO;
import com.example.ojt.model.dto.mapper.ResponseMapper;
import com.example.ojt.model.dto.request.LeveJobRequestDTO;
import com.example.ojt.model.dto.request.LocationRequestDTO;
import com.example.ojt.model.dto.response.APIResponse;
import com.example.ojt.model.dto.response.LevelJobResponse;
import com.example.ojt.model.dto.response.LocationResponse;
import com.example.ojt.service.levejob.ILeveJobService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api.myservice.com/v1/admin/level-job")
public class LevelJobController {
    @Autowired
    private ILeveJobService leveJobService;
    @RequestMapping("")
    public ResponseEntity<?> addLevelJob(@Valid @RequestBody LeveJobRequestDTO leveJobRequestDTO) throws CustomException {
        boolean check = leveJobService.save(leveJobRequestDTO);
        if (check) {
            APIResponse apiResponse = new APIResponse(200, "Create Level success");
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        } else {
            throw new CustomException("Lack of compulsory registration information or invalid information.", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
    @PutMapping("/{id}")
    ResponseEntity<?> EditLevelJob(@Valid @RequestBody @PathVariable Integer id, LeveJobRequestDTO leveJobRequestDTO) throws CustomException {
        boolean check = leveJobService.update(leveJobRequestDTO,id);
        if (check) {
            APIResponse apiResponse = new APIResponse(200, "Update Location success");
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        } else {
            throw new CustomException("Lack of compulsory registration information or invalid information.", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<?> removeLocation(@PathVariable Integer id) throws CustomException {
        boolean check = leveJobService.removeLevelJob(id);
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
        PageDataDTO<LevelJobResponse> typeCompany = leveJobService.getLevelJob(keyword, page, limit, sort, order);
        return new ResponseEntity<>(new ResponseMapper<>(
                HttpResponse.SUCCESS,
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                typeCompany
        ), HttpStatus.OK);
    }

}

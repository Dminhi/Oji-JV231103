package com.example.ojt.controller.candidate;

import com.example.ojt.exception.CustomException;
import com.example.ojt.model.dto.mapper.HttpResponse;
import com.example.ojt.model.dto.mapper.PageDataDTO;
import com.example.ojt.model.dto.mapper.ResponseMapper;
import com.example.ojt.model.dto.request.ExperienceCandidateRequestDTO;
import com.example.ojt.model.dto.response.APIResponse;
import com.example.ojt.model.dto.response.ExperienceCandidateResponseDTO;
import com.example.ojt.service.experienceCandidate.IExperienceCandidateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api.myservice.com/v1/account/ex-candidate")
public class ExperienceCandidateController {
    @Autowired
    private IExperienceCandidateService experienceCandidateService;

    @PostMapping("")
    ResponseEntity<?> addCandidate(@Valid @RequestBody ExperienceCandidateRequestDTO experienceCandidateRequestDTO) throws CustomException {
        boolean check = experienceCandidateService.saveOrUpdate(experienceCandidateRequestDTO);
        if (check) {
            APIResponse apiResponse = new APIResponse(200, "Create Experience candidate success");
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        } else {
            throw new CustomException("Lack of compulsory registration information or invalid information.", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @PutMapping("")
    ResponseEntity<?> EditEduCandidate(@Valid @RequestBody ExperienceCandidateRequestDTO experienceCandidateRequestDTO) throws CustomException {
        boolean check = experienceCandidateService.saveOrUpdate(experienceCandidateRequestDTO);
        if (check) {
            APIResponse apiResponse = new APIResponse(200, "Update Experience candidate success");
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        } else {
            throw new CustomException("Lack of compulsory registration information or invalid information.", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<?> removeExCandidate(@PathVariable Integer id) throws CustomException {
        boolean check = experienceCandidateService.removeExCandidate(id);
        if (check) {
            APIResponse apiResponse = new APIResponse(200, "Delete Experience success");
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        } else {
            throw new CustomException("Lack of compulsory registration information or invalid information.", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getProCandidate(@RequestParam(name = "keyword", required = false) String keyword,
                                             @RequestParam(defaultValue = "5", name = "limit") int limit,
                                             @RequestParam(defaultValue = "0", name = "page") int page,
                                             @RequestParam(defaultValue = "id", name = "sort") String sort,
                                             @RequestParam(defaultValue = "asc", name = "order") String order) throws CustomException {
        PageDataDTO<ExperienceCandidateResponseDTO> exCandidate = experienceCandidateService.getExCandidate(keyword, page, limit, sort, order);
        return new ResponseEntity<>(new ResponseMapper<>(
                HttpResponse.SUCCESS,
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                exCandidate
        ), HttpStatus.OK);
    }

}

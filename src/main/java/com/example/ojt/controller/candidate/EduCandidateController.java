package com.example.ojt.controller.candidate;

import com.example.ojt.exception.CustomException;
import com.example.ojt.model.dto.request.CandidateRequestDTO;
import com.example.ojt.model.dto.request.EduCandidateRequestDTO;
import com.example.ojt.model.dto.response.APIResponse;
import com.example.ojt.service.eduCandidadte.IEduCandidateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api.myservice.com/v1/account/edu-candidate")
public class EduCandidateController {
    @Autowired
    private IEduCandidateService eduCandidateService;
    @PostMapping("")
    ResponseEntity<?> addCandidate(@Valid @RequestBody EduCandidateRequestDTO eduCandidateRequestDTO) throws CustomException {
        boolean check = eduCandidateService.saveOrUpdate(eduCandidateRequestDTO);
        if(check) {
            APIResponse apiResponse = new APIResponse(200, "Create candidate success");
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        }else {
            throw new CustomException("Lack of compulsory registration information or invalid information.", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
    @PutMapping("")
    ResponseEntity<?> editCandidate(@Valid @RequestBody EduCandidateRequestDTO eduCandidateRequestDTO) throws CustomException {
        boolean check = eduCandidateService.saveOrUpdate(eduCandidateRequestDTO);
        if(check) {
            APIResponse apiResponse = new APIResponse(200, "Update EducationCandidate success");
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        }else {
            throw new CustomException("Lack of compulsory registration information or invalid information.", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}

package com.example.ojt.controller.candidate;

import com.example.ojt.exception.CustomException;
import com.example.ojt.model.dto.request.EduCandidateRequestDTO;
import com.example.ojt.model.dto.request.ExperienceCandidateRequestDTO;
import com.example.ojt.model.dto.response.APIResponse;
import com.example.ojt.service.eduCandidate.IEduCandidateService;
import com.example.ojt.service.experienceCandidate.IExperienceCandidateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        if(check) {
            APIResponse apiResponse = new APIResponse(200, "Create Experience candidate success");
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        }else {
            throw new CustomException("Lack of compulsory registration information or invalid information.", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}

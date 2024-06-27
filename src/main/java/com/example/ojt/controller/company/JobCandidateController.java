package com.example.ojt.controller.company;

import com.example.ojt.exception.CustomException;
import com.example.ojt.model.dto.request.JobCandidateRequestDTO;
import com.example.ojt.model.dto.response.APIResponse;
import com.example.ojt.service.jobCandidate.IJobCandidateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api.myservice.com/v1/account/company/job-candidate")
public class JobCandidateController {
    @Autowired
    private IJobCandidateService jobCandidateService;

    @PostMapping("")
    ResponseEntity<?> addJobCandidate(@Valid @RequestBody JobCandidateRequestDTO jobCandidateRequestDTO) throws CustomException {
        boolean check = jobCandidateService.save(jobCandidateRequestDTO);
        if (check) {
            APIResponse apiResponse = new APIResponse(200, "Create Job Candidate success");
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        } else {
            throw new CustomException("Lack of compulsory registration information or invalid information.", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<?> EditJobCandidate(@Valid @PathVariable Integer id, @RequestBody JobCandidateRequestDTO jobCandidateRequestDTO) throws CustomException {
        boolean check = jobCandidateService.update(jobCandidateRequestDTO ,id);
        if (check) {
            APIResponse apiResponse = new APIResponse(200, "Update Job candidate success");
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        } else {
            throw new CustomException("Lack of compulsory registration information or invalid information.", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<?> removeJobCandidate(@PathVariable Integer id) throws CustomException {
        boolean check = jobCandidateService.removeJobCandidate(id);
        if (check) {
            APIResponse apiResponse = new APIResponse(200, "Delete JOb Certificate success");
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        } else {
            throw new CustomException("Lack of compulsory registration information or invalid information.", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

}

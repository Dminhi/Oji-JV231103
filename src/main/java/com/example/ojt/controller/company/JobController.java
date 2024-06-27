package com.example.ojt.controller.company;

import com.example.ojt.exception.CustomException;
import com.example.ojt.model.dto.request.AddressCompanyRequestDTO;
import com.example.ojt.model.dto.request.JobRequestDTO;
import com.example.ojt.model.dto.response.APIResponse;
import com.example.ojt.service.job.IJobService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api.myservice.com/v1/account/company/job-company")
public class JobController {
    @Autowired
    private IJobService jobService;
    @PostMapping("")
    public ResponseEntity<?> AddJob(@Valid @RequestBody JobRequestDTO jobRequestDTO) throws CustomException {
        boolean check = jobService.save(jobRequestDTO);
        if(check) {
            APIResponse apiResponse = new APIResponse(200, "Create Job success");
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        } else {
            throw new CustomException("Lack of compulsory registration information or invalid information.", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
    @PutMapping("/{id}")
    ResponseEntity<?> EditJob(@Valid @PathVariable("id") Integer id , @RequestBody JobRequestDTO jobRequestDTO) throws CustomException {
        boolean check = jobService.update(jobRequestDTO,id);
        if(check) {
            APIResponse apiResponse = new APIResponse(200, "Update Address success");
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        }else {
            throw new CustomException("Lack of compulsory registration information or invalid information.", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}

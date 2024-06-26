package com.example.ojt.controller.candidate;

import com.example.ojt.exception.CustomException;
import com.example.ojt.model.dto.mapper.HttpResponse;
import com.example.ojt.model.dto.mapper.PageDataDTO;
import com.example.ojt.model.dto.mapper.ResponseMapper;
import com.example.ojt.model.dto.request.JobCandidateRequestDTO;
import com.example.ojt.model.dto.response.APIResponse;
import com.example.ojt.model.dto.response.JobCandidateResponseDTO;
import com.example.ojt.service.jobCandidate.IJobCandidateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api.myservice.com/v1/account/candidate/job-candidate")
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
    ResponseEntity<?> EditJObCandidate(@Valid @PathVariable Integer id, @RequestBody JobCandidateRequestDTO jobCandidateRequestDTO) throws CustomException {
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
    @GetMapping("")
    public ResponseEntity<?> getCerCandidate(@RequestParam(name = "keyword", required = false) String keyword,
                                             @RequestParam(defaultValue = "5", name = "limit") int limit,
                                             @RequestParam(defaultValue = "0", name = "page") int page,
                                             @RequestParam(defaultValue = "id", name = "sort") String sort,
                                             @RequestParam(defaultValue = "asc", name = "order") String order) throws CustomException {
        PageDataDTO<JobCandidateResponseDTO> jobCandidatePage = jobCandidateService.getJobCandidate(keyword, page, limit, sort, order);
        return new ResponseEntity<>(new ResponseMapper<>(
                HttpResponse.SUCCESS,
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                jobCandidatePage
        ), HttpStatus.OK);
    }
}

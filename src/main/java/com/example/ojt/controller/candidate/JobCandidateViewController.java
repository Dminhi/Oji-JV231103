package com.example.ojt.controller.candidate;

import com.example.ojt.exception.CustomException;
import com.example.ojt.model.dto.mapper.HttpResponse;
import com.example.ojt.model.dto.mapper.PageDataDTO;
import com.example.ojt.model.dto.mapper.ResponseMapper;
import com.example.ojt.model.dto.response.JobCandidateListResponseDTO;
import com.example.ojt.model.dto.response.JobCandidateResponseDTO;
import com.example.ojt.model.entity.JobCandidates;
import com.example.ojt.service.jobCandidate.IJobCandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api.myservice.com/v1/account/candicade/job-candidate")
public class JobCandidateViewController {
    @Autowired
    private IJobCandidateService jobCandidateService;
    @GetMapping("")
    public ResponseEntity<?> getJobCandidate(@RequestParam(name = "keyword", required = false) String keyword,
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
    @GetMapping("/{id}")
    public ResponseEntity<?> findCompanyById(@PathVariable Integer id) throws CustomException {
        JobCandidates jobCandidates = jobCandidateService.findById(id);
        JobCandidateListResponseDTO jobCandidateResponseDTO = new JobCandidateListResponseDTO(jobCandidates);
        return new ResponseEntity<>(new ResponseMapper<>(
                HttpResponse.SUCCESS,
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                jobCandidateResponseDTO
        ), HttpStatus.OK);
    }
}

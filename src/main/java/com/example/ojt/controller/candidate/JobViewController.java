package com.example.ojt.controller.candidate;

import com.example.ojt.exception.CustomException;
import com.example.ojt.model.dto.mapper.HttpResponse;
import com.example.ojt.model.dto.mapper.PageDataDTO;
import com.example.ojt.model.dto.mapper.ResponseMapper;
import com.example.ojt.model.dto.response.JobCandidateListResponseDTO;
import com.example.ojt.model.dto.response.JobCandidateResponseDTO;
import com.example.ojt.model.dto.response.JobDetailResponseDTO;
import com.example.ojt.model.dto.response.JobListResponseDTO;
import com.example.ojt.model.entity.Job;
import com.example.ojt.model.entity.JobCandidates;
import com.example.ojt.repository.JobRepository;
import com.example.ojt.service.job.IJobService;
import com.example.ojt.service.jobCandidate.IJobCandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api.myservice.com/v1/account/candicade/job")
public class JobViewController {
    @Autowired
    private IJobService jobService;
    @GetMapping("")
    public ResponseEntity<?> getJob(@RequestParam(name = "keyword", required = false) String keyword,
                                             @RequestParam(defaultValue = "5", name = "limit") int limit,
                                             @RequestParam(defaultValue = "0", name = "page") int page,
                                             @RequestParam(defaultValue = "id", name = "sort") String sort,
                                             @RequestParam(defaultValue = "asc", name = "order") String order) throws CustomException {
        PageDataDTO<JobListResponseDTO> jobCandidatePage = jobService.getJob(keyword, page, limit, sort, order);
        return new ResponseEntity<>(new ResponseMapper<>(
                HttpResponse.SUCCESS,
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                jobCandidatePage
        ), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> findJobById(@PathVariable Integer id) throws CustomException {
        Job job = jobService.findById(id);
        JobDetailResponseDTO jobDetailResponseDTO = new JobDetailResponseDTO(job);
        return new ResponseEntity<>(new ResponseMapper<>(
                HttpResponse.SUCCESS,
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                jobDetailResponseDTO
        ), HttpStatus.OK);
    }
}

package com.example.ojt.controller;

import com.example.ojt.exception.CustomException;
import com.example.ojt.model.dto.mapper.HttpResponse;
import com.example.ojt.model.dto.mapper.PageDataDTO;
import com.example.ojt.model.dto.mapper.ResponseMapper;
import com.example.ojt.model.dto.response.CompanyResponseDTO;
import com.example.ojt.model.dto.response.JobListResponseDTO;
import com.example.ojt.service.company.ICompanyService;
import com.example.ojt.service.job.IJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api.myservice.com/v1/home")
public class HomeController {
    @Autowired
    private IJobService jobService;
    @Autowired
    private ICompanyService companyService;
    @GetMapping("/job-list")
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
    @GetMapping("/company-list")
    public ResponseEntity<?> getCompany(@RequestParam(name = "keyword", required = false) String keyword,
                                        @RequestParam(defaultValue = "5", name = "limit") int limit,
                                        @RequestParam(defaultValue = "0", name = "page") int page,
                                        @RequestParam(defaultValue = "id", name = "sort") String sort,
                                        @RequestParam(defaultValue = "asc", name = "order") String order) throws CustomException {
        PageDataDTO<CompanyResponseDTO> companyResponseDTOPageDataDTO = companyService.getCompany(keyword, page, limit, sort, order);
        return new ResponseEntity<>(new ResponseMapper<>(
                HttpResponse.SUCCESS,
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                companyResponseDTOPageDataDTO
        ), HttpStatus.OK);
    }
}

package com.example.ojt.controller.company;

import com.example.ojt.exception.CustomException;
import com.example.ojt.model.dto.mapper.HttpResponse;
import com.example.ojt.model.dto.mapper.PageDataDTO;
import com.example.ojt.model.dto.mapper.ResponseMapper;
import com.example.ojt.model.dto.response.CandidateListResponseDTO;
import com.example.ojt.model.dto.response.CandidateResponseDTO;
import com.example.ojt.service.candidate.ICandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api.myservice.com/v1/account/company/candidate")
public class CandidateCompanyController {
    @Autowired
    private ICandidateService candidateService;
    @GetMapping("")
    public ResponseEntity<?> getCandidate(@RequestParam(name = "keyword", required = false) String keyword,
                                        @RequestParam(defaultValue = "5", name = "limit") int limit,
                                        @RequestParam(defaultValue = "0", name = "page") int page,
                                        @RequestParam(defaultValue = "id", name = "sort") String sort,
                                        @RequestParam(defaultValue = "asc", name = "order") String order) throws CustomException {
        PageDataDTO<CandidateListResponseDTO> candidateResponseDTOPageDataDTO = candidateService.getAllCandidate(keyword, page, limit, sort, order);
        return new ResponseEntity<>(new ResponseMapper<>(
                HttpResponse.SUCCESS,
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                candidateResponseDTOPageDataDTO
        ), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findCompanyById(@PathVariable Integer id) throws CustomException {
        CandidateResponseDTO candidateResponseDTO  = candidateService.findById(id);
        return new ResponseEntity<>(new ResponseMapper<>(
                HttpResponse.SUCCESS,
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                candidateResponseDTO
        ), HttpStatus.OK);
    }
}

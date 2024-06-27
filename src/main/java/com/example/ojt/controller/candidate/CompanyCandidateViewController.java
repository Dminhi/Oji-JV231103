package com.example.ojt.controller.candidate;

import com.example.ojt.exception.CustomException;
import com.example.ojt.model.dto.mapper.HttpResponse;
import com.example.ojt.model.dto.mapper.PageDataDTO;
import com.example.ojt.model.dto.mapper.ResponseMapper;
import com.example.ojt.model.dto.response.CompanyDetailResponseDTO;
import com.example.ojt.model.dto.response.CompanyResponseDTO;
import com.example.ojt.model.entity.Company;
import com.example.ojt.service.company.ICompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api.myservice.com/v1/account/candidate/company")
public class CompanyCandidateViewController {
    @Autowired
    private ICompanyService companyService;

    @GetMapping("")
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

    @GetMapping("/{id}")
    public ResponseEntity<?> findCompanyById(@PathVariable Integer id) throws CustomException {
        Company company = companyService.findById(id);
        CompanyDetailResponseDTO companyResponseDTO = new CompanyDetailResponseDTO(company);
        return new ResponseEntity<>(new ResponseMapper<>(
                HttpResponse.SUCCESS,
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                companyResponseDTO
        ), HttpStatus.OK);
    }
}

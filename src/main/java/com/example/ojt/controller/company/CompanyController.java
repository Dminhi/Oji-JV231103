package com.example.ojt.controller.company;

import com.example.ojt.exception.CustomException;
import com.example.ojt.model.dto.request.CandidateRequestDTO;
import com.example.ojt.model.dto.request.CompanyRequestDTO;
import com.example.ojt.model.dto.request.EditCompanyRequestDTO;
import com.example.ojt.model.dto.response.APIResponse;
import com.example.ojt.service.company.ICompanyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api.myservice.com/v1/account/company")
public class CompanyController {
    @Autowired
    private ICompanyService   companyService;
    @PostMapping("")
    public ResponseEntity<?> addCompany(@Valid @RequestBody CompanyRequestDTO companyRequestDTO) throws CustomException {
        boolean check = companyService.save(companyRequestDTO);
        if(check) {
            APIResponse apiResponse = new APIResponse(200, "Create company success");
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);

        }  else {
            throw new CustomException("Lack of compulsory registration information or invalid information.", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
    @PutMapping("/{id}")
    ResponseEntity<?> EditCompany(@Valid @PathVariable Integer id, @ModelAttribute("company") EditCompanyRequestDTO companyRequestDTO) throws CustomException {
        boolean check = companyService.update(companyRequestDTO,id);
        if(check) {
            APIResponse apiResponse = new APIResponse(200, "Update company success");
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        }else {
            throw new CustomException("Lack of compulsory registration information or invalid information.", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}

package com.example.ojt.controller.candidate;

import com.example.ojt.exception.CustomException;
import com.example.ojt.model.dto.request.CandidateRequestDTO;
import com.example.ojt.model.dto.request.EduCandidateRequestDTO;
import com.example.ojt.model.dto.response.APIResponse;
import com.example.ojt.model.entity.CertificateCandidate;
import com.example.ojt.service.candidate.ICandidateService;
import com.example.ojt.service.cerCandidate.ICertificateCandidateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api.myservice.com/v1/account/cer-candidate")
public class CertificateCandidateController {
    @Autowired
    private ICertificateCandidateService certificateCandidateService;
    @PostMapping("")
    ResponseEntity<?> addCandidate(@Valid @RequestBody CertificateCandidate certificateCandidate) throws CustomException {
        boolean check = certificateCandidateService.saveOrUpdate(certificateCandidate);
        if(check) {
            APIResponse apiResponse = new APIResponse(200, "Create certificate Candidate success");
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        }else {
            throw new CustomException("Lack of compulsory registration information or invalid information.", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
    @PutMapping("")
    ResponseEntity<?> EditCerEduCandidate(@Valid @RequestBody CertificateCandidate certificateCandidate) throws CustomException {
        boolean check = certificateCandidateService.saveOrUpdate(certificateCandidate);
        if(check) {
            APIResponse apiResponse = new APIResponse(200, "Update Certificate candidate success");
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        }else {
            throw new CustomException("Lack of compulsory registration information or invalid information.", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}

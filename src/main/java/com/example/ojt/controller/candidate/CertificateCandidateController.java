package com.example.ojt.controller.candidate;

import com.example.ojt.exception.CustomException;
import com.example.ojt.model.dto.mapper.HttpResponse;
import com.example.ojt.model.dto.mapper.PageDataDTO;
import com.example.ojt.model.dto.mapper.ResponseMapper;
import com.example.ojt.model.dto.request.CertificateCandidateRequestDTO;
import com.example.ojt.model.dto.response.APIResponse;
import com.example.ojt.model.dto.response.CertificateCandidateResponseDTO;
import com.example.ojt.model.entity.CertificateCandidate;
import com.example.ojt.service.cerCandidate.ICertificateCandidateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api.myservice.com/v1/account/candidate/cer-candidate")
public class CertificateCandidateController {
    @Autowired
    private ICertificateCandidateService certificateCandidateService;

    @PostMapping("")
    ResponseEntity<?> addCerCandidate(@Valid @RequestBody CertificateCandidateRequestDTO certificateCandidateRequestDTO) throws CustomException {
        boolean check = certificateCandidateService.save(certificateCandidateRequestDTO);
        if (check) {
            APIResponse apiResponse = new APIResponse(200, "Create certificate Candidate success");
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        } else {
            throw new CustomException("Lack of compulsory registration information or invalid information.", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<?> EditCerCandidate(@Valid @PathVariable Integer id, @RequestBody CertificateCandidateRequestDTO certificateCandidateRequestDTO) throws CustomException {
        boolean check = certificateCandidateService.update(certificateCandidateRequestDTO ,id);
        if (check) {
            APIResponse apiResponse = new APIResponse(200, "Update Certificate candidate success");
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        } else {
            throw new CustomException("Lack of compulsory registration information or invalid information.", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<?> removeCertificateCandidate(@PathVariable Integer id) throws CustomException {
        boolean check = certificateCandidateService.removeCerCandidate(id);
        if (check) {
            APIResponse apiResponse = new APIResponse(200, "Delete Certificate success");
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
        PageDataDTO<CertificateCandidateResponseDTO> cerCandidatePage = certificateCandidateService.getCerCandidate(keyword, page, limit, sort, order);
        return new ResponseEntity<>(new ResponseMapper<>(
                HttpResponse.SUCCESS,
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                cerCandidatePage
        ), HttpStatus.OK);
    }
}

package com.example.ojt.controller.candidate;

import com.example.ojt.exception.CustomException;
import com.example.ojt.model.dto.mapper.HttpResponse;
import com.example.ojt.model.dto.mapper.PageDataDTO;
import com.example.ojt.model.dto.mapper.ResponseMapper;
import com.example.ojt.model.dto.request.CandidateRequestDTO;
import com.example.ojt.model.dto.response.APIResponse;
import com.example.ojt.model.dto.response.CandidateResponseDTO;
import com.example.ojt.model.entity.Candidate;
import com.example.ojt.service.candidate.ICandidateService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api.myservice.com/v1/account/candidate")
public class CandidateController {
    @Autowired
    private ICandidateService candidateService;
    @PostMapping("")
    ResponseEntity<?> addCandidate(@Valid @ModelAttribute("candidate")CandidateRequestDTO candidateRequestDTO) throws CustomException {
            boolean check = candidateService.saveOrUpdate(candidateRequestDTO);
            if(check) {
                APIResponse apiResponse = new APIResponse(200, "Create candidate success");
                return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
            }else {
                throw new CustomException("Lack of compulsory registration information or invalid information.", HttpStatus.UNPROCESSABLE_ENTITY);
            }
    }
    @PutMapping("")
    ResponseEntity<?> EditCandidate(@Valid @ModelAttribute("candidate")CandidateRequestDTO candidateRequestDTO) throws CustomException {
        boolean check = candidateService.saveOrUpdate(candidateRequestDTO);
        if(check) {
            APIResponse apiResponse = new APIResponse(200, "Update candidate success");
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        }else {
            throw new CustomException("Lack of compulsory registration information or invalid information.", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<?> removeCandidate(@PathVariable Integer id) throws CustomException {
        boolean check = candidateService.removeCandidate(id);
        if(check) {
            APIResponse apiResponse = new APIResponse(200, "Delete candidate success");
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        }else {
            throw new CustomException("Lack of compulsory registration information or invalid information.", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
    @GetMapping("")
    public ResponseEntity<?> getCandidate() throws CustomException {
        return new ResponseEntity<>(candidateService.getCandidate(), HttpStatus.CREATED);
    }
}

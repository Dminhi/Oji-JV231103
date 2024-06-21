package com.example.ojt.controller.candidate;
import com.example.ojt.exception.CustomException;
import com.example.ojt.model.dto.request.ProjectCandidateRequestDTO;
import com.example.ojt.model.dto.response.APIResponse;
import com.example.ojt.service.projectCandidatde.IProjectCandidateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api.myservice.com/v1/account/pro-candidate")
public class ProjectCandidateController {
    @Autowired
    private IProjectCandidateService projectCandidateService;
    @PostMapping("")
    ResponseEntity<?> addCandidate(@Valid @RequestBody ProjectCandidateRequestDTO projectCandidateRequestDTO) throws CustomException {
        boolean check = projectCandidateService.saveOrUpdate(projectCandidateRequestDTO);
        if(check) {
            APIResponse apiResponse = new APIResponse(200, "Create Project Candidate success");
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        }else {
            throw new CustomException("Lack of compulsory registration information or invalid information.", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
    @PutMapping("")
    ResponseEntity<?> editProjectCandidate(@Valid @RequestBody ProjectCandidateRequestDTO projectCandidateRequestDTO) throws CustomException {
        boolean check = projectCandidateService.saveOrUpdate(projectCandidateRequestDTO);
        if(check) {
            APIResponse apiResponse = new APIResponse(200, "Update ProjectCandidate success");
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        }else {
            throw new CustomException("Lack of compulsory registration information or invalid information.", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}

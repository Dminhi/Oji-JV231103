package com.example.ojt.controller.candidate;

import com.example.ojt.exception.CustomException;
import com.example.ojt.model.dto.mapper.HttpResponse;
import com.example.ojt.model.dto.mapper.PageDataDTO;
import com.example.ojt.model.dto.mapper.ResponseMapper;
import com.example.ojt.model.dto.request.SkillCandidateRequestDTO;
import com.example.ojt.model.dto.response.APIResponse;
import com.example.ojt.model.dto.response.SkillCandidateResponse;
import com.example.ojt.service.skillCandidate.ISkillCandidateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api.myservice.com/v1/account/candidate/skill-candidate")
public class SkillCandidateController {
    @Autowired
    private ISkillCandidateService skillCandidateService;

    @PostMapping("")
    ResponseEntity<?> addSkillCandidate(@Valid @RequestBody SkillCandidateRequestDTO skillCandidateRequestDTO) throws CustomException {
        boolean check = skillCandidateService.save(skillCandidateRequestDTO);
        if (check) {
            APIResponse apiResponse = new APIResponse(200, "Create Skill Candidate success");
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        } else {
            throw new CustomException("Lack of compulsory registration information or invalid information.", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
    @PutMapping("/{id}")
    ResponseEntity<?> EditSkillCandidate(@Valid @PathVariable Integer id, @RequestBody SkillCandidateRequestDTO skillCandidateRequestDTO) throws CustomException {
        boolean check = skillCandidateService.update(skillCandidateRequestDTO ,id);
        if (check) {
            APIResponse apiResponse = new APIResponse(200, "Update Skill candidate success");
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        } else {
            throw new CustomException("Lack of compulsory registration information or invalid information.", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
    @DeleteMapping("/remove/{id}")
    public ResponseEntity<?> removeSkillCandidate(@PathVariable Integer id) throws CustomException {
        boolean check = skillCandidateService.removeSkillCandidate(id);
        if (check) {
            APIResponse apiResponse = new APIResponse(200, "Delete Skill Candidate success");
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
        PageDataDTO<SkillCandidateResponse> skillCandidateResponsePageDataDTO = skillCandidateService.getSkillCandidate(keyword, page, limit, sort, order);
        return new ResponseEntity<>(new ResponseMapper<>(
                HttpResponse.SUCCESS,
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                skillCandidateResponsePageDataDTO
        ), HttpStatus.OK);
    }
}

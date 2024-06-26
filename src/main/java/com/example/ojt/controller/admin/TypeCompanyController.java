package com.example.ojt.controller.admin;

import com.example.ojt.exception.CustomException;
import com.example.ojt.model.dto.mapper.HttpResponse;
import com.example.ojt.model.dto.mapper.PageDataDTO;
import com.example.ojt.model.dto.mapper.ResponseMapper;
import com.example.ojt.model.dto.request.EduCandidateRequestDTO;
import com.example.ojt.model.dto.request.TypeCompanyRequestDTO;
import com.example.ojt.model.dto.response.APIResponse;
import com.example.ojt.model.dto.response.EducationCandidateResponseDTO;
import com.example.ojt.model.dto.response.TypeCompanyResponse;
import com.example.ojt.service.typeCompany.ITypeCompanyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api.myservice.com/v1/admin/company/typeCompany")
public class TypeCompanyController {
    @Autowired
    private ITypeCompanyService typeCompanyService;
    @RequestMapping("")
    public ResponseEntity<?> addTypeCompany(@Valid @RequestBody TypeCompanyRequestDTO typeCompanyRequestDTO) throws CustomException {
        boolean check = typeCompanyService.save(typeCompanyRequestDTO);
        if (check) {
            APIResponse apiResponse = new APIResponse(200, "Create Type Company success");
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        } else {
            throw new CustomException("Lack of compulsory registration information or invalid information.", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
    @PutMapping("/{id}")
    ResponseEntity<?> EditTypeCompany(@Valid @RequestBody @PathVariable Integer id, TypeCompanyRequestDTO typeCompanyRequestDTO) throws CustomException {
        boolean check = typeCompanyService.update(typeCompanyRequestDTO,id);
        if (check) {
            APIResponse apiResponse = new APIResponse(200, "Update Type Company success");
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        } else {
            throw new CustomException("Lack of compulsory registration information or invalid information.", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<?> removeEducationCandidate(@PathVariable Integer id) throws CustomException {
        boolean check = typeCompanyService.removeTypeCompany(id);
        if (check) {
            APIResponse apiResponse = new APIResponse(200, "Delete Type Company success");
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        } else {
            throw new CustomException("Lack of compulsory registration information or invalid information.", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
    @GetMapping("")
    public ResponseEntity<?> getProCandidate(@RequestParam(name = "keyword", required = false) String keyword,
                                             @RequestParam(defaultValue = "5", name = "limit") int limit,
                                             @RequestParam(defaultValue = "0", name = "page") int page,
                                             @RequestParam(defaultValue = "id", name = "sort") String sort,
                                             @RequestParam(defaultValue = "asc", name = "order") String order) throws CustomException {
        PageDataDTO<TypeCompanyResponse> typeCompany = typeCompanyService.getTypeCompany(keyword, page, limit, sort, order);
        return new ResponseEntity<>(new ResponseMapper<>(
                HttpResponse.SUCCESS,
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                typeCompany
        ), HttpStatus.OK);
    }

}

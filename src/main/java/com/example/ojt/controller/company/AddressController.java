package com.example.ojt.controller.company;

import com.example.ojt.exception.CustomException;
import com.example.ojt.model.dto.mapper.HttpResponse;
import com.example.ojt.model.dto.mapper.PageDataDTO;
import com.example.ojt.model.dto.mapper.ResponseMapper;
import com.example.ojt.model.dto.request.AddressCompanyRequestDTO;
import com.example.ojt.model.dto.request.EditCompanyRequestDTO;
import com.example.ojt.model.dto.response.APIResponse;
import com.example.ojt.model.dto.response.AddressCompanyResponseDTO;
import com.example.ojt.model.dto.response.EducationCandidateResponseDTO;
import com.example.ojt.service.address.IAddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api.myservice.com/v1/account/company/address-company")
public class AddressController {
    @Autowired
    private IAddressService addressService;
    @PostMapping("")
    public ResponseEntity<?> addAddress(@Valid @RequestBody AddressCompanyRequestDTO addressCompanyRequestDTO) throws CustomException{
        boolean check = addressService.save(addressCompanyRequestDTO);
        if(check) {
            APIResponse apiResponse = new APIResponse(200, "Create address success");
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        } else {
            throw new CustomException("Lack of compulsory registration information or invalid information.", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
    @PutMapping("/{id}")
    ResponseEntity<?> EditAddress(@Valid @PathVariable("id") Integer id , @RequestBody AddressCompanyRequestDTO addressCompanyRequestDTO) throws CustomException {
        boolean check = addressService.update(addressCompanyRequestDTO,id);
        if(check) {
            APIResponse apiResponse = new APIResponse(200, "Update Address success");
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        }else {
            throw new CustomException("Lack of compulsory registration information or invalid information.", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
    @DeleteMapping("/{id}")
    ResponseEntity<?> DeleteAddress(@Valid @PathVariable("id") Integer id ) throws CustomException {
        boolean check = addressService.delete(id);
        if(check) {
            APIResponse apiResponse = new APIResponse(200, "Delete Address success");
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        }else {
            throw new CustomException("Lack of compulsory registration information or invalid information.", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
    @GetMapping("")
    public ResponseEntity<?> getAddress(@RequestParam(name = "keyword", required = false) String keyword,
                                             @RequestParam(defaultValue = "5", name = "limit") int limit,
                                             @RequestParam(defaultValue = "0", name = "page") int page,
                                             @RequestParam(defaultValue = "id", name = "sort") String sort,
                                             @RequestParam(defaultValue = "asc", name = "order") String order) throws CustomException {
        PageDataDTO<AddressCompanyResponseDTO> addressPage = addressService.getAddress(keyword, page, limit, sort, order);
        return new ResponseEntity<>(new ResponseMapper<>(
                HttpResponse.SUCCESS,
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                addressPage
        ), HttpStatus.OK);
    }
}

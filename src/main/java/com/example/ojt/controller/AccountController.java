package com.example.ojt.controller;

import com.example.ojt.exception.NotFoundException;
import com.example.ojt.exception.RequestErrorException;
import com.example.ojt.model.dto.request.AccountEditPassword;
import com.example.ojt.model.dto.response.AccountResponse;
import com.example.ojt.model.dto.responsewapper.ResponseWapper;
import com.example.ojt.service.accountService.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api.myservice.com/v1/user")
public class AccountController {
    @Autowired
    private IAccountService accountService;

    @PutMapping("/account/change-password")
    public ResponseEntity<?> updatePasswordAccount(@RequestBody AccountEditPassword accountEditPassword) throws NotFoundException, RequestErrorException {
       AccountResponse account = accountService.changePassword(accountEditPassword);
        return new ResponseEntity<>(new ResponseWapper<>(
                HttpStatus.OK.value(),
                "Change password success",
                account), HttpStatus.OK);
    }

}

package com.example.ojt.controller;

import com.example.ojt.exception.CustomException;
import com.example.ojt.exception.RequestErrorException;
import com.example.ojt.model.dto.request.*;
import com.example.ojt.model.dto.mapper.HttpResponse;
import com.example.ojt.model.dto.mapper.ResponseMapper;
import com.example.ojt.model.dto.request.LoginAccountRequest;
import com.example.ojt.model.dto.request.RegisterAccount;
import com.example.ojt.model.dto.request.RegisterAccountCompany;
import com.example.ojt.model.dto.response.APIResponse;
import com.example.ojt.model.dto.response.JWTResponse;
import com.example.ojt.model.dto.responsewapper.ResponseWapper;
import com.example.ojt.service.account.IAccountService;
import com.example.ojt.exception.NotFoundException;
import com.example.ojt.service.account.EmailService;
import com.example.ojt.service.account.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api.myservice.com/v1/auth")
public class AuthController {
    @Autowired
    private IAccountService accountService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/sign-in")
    public ResponseEntity<?> doLogin(@Valid @RequestBody LoginAccountRequest loginAccountRequest) throws Exception {
        JWTResponse jwtResponse = accountService.login(loginAccountRequest);
        return new ResponseEntity<>(new ResponseWapper<>(
                HttpStatus.OK.value(),
                "Login successful",
                jwtResponse), HttpStatus.OK);

    }

    @PostMapping("company/sign-in")
    public ResponseEntity<?> doCompanyLogin(@Valid @RequestBody LoginAccountRequest loginAccountRequest) throws Exception {
        JWTResponse jwtResponse = accountService.companyLogin(loginAccountRequest);
        return new ResponseEntity<>(new ResponseWapper<>(
                HttpStatus.OK.value(),
                "Login successful",
                jwtResponse), HttpStatus.OK);

    
    }


    @PostMapping("/sign-up")
    public ResponseEntity<?> doRegister(@Valid @RequestBody RegisterAccount registerAccount) throws CustomException, RequestErrorException {
        boolean check = accountService.register(registerAccount);
        if (check) {
            APIResponse response = new APIResponse(200, "Register successful");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            throw new CustomException("Lack of compulsory registration information or invalid information.", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @PostMapping("/sign-up/company")
    public ResponseEntity<?> doRegisterCompany(@Valid @RequestBody RegisterAccountCompany registerAccount) throws CustomException {
        boolean check = accountService.registerCompany(registerAccount);
        if (check) {
            APIResponse response = new APIResponse(200, "Register successful");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            throw new CustomException("Lack of compulsory registration information or invalid information.", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @PostMapping("/reset-password-request")
    public ResponseEntity<String> resetPasswordRequest(@RequestBody AccountForgotPassword accountForgotPassword) throws NotFoundException {
        String token = tokenService.generateToken(accountForgotPassword.getEmail()); // Generate token for the email
        emailService.sendResetPasswordEmail(accountForgotPassword.getEmail(), token);
        return ResponseEntity.ok("Reset password email sent.");
    }

    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam String token) {
        if (tokenService.isValid(token)) {
            return "Enter new password"; // Return your reset password form
        } else {
            return "Invalid token";
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestBody ResetPassword newPassword) throws NotFoundException {
        System.out.println(newPassword);
        if (tokenService.isValid(token)) {
            String email = tokenService.getEmailFromToken(token);
            accountService.updatePassword(email, newPassword.getNewPassword());
            tokenService.removeToken(token);
            return ResponseEntity.ok("Password updated successfully.");
        } else {
            return ResponseEntity.badRequest().body("Invalid token");
        }
    }
}

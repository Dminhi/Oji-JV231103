package com.example.ojt.controller;

import com.example.ojt.exception.AccountLockedException;
import com.example.ojt.exception.NotFoundException;
import com.example.ojt.exception.RequestErrorException;
import com.example.ojt.model.dto.request.AccountForgotPassword;
import com.example.ojt.model.dto.request.FormLogin;
import com.example.ojt.model.dto.request.ResetPassword;
import com.example.ojt.model.dto.responsewapper.ResponseWapper;
import com.example.ojt.service.accountService.EmailService;
import com.example.ojt.service.accountService.IAccountService;
import com.example.ojt.service.accountService.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api.example.com/v1/auth")
public class AuthController {
    @Autowired
    private IAccountService accountService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private TokenService tokenService;
    @PostMapping("/sign-in")
    public ResponseEntity<?> doLogin(@Valid @RequestBody FormLogin formLogin) throws AccountLockedException, NotFoundException, RequestErrorException {
        return new ResponseEntity<>(new ResponseWapper<>(
                HttpStatus.OK.value(),
                "Login successfull",
                accountService.login(formLogin)), HttpStatus.OK);
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
    public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestBody ResetPassword resetPassword) throws NotFoundException {
        if (tokenService.isValid(token)) {
            String email = tokenService.getEmailFromToken(token);
            accountService.updatePassword(email, resetPassword.getNewPassword());
            tokenService.deleteToken(token);
            return ResponseEntity.ok("Password updated successfully.");
        } else {
            return ResponseEntity.badRequest().body("Invalid token");
        }
    }

}

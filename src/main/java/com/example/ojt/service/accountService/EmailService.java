package com.example.ojt.service.accountService;

import com.example.ojt.exception.NotFoundException;
import com.example.ojt.model.entity.Account;
import com.example.ojt.repository.IAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private IAccountRepository accountRepository;

    public void sendResetPasswordEmail(String to, String token) throws NotFoundException {
        Account account = accountRepository.findByEmail(to).orElseThrow(()->new NotFoundException("Email : "+to+" is not exist"));;
        String subject = "Reset Password";
        String url = "http://localhost:5173/reset-password/" + token;
        String message = "Click the link to reset your password: " + url;
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(to);
        email.setSubject(subject);
        email.setText(message);

        mailSender.send(email);
    }
}

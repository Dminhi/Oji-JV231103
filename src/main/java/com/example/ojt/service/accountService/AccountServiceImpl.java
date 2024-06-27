package com.example.ojt.service.accountService;


import com.example.ojt.exception.AccountLockedException;
import com.example.ojt.exception.NotFoundException;
import com.example.ojt.exception.RequestErrorException;
import com.example.ojt.model.dto.request.AccountEditPassword;
import com.example.ojt.model.dto.request.FormLogin;
import com.example.ojt.model.dto.response.AccountResponse;
import com.example.ojt.model.dto.response.JWTResponse;
import com.example.ojt.model.entity.Account;
import com.example.ojt.repository.IAccountRepository;
import com.example.ojt.security.jwt.JWTProvider;
import com.example.ojt.security.principle.AccountDetailsCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements IAccountService {
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTProvider jwtProvider;
    @Autowired
    private IAccountRepository accountRepository;

    @Autowired
    private TokenService tokenService;

    @Override
    public JWTResponse login(FormLogin formLogin) throws AccountLockedException, RequestErrorException {
        Authentication authentication = null;
        try {
            authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(formLogin.getEmail(), formLogin.getPassword()));
        } catch (AuthenticationException e) {
            throw new RequestErrorException("email or password incorrect");
        }
        AccountDetailsCustom detailsCustom = (AccountDetailsCustom) authentication.getPrincipal();
        if (detailsCustom.getStatus()==2) {
            throw new AccountLockedException("Your account has been locked");
        }
        String accessToken = jwtProvider.generateAccessToken(detailsCustom);
        return JWTResponse.builder()
                .email(detailsCustom.getEmail())
                .roleSet(detailsCustom.getAuthorities())
                .status(detailsCustom.getStatus())
                .accessToken(accessToken)
                .build();
    }


    @Override
    public AccountResponse changePassword(AccountEditPassword accountEditPassword) throws RequestErrorException, NotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetailsCustom accountDetailsCustom = (AccountDetailsCustom) authentication.getPrincipal();
        Account account = accountRepository.findById(accountDetailsCustom.getId()).orElseThrow(() -> new NotFoundException("email not found"));
        if (!passwordEncoder.matches(accountEditPassword.getOldPassword(),account.getPassword())) {
            throw new RequestErrorException("old password wrong");
        }
        account.setPassword(passwordEncoder.encode(accountEditPassword.getNewPassWord()));
        accountRepository.save(account);
        return toUserResponse(account);
    }


    @Override
    public void updatePassword(String email, String newPassword) throws NotFoundException {
        Account account = accountRepository.findByEmail(email).orElseThrow(()->new NotFoundException(email+" is not exist"));;
        account.setPassword(passwordEncoder.encode(newPassword));
        accountRepository.save(account);
    }

    public static AccountResponse toUserResponse(Account account) {
        if (account == null) {
            return null; // Nếu user là null, trả về null để tránh lỗi
        }
        // Sử dụng Builder của UserResponse để tạo một đối tượng mới
        return AccountResponse.builder()
                .email(account.getEmail())
                .build();
    }
    
}

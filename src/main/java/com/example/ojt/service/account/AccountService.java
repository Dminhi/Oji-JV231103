package com.example.ojt.service.account;

import com.example.ojt.exception.CustomException;
import com.example.ojt.exception.NotFoundException;
import com.example.ojt.exception.RequestErrorException;
import com.example.ojt.model.dto.request.AccountEditPassword;
import com.example.ojt.model.dto.request.LoginAccountRequest;
import com.example.ojt.model.dto.request.RegisterAccount;
import com.example.ojt.model.dto.request.RegisterAccountCompany;
import com.example.ojt.model.dto.response.AccountResponse;
import com.example.ojt.model.dto.response.JWTResponse;
import com.example.ojt.model.entity.*;
import com.example.ojt.repository.IAccountRepository;
import com.example.ojt.repository.ICandidateRepository;
import com.example.ojt.repository.ICompanyRepository;
import com.example.ojt.repository.IRoleRepository;
import com.example.ojt.security.jwt.JWTProvider;
import com.example.ojt.security.principle.AccountDetailsCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static com.example.ojt.model.entity.RoleName.ROLE_COMPANY;

@Service
public class AccountService implements IAccountService {
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private IAccountRepository accountRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTProvider jwtProvider;
    @Autowired
    private IRoleRepository roleRepository;
    @Autowired
    private ICompanyRepository companyRepository;
    @Autowired
    private ICandidateRepository candidateRepository;
    @Override
    public JWTResponse login(LoginAccountRequest loginAccountRequest) throws CustomException {
      // Xac thuc email and password
        Authentication authentication = null;
        try {
            authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(loginAccountRequest.getEmail(), loginAccountRequest.getPassword()));
        } catch (AuthenticationException e) {
            System.out.println(e);
            throw new CustomException("Email or password incorrect", HttpStatus.NOT_FOUND);
        }
        AccountDetailsCustom detailsCustom = (AccountDetailsCustom) authentication.getPrincipal();
        if (detailsCustom.getStatus() == 2) {
            throw new CustomException("Account has been blocked!", HttpStatus.FORBIDDEN);
        }
        if (detailsCustom.getRoleName().equals("ROLE_COMPANY")) {
            throw new CustomException("Your Account invalid", HttpStatus.FORBIDDEN);
        }
        

        String accessToken = jwtProvider.generateAccessToken(detailsCustom);
        return JWTResponse.builder()
                .email(detailsCustom.getEmail())
                .roleName(detailsCustom.getRoleName())
                .status(detailsCustom.getStatus())
                .name(detailsCustom.getName())
                .avatar(detailsCustom.getAvatar())
                .accessToken(accessToken)
                .accountId(detailsCustom.getId())
                .roleSet(detailsCustom.getAuthorities())
                .build();
    }

    @Override
    public JWTResponse companyLogin(LoginAccountRequest loginAccountRequest) throws CustomException {
        // Xac thuc email and password
        Authentication authentication = null;
        try {
            authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(loginAccountRequest.getEmail(), loginAccountRequest.getPassword()));
        } catch (AuthenticationException e) {
            throw new CustomException("Email or password incorrect", HttpStatus.NOT_FOUND);
        }
        AccountDetailsCustom detailsCustom = (AccountDetailsCustom) authentication.getPrincipal();
        if (detailsCustom.getStatus() == 2) {
            throw new CustomException("Account has been blocked!", HttpStatus.FORBIDDEN);
        }
        if (detailsCustom.getRoleName().equals("ROLE_CANDIDATE")) {
            throw new CustomException("Your Account invalid", HttpStatus.FORBIDDEN);
        }
        String accessToken = jwtProvider.generateAccessToken(detailsCustom);
        return JWTResponse.builder()
                .email(detailsCustom.getEmail())
                .roleName(detailsCustom.getRoleName())
                .status(detailsCustom.getStatus())
                .accessToken(accessToken)
                .build();
    }

    
    public boolean register(RegisterAccount registerAccount) throws CustomException {
        if (accountRepository.existsByEmail(registerAccount.getEmail())) {
            throw new CustomException("Email existed!", HttpStatus.CONFLICT);
        }
        if (!registerAccount.getPassword().equals(registerAccount.getConfirmPassword())) {
            throw new CustomException("Password do not match!",HttpStatus.CONFLICT);
        }
        Role role = roleRepository.findByRoleName(RoleName.valueOf(registerAccount.getRoleName()))
                .orElseThrow(() -> new CustomException("Role not found", HttpStatus.NOT_FOUND));
        Account account = Account.builder()
                .email(registerAccount.getEmail())
                .password(passwordEncoder.encode(registerAccount.getPassword()))
                .status(1)
                .role(role)
                .build();
        accountRepository.save(account);
        Candidate candidate = new Candidate();
        candidate.setAccount(account);
        candidate.setCreatedAt(new Date());
        candidate.setAvatar("https://seeklogo.com/images/A/anonymous-logo-7E968E8797-seeklogo.com.png");
        candidateRepository.save(candidate);
        return true;
    }

    @Override
    @Transactional
    public boolean registerCompany(RegisterAccountCompany registerAccount) throws CustomException {
        if (accountRepository.existsByEmail(registerAccount.getEmail())) {
            throw new CustomException("Email existed!", HttpStatus.CONFLICT);
        }
        Role role = roleRepository.findByRoleName(RoleName.valueOf(registerAccount.getRoleName()))
                .orElseThrow(() -> new CustomException("Role not found", HttpStatus.NOT_FOUND));
        Account account = Account.builder()
                .email(registerAccount.getEmail())
                .password(passwordEncoder.encode(registerAccount.getPassword()))
                .status(1)
                .role(role)
                .build();
        accountRepository.save(account);

            if (companyRepository.existsByName(registerAccount.getNameCompany())) {
                throw new CustomException("Company with name " + registerAccount.getNameCompany() + " already exists", HttpStatus.CONFLICT);
            }
            if (companyRepository.existsByEmailCompany(registerAccount.getEmail())) {
                throw new CustomException("Company with email " + registerAccount.getEmail() + " already exists", HttpStatus.CONFLICT);
            }

            Company company = new Company();
            company.setName(registerAccount.getNameCompany());
            company.setEmailCompany(registerAccount.getEmail());
            company.setPhone(registerAccount.getPhone());
            company.setAccount(account);
            company.setLogo("https://img.freepik.com/free-vector/free-vector-panda-bamboo-mascot-logo_779267-1386.jpg?t=st=1719451968~exp=1719455568~hmac=f2499171ac9d6522bb5f76f54c90acd2b67bf651182b9820fc9c3bb45793ee97&w=740");
            company.setCreatedAt(new Date());
            companyRepository.save(company);
        return true;

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
        Account account = accountRepository.findByEmail(email).orElseThrow(()->new NotFoundException("Account not found with this email : "+email));;
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

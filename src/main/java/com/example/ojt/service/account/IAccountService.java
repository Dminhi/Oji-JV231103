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

public interface IAccountService {
    JWTResponse login(LoginAccountRequest loginAccountRequest) throws CustomException;

    boolean register(RegisterAccount registerAccount) throws CustomException, RequestErrorException;

    boolean registerCompany(RegisterAccountCompany registerAccount) throws CustomException;
    AccountResponse changePassword(AccountEditPassword accountEditPassword) throws NotFoundException, RequestErrorException;

    void updatePassword(String email, String newPassword) throws NotFoundException;

    public JWTResponse companyLogin(LoginAccountRequest loginAccountRequest) throws CustomException;
}

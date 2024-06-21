package com.example.ojt.service.accountService;

import com.example.ojt.exception.AccountLockedException;
import com.example.ojt.exception.NotFoundException;
import com.example.ojt.exception.RequestErrorException;
import com.example.ojt.model.dto.request.AccountEditPassword;
import com.example.ojt.model.dto.request.FormLogin;
import com.example.ojt.model.dto.response.AccountResponse;
import com.example.ojt.model.dto.response.JWTResponse;

public interface IAccountService {
    JWTResponse login(FormLogin formLogin) throws NotFoundException, AccountLockedException;

    AccountResponse changePassword(AccountEditPassword accountEditPassword) throws NotFoundException, RequestErrorException;

    void updatePassword(String email, String newPassword) throws NotFoundException;
}

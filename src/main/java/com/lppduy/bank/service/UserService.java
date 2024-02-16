package com.lppduy.bank.service;

import com.lppduy.bank.dto.BankResponse;
import com.lppduy.bank.dto.UserRequest;

public interface UserService {
    BankResponse createAccount(UserRequest userRequest);
}

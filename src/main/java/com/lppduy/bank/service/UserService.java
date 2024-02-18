package com.lppduy.bank.service;

import com.lppduy.bank.dto.BankResponse;
import com.lppduy.bank.dto.CreditDebitRequest;
import com.lppduy.bank.dto.EnquiryRequest;
import com.lppduy.bank.dto.UserRequest;

public interface UserService {
    BankResponse createAccount(UserRequest userRequest);
    BankResponse balanceEnquiry(EnquiryRequest request);
    String nameEnquiry(EnquiryRequest request);
    BankResponse creditAccount(CreditDebitRequest request);
    BankResponse debitAccount(CreditDebitRequest request);

}

package com.lppduy.bank.service;

import com.lppduy.bank.dto.*;

import java.util.List;

public interface UserService {

    List<AccountInfo> getAllAccount();
    BankResponse createAccount(UserRequest userRequest);
    BankResponse balanceEnquiry(EnquiryRequest request);
    String nameEnquiry(EnquiryRequest request);
    BankResponse creditAccount(CreditDebitRequest request);
    BankResponse debitAccount(CreditDebitRequest request);
    BankResponse transfer(TransferRequest request);
}

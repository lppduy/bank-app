package com.lppduy.bank.service;

import com.lppduy.bank.dto.TransactionDTO;
import com.lppduy.bank.entity.Transaction;

public interface TransactionService {
    void saveTransaction(TransactionDTO transactionDTO);
}

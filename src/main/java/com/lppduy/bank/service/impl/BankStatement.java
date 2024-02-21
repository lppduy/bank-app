package com.lppduy.bank.service.impl;

import com.lppduy.bank.entity.Transaction;
import com.lppduy.bank.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class BankStatement {

    @Autowired
    TransactionRepository transactionRepository;

    public List<Transaction> generateStatement(String accountNumber, String startDate, String endDate) {

        LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE);
        LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE);

        List<Transaction> transactionList = transactionRepository.findAll()
                .stream()
                .filter(transaction -> transaction.getAccountNumber().equals(accountNumber))
                .filter(transaction -> transaction.getCreatedAt().isAfter(start)
                        && transaction.getCreatedAt().isBefore(end)) // not include start & end
                .sorted(Comparator.comparing(Transaction::getCreatedAt)) // Sort by createdAt
                .collect(Collectors.toList());

        return transactionList;
    }

}

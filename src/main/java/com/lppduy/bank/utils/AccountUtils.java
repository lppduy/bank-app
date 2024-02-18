package com.lppduy.bank.utils;

import com.lppduy.bank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Year;

public class AccountUtils {


    public static final String ACCOUNT_EXISTS_CODE = "001";
    public static final String ACCOUNT_EXISTS_MESSAGE = "This user already has an account created!";
    public static final String ACCOUNT_CREATION_CODE = "002";
    public static final String ACCOUNT_CREATION_MESSAGE = "Account has been successfully created!";
    public static final String ACCOUNT_NOT_EXIST_CODE = "003";
    public static final String ACCOUNT_NOT_EXIST_MESSAGE = "User with the provided Account Number does not exist!";
    public static final String ACCOUNT_FOUND_CODE = "004";
    public static final String ACCOUNT_FOUND_SUCCESS = "User Account Found";
    public static final String ACCOUNT_CREDITED_SUCCESS = "005";
    public static final String ACCOUNT_CREDITED_SUCCESS_MESSAGE = "User Account Credited Successfully";
    public static final String INSUFFICIENT_BALANCE_CODE = "006";
    public static final String INSUFFICIENT_BALANCE_MESSAGE = "Insufficient Balance";
    public static final String ACCOUNT_DEBITED_SUCCESS = "007";
    public static final String ACCOUNT_DEBITED_MESSAGE = "Account has been successfully debited!";
    public static final String TRANSFER_SUCCESSFUL_CODE = "008";
    public static final String TRANSFER_SUCCESSFUL_MESSAGE = "Transfer Successful";


    public static String generateAccountNumber() {
        Year currentYear = Year.now();
        int min = 100000;
        int max = 999999;
        int randomNumber = (int) Math.floor(Math.random() * (max - min + 1) + min);

        String yearString = String.valueOf(currentYear);
        String randomNumberString = String.valueOf(randomNumber);
        StringBuilder accountNumber = new StringBuilder();
        // accountNumber: 2024 + randomSixDigits
        return accountNumber.append(yearString).append(randomNumberString).toString();
    }
}

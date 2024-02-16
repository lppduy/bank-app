package com.lppduy.bank.utils;

import com.lppduy.bank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Year;

public class AccountUtils {


    public static final String ACCOUNT_EXISTS_CODE = "001";
    public static final String ACCOUNT_EXISTS_MESSAGE = "This user already has an account created!";
    public static final String ACCOUNT_CREATION_CODE = "002!";
    public static final String ACCOUNT_CREATION_MESSAGE = "Account has been successfully created!";

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

package com.lppduy.bank.service.impl;

import com.lppduy.bank.dto.*;
import com.lppduy.bank.entity.User;
import com.lppduy.bank.repository.UserRepository;
import com.lppduy.bank.service.EmailService;
import com.lppduy.bank.service.UserService;
import com.lppduy.bank.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;

@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    EmailService emailService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,  EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    @Override
    public BankResponse createAccount(UserRequest userRequest) {

        if (userRepository.existsByEmail(userRequest.getEmail())) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        User newUser = User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .otherName(userRequest.getOtherName())
                .gender(userRequest.getGender())
                .address(userRequest.getAddress())
                .stateOfOrigin(userRequest.getStateOfOrigin())
                .accountNumber(AccountUtils.generateAccountNumber())
                .accountBalance(BigDecimal.ZERO)
                .email(userRequest.getEmail())
                .phoneNumber(userRequest.getPhoneNumber())
                .alternativePhoneNumber(userRequest.getAlternativePhoneNumber())
                .status("ACTIVE")
                .build();

        while (userRepository.existsByAccountNumber(newUser.getAccountNumber())) {
            newUser.setAccountNumber(AccountUtils.generateAccountNumber());
        }

        User savedUser = userRepository.save(newUser);

        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(savedUser.getEmail())
                .subject("ACCOUNT CREATION")
                .messageBody("Congratulations! You account has been created successfully!\n" +
                        "You account details:\n" +
                        "Account Name: " + savedUser.getFirstName() +" " + savedUser.getLastName() + " " + savedUser.getOtherName() +
                        "\nAccount Number: " + savedUser.getAccountNumber() + "\n")
                .build();

        emailService.sendEmailAlert(emailDetails);

        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_CREATION_CODE)
                .responseMessage(AccountUtils.ACCOUNT_CREATION_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountName(savedUser.getFirstName() +" " + savedUser.getLastName() + " " + savedUser.getOtherName())
                        .accountBalance(savedUser.getAccountBalance())
                        .accountNumber(savedUser.getAccountNumber())
                        .build())
                .build();
    }

    @Override
    public BankResponse balanceEnquiry(EnquiryRequest request) {

        boolean isAccountExist = userRepository.existsByAccountNumber(request.getAccountNumber());
        if (!isAccountExist) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        User foundUser = userRepository.findByAccountNumber(request.getAccountNumber());

        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_FOUND_CODE)
                .responseMessage(AccountUtils.ACCOUNT_FOUND_SUCCESS)
                .accountInfo(
                        AccountInfo.builder()
                                .accountName(foundUser.getFirstName() +" " + foundUser.getLastName() + " " + foundUser.getOtherName())
                                .accountBalance(foundUser.getAccountBalance())
                                .accountNumber(foundUser.getAccountNumber())
                                .build()
                )
                .build();
    }

    @Override
    public String nameEnquiry(EnquiryRequest request) {

        boolean isAccountExist = userRepository.existsByAccountNumber(request.getAccountNumber());
        if (!isAccountExist) {
            return AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE;
        }

        User foundUser = userRepository.findByAccountNumber(request.getAccountNumber());

        return foundUser.getFirstName() +" " + foundUser.getLastName() + " " + foundUser.getOtherName( );
    }

    @Override
    public BankResponse creditAccount(CreditDebitRequest request) {

        boolean isAccountExist = userRepository.existsByAccountNumber(request.getAccountNumber());
        if (!isAccountExist) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        User userToCredit = userRepository.findByAccountNumber(request.getAccountNumber());

        userToCredit.setAccountBalance(userToCredit.getAccountBalance().add(request.getAmount()));

        userRepository.save(userToCredit);

        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_CREDITED_SUCCESS)
                .responseMessage(AccountUtils.ACCOUNT_CREDITED_SUCCESS_MESSAGE)
                .accountInfo(
                        AccountInfo.builder()
                                .accountName(userToCredit.getFirstName() +" " + userToCredit.getLastName() + " " + userToCredit.getOtherName())
                                .accountBalance(userToCredit.getAccountBalance())
                                .accountNumber(userToCredit.getAccountNumber())
                                .build()
                )
                .build();
    }

    @Override
    public BankResponse debitAccount(CreditDebitRequest request) {

        boolean isAccountExist = userRepository.existsByAccountNumber(request.getAccountNumber());
        if (!isAccountExist) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        User userToDebit = userRepository.findByAccountNumber(request.getAccountNumber());

        BigInteger availableBalance = userToDebit.getAccountBalance().toBigInteger();
        BigInteger debitAmount = request.getAmount().toBigInteger();

        if (availableBalance.intValue() < debitAmount.intValue()) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.INSUFFICIENT_BALANCE_CODE)
                    .responseMessage(AccountUtils.INSUFFICIENT_BALANCE_MESSAGE)
                    .accountInfo(null)
                    .build();
        } else {
            userToDebit.setAccountBalance(userToDebit.getAccountBalance().subtract(request.getAmount()));
            userRepository.save(userToDebit);

            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_DEBITED_SUCCESS)
                    .responseMessage(AccountUtils.ACCOUNT_DEBITED_MESSAGE)
                    .accountInfo(
                            AccountInfo.builder()
                                    .accountName(userToDebit.getFirstName() +" " + userToDebit.getLastName() + " " + userToDebit.getOtherName())
                                    .accountBalance(userToDebit.getAccountBalance())
                                    .accountNumber(userToDebit.getAccountNumber())
                                    .build()
                    )
                    .build();
        }
    }

}

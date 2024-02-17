package com.lppduy.bank.service;

import com.lppduy.bank.dto.EmailDetails;

public interface EmailService {
    void sendEmailAlert(EmailDetails emailDetails);
}

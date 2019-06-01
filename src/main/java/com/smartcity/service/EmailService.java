package com.smartcity.service;

import java.io.File;

public interface EmailService {
    void sendSimpleMessage(String subject, String text, String... emailTo);

    void sendMessageWithAttachment(String subject, String text, File[] attachments, String... emailTo);
}

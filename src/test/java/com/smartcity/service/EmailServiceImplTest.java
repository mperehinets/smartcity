package com.smartcity.service;

import name.falgout.jeffrey.testing.junit.mockito.MockitoExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import java.io.File;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
public class EmailServiceImplTest {

    @Mock
    private EmailService emailSender;

    @Test
    public void testSendEmailWithAttachment() {
        File[] attachments = new File[]{new File("README.md")};
        emailSender.sendMessageWithAttachment("Subject",
                "Hello Dear, Please open the attachments.",
                attachments,
                "test.mail.client008@gmail.com"
        );
        verify(emailSender, times(1)).sendMessageWithAttachment("Subject",
                "Hello Dear, Please open the attachments.",
                attachments,
                "test.mail.client008@gmail.com");
    }

    @Test
    public void testSendEmail() {
        emailSender.sendSimpleMessage("Subject",
                "Hello Dear, How are you ?",
                "test.mail.client008@gmail.com"
        );
        verify(emailSender, times(1)).sendSimpleMessage("Subject",
                "Hello Dear, How are you ?",
                "test.mail.client008@gmail.com");
    }

}

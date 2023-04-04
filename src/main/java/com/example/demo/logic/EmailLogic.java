package com.example.demo.logic;

import com.example.demo.exception.EmailException;
import com.example.demo.service.EmailService;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import org.yaml.snakeyaml.emitter.EmitterException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

@Service
public class EmailLogic {

    private final EmailService emailService;

    public EmailLogic(EmailService emailService) {
        this.emailService = emailService;
    }

    public void sendActivateUserEmail(String email, String name, String token) throws EmailException {
        //prepare content html
        String subject = "";
        String html = null;
        String finalLink = "http://localhost:8080/activate/"+ token;
        try {
            html = readEmailTemplate("email-activate-user.html");
        } catch (IOException e) {
            throw EmailException.templateNotFound();
        }
        html = html.replace("${P_NAME}", name);
        html = html.replace("${LINK}", finalLink);
        subject = "Account Activation";
        emailService.send(email, subject, html);
    }

    private String readEmailTemplate(String fileName) throws IOException {
        File file = ResourceUtils.getFile("classpath:email/" + fileName);
       return FileCopyUtils.copyToString(new FileReader(file));
    }
}

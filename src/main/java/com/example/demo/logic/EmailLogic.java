package com.example.demo.logic;

import com.example.demo.common.EmailRequest;
import com.example.demo.exception.EmailException;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Log4j2
@Service
public class EmailLogic {

    private final KafkaTemplate<String, EmailRequest> kafkaEmailTemplate;

    public EmailLogic(KafkaTemplate<String, EmailRequest> kafkaEmailTemplate) {
        this.kafkaEmailTemplate = kafkaEmailTemplate;
    }

    public void sendActivateUserEmail(String email, String name, String token) throws EmailException {

        //prepare content html
        String subject = "";
        String html = null;
        String finalLink = "http://localhost:8080/activate/" + token;
        try {
            html = readEmailTemplate("email-activate-user.html");
        } catch (IOException e) {
            throw EmailException.templateNotFound();
        }
        html = html.replace("${P_NAME}", name);
        html = html.replace("${LINK}", finalLink);
        subject = "Account Activation";

        EmailRequest request = new EmailRequest();
        request.setTo(email);
        request.setSubject(subject);
        request.setContent(html);

        ListenableFuture<SendResult<String, EmailRequest>> future = kafkaEmailTemplate.send("activation-email", request);
        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("Kafka send fail");
                log.error(ex);
            }

            @Override
            public void onSuccess(SendResult<String, EmailRequest> result) {
                log.info("Kafka send success");
                log.info(result);
            }
        });

    }

    private String readEmailTemplate(String fileName) throws IOException {
        File file = ResourceUtils.getFile("classpath:email/" + fileName);
        return FileCopyUtils.copyToString(new FileReader(file));
    }
}

package com.example.demo.model;

import lombok.Data;
import org.apache.tomcat.jni.Time;

import java.util.Date;

@Data
public class ChatMessage {

    private String from;
    private String message;
    private Date date;

    public ChatMessage(){
        this.date = new Date();
    }
}

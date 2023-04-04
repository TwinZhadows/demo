package com.example.demo.model;

import lombok.Data;
<<<<<<< Updated upstream
import org.apache.tomcat.jni.Time;

import java.util.Date;

=======

import java.util.Date;
>>>>>>> Stashed changes
@Data
public class ChatMessage {

    private String from;
    private String message;
    private Date date;

    public ChatMessage(){
<<<<<<< Updated upstream
        this.date = new Date();
    }
}
=======
        date = new Date();
    }

}


>>>>>>> Stashed changes

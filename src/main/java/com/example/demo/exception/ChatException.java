package com.example.demo.exception;

public class ChatException extends BaseException{

    public ChatException(String code){
        super("user." + code);
    }
    public static ChatException accessDenied(){
        return new ChatException("chat.access.denied");
    }

}

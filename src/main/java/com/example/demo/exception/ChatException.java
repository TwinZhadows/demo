package com.example.demo.exception;

public class ChatException extends BaseException{
<<<<<<< Updated upstream

    public ChatException(String code){
        super("user." + code);
    }
    public static ChatException accessDenied(){
        return new ChatException("chat.access.denied");
    }

=======
    public ChatException(String code){
        super("email."+ code);
    }

    public static ChatException AccessDenied(){
        return new ChatException("chat.access.denied");
    }
>>>>>>> Stashed changes
}

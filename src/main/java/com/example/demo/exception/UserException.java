package com.example.demo.exception;

public class UserException extends BaseException{

    public UserException(String code){
        super("user." + code);
    }
    public static UserException requestNull(){
        return new UserException("register.request.null");
    }
    public static UserException emailNull(){
        return new UserException("register.email.null");
    }

    public static UserException createEmailNull(){
        return new UserException("create.email.null");
    }

    public static UserException createNameNull(){
        return new UserException("create.name.null");
    }
    public static UserException createPasswordNull(){
        return new UserException("create.password.null");
    }

    public static UserException loginFailedEmailNotFound(){
        return new UserException("login.fail.email");
    }
    public static UserException loginFailedLoginIncorrect(){
        return new UserException("login.fail.incorrect");
    }
    public static UserException notFound(){
        return new UserException("user.not.Found");
    }
}

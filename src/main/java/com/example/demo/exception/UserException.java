package com.example.demo.exception;

public class UserException extends BaseException {

    public UserException(String code) {
        super("user." + code);
    }

    public static UserException unauthorized() {
        return new UserException("unauthorized");
    }

    //Repository Update
    public static UserException updateNameNull() {
        return new UserException("update.name.null");
    }

    //Register
    public static UserException requestNull() {
        return new UserException("register.request.null");
    }

    public static UserException emailNull() {
        return new UserException("register.email.null");
    }

    //Repository create
    public static UserException createEmailNull() {
        return new UserException("create.email.null");
    }

    public static UserException createNameNull() {
        return new UserException("create.name.null");
    }

    public static UserException createPasswordNull() {
        return new UserException("create.password.null");
    }

    //Login

    public static UserException loginFailedEmailNotFound() {
        return new UserException("login.fail.email");
    }

    public static UserException loginFailedLoginIncorrect() {
        return new UserException("login.fail.incorrect");
    }

    public static UserException notFound() {
        return new UserException("login.user.not.Found");
    }

    //Activation
    public static UserException noToken() {
        return new UserException("activation.no.token");
    }

    public static UserException activationFail() {
        return new UserException("activation.fail");
    }

    public static UserException tokenExpire() {
        return new UserException("activation.user.token.expired");
    }

    public static UserException noActivation() {
        return new UserException("activation.user.not.activated");
    }

    //Reactivation
    public static UserException nullEmail() {
        return new UserException("reactivation.null.email");
    }

    public static UserException emailNotFound() {
        return new UserException("reactivation.email.not.Found");
    }

    public static UserException alreadyActivated() {
        return new UserException("reactivation.already");
    }

}

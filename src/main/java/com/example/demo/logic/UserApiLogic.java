package com.example.demo.logic;

import com.example.demo.Entity.User;
import com.example.demo.exception.BaseException;
import com.example.demo.exception.EmailException;
import com.example.demo.exception.FileException;
import com.example.demo.exception.UserException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.LoginRequest;
import com.example.demo.model.LoginResponse;
import com.example.demo.model.RegisterRequest;
import com.example.demo.model.RegisterResponse;
import com.example.demo.service.TokenService;
import com.example.demo.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Service
public class UserApiLogic {

    private final UserService userService;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailLogic emailLogic;
    private final TokenService tokenService;
     public UserApiLogic(UserService userService, UserMapper mapper, PasswordEncoder passwordEncoder, EmailLogic emailLogic, TokenService tokenService){
         this.userService = userService;
         this.mapper = mapper;
         this.passwordEncoder = passwordEncoder;
         this.emailLogic = emailLogic;
         this.tokenService = tokenService;
     }
    public RegisterResponse register(RegisterRequest request) throws UserException, EmailException {
        User user = userService.create(request.getEmail(), request.getPassword(), request.getName());
        emailLogic.sendActivateUserEmail(request.getEmail(), request.getName(), "TestTken!@#!@ASDASD");
        return mapper.toRegisterResponse(user);
    }

    public String uploadProfilePicture(MultipartFile file)throws FileException{
        if(file == null){
            throw FileException.fileNull();
        }
        if(file.getSize()>1048576*2){
            throw FileException.maxFileSize();
        }
        String contentType = file.getContentType();
        if(contentType == null){
            throw FileException.fileNull();
        }
        List<String> supportedTypes = Arrays.asList("image/jpeg","image/png");
        if(!supportedTypes.contains(contentType)){
            throw FileException.unsupportedFileType();
        }

        // TODO: upload file to DB
        try {
            byte[] bytes = file.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "uploaded";
    }

    public LoginResponse login(LoginRequest request) throws BaseException {
        //validate request
        //verify db
        Optional<User> opt = userService.findByEmail(request.getEmail());

        if (opt.isEmpty()) {
            throw UserException.loginFailedEmailNotFound();
        }
        User user = opt.get();
        if (!userService.matchPassword(request.getPassword(), user.getPassword())) {
            throw UserException.loginFailedLoginIncorrect();
        }
        //TODO return jwt
        LoginResponse response = new LoginResponse();
        response.setToken(tokenService.tokenize(user));
        return response;
    }
}

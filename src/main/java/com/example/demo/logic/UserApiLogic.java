package com.example.demo.logic;

import com.example.demo.Entity.User;
import com.example.demo.exception.BaseException;
import com.example.demo.exception.FileException;
import com.example.demo.exception.UserException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.LoginRequest;
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
    private final TokenService tokenService;
     public UserApiLogic(UserService userService, UserMapper mapper, PasswordEncoder passwordEncoder, TokenService tokenService){
         this.userService = userService;
         this.mapper = mapper;
         this.passwordEncoder = passwordEncoder;
         this.tokenService = tokenService;
     }
    public RegisterResponse register(RegisterRequest request) throws UserException {
        User user = userService.create(request.getEmail(), request.getPass(), request.getName());

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

    public String login(LoginRequest request)throws BaseException {
        //validate request
        //verify db
        Optional<User> opt = userService.findByEmail(request.getEmail());

        if(opt.isEmpty()){
            throw UserException.loginFailedEmailNotFound();
        }
        User user = opt.get();
        if(!userService.matchPassword(request.getPass(), user.getPassword() )){
            throw UserException.loginFailedLoginIncorrect();
        }
        //TODO return jwt

        return tokenService.tokenize(user);
    }
}

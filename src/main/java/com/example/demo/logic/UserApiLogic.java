package com.example.demo.logic;

import com.example.demo.Entity.User;
import com.example.demo.exception.BaseException;
import com.example.demo.exception.EmailException;
import com.example.demo.exception.FileException;
import com.example.demo.exception.UserException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.*;
import com.example.demo.service.TokenService;
import com.example.demo.service.UserService;
import com.example.demo.util.SecurityUtil;
import io.netty.util.internal.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Log4j2
@Service
public class UserApiLogic {
    private final UserService userService;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailLogic emailLogic;
    private final TokenService tokenService;
    private final UserMapper userMapper;

    public UserApiLogic(UserService userService, UserMapper mapper, PasswordEncoder passwordEncoder, EmailLogic emailLogic, TokenService tokenService, UserMapper userMapper) {

        this.userService = userService;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
        this.emailLogic = emailLogic;
        this.tokenService = tokenService;

        this.userMapper = userMapper;
    }

    public RegisterResponse register(RegisterRequest request) throws UserException, EmailException {
        String token = SecurityUtil.generateToken();
        User user = userService.create(request.getEmail(), request.getPassword(), request.getName(), token, setTokenExpireDate());
        sendEmail(user);
        return mapper.toRegisterResponse(user);
    }

    private void sendEmail(User user) throws EmailException {

        String token = user.getActivateToken();
        emailLogic.sendActivateUserEmail(user.getEmail(), user.getName(), token);
        //emailLogic.sendActivateUserEmail(request.getEmail(), request.getName(), "TestTken!@#!@ASDASD");

    }

    public String uploadProfilePicture(MultipartFile file) throws FileException {
        if (file == null) {
            throw FileException.fileNull();
        }
        if (file.getSize() > 1048576 * 2) {
            throw FileException.maxFileSize();
        }
        String contentType = file.getContentType();
        if (contentType == null) {
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
        //verify password
        User user = opt.get();
        if (!userService.matchPassword(request.getPassword(), user.getPassword())) {
            throw UserException.loginFailedLoginIncorrect();
        }
        if (!user.isActivated()) {
            throw UserException.noActivation();
        }
        //verify activated status
        LoginResponse response = new LoginResponse();
        response.setToken(tokenService.tokenize(user));
        return response;
    }

    public ActivateResponse activate(ActivateRequest request) throws BaseException {
        String token = request.getToken();
        if (StringUtil.isNullOrEmpty(token)) {
            throw UserException.noToken();
        }
        Optional<User> opt = userService.findByActivateToken(token);
        if (opt.isEmpty()) {
            throw UserException.activationFail();
        }
        User user = opt.get();
        //user already activated
        if (user.isActivated()) {
            throw UserException.alreadyActivated();
        }

        Date date = user.getTokenExpire();
        Date now = new Date();
        if (now.after(date)) {
            throw UserException.tokenExpire();
            //TODO: resend email or remove user
        }
        user.setActivated(true);
        userService.update(user);
        ActivateResponse response = new ActivateResponse();
        response.setActivated(true);
        return response;
    }

    public void reactivate(ReactivationRequest request) throws BaseException {
        //resend activation email
        String email = request.getEmail();
        if (StringUtil.isNullOrEmpty(email)) {
            throw UserException.nullEmail();
        }
        Optional<User> opt = userService.findByEmail(email);
        if (opt.isEmpty()) {
            throw UserException.emailNotFound();
        }
        User user = opt.get();
        //if user already activated
        if (user.isActivated()) {
            throw UserException.alreadyActivated();
        }
        String token = SecurityUtil.generateToken();
        Date expireDate = setTokenExpireDate();
        user.setActivateToken(token);
        user.setTokenExpire(expireDate);
        userService.update(user);
        sendEmail(user);
    }

    public String refreshToken() throws BaseException {
        Optional<String> opt = SecurityUtil.getCurrentUserId();
        if (opt.isEmpty()) {
            throw UserException.unauthorized();
        }

        String userId = opt.get();

        Optional<User> optUser = userService.findById(userId);
        if (optUser.isEmpty()) {
            throw UserException.notFound();
        }

        User user = optUser.get();
        return tokenService.tokenize(user);
    }

    private Date setTokenExpireDate() {
        //token expire in 30 mins
        Calendar calendar = Calendar.getInstance();
        calendar.add(calendar.MINUTE, 30);
        return calendar.getTime();
    }

    public UserProfile getUserProfile() throws BaseException {
        Optional<String> opt = SecurityUtil.getCurrentUserId();
        if (opt.isEmpty()) {
            throw UserException.unauthorized();
        }

        String userId = opt.get();

        Optional<User> optUser = userService.findById(userId);
        if (optUser.isEmpty()) {
            throw UserException.notFound();
        }

        User user = optUser.get();
        return userMapper.toUserProfile(user);
    }

    public UserProfile updateUserProfile(UpdateUserProfileRequest request) throws UserException {
        Optional<String> opt = SecurityUtil.getCurrentUserId();
        if (opt.isEmpty()) {
            throw UserException.unauthorized();
        }

        if (ObjectUtils.isEmpty(request.getName())) {
            throw UserException.updateNameNull();
        }

        String userId = opt.get();

        User user = userService.updateName(userId, request.getName());
        return userMapper.toUserProfile(user);
    }

//    public void deleteMyAccount() throws UserException {
//
//        Optional<String> opt = SecurityUtil.getCurrentUserId();
//        if (opt.isEmpty()) {
//            throw UserException.unauthorized();
//        }
//        String userId = opt.get();
//        userService.deleteById(userId);
//    }
}

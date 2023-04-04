package com.example.demo.util;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class SecurityUtil {

<<<<<<< Updated upstream
    private SecurityUtil(){

    }
    public static Optional<String> getCurrentUserId() {
        Optional<String> result;
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) {
            result = Optional.empty();
        }

        Authentication authentication = context.getAuthentication();
        if(authentication == null){
=======
    private SecurityUtil() {

    }

    public static Optional<String> getCurrentUserId() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) {
            return Optional.empty();
        }

        Authentication authentication = context.getAuthentication();
        if (authentication == null) {
>>>>>>> Stashed changes
            return Optional.empty();
        }

        Object principal = authentication.getPrincipal();
<<<<<<< Updated upstream
        if(principal == null){
            return Optional.empty();
        }
        String userId = (String)principal;
        return Optional.of(userId);
    }

}
=======
        if (principal == null) {
            return Optional.empty();
        }

        String userId = (String) principal;
        return Optional.of(userId);
    }



}
>>>>>>> Stashed changes

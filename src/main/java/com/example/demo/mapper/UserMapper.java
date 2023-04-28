package com.example.demo.mapper;

import com.example.demo.Entity.User;
import com.example.demo.model.RegisterResponse;
import com.example.demo.model.UserProfile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    //map User to RegisterResponse to ged rid of sensitive info when passing back e.g. password, citizenId
    RegisterResponse toRegisterResponse(User user);

    UserProfile toUserProfile(User user);
}

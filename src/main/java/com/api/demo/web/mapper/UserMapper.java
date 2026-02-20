package com.api.demo.web.mapper;

import com.api.demo.models.UserModel;
import com.api.demo.web.dto.UserCreateRequest;

public class UserMapper {

    public static UserModel toModel(UserCreateRequest request) {
        UserModel user = new UserModel();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        return user;
    }
}
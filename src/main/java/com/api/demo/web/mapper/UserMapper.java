package com.api.demo.web.mapper;

import com.api.demo.models.UserModel;
import com.api.demo.web.dto.CreateUserRequest;

public class UserMapper {

    public static UserModel toModel(CreateUserRequest request) {
        return new UserModel(
                request.getFirstName(),
                request.getLastName(),
                request.getEmail()
        );
    }
}
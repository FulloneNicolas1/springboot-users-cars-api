package com.api.demo.services;
import com.api.demo.exceptions.UserNotFoundException;

import com.api.demo.models.UserModel;
import com.api.demo.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private IUserRepository userRepository;

    public List<UserModel> getUsers() {
        return userRepository.findAll();
    }

    public UserModel saveUser(UserModel user) {
        return userRepository.save(user);
    }

    public Optional<UserModel> getUserById(long id) {
        return userRepository.findById(id);
    }

    public UserModel updateById(UserModel request, long id) {
        UserModel user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));


        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());

        return userRepository.save(user);
    }

    public Boolean deleteUser(long id) {
        try {
            userRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

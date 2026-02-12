package com.api.demo.controllers;

import com.api.demo.models.UserModel;
import com.api.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserModel> getUsers() {
        return this.userService.getUsers();
    }

    @PostMapping
    public UserModel saveUser(@RequestBody UserModel user) {
        return this.userService.saveUser(user);
    }

    @GetMapping(path = "/{id}")
    public Optional<UserModel> getUserById(@PathVariable long id) {
        return this.userService.getUserById(id);
    }

    @PutMapping(path = "/{id}")
    public UserModel updateUserById(@RequestBody UserModel request,@PathVariable long id) {
        return this.userService.updateById(request, id);
    }

    @DeleteMapping(path = "/{id}")
    public String deleteById(@PathVariable("id") long id) {
        boolean ok = this.userService.deleteUser(id);

        if (ok) {
            return "User with Id" + id + "deleted";
        } else {
            return "Error" + id + " not deleted";
        }
    }

}

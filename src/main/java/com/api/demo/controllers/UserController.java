package com.api.demo.controllers;

import com.api.demo.models.UserModel;
import com.api.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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

    @GetMapping("/{id}")
    public ResponseEntity<UserModel> getUserById(@PathVariable long id) {
        UserModel user = userService.getUserById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ResponseEntity.ok(user);
    }


    @PutMapping("/{id}")
    public ResponseEntity<UserModel> updateUserById(@RequestBody UserModel request,
                                                    @PathVariable Long id) {
        UserModel updated = userService.updateById(request, id);
        return ResponseEntity.ok(updated);
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

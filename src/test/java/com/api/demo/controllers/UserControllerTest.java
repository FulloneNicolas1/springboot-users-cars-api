package com.api.demo.controllers;

import com.api.demo.models.UserModel;
import com.api.demo.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnAllUsers() throws Exception {
        UserModel user = new UserModel();
        user.setFirstName("Juan");

        when(userService.getUsers()).thenReturn(List.of(user));

        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Juan"));

        verify(userService).getUsers();
    }

    @Test
    void shouldSaveUser() throws Exception {
        UserModel user = new UserModel();
        user.setFirstName("Ana");

        when(userService.saveUser(any(UserModel.class))).thenReturn(user);

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Ana"));

        verify(userService).saveUser(any(UserModel.class));
    }

    @Test
    void shouldReturnUserById() throws Exception {
        UserModel user = new UserModel();
        user.setFirstName("Pedro");

        when(userService.getUserById(1L)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Pedro"));

        verify(userService).getUserById(1L);
    }

    @Test
    void shouldUpdateUser() throws Exception {
        UserModel user = new UserModel();
        user.setFirstName("Updated");

        when(userService.updateById(any(UserModel.class), eq(1L))).thenReturn(user);

        mockMvc.perform(put("/user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Updated"));

        verify(userService).updateById(any(UserModel.class), eq(1L));
    }

    @Test
    void shouldDeleteUser() throws Exception {
        when(userService.deleteUser(1L)).thenReturn(true);

        mockMvc.perform(delete("/user/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("User with Id1deleted"));

        verify(userService).deleteUser(1L);
    }
    @Test
    void shouldReturn404WhenUserNotFound() throws Exception {
        when(userService.getUserById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/user/1"))
                .andExpect(status().isNotFound());
    }

}

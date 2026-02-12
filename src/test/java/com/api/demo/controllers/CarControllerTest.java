package com.api.demo.controllers;

import com.api.demo.models.CarModel;
import com.api.demo.services.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CarController.class)
class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void listarAutos_shouldReturnCarsForUser() throws Exception {
        Long userId = 1L;

        CarModel car = new CarModel();
        car.setMarca("Toyota");
        car.setModelo("Corolla");
        car.setPatente("ABC123");

        when(carService.obtenerAutosDeUsuario(userId)).thenReturn(List.of(car));

        mockMvc.perform(get("/user/{id}/car", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].marca").value("Toyota"))
                .andExpect(jsonPath("$[0].modelo").value("Corolla"))
                .andExpect(jsonPath("$[0].patente").value("ABC123"));

        verify(carService).obtenerAutosDeUsuario(userId);
        verifyNoMoreInteractions(carService);
    }

    @Test
    void crearAuto_shouldCreateCarForUser() throws Exception {
        Long userId = 2L;

        CarModel request = new CarModel();
        request.setMarca("Ford");
        request.setModelo("Fiesta");
        request.setPatente("XYZ999");

        CarModel response = new CarModel();
        response.setMarca("Ford");
        response.setModelo("Fiesta");
        response.setPatente("XYZ999");

        when(carService.guardarAutoParaUsuario(eq(userId), any(CarModel.class))).thenReturn(response);

        mockMvc.perform(post("/user/{id}/car", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.marca").value("Ford"))
                .andExpect(jsonPath("$.modelo").value("Fiesta"))
                .andExpect(jsonPath("$.patente").value("XYZ999"));

        verify(carService).guardarAutoParaUsuario(eq(userId), any(CarModel.class));
        verifyNoMoreInteractions(carService);
    }
}

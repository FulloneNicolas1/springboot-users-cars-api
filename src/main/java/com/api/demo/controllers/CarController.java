package com.api.demo.controllers;

import com.api.demo.models.CarModel;
import com.api.demo.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class CarController {

    @Autowired
    private CarService carService;

    @PostMapping("/{id}/car")
    public CarModel crearAuto(@PathVariable Long id, @RequestBody CarModel auto) {
        return carService.guardarAutoParaUsuario(id, auto);
    }

    @GetMapping("/{id}/car")
    public List<CarModel> listarAutos(@PathVariable Long id) {
        return carService.obtenerAutosDeUsuario(id);
    }
}

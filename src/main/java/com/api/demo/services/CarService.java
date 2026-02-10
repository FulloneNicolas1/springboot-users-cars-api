package com.api.demo.services;

import com.api.demo.models.CarModel;
import com.api.demo.models.UserModel;
import com.api.demo.repositories.ICarRepository;
import com.api.demo.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    @Autowired
    private ICarRepository carRepository;

    @Autowired
    private IUserRepository userRepository;

    public CarModel guardarAutoParaUsuario(Long userId, CarModel auto) {
        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        auto.setUser(user);
        return carRepository.save(auto);
    }

    public List<CarModel> obtenerAutosDeUsuario(Long userId) {
        return carRepository.findByUserId(userId);
    }
}

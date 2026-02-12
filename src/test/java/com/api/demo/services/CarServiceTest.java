package com.api.demo.services;

import com.api.demo.models.CarModel;
import com.api.demo.models.UserModel;
import com.api.demo.repositories.ICarRepository;
import com.api.demo.repositories.IUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @Mock
    private ICarRepository carRepository;

    @Mock
    private IUserRepository userRepository;

    @InjectMocks
    private CarService carService;

    @Test
    void guardarAutoParaUsuario_shouldSetUserAndSaveCar_whenUserExists() {

        Long userId = 1L;

        UserModel user = new UserModel();
        CarModel auto = new CarModel();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(carRepository.save(any(CarModel.class))).thenAnswer(inv -> inv.getArgument(0));

        CarModel result = carService.guardarAutoParaUsuario(userId, auto);

        assertNotNull(result);


        ArgumentCaptor<CarModel> carCaptor = ArgumentCaptor.forClass(CarModel.class);
        verify(carRepository).save(carCaptor.capture());

        CarModel savedCar = carCaptor.getValue();

        verify(userRepository).findById(userId);
    }

    @Test
    void guardarAutoParaUsuario_shouldThrow_whenUserNotFound() {

        Long userId = 99L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> carService.guardarAutoParaUsuario(userId, new CarModel()));

        assertEquals("Usuario no encontrado", ex.getMessage());

        verify(userRepository).findById(userId);
        verifyNoInteractions(carRepository);
    }

    @Test
    void obtenerAutosDeUsuario_shouldReturnCarsFromRepository() {

        Long userId = 7L;
        List<CarModel> cars = List.of(new CarModel(), new CarModel());

        when(carRepository.findByUserId(userId)).thenReturn(cars);


        List<CarModel> result = carService.obtenerAutosDeUsuario(userId);


        assertNotNull(result);
        assertEquals(2, result.size());
        assertSame(cars, result);

        verify(carRepository).findByUserId(userId);
        verifyNoInteractions(userRepository);
    }
}

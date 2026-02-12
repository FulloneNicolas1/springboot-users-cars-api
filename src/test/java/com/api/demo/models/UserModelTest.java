package com.api.demo.models;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserModelTest {

    @Test
    void shouldSetAndGetBasicFields() {
        UserModel user = new UserModel();

        user.setFirstName("Juan");
        user.setLastName("Perez");
        user.setEmail("juan@mail.com");

        assertNull(user.getId()); // sin persistir, id debe ser null
        assertEquals("Juan", user.getFirstName());
        assertEquals("Perez", user.getLastName());
        assertEquals("juan@mail.com", user.getEmail());
    }

    @Test
    void cars_shouldBeInitializedAndNotNull() {
        UserModel user = new UserModel();

        assertNotNull(user.getCars());
        assertTrue(user.getCars().isEmpty());
    }

    @Test
    void shouldSetAndGetCarsList() {
        UserModel user = new UserModel();

        List<CarModel> cars = new ArrayList<>();
        cars.add(new CarModel());
        cars.add(new CarModel());

        user.setCars(cars);

        assertNotNull(user.getCars());
        assertEquals(2, user.getCars().size());
        assertSame(cars, user.getCars());
    }
}

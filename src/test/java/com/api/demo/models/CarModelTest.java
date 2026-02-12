package com.api.demo.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarModelTest {

    @Test
    void shouldSetAndGetBasicFields() {
        var car = new CarModel();

        car.setMarca("Toyota");
        car.setModelo("Corolla");
        car.setPatente("ABC123");

        assertNull(car.getId());
        assertEquals("Toyota", car.getMarca());
        assertEquals("Corolla", car.getModelo());
        assertEquals("ABC123", car.getPatente());
    }

    @Test
    void shouldSetAndGetUser() {
        CarModel car = new CarModel();
        UserModel user = new UserModel();

        car.setUser(user);

        assertNotNull(car.getUser());
        assertSame(user, car.getUser());
    }

    @Test
    void hola_shouldReturnOne() {
        CarModel car = new CarModel();

        assertEquals(1, car.hola());
    }
}

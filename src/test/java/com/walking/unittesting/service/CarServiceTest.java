package com.walking.unittesting.service;

import com.walking.unittesting.model.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CarServiceTest {
    private static final Car CAR_1 = new Car("RR-111-RR", 2015, "yellow", true);

    private CarService carService;

    @BeforeEach
    void setUp() {
        carService = new CarService();
    }

    @Test
    void add_success() {
//        when
        var actual = carService.add(CAR_1);

//        then
        assertEquals(CAR_1, actual);
        assertEquals(1, carService.findAll().size());
    }

    @Test
    void add_carExists_failed() {
//        given
        carService.add(CAR_1);

//        when
        Executable actual = () -> carService.add(CAR_1);

//        then
        assertThrows(RuntimeException.class, actual);
        assertEquals(1, carService.findAll().size());
    }

    @ParameterizedTest
    @MethodSource("carSource")
    void add_differentCars_success(String number, int year, String color, boolean actualTechnicalInspection) {
//        given
        var car = new Car(number, year, color, actualTechnicalInspection);

//        when
        var actual = carService.add(car);

//        then
        assertEquals(car, actual);
        assertEquals(1, carService.findAll().size());
    }

    @RepeatedTest(5)
    void delete_success() {
//        when
        Executable actual = () -> carService.delete(CAR_1);

//        then
        assertDoesNotThrow(actual);
    }

    static Stream<Arguments> carSource() {
        return Stream.of(
                Arguments.of("RR-111-RR", 2015, "yellow", true),
                Arguments.of("RR-222-RR", 2016, "yellow", true),
                Arguments.of("RR-333-RR", 2017, "yellow", true)
        );
    }
}
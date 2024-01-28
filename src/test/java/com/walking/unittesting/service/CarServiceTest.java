package com.walking.unittesting.service;

import com.walking.unittesting.model.Car;
import com.walking.unittesting.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CarServiceTest {
    private static final Car CAR_1 = new Car("RR-111-RR", 2015, "yellow", true);

    private CarService carService;
    private CarRepository carRepository;

    @BeforeEach
    void setUp() {
        carRepository = Mockito.mock(CarRepository.class);
        carService = new CarService(carRepository);
    }

    @Test
    void add_success() {
//        given
        Mockito.doReturn(CAR_1)
                .when(carRepository)
                .create(CAR_1);

//        when
        var actual = carService.add(CAR_1);

//        then
        assertEquals(CAR_1, actual);

        Mockito.verify(carRepository)
                .create(CAR_1);
    }

    @Test
    void add_carExists_failed() {
//        given
        Mockito.doReturn(true)
                .when(carRepository)
                .isExists(Mockito.any());

//        when
        Executable actual = () -> carService.add(CAR_1);

//        then
        assertThrows(RuntimeException.class, actual);

        Mockito.verify(carRepository, Mockito.never())
                .create(Mockito.any());
    }

    @ParameterizedTest
    @MethodSource("carSource")
    void add_differentCars_success(String number, int year, String color, boolean actualTechnicalInspection) {
//        given
        var car = new Car(number, year, color, actualTechnicalInspection);

        Mockito.doReturn(car)
                .when(carRepository)
                .create(car);

//        when
        var actual = carService.add(car);

//        then
        assertEquals(car, actual);

        Mockito.verify(carRepository)
                .create(car);
    }

    @RepeatedTest(5)
    void delete_success() {
//        when
        Executable actual = () -> carService.delete(CAR_1);

//        then
        assertDoesNotThrow(actual);

        Mockito.verify(carRepository)
                .delete(CAR_1);
    }

    static Stream<Arguments> carSource() {
        return Stream.of(
                Arguments.of("RR-111-RR", 2015, "yellow", true),
                Arguments.of("RR-222-RR", 2016, "yellow", true),
                Arguments.of("RR-333-RR", 2017, "yellow", true)
        );
    }
}
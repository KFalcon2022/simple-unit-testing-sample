package com.walking.unittesting.service;


import com.walking.unittesting.model.Car;
import com.walking.unittesting.repository.CarRepository;

import java.util.List;

public class CarService {
    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public synchronized List<Car> findAll() {
        return new CarRepository().findAll();
    }

    public synchronized Car add(Car car) {
        if (carRepository.isExists(car)) {
            throw new RuntimeException("Car exists");
        }

        return carRepository.create(car);
    }

    public synchronized void delete(Car car) {
        carRepository.delete(car);
    }
}

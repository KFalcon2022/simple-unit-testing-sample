package com.walking.unittesting.service;


import com.walking.unittesting.model.Car;
import com.walking.unittesting.model.CarIdentifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CarService {
    private final Map<CarIdentifier, Car> cars = new ConcurrentHashMap<>();

    public synchronized List<Car> findAll() {
        return new ArrayList<>(cars.values());
    }

    public synchronized Car add(Car car) {
        if (cars.containsKey(car.getIdentifier())) {
            throw new RuntimeException("Car exists");
        }

        cars.put(car.getIdentifier(), car);

        return car;
    }

    public synchronized void delete(Car car) {
        cars.remove(car.getIdentifier());
    }
}

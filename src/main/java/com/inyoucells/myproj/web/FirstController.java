package com.inyoucells.myproj.web;

import com.inyoucells.myproj.models.Car;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class FirstController {

    int cardId = 0;

    Set<Car> cars = new HashSet<>();

    @GetMapping(path = "/car")
    List<Car> getCars() {
        return new ArrayList<>(cars);
    }

    @DeleteMapping(path = "/car")
    void removeCar(long id) {
        cars.remove(new Car(id));
    }

    @PostMapping(path = "/car")
    int addCar(@RequestBody Car car) {
        cardId++;
        cars.add(new Car(cardId, car.getBrand(), car.getYear(), car.isUsed(), car.getHorsepower()));
        return cardId;
    }
}

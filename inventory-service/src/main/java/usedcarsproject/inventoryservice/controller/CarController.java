package usedcarsproject.inventoryservice.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import usedcarsproject.inventoryservice.dto.CarDto;
import usedcarsproject.inventoryservice.service.CarService;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public List<CarDto> getAllAvailableCars() {
        return carService.getAllAvailableCars();
    }

    @GetMapping("/{id}")
    public CarDto getCarById(@PathVariable Long id) {
        return carService.getCarById(id);
    }

    @PostMapping
    public CarDto addNewCar(@Valid @RequestBody CarDto carDto) {
        CarDto savedCarDto = carService.addNewCar(carDto);
        return savedCarDto;
    }

    @PutMapping("/{id}")
    public CarDto updateCar(@PathVariable Long id, @Valid @RequestBody CarDto carDto) {
        return carService.updateCar(id, carDto);
    }

    @DeleteMapping("/{id}")
    public Map<String, String> deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
        return Map.of("message", "Car deleted successfully");
    }

}

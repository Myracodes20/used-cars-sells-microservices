package usedcarsproject.inventoryservice.service;

import usedcarsproject.inventoryservice.dto.CarDto;

import java.util.List;

public interface CarService {
    List<CarDto> getAllAvailableCars();
    CarDto getCarById(Long id);
    CarDto addNewCar(CarDto carDto);
    CarDto updateCar(Long id, CarDto carDto);
    void deleteCar(Long id);
}

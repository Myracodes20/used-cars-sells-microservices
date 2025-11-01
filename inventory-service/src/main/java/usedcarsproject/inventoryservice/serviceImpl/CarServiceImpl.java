package usedcarsproject.inventoryservice.serviceImpl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import usedcarsproject.inventoryservice.dto.CarDto;
import usedcarsproject.inventoryservice.entity.Car;
import usedcarsproject.inventoryservice.exception.ResourceNotFoundException;
import usedcarsproject.inventoryservice.repository.CarRepository;
import usedcarsproject.inventoryservice.service.CarService;
import usedcarsproject.inventoryservice.util.CarStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final ModelMapper modelMapper;
    public CarServiceImpl(CarRepository carRepository, ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.carRepository = carRepository;
    }
    @Override
    public List<CarDto> getAllAvailableCars() {
        return carRepository
                .findByCarStatus(CarStatus.AVAILABLE)
                .stream()
                .map(car -> modelMapper.map(car, CarDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public CarDto getCarById(Long id) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + id));
        return modelMapper.map(car, CarDto.class);
    }

    @Override
    public CarDto addNewCar(CarDto carDto) {
        // For create: ignore client-provided id to let JPA generate it
        carDto.setId(null);
        // Default status to AVAILABLE if not provided
        if (carDto.getCarStatus() == null) {
            carDto.setCarStatus(CarStatus.AVAILABLE);
        }
        // Use provided dateListed if present; otherwise set to today
        if (carDto.getDateListed() == null) {
            carDto.setDateListed(LocalDate.now());
        }
        Car savedCar = carRepository.save(modelMapper.map(carDto, Car.class));
        return modelMapper.map(savedCar, CarDto.class);
    }

    @Override
    public CarDto updateCar(Long id, CarDto carDto) {
        Car existingCar = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + id));
        // Map incoming changes onto the managed entity to preserve ID and any non-provided fields
        modelMapper.map(carDto, existingCar);
        Car updatedCar = carRepository.save(existingCar);
        return modelMapper.map(updatedCar, CarDto.class);
    }

    @Override
    public void deleteCar(Long id) {
        // Check if the car exists before trying to delete it
        if (!carRepository.existsById(id)) {
            throw new ResourceNotFoundException("Car not found with id: " + id);
        }
        carRepository.deleteById(id);
    }
}

package usedcarsproject.inventoryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import usedcarsproject.inventoryservice.entity.Car;
import usedcarsproject.inventoryservice.util.CarStatus;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByCarStatus(CarStatus carStatus);
}

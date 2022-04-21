package tqsdemo.CarService;


import java.util.List;
import java.util.Optional;

public interface ICarService {
 List<Car> getAllCars();
 Optional<Car> getCarById(Long id);
 Car createCar(Car c);
 
 
}

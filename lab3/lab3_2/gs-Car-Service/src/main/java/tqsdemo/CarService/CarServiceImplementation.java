package tqsdemo.CarService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarServiceImplementation implements ICarService {
  
  @Autowired
  CarRepository repository;
  
  @Override public List<Car> getAllCars() {
    return repository.findAll();
  }
  
  @Override public Optional<Car> getCarById( Long id ) {
    return repository.findById( id );
  }
  
  @Override public Car createCar( Car c ) {
    return repository.save( c );
    
  }
}

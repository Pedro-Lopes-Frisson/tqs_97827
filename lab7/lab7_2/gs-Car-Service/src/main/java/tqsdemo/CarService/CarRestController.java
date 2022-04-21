package tqsdemo.CarService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@RequestMapping("/api")
@RestController public class CarRestController {
  
  @Autowired
  CarServiceImplementation service;
  
  @PostMapping("/car")
  ResponseEntity<Car> createCar( @RequestBody @Valid Car c ) {
    return new ResponseEntity<Car>( service.createCar( c ), HttpStatus.CREATED );
  }
  
  @GetMapping("/car")
  List<Car> getAllCars() {
    return service.getAllCars();
  }
  
  
  @GetMapping("/car/{id}")
  public ResponseEntity<Car> getCarByID( @PathVariable(value = "id") Long id ) {
    Optional<Car> c = service.getCarById( 1L );
    return c.map( car -> new ResponseEntity<>( car, HttpStatus.OK ) )
            .orElseGet( () -> new ResponseEntity<>( HttpStatus.NOT_FOUND ) );
    
  }
  
}

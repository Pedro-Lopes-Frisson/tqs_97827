package tqsdemo.CarService;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface CarRepository extends JpaRepository<Car, Long> {
  Car findByCarID( Long l );
  
  List<Car> findAll();
}

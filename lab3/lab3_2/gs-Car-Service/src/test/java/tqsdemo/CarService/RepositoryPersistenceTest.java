package tqsdemo.CarService;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(properties = {
  "spring.datasource.url=jdbc:h2:mem:testdb",
  "spring.datasource.driverClassName=org.h2.Driver",
  "spring.datasource.username=sa",
  "spring.datasource.password=password",
  "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
  "spring.jpa.hibernate.ddl-auto=validate",
  "spring.jpa.hibernate.ddl-auto=create-drop"
})
class RepositoryPersistenceTest {
  
  
  @Autowired
  private TestEntityManager entityManager;
  
  @Autowired
  private CarRepository carRepository;
  
  @Test
  public void whenPersistSeat_retrieveSeat_getSeat() {
    Car alex = new Car( "Seat", "Ibiza" );
    entityManager.persistAndFlush( alex ); //ensure data is persisted at this point
    
    Optional<Car> found = carRepository.findById( alex.getCarID() );
    assertThat( found.isPresent() ).isTrue();
    assertThat( found.get() ).isEqualTo( alex );
  }
  
  @Test
  public void whenInvalidCarName_thenReturnNull() {
    Optional<Car> fromDb = carRepository.findById( - 1L );
    assertThat( fromDb.isPresent() ).isFalse();
  }
  
  @Test
  public void whenFindCarByExistingId_thenReturnCar() {
    Car c = new Car( "test", "testS" );
    entityManager.persistAndFlush( c );
    
    Car fromDb = carRepository.findById( c.getCarID() ).orElse( null );
    assertThat( fromDb ).isNotNull();
    assertThat( fromDb.getMaker() ).isEqualTo( c.getMaker() );
  }
  
  @Test
  public void whenInvalidId_thenReturnNull() {
    Car fromDb = carRepository.findById( - 111L ).orElse( null );
    assertThat( fromDb ).isNull();
  }
  
  @Test
  public void givenSetOfCars_whenFindAll_thenReturnAllCars() {
    Car alex = new Car( "Toyota", "Prius" );
    Car ron = new Car( "Nissan", "Almera" );
    Car bob = new Car( "Opel", "Corsa B" );
    
    entityManager.persist( alex );
    entityManager.persist( bob );
    entityManager.persist( ron );
    entityManager.flush();
    
    List<Car> allCars = carRepository.findAll();
    
    assertThat( allCars ).hasSize( 3 ).extracting( Car::getMaker )
                         .containsOnly( alex.getMaker(), ron.getMaker(), bob.getMaker() );
  }
  
}

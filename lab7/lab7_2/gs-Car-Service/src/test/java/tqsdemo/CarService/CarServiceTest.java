package tqsdemo.CarService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class CarServiceTest {
  
  Car seat;
  Car toyota;
  Car citroen;
  
  @Mock(lenient = true)
  private CarRepository repository;
  @InjectMocks
  private CarServiceImplementation service;
  
  @BeforeEach
  public void setUp() {
    
    seat = new Car( "Seat", "Ibiza" );
    toyota = new Car( "Toyota", "Prius" );
    citroen = new Car( "Citroen", "C3" );
    
    seat.setCarID( 1L );
    toyota.setCarID( 2L );
    citroen.setCarID( 3L );
    
    List<Car> allcars = Arrays.asList( seat, toyota, citroen );
    
    Mockito.when( repository.findById( seat.getCarID() ) ).thenReturn( Optional.of( seat ) );
    Mockito.when( repository.findById( toyota.getCarID() ) ).thenReturn( Optional.of( toyota ) );
    Mockito.when( repository.findById( 50000L) ).thenReturn( null );
    Mockito.when( repository.findById( seat.getCarID() ) ).thenReturn( Optional.of( seat ) );
    Mockito.when( repository.findAll() ).thenReturn( allcars );
    Mockito.when( repository.findById( - 99L ) ).thenReturn( Optional.empty() );
  }
  
  @Test
  public void whenValidID_ThenCarIsFound() {
    String maker = "Seat";
    Optional<Car> c = service.getCarById( seat.getCarID() );
    assertThat( c.isPresent() ).isTrue();
    assertThat( c.get().getMaker() ).isEqualTo( maker );
    verifyFindByIdIsCalledOnce();
  }
  
  @Test
  public void whenInValidID_ThenCarIsNotFound() {
    Optional<Car> c = service.getCarById(50000L);
    assertThat( c ).isNull();
    verifyFindByIdIsCalledOnce();
  }
  
  @Test
  public void given3Employees_whengetAll_thenReturn3Records() {
    
    Car seat = new Car( "Seat", "Ibiza" );
    
    Car toyota = new Car( "Toyota", "Prius" );
    Car citroen = new Car( "Citroen", "C3" );
    
    List<Car> allCars = service.getAllCars();
    verifyFindAllCarIsCalledOnce();
    assertThat( allCars ).hasSize( 3 ).extracting( Car::getMaker ).contains( seat.getMaker(), toyota.getMaker(),
      citroen.getMaker() );
  }
  
  
  private void verifyFindByIdIsCalledOnce() {
    Mockito.verify( repository, VerificationModeFactory.times( 1 ) ).findById( Mockito.anyLong() );
  }
  
  private void verifyFindAllCarIsCalledOnce() {
    Mockito.verify( repository, VerificationModeFactory.times( 1 ) ).findAll();
  }
}

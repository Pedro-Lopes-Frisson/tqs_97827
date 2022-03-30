package tqsdemo.CarService;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarRestController.class) public class CarControllerTest {
  
  @Autowired private MockMvc mvc;
  @MockBean private CarServiceImplementation service;
  
  public static String asJsonString( final Object obj ) {
    try {
      return new ObjectMapper().writeValueAsString( obj );
    } catch (Exception e) {
      throw new RuntimeException( e );
    }
  }
  
  @BeforeEach public void setUp() throws Exception {
  
  }
  
  @Test public void whenPostCar_thenCreateCar() throws Exception {
    Car toyota = new Car("Toyota", "Prius" );
    
    //given(service.save(Mockito.any())).willReturn(alex);
    when( service.createCar( Mockito.any() ) ).thenReturn( toyota );
    
    mvc.perform( post( "/api/car" ).contentType( MediaType.APPLICATION_JSON ).content( asJsonString( toyota ) ) )
       .andExpect( status().isCreated() ).andExpect( jsonPath( "$.maker", is( toyota.getMaker() ) ) );
    
    verify( service, times( 1 ) ).createCar( Mockito.any() );
    
  }
  
  @Test public void givenCars_whenGetCars_thenReturnJsonArray() throws Exception {
    Car nissan = new Car(  "Nissan", "Almera GT" );
    Car renault = new Car("Renault", "Clio Phase 2" );
    Car opel = new Car( "Opel", "Corsa B" );
    
    List<Car> allCars = Arrays.asList( nissan, renault, opel );
    
    given( service.getAllCars() ).willReturn( allCars );
    
    mvc.perform( get( "/api/car" ).contentType( MediaType.APPLICATION_JSON ) ).andExpect( status().isOk() )
       .andExpect( jsonPath( "$", hasSize( 3 ) ) ).andExpect( jsonPath( "$[0].maker", is( nissan.getMaker() ) ) )
       .andExpect( jsonPath( "$[1].maker", is( renault.getMaker() ) ) )
       .andExpect( jsonPath( "$[2].maker", is( opel.getMaker() ) ) );
    verify( service, VerificationModeFactory.times( 1 ) ).getAllCars();
    
  }
  
  @Test public void givenCars_whenGetCarById_ThenReturnCarWithID() throws Exception {
    Car nissan = new Car( 1L, "Nissan", "Almera GT" );
    
    given( service.getCarById( Mockito.any() ) ).willReturn( Optional.of( nissan ) );
    
    mvc.perform( get( "/api/car/1" ) ).andExpect( status().isOk() )
       .andExpect( jsonPath( "$.maker", is( nissan.getMaker() ) ) );
    
    verify( service, VerificationModeFactory.times( 1 ) ).getCarById( Mockito.any() );
    
  }
}

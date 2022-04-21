package tqsdemo.CarService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = CarApplication.class)
@AutoConfigureMockMvc
// adapt AutoConfigureTestDatabase with TestPropertySource to use a real database
@TestPropertySource(properties = {
  "spring.datasource.url=jdbc:mysql://localhost:33060/tqsdemo",
  "spring.datasource.driver-class-name=com.mysql.jdbc.Driver",
  "spring.jpa.hibernate.ddl-auto=create-drop",
  "spring.datasource.username=demo",
  "spring.datasource.password=demo"
})

public class CarControllerTestIT {
  
  @Autowired
  private MockMvc mvc;
  
  @Autowired
  private CarRepository repository;
  
  @AfterEach
  public void resetDb() {
    repository.deleteAll();
  }
  
  @Test
  void whenValidInput_thenCreateCar() throws IOException, Exception {
    Car seat = new Car("Seat", "Ibiza");
    
    mvc.perform(post("/api/car").contentType( MediaType.APPLICATION_JSON).content(asJsonString(seat)));
    
    List<Car> found = repository.findAll();
    
    assertThat(found).extracting(Car::getMaker).containsOnly(seat.getMaker());
  }
  
  @Test
  void givenCar_whenGetCar_thenStatus200() throws Exception {
    Car c = new Car("Nissan", "Almera");
    repository.saveAndFlush(c);
  
    Car c1 = new Car("Citroen", "C3");
    repository.saveAndFlush(c1);
    
    mvc.perform(get("/api/car").contentType(MediaType.APPLICATION_JSON))
       .andDo(print())
       .andExpect(status().isOk())
       .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
       .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))))
       .andExpect(jsonPath("$[0].maker", is("Nissan")))
       .andExpect(jsonPath("$[1].maker", is("Citroen")));
  }
  
  @Test
  void givenCar_whenGetCarByID_thenStatus200() throws Exception {
  
    Car c = new Car("Nissan", "Almera");
    repository.saveAndFlush(c);
    long i = repository.findAll().get( 0 ).getCarID();
    mvc.perform(get("/api/car/" + i ).contentType(MediaType.APPLICATION_JSON))
       .andDo(print())
       .andExpect(status().isOk())
       .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
       .andExpect(jsonPath("$.maker", is("Nissan")));
  }
  
  
  
  public static String asJsonString( final Object obj ) {
    try {
      return new ObjectMapper().writeValueAsString( obj );
    } catch (Exception e) {
      throw new RuntimeException( e );
    }
  }
  
  
}

package pedrolopes.tqs.covid19trackingservice;


import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import pedrolopes.tqs.covid19trackingservice.repository.CacheRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = Covid19TrackingServiceApplication.class)
@AutoConfigureMockMvc
// adapt AutoConfigureTestDatabase with TestPropertySource to use a real database
@TestPropertySource(properties = {
  "spring.datasource.url=jdbc:mysql://localhost:33060/tqsdemo",
  "spring.datasource.driver-class-name=com.mysql.jdbc.Driver",
  "spring.jpa.hibernate.ddl-auto=create-drop",
  "spring.datasource.username=demo",
  "spring.datasource.password=demo"
})

public class RestControllerTestIT {
  @Autowired
  private MockMvc mvc;
  
  @Autowired
  private CacheRepository repository;
  
  @AfterEach
  public void resetDb() {
    repository.deleteAll();
  }
  
  
}

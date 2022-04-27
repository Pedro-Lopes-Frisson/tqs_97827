package pedrolopes.tqs.covid19trackingservice;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@CucumberContextConfiguration
@SpringBootTest(classes = Covid19TrackingServiceApplication.class,
  webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SpringBootCocumberTest {
}

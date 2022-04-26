package pedrolopes.tqs.covid19trackingservice;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import pedrolopes.tqs.covid19trackingservice.repository.CacheRepository;

import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = Covid19TrackingServiceApplication.class)
@AutoConfigureMockMvc
public class RestControllerTestIT {
  @Autowired
  private MockMvc mvc;
  
  @Autowired
  private CacheRepository repository;
  
  @AfterEach
  public void resetDb() {
    repository.deleteAll();
  }
  @Test
  void whenValidInput_thenReturnResponseData() throws Exception {
    mvc.perform(get("/api/reports/total?date=2020-04-11").contentType(MediaType.APPLICATION_JSON))
       .andDo(print()).andExpect( status().isOk() );
    
  }
  
}

package pedrolopes.tqs.covid19trackingservice;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pedrolopes.tqs.covid19trackingservice.repository.CacheRepository;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


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
  void whenValidInput_thenReturnResponseDataShouldBeSummaryReport() throws Exception {
    mvc.perform( get( "/api/reports/total?date=2020-04-11" ).contentType( MediaType.APPLICATION_JSON ) )
       .andDo( print() ).andExpect( status().isOk() )
       .andExpect( content().contentTypeCompatibleWith( MediaType.APPLICATION_JSON ) )
       .andExpect( jsonPath( "$.data.date", is( "2020-04-11" ) ) )
       .andExpect( jsonPath( "$.data.confirmed", is( 1771514 ) ) )
       .andExpect( jsonPath( "$.data.fatality_rate", is( 0.0612 ) ) );
    
  }
  
  @Test
  void whenValidInput_thenReturnResponseDataShouldBeRootReportCompatible() throws Exception {
    mvc.perform( get( "/api/reports?city=Autauga&date=2020-04-11" ).contentType( MediaType.APPLICATION_JSON ) )
       .andDo( print() ).andExpect( status().isOk() )
       .andExpect( content().contentTypeCompatibleWith( MediaType.APPLICATION_JSON ) )
       .andExpect( jsonPath( "$.data[0].date", is( "2020-04-11" ) ) )
       .andExpect( jsonPath( "$.data[0].deaths", is( 92 ) ) )
       .andExpect( jsonPath( "$.data[0].fatality_rate", is( 0.0286 ) ) )
       .andExpect( jsonPath( "$.data[0].region.cities[0].name", is( "Autauga" ) ) )
       .andExpect( jsonPath( "$.data[0].region.iso", is( "USA" ) ) )
       .andExpect( jsonPath( "$.data[0].region.province", is( "Alabama" ) ) );
    
  }
  
  @Test
  void whenInValidCity_AnEmptyDataResponseShouldBeReturned() throws Exception {
    mvc.perform( get( "/api/reports?city=NotExistent&date=2020-04-11" ).contentType( MediaType.APPLICATION_JSON ) )
       .andDo( print() ).andExpect( status().isOk() )
       .andExpect( content().contentTypeCompatibleWith( MediaType.APPLICATION_JSON ) )
       .andExpect( jsonPath( "$.data", hasSize( 0 ) ) );
    
  }
  
  
  @Test
  void whenInvalidDateErrorMessageShouldBeReturnedForWorldReport() throws Exception {
    mvc.perform( get( "/api/reports/total?date=2-04-11" ).contentType( MediaType.APPLICATION_JSON ) )
       .andDo( print() ).andExpect( status().is( 422 ) )
       .andExpect( content().contentTypeCompatibleWith( MediaType.APPLICATION_JSON ) )
       .andExpect( jsonPath( "$.error", is( "Request resulted in an invalid operation" ) ) );
    
  }
  
  @Test
  void whenInvalidDateErrorMessageShouldBeReturnedForEndPointSpecificReport() throws Exception {
    mvc.perform( get( "/api/reports?city=Autauga&date=2-04-11" ).contentType( MediaType.APPLICATION_JSON ) )
       .andDo( print() ).andExpect( status().is( 422 ) )
       .andExpect( content().contentTypeCompatibleWith( MediaType.APPLICATION_JSON ) )
       .andExpect( jsonPath( "$.error", is( "Request resulted in an invalid operation" ) ) );
    
  }
  
  
}

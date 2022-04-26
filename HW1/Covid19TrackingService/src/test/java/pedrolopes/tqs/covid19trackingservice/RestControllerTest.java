package pedrolopes.tqs.covid19trackingservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import pedrolopes.tqs.covid19trackingservice.controller.RestControllerCovid19;
import pedrolopes.tqs.covid19trackingservice.services.Covid19Service;
import pedrolopes.tqs.covid19trackingservice.models.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;


@WebMvcTest(RestControllerCovid19.class)  class RestControllerTest {
  @MockBean private Covid19Service service;
  
  @Autowired private MockMvc mvc;
  
  @BeforeEach  void setUp() throws Exception {
    RestAssuredMockMvc.mockMvc( mvc );
  }
  
  @AfterEach  void tearDown() throws Exception {
    RestAssuredMockMvc.reset();
  }
  
  @Test  void testEndpointFor200StatusCodeAnd1callToServiceGetCacheWillResultIn1CallToServiceGetCache() {
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode objectNode = objectMapper.createObjectNode();
    objectNode.put( "hits", 0 );
    objectNode.put( "misses", 0 );
    objectNode.put( "requests", 0 );
    when( service.getCache() ).thenReturn( ResponseEntity.ok( objectNode ) );
    given().get( "/api/cache" ).then().log().body().assertThat().status( HttpStatus.OK ).and()
           .contentType( ContentType.JSON ).and().body( "hits", is( 0 ) ).and().body( "misses", is( 0 ) ).and()
           .body( "requests", is( 0 ) );
    verify( service, times( 1 ) ).getCache();
  }
  
  @Test  void testGetSpecificPlaceDataForAValidDate() {
    ReportData reportData =
      new ReportData( "2020-04-11", 3217, 92, 0, 270, 12,
        0, "2020-04-11 22:45:33", 3125, 258, 0.0286
        , new RegionReportData() );
    ArrayList<ReportData> arrayListData = new ArrayList<>();
    arrayListData.add( reportData );
    RootReport rootReport = new RootReport( arrayListData );
    
    when( service.getReportForCityAndDate( Mockito.any(), Mockito.any() ) ).thenReturn(
      ResponseEntity.ok( rootReport ) );
    
    given().
      param( "date", "2020-04-11" ).
      param( "city", "Autauga" )
      .get( "api/reports" )
      .then()
      .log().body()
      .assertThat().statusCode( 200 ).and()
      .contentType( ContentType.JSON ).and()
      .body( "data[0].date", is( "2020-04-11" ) );
    
    verify( service, times( 1 ) ).getReportForCityAndDate( Mockito.any(), Mockito.any() );
  }
  
  @Test  void testGetSpecificPlaceDataForAnEmptyDateAndValidCity() {
    
    String todaysDate =
      LocalDateTime.now().format( DateTimeFormatter.ofPattern( "yyyy-MM-dd" ) );
    
    ReportData reportData =
      new ReportData( todaysDate, 3217, 92, 0, 270, 12,
        0, "2020-04-11 22:45:33", 3125, 258, 0.0286
        , new RegionReportData() );
    ArrayList<ReportData> arrayListData = new ArrayList<>();
    arrayListData.add( reportData );
    RootReport rootReport = new RootReport( arrayListData );
    
    when( service.getReportForCityAndDate( Mockito.any(), Mockito.any() ) ).thenReturn(
      ResponseEntity.ok( rootReport ) );
    
    given().
      param( "city", "Autauga" )
      .get( "api/reports" )
      .then()
      .log().body()
      .assertThat().statusCode( 200 ).and()
      .contentType( ContentType.JSON ).and()
      .body( "data[0].date", is( todaysDate ) );
    
    verify( service, times( 1 ) ).getReportForCityAndDate( Mockito.any(), eq( todaysDate ) );
  }
  
  @Test  void testGetReportForInvalidPlace() {
    ArrayList<ReportData> arrayListData = new ArrayList<>();
    RootReport rootReport = new RootReport( arrayListData );
    
    when( service.getReportForCityAndDate( Mockito.any(), Mockito.any() ) ).thenReturn(
      ResponseEntity.ok( rootReport ) );
    
    given().
      param( "date", "2020-04-10" ).
      param( "city", "Neverland" )
      .get( "/api/reports" )
      .then()
      .log().body()
      .assertThat().status( HttpStatus.OK )
      .and().body( "data", hasSize( 0 ) );
    
    verify( service, times( 1 ) ).getReportForCityAndDate( Mockito.any(), Mockito.any() );
  }
  
  @Test  void testGetSpecificPlaceDataForAnInvalidDateAndValidCity() {
    when( service.getReportForCityAndDate( Mockito.any(), Mockito.any() ) ).thenReturn(
      ResponseEntity.unprocessableEntity().body( "{" +
        "   \"error\" : {" +
        "      \"date\" : [" +
        "         \"The date does not match the format Y-m-d.\"" +
        "      ]" +
        "   }" +
        "}" ) );
    
    given().
      param( "date", "2-0-1" ).
      param( "city", "Autauga" ).
      get( "api/reports" )
      .then()
      .log().body()
      .assertThat().status( HttpStatus.UNPROCESSABLE_ENTITY )
      .and().body( "error.date", hasItem( "The date does not match the format Y-m-d." ) );
    
    verify( service, times( 1 ) ).getReportForCityAndDate( Mockito.any(), Mockito.any() );
  }
  
  @Test  void testGetSpecificPlaceDataForAnInvalidDateAndInvalidCity() {
    when( service.getReportForCityAndDate( Mockito.any(), Mockito.any() ) ).thenReturn(
      ResponseEntity.unprocessableEntity().body( "{" +
        "   \"error\" : {" +
        "      \"date\" : [" +
        "         \"The date does not match the format Y-m-d.\"" +
        "      ]" +
        "   }" +
        "}" ) );
    
    given().
      param( "date", "2-0-1" ).
      param( "city", "Neverland" ).
      get( "api/reports" )
      .then()
      .log().body()
      .assertThat().status( HttpStatus.UNPROCESSABLE_ENTITY )
      .and().body( "error.date", hasItem( "The date does not match the format Y-m-d." ) );
    
    verify( service, times( 1 ) ).getReportForCityAndDate( Mockito.any(), Mockito.any() );
  }
  
  @Test  void testGetWorldDataForAnValidDate() {
    SummaryReport summaryReport = new SummaryReport( new SummaryReportData(
      "2020-04-11", "2020-04-11 22:52:46", (long) 1771514L, (long) 79795L, 108502L, (long) 5977L, (long) 402110L,
      26014L, 1260902L, 47804L, (float) 0.0612
    ) );
    when( service.getReportForWorld( Mockito.any() ) ).thenReturn(
      ResponseEntity.ok().body( summaryReport ) );
    
    given().
      param( "date", "2020-04-11" ).
      get( "api/reports/total" )
      .then()
      .log().body()
      .assertThat().status( HttpStatus.OK )
      .and().body( "data.date", is( "2020-04-11" ) )
      .and().body( "data.fatality_rate", is( 0.0612f ) );
    
    verify( service, times( 1 ) ).getReportForWorld( Mockito.any() );
  }
  
  @Test  void testGetWorldDataForAnInvalidDate() {
    when( service.getReportForWorld( Mockito.any() ) ).thenReturn(
      ResponseEntity.unprocessableEntity().body( "{" +
        "   \"error\" : {" +
        "      \"date\" : [" +
        "         \"The date does not match the format Y-m-d.\"" +
        "      ]" +
        "   }" +
        "}" ) );
    
    given().
      param( "date", "2-0-1" ).
      get( "api/reports/total" )
      .then()
      .log().body()
      .assertThat().status( HttpStatus.UNPROCESSABLE_ENTITY )
      .and().body( "error.date", hasItem( "The date does not match the format Y-m-d." ) );
    
    verify( service, times( 1 ) ).getReportForWorld( Mockito.any() );
  }
  
  @Test  void testGetWorldDataForAnEmptyDate() {
    String todaysDate =
      LocalDateTime.now().format( DateTimeFormatter.ofPattern( "yyyy-MM-dd" ) );
    SummaryReport summaryReport = new SummaryReport( new SummaryReportData(
      todaysDate, todaysDate + " 22:52:46", (long) 1771514L, (long) 79795L, 108502L, (long) 5977L, (long) 402110L,
      26014L, 1260902L, 47804L, (float) 0.0612
    ) );
    when( service.getReportForWorld( Mockito.any() ) ).thenReturn(
      ResponseEntity.ok().body( summaryReport ) );
    
    given().
      get( "api/reports/total" )
      .then()
      .log().body()
      .assertThat().status( HttpStatus.OK )
      .and().body( "data.date", is( todaysDate ) )
      .and().body( "data.fatality_rate", is( 0.0612f ) );
    
    verify( service, times( 1 ) ).getReportForWorld( todaysDate );
  }
}

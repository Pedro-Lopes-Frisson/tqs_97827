package pedrolopes.tqs.covid19trackingservice;


import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pedrolopes.tqs.covid19trackingservice.apiconsumer.Resolver;
import pedrolopes.tqs.covid19trackingservice.models.RootReport;
import pedrolopes.tqs.covid19trackingservice.models.SummaryReport;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)  class ResolverTest {
  
  private static final String ERROR_RESPONSE = "{\"error\":{\"date\":[\"The date does not match the format Y-m-d" +
    ".\"]}}";
  final String VALID_RESPONSE =
    "{\"data\":[{\"date\":\"2020-04-11\",\"confirmed\":3217,\"deaths\":92," + "\"recovered\":0," +
      "\"confirmed_diff\":270,\"deaths_diff\":12,\"recovered_diff\":0,\"last_update\":\"2020-04-11 22:45:33\"," +
      "\"active\":3125,\"active_diff\":258,\"fatality_rate\":0.0286,\"region\":{\"iso\":\"USA\",\"name\":\"US\"," +
      "\"province\":\"Alabama\",\"lat\":\"32.3182\",\"long\":\"-86.9023\",\"cities\":[{\"name\":\"Autauga\"," +
      "\"date\":\"2020-04-11\",\"fips\":1001,\"lat\":\"32.53952745\",\"long\":\"-86.64408227\",\"confirmed\":19," +
      "\"deaths\":1,\"confirmed_diff\":2,\"deaths_diff\":0,\"last_update\":\"2020-04-11 22:45:33\"}]}}]}";
  final String VALIDE_RESPONSE_SUMMARY_REPORT =
    "{\"data\":{\"date\":\"2020-04-11\",\"last_update\":\"2020-04-1122:52:46\",\"confirmed\":1771514," +
      "\"confirmed_diff\":79795,\"deaths\":108502,\"deaths_diff\":5977,\"recovered\":402110,\"recovered_diff\":26014," +
      "\"active\":1260902,\"active_diff\":47804,\"fatality_rate\":0.0612}}";
   MockWebServer mockWebServer;
  
  
  Resolver resolver;
  
  @BeforeEach void setUp() throws IOException {
    mockWebServer = new MockWebServer();
    mockWebServer.start();
    String httpUrl = mockWebServer.url( "/" ).toString();
    resolver = new Resolver( httpUrl );
  }
  
  @AfterEach void tearDown() throws IOException {
    System.out.println( "Shutting down" );
    mockWebServer.shutdown();
  }
  
  @Test void testWhenValidRequestGetRootReportResponse() throws InterruptedException {
    MockResponse mockedResponse =
      new MockResponse().setBody( VALID_RESPONSE ).addHeader( "Content-Type", "application/json; charset=utf-8" );
    mockWebServer.enqueue( mockedResponse );
    
    
    ResponseEntity<Object> autauga = resolver.getSpecificReportFor( "Autauga", "2020-04-11" );
    RecordedRequest request = mockWebServer.takeRequest();
    assertThat( request.getPath() ).isEqualTo( "/reports?city_name=Autauga&date=2020-04-11" );
    System.out.println( autauga.getBody() );
    AssertionsForClassTypes.assertThat( ( (RootReport) autauga.getBody() ).getData().get( 0 ).getDate() )
                           .isEqualTo( "2020-04-11" );
  }
  
  
  @Test void testWhenValidRequestGetSummaryReportResponse() throws InterruptedException {
    
    MockResponse mockedResponse =
      new MockResponse().setBody( VALIDE_RESPONSE_SUMMARY_REPORT )
                        .addHeader( "Content-Type", "application/json; charset=utf-8" );
    
    mockWebServer.enqueue( mockedResponse );
    
    ResponseEntity<Object> autauga = resolver.getWorldReport( "2020-04-11" );
    RecordedRequest request = mockWebServer.takeRequest();
    assertThat( request.getPath() ).isEqualTo( "/reports/total?date=2020-04-11" );
    System.out.println( autauga.getBody() );
    assertThat( ( (SummaryReport) autauga.getBody() ).getSummaryReportData().toString() ).contains( "2020-04-11" );
  }
  
  @Test void testWhenAPItimeoutThrowException() throws InterruptedException, IOException {
    
    MockResponse mockedResponse =
      new MockResponse().setBody( VALIDE_RESPONSE_SUMMARY_REPORT )
                        .addHeader( "Content-Type", "application/json; charset=utf-8" );
    
    mockWebServer.shutdown();
    
    assertThat( resolver.getWorldReport( "2020-04-11" ).getStatusCodeValue() ).isEqualTo(
      HttpStatus.INTERNAL_SERVER_ERROR.value() );
    assertThat( resolver.getSpecificReportFor( "Autauga", "2020-04-11" ).getStatusCodeValue() ).isEqualTo( HttpStatus
      .INTERNAL_SERVER_ERROR.value() );
  }
  
  @Test  void testWhenAPIReturnsErrorMessageReturnErrorToo() {
    MockResponse mockedResponse =
      new MockResponse().setBody( ERROR_RESPONSE ).setResponseCode( 422 )
                        .addHeader( "Content-Type", "application/json; charset=utf-8" );
    
    mockWebServer.enqueue( mockedResponse );
    
    ResponseEntity<Object> autauga = resolver.getWorldReport( "2-04-11" );
    assertThat( autauga.getStatusCodeValue() ).isEqualTo( 422 );
    
    mockWebServer.enqueue( mockedResponse );
    
    ResponseEntity<Object> responseEntitySpecific = resolver.getSpecificReportFor( "Autauga", "20-1-1" );
    assertThat( autauga.getStatusCodeValue() ).isEqualTo( 422 );
    
  }
  
  @Test
   void testWhenApiReturnsEmptyBodyReturnErrorMessage() {
    
    MockResponse mockedResponse =
      new MockResponse().addHeader( "Content-Type", "application/json; charset=utf-8" );
    
    mockWebServer.enqueue( mockedResponse );
    
    ResponseEntity<Object> autauga = resolver.getWorldReport( "2-04-11" );
    assertThat( autauga.getStatusCodeValue() ).isEqualTo( 500 );
    
    mockWebServer.enqueue( mockedResponse );
    
    ResponseEntity<Object> responseEntitySpecific = resolver.getSpecificReportFor( "Autauga", "20-1-1" );
    assertThat( autauga.getStatusCodeValue() ).isEqualTo( 500 );
    
  }
  
  @Test
   void testWhenAskedForURLWithValidPath() {
    
    assertThat( resolver.getURLForSpecificCityAndDate( "Autauga", "2020-04-11" ) ).contains( "reports?city_name" +
      "=Autauga&date" +
      "=2020-04-11" );
    
    assertThat( resolver.getUrlForWorldReport( "2020-04-11" ) ).contains( "reports/total?date=2020-04-11" );
    
  }
}
package pedrolopes.tqs.covid19trackingservice;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pedrolopes.tqs.covid19trackingservice.apiconsumer.Resolver;
import pedrolopes.tqs.covid19trackingservice.manager.CacheManager;
import pedrolopes.tqs.covid19trackingservice.models.*;
import pedrolopes.tqs.covid19trackingservice.repository.CacheRepository;
import pedrolopes.tqs.covid19trackingservice.services.Covid19Service;

import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
 class ServiceTest {
  
  @Mock
  Resolver resolver;
  @Mock
  private CacheRepository repository;
  @Mock
  private CacheManager cacheManager;
  @InjectMocks
  private Covid19Service service;
  
  @Test
   void testReportForCachedResult() {
    ReportData reportData =
      new ReportData( "2020-04-11", 3217, 92, 0, 270, 12,
        0, "2020-04-11 22:45:33", 3125, 258, 0.0286
        , new RegionReportData() );
    ArrayList<ReportData> arrayListData = new ArrayList<>();
    arrayListData.add( reportData );
    RootReport rootReport = new RootReport( arrayListData );
    
    String url = "https://covid-19-statistics.p.rapidapi.com/reports?city_name=Autauga&date=2020-04-11";
    when( resolver.getURLForSpecificCityAndDate( Mockito.any(), Mockito.any() ) ).thenReturn( url );
    
    when( cacheManager.inCache( "https://covid-19-statistics.p.rapidapi" +
      ".com/reports?city_name=Autauga&date=2020-04-11" ) ).thenReturn( new Cache( url, rootReport ) );
    
    ResponseEntity<Object> responseEntity = service.getReportForCityAndDate( "Autauga", "2020-04-11" );
    assertThat( responseEntity.getStatusCode() ).isEqualTo( HttpStatus.OK );
    
    verify( resolver, times( 1 ) ).getURLForSpecificCityAndDate( Mockito.any(), Mockito.any() );
    verify( resolver, times( 0 ) ).getSpecificReportFor( Mockito.any(), Mockito.any() );
    verify( cacheManager, times( 1 ) ).inCache( Mockito.any() );
  }
  
  @Test
   void testReportForNotCachedResult() {
    ReportData reportData =
      new ReportData( "2020-04-11", 3217, 92, 0, 270, 12,
        0, "2020-04-11 22:45:33", 3125, 258, 0.0286
        , new RegionReportData() );
    ArrayList<ReportData> arrayListData = new ArrayList<>();
    arrayListData.add( reportData );
    RootReport rootReport = new RootReport( arrayListData );
    
    String url = "https://covid-19-statistics.p.rapidapi.com/reports?city_name=Autauga&date=2020-04-11";
    
    when( resolver.getURLForSpecificCityAndDate( Mockito.any(), Mockito.any() ) ).thenReturn( url );
    when( resolver.getSpecificReportFor( Mockito.any(), Mockito.any() ) ).thenReturn( ResponseEntity.ok( rootReport ) );
    
    when( cacheManager.inCache( url ) ).thenReturn( null );
    when( cacheManager.saveCache( Mockito.any() ) ).thenReturn( new Cache( url, rootReport ) );
    
    ResponseEntity<Object> responseEntity = service.getReportForCityAndDate( "Autauga", "2020-04-11" );
    assertThat( responseEntity.getStatusCode() ).isEqualTo( HttpStatus.OK );
    
    verify( resolver, times( 1 ) ).getURLForSpecificCityAndDate( Mockito.any(), Mockito.any() );
    verify( resolver, times( 1 ) ).getSpecificReportFor( Mockito.any(), Mockito.any() );
    verify( cacheManager, times( 1 ) ).inCache( Mockito.any() );
  }
  
  
  @Test
   void testSummaryReportInCache() {
    SummaryReport summaryReport = new SummaryReport( new SummaryReportData(
      "2020-04-11", "2020-04-11 22:52:46", (long) 1771514L, (long) 79795L, 108502L, (long) 5977L, (long) 402110L,
      26014L, 1260902L, 47804L, (float) 0.0612
    ) );
    
    String url = "https://covid-19-statistics.p.rapidapi.com/reports/total?date=2020-04-11";
    
    when( resolver.getUrlForWorldReport( Mockito.any() ) ).thenReturn( url );
    
    when( cacheManager.inCache( url ) ).thenReturn( new Cache( url, summaryReport ) );
    
    ResponseEntity<Object> responseEntity = service.getReportForWorld( "2020-04-11" );
    assertThat( responseEntity.getStatusCode() ).isEqualTo( HttpStatus.OK );
    
    verify( resolver, times( 1 ) ).getUrlForWorldReport( Mockito.any() );
    verify( resolver, times( 0 ) ).getWorldReport( Mockito.any() );
    verify( cacheManager, times( 1 ) ).inCache( Mockito.any() );
  }
  
  @Test
   void testSummaryReportNotInCache() {
    SummaryReport summaryReport = new SummaryReport( new SummaryReportData(
      "2020-04-11", "2020-04-11 22:52:46", (long) 1771514L, (long) 79795L, 108502L, (long) 5977L, (long) 402110L,
      26014L, 1260902L, 47804L, (float) 0.0612
    ) );
    
    String url = "https://covid-19-statistics.p.rapidapi.com/reports/total?date=2020-04-11";
    
    when( resolver.getUrlForWorldReport( Mockito.any() ) ).thenReturn( url );
    
    when( cacheManager.inCache( url ) ).thenReturn( null );
    when( cacheManager.saveCache( Mockito.any() ) ).thenReturn( new Cache( url, summaryReport ) );
    when( resolver.getWorldReport( Mockito.any() ) ).thenReturn( ResponseEntity.ok( summaryReport ) );
    
    ResponseEntity<Object> responseEntity = service.getReportForWorld( "2020-04-11" );
    assertThat( responseEntity.getStatusCode() ).isEqualTo( HttpStatus.OK );
    
    verify( resolver, times( 1 ) ).getUrlForWorldReport( Mockito.any() );
    verify( resolver, times( 1 ) ).getWorldReport( Mockito.any() );
    verify( cacheManager, times( 1 ) ).inCache( Mockito.any() );
    
  }
  
  @Test
   void testWhenValueInCacheGetCacheShouldReturn1Hit() {
    
    SummaryReport summaryReport = new SummaryReport( new SummaryReportData(
      "2020-04-11", "2020-04-11 22:52:46", (long) 1771514L, (long) 79795L, 108502L, (long) 5977L, (long) 402110L,
      26014L, 1260902L, 47804L, (float) 0.0612
    ) );
    
    String url = "https://covid-19-statistics.p.rapidapi.com/reports/total?date=2020-04-11";
    
    when( resolver.getUrlForWorldReport( Mockito.any() ) ).thenReturn( url );
    when( cacheManager.inCache( url ) ).thenReturn( new Cache( url, summaryReport ) );
    when( cacheManager.getNumberMisses() ).thenReturn( 0 );
    when( cacheManager.getNumberHits() ).thenReturn( 1 );
    when( cacheManager.getNumberOfRequests() ).thenReturn( 1 );
    
    ResponseEntity<Object> responseEntity = service.getReportForWorld( "2020-04-11" );
    assertThat( responseEntity.getStatusCode() ).isEqualTo( HttpStatus.OK );
    
    ResponseEntity<Object> cacheEntity = service.getCache();
    assertThat( (String) cacheEntity.getBody() ).isEqualTo(
      String.format( "{\"hits\": %d, \"misses\": %d, \"requests\": %d}", 1, 0, 1
      ) );
    
    verify( resolver, times( 1 ) ).getUrlForWorldReport( Mockito.any() );
    verify( resolver, times( 0 ) ).getWorldReport( Mockito.any() );
    verify( cacheManager, times( 1 ) ).inCache( Mockito.any() );
    verify( cacheManager, times( 1 ) ).getNumberHits();
    verify( cacheManager, times( 1 ) ).getNumberMisses();
    verify( cacheManager, times( 1 ) ).getNumberOfRequests();
  }
  
  @Test
   void testWhenValueInCacheGetCacheShouldReturn1Miss() {
    
    SummaryReport summaryReport = new SummaryReport( new SummaryReportData(
      "2020-04-11", "2020-04-11 22:52:46", (long) 1771514L, (long) 79795L, 108502L, (long) 5977L, (long) 402110L,
      26014L, 1260902L, 47804L, (float) 0.0612
    ) );
    
    String url = "https://covid-19-statistics.p.rapidapi.com/reports/total?date=2020-04-11";
    
    when( resolver.getUrlForWorldReport( Mockito.any() ) ).thenReturn( url );
    when( cacheManager.inCache( url ) ).thenReturn( new Cache( url, summaryReport ) );
    when( cacheManager.getNumberMisses() ).thenReturn( 1 );
    when( cacheManager.getNumberHits() ).thenReturn( 0 );
    when( cacheManager.getNumberOfRequests() ).thenReturn( 1 );
    
    ResponseEntity<Object> responseEntity = service.getReportForWorld( "2020-04-11" );
    assertThat( responseEntity.getStatusCode() ).isEqualTo( HttpStatus.OK );
    
    ResponseEntity<Object> cacheEntity = service.getCache();
    assertThat( (String) cacheEntity.getBody() ).isEqualTo(
      String.format( "{\"hits\": %d, \"misses\": %d, \"requests\": %d}", 0,1, 1
      ) );
    
    verify( resolver, times( 1 ) ).getUrlForWorldReport( Mockito.any() );
    verify( resolver, times( 0 ) ).getWorldReport( Mockito.any() );
    verify( cacheManager, times( 1 ) ).inCache( Mockito.any() );
    verify( cacheManager, times( 1 ) ).getNumberHits();
    verify( cacheManager, times( 1 ) ).getNumberMisses();
    verify( cacheManager, times( 1 ) ).getNumberOfRequests();
  }
  
}

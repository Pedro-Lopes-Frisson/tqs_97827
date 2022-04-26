package pedroLopes.tqs.covid19trackingservice;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pedroLopes.tqs.covid19trackingservice.apiconsumer.Resolver;
import pedroLopes.tqs.covid19trackingservice.manager.CacheManager;
import pedroLopes.tqs.covid19trackingservice.models.*;
import pedroLopes.tqs.covid19trackingservice.repository.CacheRepository;
import pedroLopes.tqs.covid19trackingservice.services.Covid19Service;

import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServiceTest {
  
  @Mock
  Resolver resolver;
  @Mock
  private CacheRepository repository;
  @Mock
  private CacheManager cacheManager;
  @InjectMocks
  private Covid19Service service;
  
  @Test
  public void testReportForCachedResult() {
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
  public void testReportForNotCachedResult() {
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
  public void testSummaryReportInCache() {
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
  public void testSummaryReportNotInCache() {
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
  
  
}

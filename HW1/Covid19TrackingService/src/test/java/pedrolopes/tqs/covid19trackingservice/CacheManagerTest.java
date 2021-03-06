package pedrolopes.tqs.covid19trackingservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pedrolopes.tqs.covid19trackingservice.manager.CacheManager;
import pedrolopes.tqs.covid19trackingservice.models.Cache;
import pedrolopes.tqs.covid19trackingservice.models.SummaryReport;
import pedrolopes.tqs.covid19trackingservice.models.SummaryReportData;
import pedrolopes.tqs.covid19trackingservice.repository.CacheRepository;

import java.time.*;

import static java.time.Instant.ofEpochMilli;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CacheManagerTest {
  final String urlWorldReport = "https://covid-19-statistics.p.rapidapi.com/reports/total?date=2020-04-11";
  @Mock
  CacheRepository repository;
  @InjectMocks CacheManager cacheManager;
  Cache objectToBeCached;
  Clock constantClock;
  
  @BeforeEach
  void setUp() {
    
    objectToBeCached = new Cache( urlWorldReport, new SummaryReport( new SummaryReportData(
      "2020-04-11", "2020-04-11 22:52:46", (long) 1771514L, (long) 79795L, 108502L, (long) 5977L, (long) 402110L,
      26014L, 1260902L, 47804L, (float) 0.0612
    ) ) );
    
  }
  
  @Test
  void WhenObjectIsInCacheThenCacheManagerShouldIncrementHitsAndRequests() {
    when( repository.findByUrlRequest( urlWorldReport ) ).thenReturn( objectToBeCached );
    Object cachedObject = cacheManager.inCache( urlWorldReport );
    assertThat( cachedObject ).isNotNull();
    assertThat( cacheManager.getNumberHits() ).isEqualTo( 1 );
    assertThat( cacheManager.getNumberOfRequests() ).isEqualTo( 1 );
    assertThat( cacheManager.getNumberMisses() ).isZero();
    verify( repository, times( 1 ) ).findByUrlRequest( urlWorldReport );
  }
  
  
  @Test
  void WhenObjectIsNotInCacheThenCacheManagerShouldIncrementMissesAndRequests() {
    when( repository.findByUrlRequest( urlWorldReport ) ).thenReturn( null );
    Object cachedObject = cacheManager.inCache( urlWorldReport );
    assertThat( cachedObject ).isNull();
    assertThat( cacheManager.getNumberHits() ).isZero();
    assertThat( cacheManager.getNumberOfRequests() ).isEqualTo( 1 );
    assertThat( cacheManager.getNumberMisses() ).isEqualTo( 1 );
    verify( repository, times( 1 ) ).findByUrlRequest( urlWorldReport );
  }
  
  @Test
  void WhenObjectIstInCacheButItShouldBeInvalidatedThenCacheManagerShouldIncrementMissesAndRequestsAndDeleteThatObjectFromCache() {
  
    //Subtract ttl so that the object is cached but invalid has cache
    objectToBeCached = new Cache(-30000L ,urlWorldReport, new SummaryReport( new SummaryReportData(
      "2020-04-11", "2020-04-11 22:52:46", (long) 1771514L, (long) 79795L, 108502L, (long) 5977L, (long) 402110L,
      26014L, 1260902L, 47804L, (float) 0.0612
    ) ) );
    when( repository.findByUrlRequest( urlWorldReport ) ).thenReturn( objectToBeCached );
    Object cachedObject = cacheManager.inCache( urlWorldReport );
    assertThat( cachedObject ).isNull();
    
    assertThat( cacheManager.getNumberHits() ).isZero();
    assertThat( cacheManager.getNumberOfRequests() ).isEqualTo( 1 );
    assertThat( cacheManager.getNumberMisses() ).isEqualTo( 1 );
    
    verify( repository, times( 1 ) ).findByUrlRequest( urlWorldReport );
    verify( repository, times( 1 ) ).deleteByUrlRequest( urlWorldReport );
  }
  
  @Test
  void WhenObjectIsSavedInCacheReturnTheSameObject() {
    when( repository.save( objectToBeCached ) ).thenReturn( objectToBeCached );
    Cache objectSaved = cacheManager.saveCache( objectToBeCached );
    assertThat( objectSaved ).isEqualTo( objectToBeCached );
    verify( repository, times( 1 ) ).save( objectToBeCached );
  }
}

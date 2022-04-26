package pedrolopes.tqs.covid19trackingservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import pedrolopes.tqs.covid19trackingservice.models.*;
import pedrolopes.tqs.covid19trackingservice.repository.CacheRepository;

import java.time.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static java.time.Instant.ofEpochMilli;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class CacheModelDataJpaTest {
  
  
  @Autowired
  private TestEntityManager entityManager;
  
  @Autowired
  private CacheRepository repository;
  
  @Test
  public void savingDataOfSpecificCountryAtDate_thenWhenFindDataReturnData() {
    ReportData reportData =
      new ReportData( "2020-04-11", 3217, 92, 0, 270, 12,
        0, "2020-04-11 22:45:33", 3125, 258, 0.0286
        , new RegionReportData() );
    ArrayList<ReportData> arrayListData = new ArrayList<>();
    arrayListData.add( reportData );
    RootReport rootReport = new RootReport( arrayListData );
    entityManager.persistAndFlush(
      new Cache( "https://covid-19-statistics.p.rapidapi.com/reports?city_name=Autauga&date=2020-04-11", rootReport ) );
    
    Cache rootReportFound = repository.findByUrlRequest( "https://covid-19-statistics.p.rapidapi" +
      ".com/reports?city_name=Autauga&date=2020-04-11" );
    assertThat( rootReportFound ).isNotNull();
    assertThat( ( (RootReport) rootReportFound.getValue() ).getData().get( 0 ).date ).isEqualTo( "2020-04-11" );
  }
  
  @Test
  public void savingDataOfWorldReportAtDate_thenWhenFindDataReturnData() {
    
    SummaryReport summaryReport = new SummaryReport( new SummaryReportData(
      "2020-04-11", "2020-04-11 22:52:46", (long) 1771514L, (long) 79795L, 108502L, (long) 5977L, (long) 402110L,
      26014L, 1260902L, 47804L, (float) 0.0612
    ) );
    
    String url = "https://covid-19-statistics.p.rapidapi.com/reports/total?date=2020-04-11";
    
    entityManager.persistAndFlush( new Cache( url, summaryReport ) );
    
    Cache rootReportFound = repository.findByUrlRequest( url );
    
    assertThat( rootReportFound ).isNotNull();
    assertThat( ( (SummaryReport) rootReportFound.getValue() ).getSummaryReportData().toString() ).contains(
      "2020-04-11" );
    
  }
  
  @Test
  public void testSavingMultipleCacheObjectAndCallingFindLessThanTTLShouldReturnOnlyTheCorrectValues() {
    Instant.now( Clock.fixed(
      Instant.parse( "2018-08-22T10:00:00Z" ),
      ZoneOffset.UTC ) );
    
    Clock constantClock = Clock.fixed( ofEpochMilli( 0 ), ZoneId.systemDefault() );
    
    // the 0 duration returns to the same clock:
    SummaryReport summaryReport = new SummaryReport( new SummaryReportData(
      "2020-04-11", "2020-04-11 22:52:46", (long) 1771514L, (long) 79795L, 108502L, (long) 5977L, (long) 402110L,
      26014L, 1260902L, 47804L, (float) 0.0612
    ) );
    
    StringBuilder url =  new StringBuilder().append( "https://covid-19-statistics.p.rapidapi.com/reports/total?date=");
    long startDelay = 30000; // delay of the first Cache
    long fixedDelay = 10000; // delay between the other ones
    
    ArrayList<Cache> listOriginal = new ArrayList<>();
    listOriginal.add( new Cache( startDelay, url.append( "2020-04-11" ).toString(), summaryReport ) );
    listOriginal.add( new Cache( startDelay + fixedDelay, url.append( "2020-04-12" ).toString(), summaryReport ) );
    listOriginal.add( new Cache( startDelay + 2 * fixedDelay, url.append( "2020-04-13" ).toString(), summaryReport ) );
    
    entityManager.persistAndFlush( listOriginal.get( 0 ) );
    entityManager.persistAndFlush( listOriginal.get( 1 ) );
    entityManager.persistAndFlush( listOriginal.get( 2 ) );
    
    Clock clockFuture10Millis = Clock.offset( constantClock, Duration.ofMillis( 10 ) ); // go 10 millis to the future
  
    ArrayList<Cache> listCachedObjects =
      (ArrayList<Cache>) repository.findBytimeRequestWasMadeLessThan( clockFuture10Millis.millis() );
    
    //No Objects Should have been returned
    assertThat(listCachedObjects.size()).isEqualTo( 0 );
  
    Clock clockFutureFuture40001 = Clock.offset( constantClock, Duration.ofMillis( 40001 ) );
  
    listCachedObjects =
      (ArrayList<Cache>) repository.findBytimeRequestWasMadeLessThan( clockFuture10Millis.millis() );
    assertThat(
      listCachedObjects.stream().map( Cache::getTimeRequestWasMade )
                       .collect( Collectors.toList() ) ).isEqualTo(
      listOriginal.stream().filter( (Cache c) -> c.getTimeRequestWasMade() < clockFutureFuture40001.millis() ).map( Cache::getTimeRequestWasMade ).collect(
        Collectors.toList()) );
  
  }
  
}

package pedrolopes.tqs.covid19trackingservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import pedrolopes.tqs.covid19trackingservice.models.*;
import pedrolopes.tqs.covid19trackingservice.repository.CacheRepository;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;

import static java.time.Instant.ofEpochMilli;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class CacheModelDataJpaTest {
  
  
  @Autowired
  private TestEntityManager entityManager;
  
  @Autowired
  private CacheRepository repository;
  
  @Test
  void savingDataOfSpecificCountryAtDate_thenWhenFindDataReturnData() {
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
    assertThat( ( (RootReport) rootReportFound.getValue() ).getData().get( 0 ).getDate() ).isEqualTo( "2020-04-11" );
  }
  
  @Test
  void savingDataOfWorldReportAtDate_thenWhenFindDataReturnData() {
    
    SummaryReport summaryReport = new SummaryReport( new SummaryReportData(
      "2020-04-11", "2020-04-11 22:52:46",  1771514L,  79795L, 108502L,  5977L,  402110L,
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
  void testSavingMultipleCacheObjectAndCallingFindLessThanTTLShouldReturnOnlyTheCorrectValues() {
    Instant.now( Clock.fixed(
      Instant.parse( "2018-08-22T10:00:00Z" ),
      ZoneOffset.UTC ) );
    
    Clock constantClock = Clock.fixed( ofEpochMilli( 0 ), ZoneId.systemDefault() );
    
    SummaryReport summaryReport = new SummaryReport( new SummaryReportData(
      "2020-04-11", "2020-04-11 22:52:46", 1771514L, 79795L, 108502L, 5977L, 402110L,
      26014L, 1260902L, 47804L, (float) 0.0612
    ) );
    
    StringBuilder url = new StringBuilder().append( "https://covid-19-statistics.p.rapidapi.com/reports/total?date=" );
    
    ArrayList<Cache> listOriginal = new ArrayList<>();
    // Subtract ttl's so that time request was made is lower then current Time in millis
    listOriginal.add( new Cache( - System.currentTimeMillis(), url.append( "2020-04-11" ).toString(), summaryReport ) );
    
    
    listOriginal.add( new Cache( url.append( "2020-04-12" ).toString(), summaryReport ) );
    listOriginal.add( new Cache( url.append( "2020-04-13" ).toString(), summaryReport ) );
    
    entityManager.persistAndFlush( listOriginal.get( 0 ) );
    entityManager.persistAndFlush( listOriginal.get( 1 ) );
    entityManager.persistAndFlush( listOriginal.get( 2 ) );
    
    
    ArrayList<Cache> listCachedObjects =
      (ArrayList<Cache>) repository.findBytimeRequestWasMadeLessThan( System.currentTimeMillis() );
    
    //No Objects Should have been returned
    assertThat( 1 ).isEqualTo( listCachedObjects.size() );
    
    
    listCachedObjects =
      (ArrayList<Cache>) repository.findBytimeRequestWasMadeLessThan( System.currentTimeMillis() );
    
    assertThat( 1 ).isEqualTo( listCachedObjects.size() );
    assertThat( listCachedObjects.get( 0 ).getUrlRequest() ).contains( "2020-04-11" );
  }
  
  
  @Test
  void testGiven3CachedObjectsSavedWhenDeleteOneOfThemTheReaminingShouldRemainThere() {
    
    // the 0 duration returns to the same clock:
    SummaryReport summaryReport = new SummaryReport( new SummaryReportData(
      "2020-04-11", "2020-04-11 22:52:46", (long) 1771514L, (long) 79795L, 108502L, (long) 5977L, (long) 402110L,
      26014L, 1260902L, 47804L, (float) 0.0612
    ) );
    
    StringBuilder url = new StringBuilder().append( "https://covid-19-statistics.p.rapidapi.com/reports/total?date=" );
    long startDelay = 30000; // delay of the first Cache
    long fixedDelay = 10000; // delay between the other ones
    
    ArrayList<Cache> listOriginal = new ArrayList<>();
    listOriginal.add( new Cache( startDelay, url.append( "2020-04-11" ).toString(), summaryReport ) );
    listOriginal.add( new Cache( startDelay + fixedDelay, url.append( "2020-04-12" ).toString(), summaryReport ) );
    listOriginal.add( new Cache( startDelay + 2 * fixedDelay, url.append( "2020-04-13" ).toString(), summaryReport ) );
    
    entityManager.persistAndFlush( listOriginal.get( 0 ) );
    entityManager.persistAndFlush( listOriginal.get( 1 ) );
    entityManager.persistAndFlush( listOriginal.get( 2 ) );
    
    
    repository.deleteByUrlRequest( url.append( "2020-04-11" ).toString() );
    Cache removedObject = listOriginal.remove( 0 );
    
    //No Objects Should have been returned
    assertThat( removedObject ).isNotIn( listOriginal );
  }
  
  @Test
  void deletingAnEntityShouldResultInItsDisappearanceFromThatTable() {
    SummaryReport summaryReport = new SummaryReport( new SummaryReportData(
      "2020-04-11", "2020-04-11 22:52:46", (long) 1771514L, (long) 79795L, 108502L, (long) 5977L, (long) 402110L,
      26014L, 1260902L, 47804L, (float) 0.0612
    ) );
    
    String url =
      "https://covid-19-statistics.p.rapidapi.com/reports/total?date=2020-04-11";
    Cache objectToDelete = new Cache( url, summaryReport );
    entityManager.persistAndFlush( objectToDelete );
    
    assertThat( repository.findByUrlRequest( url ) ).isNotNull();
    repository.delete( objectToDelete );
    assertThat( repository.findByUrlRequest( url ) ).isNull();
  }
  
}

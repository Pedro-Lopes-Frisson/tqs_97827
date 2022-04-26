package pedrolopes.tqs.covid19trackingservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import pedrolopes.tqs.covid19trackingservice.models.*;
import pedrolopes.tqs.covid19trackingservice.repository.CacheRepository;

import java.util.ArrayList;

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
    entityManager.persistAndFlush( new Cache(  "https://covid-19-statistics.p.rapidapi.com/reports?city_name=Autauga&date=2020-04-11" ,rootReport  ) );
    
    Cache rootReportFound = repository.findByUrlRequest( "https://covid-19-statistics.p.rapidapi" +
      ".com/reports?city_name=Autauga&date=2020-04-11" );
    assertThat( rootReportFound ).isNotNull();
    assertThat( ((RootReport)rootReportFound.getValue()).getData().get( 0 ).date ).isEqualTo( "2020-04-11" );
  }
  
  @Test
  public void savingDataOfWorldReportAtDate_thenWhenFindDataReturnData() {
  
    SummaryReport summaryReport = new SummaryReport( new SummaryReportData(
      "2020-04-11", "2020-04-11 22:52:46", (long) 1771514L, (long) 79795L, 108502L, (long) 5977L, (long) 402110L,
      26014L, 1260902L, 47804L, (float) 0.0612
    ) );
  
    String url = "https://covid-19-statistics.p.rapidapi.com/reports/total?date=2020-04-11";
  
    entityManager.persistAndFlush( new Cache(url, summaryReport) );
  
    Cache rootReportFound = repository.findByUrlRequest(url);
    
    assertThat( rootReportFound ).isNotNull();
    assertThat( ((SummaryReport)rootReportFound.getValue()).getSummaryReportData().toString() ).contains( "2020-04-11" );
  
  }
  
}

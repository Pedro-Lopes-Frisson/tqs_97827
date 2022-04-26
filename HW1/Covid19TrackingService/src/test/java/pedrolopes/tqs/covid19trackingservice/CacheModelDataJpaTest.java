package pedrolopes.tqs.covid19trackingservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import pedrolopes.tqs.covid19trackingservice.models.Cache;
import pedrolopes.tqs.covid19trackingservice.models.RegionReportData;
import pedrolopes.tqs.covid19trackingservice.models.ReportData;
import pedrolopes.tqs.covid19trackingservice.models.RootReport;
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
    entityManager.persistAndFlush( rootReport );
    
    Cache rootReportFound = repository.findByUrlRequest( "https://covid-19-statistics.p.rapidapi" +
      ".com/reports?city_name=Autauga&date=2020-04-11" );
    assertThat( rootReportFound ).isNotNull();
    assertThat( ((RootReport)rootReportFound.getValue()).getData().get( 0 ).date ).isEqualTo( "2020-04-11" );
  }
  
}

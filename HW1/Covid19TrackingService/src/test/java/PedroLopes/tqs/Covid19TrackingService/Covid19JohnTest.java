package PedroLopes.tqs.Covid19TrackingService;


import PedroLopes.tqs.Covid19TrackingService.ApiConsumer.Covid19JonhsHopkinsCSSE;
import PedroLopes.tqs.Covid19TrackingService.Models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Covid19JohnTest {
  Covid19JonhsHopkinsCSSE c;
  
  @BeforeEach public void setUp() {
    c = new Covid19JonhsHopkinsCSSE();
  }
  
  @Test public void testReport() throws ParseException {
    ArrayList<CityReportData> cRdata = new ArrayList<CityReportData>();
    cRdata.add( new CityReportData( "Autauga", "2020-04-16", 1001, "32.53952745", "-86.64408227", 26, 1, 2, 0,
      "2020-04-16 23:30:51" ) );
    RegionReportData rData = new RegionReportData( "USA", "US", "Alabama", "32.3182", "-86.9023", cRdata );
    ReportData r =
      new ReportData( "2020-04-16", 4345, 133, 0, 270, 15, 0, "2020-04-16 23:30:51", 4212, 255, 0.0306, rData );
    ArrayList<ReportData> a = new ArrayList<>();
    a.add( r );
    RootReport rr = new RootReport( a );
    assertThat( Objects.requireNonNull(
      c.getReport( "Autauga", "Alabama", "USA", "US","US Alabama", "2020-04-16" )
       .block() ).getBody() ).isEqualTo(
      rr );
  }
  
  @Test public void testgetSummaryReport() throws ParseException {
    SummaryReportData sData = new SummaryReportData( "2022-04-10", "2022-04-11 04:21:02", 498890328L,
      632942L, 6178768L, 2151L, 0L, 0L, 492711560L, 630791L, (float) 0.0124 );
    
    SummaryReport s = new SummaryReport( sData );
    
    assertThat( Objects.requireNonNull(
      c.getTotalReport( "2022-04-10" ).block() ).getBody() ).isEqualTo( s );
  }
  
}

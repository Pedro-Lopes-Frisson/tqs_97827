package PedroLopes.tqs.Covid19TrackingService.Services;

import PedroLopes.tqs.Covid19TrackingService.ApiConsumer.Covid19JonhsHopkinsCSSE;
import PedroLopes.tqs.Covid19TrackingService.Manager.CacheManager;
import PedroLopes.tqs.Covid19TrackingService.Models.Cache;
import PedroLopes.tqs.Covid19TrackingService.Models.RootReport;
import PedroLopes.tqs.Covid19TrackingService.Models.SummaryReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class Covid19Service {
  @Autowired Covid19JonhsHopkinsCSSE covid19JonhsHopkinsCSSE;
  @Autowired CacheManager cacheManager;
  
  public Mono<ResponseEntity<SummaryReport>> getTotalReport( String date ) {
    StringBuilder sb = new StringBuilder();
    if ( date.isEmpty() ) {
      sb.append( "date=" ).append( date ).append( "&" );
    }
    Mono<ResponseEntity<SummaryReport>> summaryReportData = covid19JonhsHopkinsCSSE.getTotalReport( date );
    
    Cache c = new Cache( summaryReportData.toString(), sb.toString() );
    cacheManager.saveCache( c );
    return summaryReportData;
    
  }
  
  public Mono<ResponseEntity<RootReport>> getTotalReport( String city,
                                                          String region_province,
                                                          String iso,
                                                          String region_name,
                                                          String query,
                                                          String date ) {
    StringBuilder paramsString = new StringBuilder().append( "?" );
    if ( city.isEmpty() ) {
      paramsString.append( "city_name=" ).append( city );
    }
    if ( iso.isEmpty() ) {
      paramsString.append( "&iso=" ).append( iso );
    }
    if ( region_name.isEmpty() ) {
      paramsString.append( "&region_name=" ).append( region_name );
    }
    if ( iso.isEmpty() ) {
      paramsString.append( "&iso=" ).append( iso );
    }
    if ( query.isEmpty() ) {
      paramsString.append( "&q=" ).append( query );
    }
    if ( date.isEmpty() ) {
      paramsString.append( "&date=" ).append( date );
    }
    if ( region_province.isEmpty() ) {
      paramsString.append( "&region_province=" ).append( region_province );
    }
    
    Mono<ResponseEntity<RootReport>> reportData =
      covid19JonhsHopkinsCSSE.getReport(city, region_province, iso,region_name,query,date);
    
    Cache c = new Cache( reportData.toString(), paramsString.toString() );
    
    cacheManager.saveCache( c );
    return reportData;
  }
  
  public ResponseEntity<Object> getCache() {
    return ResponseEntity.ok().body(
      "{ 'hits' : " + cacheManager.getNumberHits() + ", 'misses' : " + cacheManager.getNumberMisses() +
        ", 'requests':" + cacheManager.getNumberOfRequests() + "}" );
  }
}
  

package pedrolopes.tqs.covid19trackingservice.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pedrolopes.tqs.covid19trackingservice.apiconsumer.Resolver;
import pedrolopes.tqs.covid19trackingservice.manager.CacheManager;
import pedrolopes.tqs.covid19trackingservice.models.Cache;
import pedrolopes.tqs.covid19trackingservice.models.RootReport;
import pedrolopes.tqs.covid19trackingservice.models.SummaryReport;

import java.io.Serializable;

@Service public class Covid19Service {
  
  private static final Logger log = LoggerFactory.getLogger( Covid19Service.class );
  @Autowired Resolver resolver;
  @Autowired CacheManager cacheManager;
  
  public ResponseEntity<Object> getReportForCityAndDate( String city, String date ) {
    
    String urlCall = resolver.getURLForSpecificCityAndDate( city, date );
    
    Cache objectInCache = (Cache) cacheManager.inCache( urlCall );
    
    // Object was in cache if the variable is not null
    if ( objectInCache != null ) {
      log.info( "Returning cached object {}", (RootReport) objectInCache.getValue() );
      return ResponseEntity.ok( (RootReport) objectInCache.getValue() );
    }
    
    log.info( "REPORT CALL --> {}", urlCall );
    ResponseEntity<Object> reportResponse = resolver.getSpecificReportFor( city, date );
    RootReport rootReportSaved = null;
    
    // save object in cache if response has a body which isn't null and if the status code is 200
    if ( reportResponse.hasBody() && reportResponse.getBody() != null &&
      reportResponse.getStatusCode() == HttpStatus.OK ) {
      assert cacheManager!= null;
      rootReportSaved = (RootReport) cacheManager.saveCache(
        new Cache( urlCall, (Serializable) reportResponse.getBody() ) ).getValue();
      
      log.info( String.format( "\nObject was saved %s", rootReportSaved ) );
    }
    
    return reportResponse;
  }
  
  public ResponseEntity<Object> getReportForWorld( String date ) {
    String urlCall = resolver.getUrlForWorldReport( date );
    log.info( "Summary Report CALL --> {}", urlCall );
    Cache objectInCache = (Cache) cacheManager.inCache( urlCall );
    
    log.info( String.format( "Cached Object Returned = %s", objectInCache ) );
    // Object was in cache if the variable is not null
    if ( objectInCache != null ) {
      log.info( "Returning cached Object" );
      return ResponseEntity.ok( (SummaryReport) objectInCache.getValue() );
    }
    
    ResponseEntity<Object> reportResponse = resolver.getWorldReport( date );
    if ( reportResponse.hasBody() && reportResponse.getBody() != null &&
      reportResponse.getStatusCode() == HttpStatus.OK ) {
      SummaryReport summaryReportSaved = (SummaryReport) cacheManager.saveCache(
        new Cache( urlCall, (Serializable) reportResponse.getBody() ) ).getValue();
      
      log.info( String.format( "%s\nObject was saved", summaryReportSaved ) );
    }
    return reportResponse;
  }
  
  
  public ResponseEntity<Object> getCache() {
    return ResponseEntity.ok(
      String.format( "{\"hits\": %d, \"misses\": %d, \"requests\": %d}", cacheManager.getNumberHits(),
        cacheManager.getNumberMisses(), cacheManager.getNumberOfRequests() ) );
  }
  
}

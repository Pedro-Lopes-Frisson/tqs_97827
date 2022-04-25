package pedroLopes.tqs.covid19trackingservice.services;

import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pedroLopes.tqs.covid19trackingservice.apiconsumer.APIJonhsHopkinsCSSE;
import pedroLopes.tqs.covid19trackingservice.manager.CacheManager;
import pedroLopes.tqs.covid19trackingservice.models.Cache;
import pedroLopes.tqs.covid19trackingservice.models.RootReport;
import pedroLopes.tqs.covid19trackingservice.models.SummaryReport;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service public class Covid19Service {
  
  private static final Logger log = LoggerFactory.getLogger( Covid19Service.class );
  final OkHttpClient okHttpClient =
    new OkHttpClient.Builder().readTimeout( 60, TimeUnit.SECONDS ).connectTimeout( 60, TimeUnit.SECONDS ).build();
  final APIJonhsHopkinsCSSE retrofit = new Retrofit.Builder().baseUrl( "https://covid-19-statistics.p.rapidapi.com/" )
                                                             .addConverterFactory( GsonConverterFactory.create() )
                                                             .client( okHttpClient ).build()
                                                             .create( APIJonhsHopkinsCSSE.class );
  
  @Autowired CacheManager cacheManager;
  
  public ResponseEntity<Object> getReportForCityAndDate( String city, String date ) {
    
    Call<RootReport> reportCall = retrofit.getSpecificReport( city, date ); // create the url and the header also get
    // ready to convert the response
    Cache objectInCache = (Cache) cacheManager.inCache( reportCall.request().url().url().toString() );
    
    // Object was in cache if the variable is not null
    if ( objectInCache != null ) {
      log.info( String.format( "Returning cached object %s", (RootReport) objectInCache.getValue() ) );
      return ResponseEntity.ok( (RootReport) objectInCache.getValue() );
    }
    
    log.info( String.format( "REPORT CALL --> %s", reportCall.request().url().url() ) );
    Response<RootReport> reportResponse = null;
    try {
      reportResponse = reportCall.execute();
    } catch (IOException e) {
      throw new RuntimeException( "Connection Timed Out or Service Not reachable" );
    }
    
    if ( reportResponse.code() != 200 ) {
      try {
        return ResponseEntity.status( reportResponse.code() ).body( reportResponse.errorBody().string() );
      } catch (IOException e) {
        return ResponseEntity.status( reportResponse.code() ).body( "{\"error\": \"There is no such data, please try " +
          "again\"}" );
      }
    }
    
    if ( reportResponse.body() == null ) {
      throw new RuntimeException( "Response Body is null" ); // Should not be thrown
    }
    // save object in cache
    RootReport rootReportSaved = (RootReport) cacheManager.saveCache(
      new Cache( reportCall.request().url().url().toString(), reportResponse.body() ) ).getValue();
    log.info( String.format( "\nObject was saved %s", rootReportSaved ) );
    
    return ResponseEntity.ok( rootReportSaved );
  }
  
  public ResponseEntity<Object> getReportForWorld( String date ) {
    Call<SummaryReport> summaryReportCall = retrofit.getWorldWideReport( date );
    log.info( String.format( "Summary Report CALL --> %s", summaryReportCall.request().url().url() ) );
    Cache objectInCache = (Cache) cacheManager.inCache( summaryReportCall.request().url().url().toString() );
    log.info( String.format( "Cached Object Returned = %s", objectInCache ) );
    // Object was in cache if the variable is not null
    if ( objectInCache != null ) {
      log.info( "Returning cached Object" );
      return ResponseEntity.ok( (SummaryReport) objectInCache.getValue() );
    }
    
    Response<SummaryReport> reportResponse = null;
    try {
      reportResponse = summaryReportCall.execute();
    } catch (IOException e) {
      throw new RuntimeException( "Connection Timed Out or Service Not reachable" );
    }
    
    if ( reportResponse.code() != 200 ) {
      try {
        return ResponseEntity.status( reportResponse.code() ).body( reportResponse.errorBody().string() );
      } catch (IOException e) {
        return ResponseEntity.status( reportResponse.code() ).body( "{\"error\": \"There is no such data, please try " +
          "again\"}" );
      }
    }
    
    if ( reportResponse.body() == null ) {
      throw new RuntimeException( "Response Body is null" ); // Should not be thrown
    }
    
    SummaryReport summaryReportSaved = (SummaryReport) cacheManager.saveCache(
      new Cache( summaryReportCall.request().url().url().toString(), reportResponse.body() ) ).getValue();
    
    log.info( String.format( "%s\nObject was saved", summaryReportSaved ) );
    return ResponseEntity.ok( summaryReportSaved );
  }
  
  
  public ResponseEntity<Object> getCache() {
    return ResponseEntity.ok(
      String.format( "{\"hits\": %d, \"misses\": %d, \"requests\": %d}", cacheManager.getNumberHits(),
        cacheManager.getNumberMisses(), cacheManager.getNumberOfRequests() ) );
  }
  
}

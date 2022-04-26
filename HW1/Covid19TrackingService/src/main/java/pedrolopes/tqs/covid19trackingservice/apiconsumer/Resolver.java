package pedrolopes.tqs.covid19trackingservice.apiconsumer;

import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import pedrolopes.tqs.covid19trackingservice.models.RootReport;
import pedrolopes.tqs.covid19trackingservice.models.SummaryReport;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
public class Resolver {
  private static final Logger log = LoggerFactory.getLogger( Resolver.class );
  static String DEFAULTAPI = "https://covid-19-statistics.p.rapidapi.com/";
  final OkHttpClient okHttpClient =
    new OkHttpClient.Builder().readTimeout( 60, TimeUnit.SECONDS ).connectTimeout( 60, TimeUnit.SECONDS ).build();
  final APIJonhsHopkinsCSSE retrofit;
  
  
  public Resolver( String url ) {
    
    retrofit = new Retrofit.Builder().baseUrl( url )
                                     .addConverterFactory( GsonConverterFactory.create() )
                                     .client( okHttpClient ).build()
                                     .create( APIJonhsHopkinsCSSE.class );
  }
  
  public String getURLForSpecificCityAndDate( String city, String date ) {
    
    return retrofit.getSpecificReport( city, date ).request().url().url()
                   .toString(); // create the url and the header also get
  }
  
  public ResponseEntity<Object> getSpecificReportFor( String city, String date ) {
    
    Call<RootReport> reportCall = retrofit.getSpecificReport( city, date ); // create the url and the header also get
    // ready to convert the response
    
    // Object was in cache if the variable is not null
    
    log.info( "REPORT CALL --> {}", reportCall.request().url().url() );
    Response<RootReport> reportResponse = null;
    try {
      reportResponse = reportCall.execute();
    } catch (IOException e) {
      return ResponseEntity.internalServerError().body( "{ \"error\" : \"Service is temporary unavailable." +
        "Please try again later\"}" );
    }
  
    if ( reportResponse.code() != 200 ) {
      return ResponseEntity.status( HttpStatus.UNPROCESSABLE_ENTITY).body( "{\"error\": \"Request resulted in an invalid operation\"}" );
    }
    
    // save object in cache
    return ResponseEntity.ok( reportResponse.body() );
  }
  
  public String getUrlForWorldReport( String date ) {
    return retrofit.getWorldWideReport( date ).request().url().url().toString();
  }
  
  public ResponseEntity<Object> getWorldReport( String date ) {
    
    Call<SummaryReport> summaryReportCall = retrofit.getWorldWideReport( date );
    
    Response<SummaryReport> reportResponse = null;
    try {
      reportResponse = summaryReportCall.execute();
    } catch (IOException e) {
      return ResponseEntity.internalServerError().body( "{ \"error\" : \"Service is temporary unavailable." +
        "Please try again later\"}" );
    }
  
    if ( reportResponse.code() != 200 ) {
      return ResponseEntity.status( HttpStatus.UNPROCESSABLE_ENTITY).body( "{\"error\": \"Request resulted in an invalid operation\"}" );
    }
    
    
    return ResponseEntity.ok( reportResponse.body() );
  }
  
  public APIJonhsHopkinsCSSE getRetrofit() {
    return this.retrofit;
  }
}
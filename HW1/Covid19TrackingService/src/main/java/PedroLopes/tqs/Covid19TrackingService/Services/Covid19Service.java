package PedroLopes.tqs.Covid19TrackingService.Services;

import PedroLopes.tqs.Covid19TrackingService.ApiConsumer.APIJonhsHopkinsCSSE;
import PedroLopes.tqs.Covid19TrackingService.Manager.CacheManager;
import PedroLopes.tqs.Covid19TrackingService.Models.RootReport;
import PedroLopes.tqs.Covid19TrackingService.Models.SummaryReport;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
public class Covid19Service {

  private static final Logger log = LoggerFactory.getLogger( Covid19Service.class );
  final OkHttpClient okHttpClient = new OkHttpClient.Builder()
    .readTimeout( 60, TimeUnit.SECONDS )
    .connectTimeout( 60, TimeUnit.SECONDS )
    .build();
  final APIJonhsHopkinsCSSE retrofit = new Retrofit.Builder()
    .baseUrl( "https://covid-19-statistics.p.rapidapi.com/" )
    .addConverterFactory( GsonConverterFactory.create() )
    .client( okHttpClient )
    .build().create( APIJonhsHopkinsCSSE.class );

  @Autowired CacheManager cacheManager;

  public ResponseEntity<Object> getReportForCityAndDate( String city, String date ) {

    Call<RootReport> reportCall = retrofit.getSpecificReport( city, date );
    log.debug("REPORT CALL -->"+reportCall.toString() );
    Response<RootReport> reportResponse = null;
    try {
      reportResponse = reportCall.execute();
    } catch (IOException e) {
      throw new RuntimeException( "Connection Timed Out or Service Not reachable" );
    }

    if ( reportResponse.code() != 200 ) {
      return ResponseEntity.badRequest().body( "{\"error\": \"There is no such data, please try again\"}" );
    }
    return ResponseEntity.ok( reportResponse.body() );
  }

  public ResponseEntity<Object> getReportForWorld( String date ) {
    Call<SummaryReport> reportCall = retrofit.getWorldWideReport( date );
    Response<SummaryReport> reportResponse = null;
    try {
      reportResponse = reportCall.execute();
    } catch (IOException e) {
      throw new RuntimeException( "Connection Timed Out or Service Not reachable" );
    }
    log.info( reportResponse.raw().body().toString() );

    if ( reportResponse.code() != 200 ) {
      return ResponseEntity.badRequest().body( "{\"error\": \"There is no such data, please try again\"}" );
    }
    return ResponseEntity.ok( reportResponse.body());
  }


  public ResponseEntity<String> getCache() {
    return ResponseEntity.ok(
      "{\"hits\" :" + cacheManager.getNumberHits() +
        ", \"misses\" : " + cacheManager.getNumberMisses() +
        ", \"requests\" : " + cacheManager.getNumberOfRequests()
        + '}' );
  }

}

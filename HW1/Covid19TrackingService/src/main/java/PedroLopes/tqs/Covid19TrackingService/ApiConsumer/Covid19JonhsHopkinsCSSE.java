package PedroLopes.tqs.Covid19TrackingService.ApiConsumer;

import PedroLopes.tqs.Covid19TrackingService.Models.RootReport;
import PedroLopes.tqs.Covid19TrackingService.Models.SummaryReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Collections;

@Component
public class Covid19JonhsHopkinsCSSE {
  
  private static final Logger LOGGER = LoggerFactory.getLogger( Covid19JonhsHopkinsCSSE.class );
  final int size = 16 * 1024 * 1024;
  final ExchangeStrategies strategies = ExchangeStrategies.builder()
                                                          .codecs(
                                                            codecs -> codecs.defaultCodecs().maxInMemorySize( size ) )
                                                          .build();
  
  WebClient client =
    WebClient.builder().exchangeStrategies( strategies ).baseUrl( "https://covid-19-statistics.p.rapidapi" +
               ".com" )
             .defaultHeader( HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE )
             .defaultUriVariables(
               Collections.singletonMap( "url", "https://covid-19-statistics.p.rapidapi.com" ) )
             .build();
  
  public Mono<ResponseEntity<RootReport>> getReport( String city, String region_province, String iso,
                                                     String region_name, String query, String date ) {
    
    RequestHeadersSpec<?> headersSpec = client.method( HttpMethod.GET ).uri(
                                                uriBuilder -> {
        
                                                  uriBuilder = uriBuilder.path( "/reports" );
                                                  if ( ! city.isEmpty() ) {
                                                    uriBuilder = uriBuilder.queryParam( "city_name", city );
                                                  }
        
                                                  if ( ! region_province.isEmpty() ) {
                                                    uriBuilder = uriBuilder.queryParam( "region_province",
                                                      region_province );
                                                  }
        
                                                  if ( ! iso.isEmpty() ) {
                                                    uriBuilder = uriBuilder.queryParam( "iso", iso );
                                                  }
        
                                                  if ( ! region_name.isEmpty() ) {
                                                    uriBuilder = uriBuilder.queryParam( "region_name", region_name );
                                                  }
        
                                                  if ( ! query.isEmpty() ) {
                                                    uriBuilder = uriBuilder.queryParam( "q", query );
                                                  }
        
                                                  if ( ! date.isEmpty() ) {
                                                    uriBuilder = uriBuilder.queryParam( "date", date );
                                                  }
                                                  return uriBuilder.build();
                                                } )
                                              .header( "X-RapidAPI-Host", "covid-19-statistics.p.rapidapi.com" )
                                              .header( "X-RapidAPI-Key",
                                                "ea0cd966e4msh3606703ced4ea73p1acda7jsn516db04bb8d3" );
    
    
    ResponseSpec s =
      headersSpec.accept( MediaType.APPLICATION_JSON ).acceptCharset( StandardCharsets.UTF_8 ).ifNoneMatch( "*" )
                 .ifModifiedSince( ZonedDateTime.now() ).retrieve();
    
    return s.toEntity( RootReport.class );
  }
  
  
  public Mono<ResponseEntity<SummaryReport>> getTotalReport( String date ) {
    
    RequestHeadersSpec<?> headersSpec = client.method( HttpMethod.GET ).uri(
                                                ( UriBuilder uriBuilder ) -> uriBuilder.path( "/reports/total" ).queryParam( "date",
                                                  date ).build() )
                                              .header( "X-RapidAPI-Host", "covid-19-statistics.p.rapidapi.com" )
                                              .header( "X-RapidAPI-Key",
                                                "ea0cd966e4msh3606703ced4ea73p1acda7jsn516db04bb8d3" );
    
    
    ResponseSpec s =
      headersSpec.accept( MediaType.APPLICATION_JSON ).acceptCharset( StandardCharsets.UTF_8 ).ifNoneMatch( "*" )
                 .ifModifiedSince( ZonedDateTime.now() ).retrieve();
    
    return s.toEntity( SummaryReport.class );
  }
}

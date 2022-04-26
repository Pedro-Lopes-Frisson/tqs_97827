package pedrolopes.tqs.covid19trackingservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pedrolopes.tqs.covid19trackingservice.services.Covid19Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api", produces = "application/json")
@CrossOrigin(origins = "http://localhost:3000")
public class RestControllerCovid19 {
  private static final Logger log = LoggerFactory.getLogger( RestControllerCovid19.class );
  @Autowired
  Covid19Service service;
  
  @CrossOrigin(origins = "http://localhost:8000")
  @GetMapping("/reports/total")
  ResponseEntity<Object> getWorldWideReportForDate( @RequestParam(required = false) Optional<String> date ) {
    
    ResponseEntity<Object> toBeReturned = service.getReportForWorld( date.orElse(
      LocalDateTime.now().format( DateTimeFormatter.ofPattern( "yyyy-MM-dd" ) ) ) );
    
    log.info(  "Value inside body {}", toBeReturned.getBody() );
    return toBeReturned;
  }
  
  @CrossOrigin(origins = "http://localhost:8000")
  @GetMapping("/reports")
  public ResponseEntity<Object> getFullDayReportFor( @RequestParam Optional<String> city,
                                                     @RequestParam Optional<String> date ) {
    
    ResponseEntity<Object> toBeReturned = service.getReportForCityAndDate( city.orElse( "" ),
      date.orElse( LocalDateTime.now().format( DateTimeFormatter.ofPattern( "yyyy-MM-dd" ) ) ) );
    log.info( String.format( "Value inside body %s", toBeReturned.getBody() ) );
    return toBeReturned;
  }
  
  @CrossOrigin(origins = "http://localhost:8000")
  @GetMapping("/cache")
  public ResponseEntity<Object> getCache() {
    return service.getCache();
  }
}

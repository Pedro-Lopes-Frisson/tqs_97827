package PedroLopes.tqs.Covid19TrackingService.Controller;

import PedroLopes.tqs.Covid19TrackingService.Services.Covid19Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class RestControllerCovid19 {
  @Autowired
  Covid19Service service;

  @CrossOrigin(origins = "http://localhost:8000")
  @GetMapping("/reports/total")
  ResponseEntity<Object> getWorldWideReportForDate( @RequestParam(required = false) Optional<String> date ) {

    return service.getReportForWorld( date.orElse(
      LocalDateTime.now().format( DateTimeFormatter.ofPattern( "yyyy-MM-dd" ) ).toString() ) );
  }

  @CrossOrigin(origins = "http://localhost:8000")
  @GetMapping("/reports")
  public ResponseEntity<Object> getFullDayReportFor( @RequestParam Optional<String> city,
                                                     @RequestParam Optional<String> date ) {
    return service.getReportForCityAndDate( city.orElse(""),
      date.orElse(LocalDateTime.now().format( DateTimeFormatter.ofPattern( "yyyy-MM-dd" ))).toString() );
  }

  @CrossOrigin(origins = "http://localhost:8000")
  @GetMapping("/cache")
  public ResponseEntity<String> getTotalReport() {
    return service.getCache();
  }
}

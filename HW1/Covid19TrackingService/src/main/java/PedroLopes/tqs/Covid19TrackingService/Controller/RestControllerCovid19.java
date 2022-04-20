package PedroLopes.tqs.Covid19TrackingService.Controller;

import PedroLopes.tqs.Covid19TrackingService.Models.RootReport;
import PedroLopes.tqs.Covid19TrackingService.Models.SummaryReport;
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
  ResponseEntity<SummaryReport> reportsAll( @RequestParam(required = false) Optional<String> date ) {
    return ResponseEntity.ok(service.getTotalReport( date.orElse(
      LocalDateTime.now().format( DateTimeFormatter.ofPattern( "yyyy-MM-dd" ) ).toString() ) ).block().getBody());
  }
  
  @CrossOrigin(origins = "http://localhost:8000")
  @GetMapping("/reports")
  public ResponseEntity<RootReport> getTotalReport( @RequestParam Optional<String> city,
                                                    @RequestParam Optional<String> region_province,
                                                    @RequestParam Optional<String> iso,
                                                    @RequestParam Optional<String> region_name,
                                                    @RequestParam Optional<String> query,
                                                    @RequestParam Optional<String> date ) {
    return ResponseEntity.ok( service.getTotalReport( city.orElse( "" ), region_province.orElse( "" ), iso.orElse(
                             "" ),
                           region_name.orElse( "" ), query.orElse( "" ),
                           date.orElse( "" ) ).block().getBody() );
  }
  
  @CrossOrigin(origins = "http://localhost:8000")
  @GetMapping("/cache")
  public ResponseEntity<Object> getTotalReport() {
    return service.getCache();
  }
}

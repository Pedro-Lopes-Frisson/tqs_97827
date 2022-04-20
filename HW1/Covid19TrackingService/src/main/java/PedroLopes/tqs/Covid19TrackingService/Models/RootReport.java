package PedroLopes.tqs.Covid19TrackingService.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class RootReport {
  @Getter
  public ArrayList<ReportData> data;
  
}

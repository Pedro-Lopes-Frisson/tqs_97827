package PedroLopes.tqs.Covid19TrackingService.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ReportData {
  public String date;
  public int confirmed;
  public int deaths;
  public int recovered;
  public int confirmed_diff;
  public int deaths_diff;
  public int recovered_diff;
  public String last_update;
  public int active;
  public int active_diff;
  public double fatality_rate;
  public RegionReportData region;
}

package pedroLopes.tqs.covid19trackingservice.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ReportData implements Serializable  {
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

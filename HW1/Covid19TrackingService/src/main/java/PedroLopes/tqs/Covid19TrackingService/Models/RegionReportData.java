package PedroLopes.tqs.Covid19TrackingService.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class RegionReportData {
  public String iso;
  public String name;
  public String province;
  public String lat;
  @JsonProperty("long")
  public String mylong;
  public ArrayList<CityReportData> cities;
}

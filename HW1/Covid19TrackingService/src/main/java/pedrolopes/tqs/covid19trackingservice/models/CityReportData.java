package pedrolopes.tqs.covid19trackingservice.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class CityReportData implements Serializable  {
  public String name;
  public String date;
  public int fips;
  public String lat;
  @JsonProperty("long")
  public String mylong;
  public int confirmed;
  public int deaths;
  public int confirmed_diff;
  public int deaths_diff;
  public String last_update;
}

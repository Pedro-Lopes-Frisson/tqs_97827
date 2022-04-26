package pedrolopes.tqs.covid19trackingservice.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Setter
@Getter
public class CityReportData implements Serializable  {
  String name;
  String date;
  int fips;
  String lat;
  @JsonProperty("long")
  String mylong;
  int confirmed;
  int deaths;
  int confirmed_diff;
  int deaths_diff;
  String last_update;
}

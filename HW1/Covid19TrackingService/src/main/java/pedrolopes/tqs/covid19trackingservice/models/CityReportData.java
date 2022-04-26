package pedrolopes.tqs.covid19trackingservice.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Setter
@Getter
public class CityReportData implements Serializable {
  String name;
  String date;
  int fips;
  String lat;
  @JsonProperty("long")
  @SerializedName("long")
  String mylong;
  int confirmed;
  int deaths;
  @JsonProperty("confirmed_diff")
  @SerializedName("confirmed_diff")
  int confirmedDiff;
  @JsonProperty("deaths_diff")
  @SerializedName("deaths_diff")
  int deathsDiff;
  String last_update;
}

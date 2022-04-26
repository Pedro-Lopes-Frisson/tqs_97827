package pedrolopes.tqs.covid19trackingservice.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Setter
@Getter
public class RegionReportData implements Serializable  {
  String iso;
  String name;
  String province;
  String lat;
  @JsonProperty("long")
  @SerializedName("long")
  String mylong;
  ArrayList<CityReportData> cities;
}

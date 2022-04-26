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
public class ReportData implements Serializable {
  String date;
  int confirmed;
  int deaths;
  int recovered;
  @JsonProperty("confirmed_diff")
  @SerializedName("confirmed_diff")
  int confirmedDiff;
  @JsonProperty("deaths_diff")
  @SerializedName("deaths_diff")
  int deathsDiff;
  @JsonProperty("recovered_diff")
  @SerializedName("recovered_diff")
  int recoveredDiff;
  @JsonProperty("last_update")
  @SerializedName("last_update"  )
  String lastUpdate;
  int active;
  @SerializedName("active_diff")
  @JsonProperty("active_diff")
  int activeDiff;
  @SerializedName("fatality_rate")
  double fatalityRate;
  RegionReportData region;
}

package pedroLopes.tqs.covid19trackingservice.models;

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
public class SummaryReport implements Serializable {
  /*
  Total report endpoint first object
  url: 'https://covid-19-statistics.p.rapidapi.com/reports/total'
   */
  @JsonProperty("data")
  @SerializedName( value="data" )
  @Getter
  @Setter
  public SummaryReportData summaryReportData;
}

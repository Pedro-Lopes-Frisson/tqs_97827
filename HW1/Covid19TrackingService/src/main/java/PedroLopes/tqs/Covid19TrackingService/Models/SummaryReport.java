package PedroLopes.tqs.Covid19TrackingService.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class SummaryReport {
  /*
  Total report endpoint first object
  url: 'https://covid-19-statistics.p.rapidapi.com/reports/total'
   */
  @JsonProperty("data")
  @SerializedName( value="data" )
  @Getter
  SummaryReportData summaryReportData;
}

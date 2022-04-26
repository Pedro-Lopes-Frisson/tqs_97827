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
public class SummaryReportData implements Serializable {
  /*
  Total report endpoit data from   url: 'https://covid-19-statistics.p.rapidapi.com/reports/total'
   */
  //Requested date
  @JsonProperty("date")
  String date;
  // Last date where data was uploaded
  @JsonProperty("last_update")
  String lastUpdate;
  // confirmed cases of date
  @JsonProperty("confirmed" )
  Long confirmed;
  // differences between day before date
  @JsonProperty("confirmed_diff" )
  Long confirmedDiff;
  // Deaths that occured at date
  @JsonProperty("deaths" )
  Long deaths;
  // differences between day before date
  @JsonProperty("deaths_diff" )
  Long deathsDiff;
  // Recuperation cases
  @JsonProperty("recovered" )
  Long recovered;
  // Recuperation cases difference between the date and the day before
  @JsonProperty("recovered_diff" )
  Long recoveredDiff;
  // active cases as of date
  @JsonProperty("active" )
  Long active;
  // active cases diff as of date and day before date
  @JsonProperty("active_diff" )
  Long activeDiff;
  // percentage of cases and dead people
  @JsonProperty("fatality_rate" )
  Float fatalityRate;
}


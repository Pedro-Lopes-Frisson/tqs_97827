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
public class SummaryReportData implements Serializable {
  /*
  Total report endpoit data from   url: 'https://covid-19-statistics.p.rapidapi.com/reports/total'
   */
  //Requested date
  @JsonProperty("date")
  String date;
  // Last date where data was uploaded
  @JsonProperty("last_update")
  String last_update;
  // confirmed cases of date
  @JsonProperty("confirmed" )
  Long confirmed;
  // differences between day before date
  @JsonProperty("confirmed_diff" )
  Long confirmed_diff;
  // Deaths that occured at date
  @JsonProperty("deaths" )
  Long deaths;
  // differences between day before date
  @JsonProperty("deaths_diff" )
  Long deaths_diff;
  // Recuperation cases
  @JsonProperty("recovered" )
  Long recovered;
  // Recuperation cases difference between the date and the day before
  @JsonProperty("recovered_diff" )
  Long recovered_diff;
  // active cases as of date
  @JsonProperty("active" )
  Long active;
  // active cases diff as of date and day before date
  @JsonProperty("active_diff" )
  Long active_diff;
  // percentage of cases and dead people
  @JsonProperty("fatality_rate" )
  Float fatality_rate;
}


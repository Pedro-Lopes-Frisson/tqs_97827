package pedrolopes.tqs.covid19trackingservice.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
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
  @SerializedName("date")
  String date;
  // Last date where data was uploaded
  @JsonProperty("last_update")
  @SerializedName("last_update")
  String lastUpdate;
  // confirmed cases of date
  @JsonProperty("confirmed")
  @SerializedName("confirmed")
  Long confirmed;
  // differences between day before date
  @JsonProperty("confirmed_diff")
  @SerializedName("confirmed_diff")
  Long confirmedDiff;
  // Deaths that occured at date
  @JsonProperty("deaths")
  @SerializedName("deaths")
  Long deaths;
  // differences between day before date
  @JsonProperty("deaths_diff")
  @SerializedName("deaths_diff")
  Long deathsDiff;
  // Recuperation cases
  @JsonProperty("recovered")
  @SerializedName("recovered")
  Long recovered;
  // Recuperation cases difference between the date and the day before
  @JsonProperty("recovered_diff")
  @SerializedName("recovered_diff")
  Long recoveredDiff;
  // active cases as of date
  @JsonProperty("active")
  @SerializedName("active")
  Long active;
  // active cases diff as of date and day before date
  @JsonProperty("active_diff")
  @SerializedName("active_diff")
  Long activeDiff;
  // percentage of cases and dead people
  @JsonProperty("fatality_rate")
  @SerializedName("fatality_rate")
  Float fatalityRate;
}


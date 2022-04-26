package pedrolopes.tqs.covid19trackingservice.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Setter
@Getter
public class ReportData implements Serializable  {
   String date;
   int confirmed;
   int deaths;
   int recovered;
   int confirmed_diff;
   int deaths_diff;
   int recovered_diff;
   String last_update;
   int active;
   int active_diff;
   double fatality_rate;
   RegionReportData region;
}

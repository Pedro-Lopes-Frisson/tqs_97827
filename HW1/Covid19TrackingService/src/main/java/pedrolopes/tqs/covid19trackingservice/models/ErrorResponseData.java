package pedrolopes.tqs.covid19trackingservice.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.io.Serializable;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ErrorResponseData implements Serializable  {
  @Getter
  ArrayList<String> date;
}

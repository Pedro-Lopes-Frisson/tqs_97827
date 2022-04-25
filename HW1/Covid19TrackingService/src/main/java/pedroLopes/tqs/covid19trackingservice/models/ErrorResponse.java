package pedroLopes.tqs.covid19trackingservice.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse implements Serializable  {
  @Getter
  public ErrorResponseData error;
}

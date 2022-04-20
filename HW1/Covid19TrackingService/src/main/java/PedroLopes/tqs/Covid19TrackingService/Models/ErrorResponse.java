package PedroLopes.tqs.Covid19TrackingService.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
  @Getter
  public ErrorResponseData error;
}

package PedroLopes.tqs.Covid19TrackingService.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ErrorResponseData {
  @Getter
  ArrayList<String> date;
}

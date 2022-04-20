package PedroLopes.tqs.Covid19TrackingService.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@AllArgsConstructor
@Table(name="Cache")
public class Cache {
  @Id
  @GeneratedValue
  Long id;
  private static final long timeToLive = 60000;
  @Getter
  private final long timeRequestWasMade;
  @Getter
  private final String value;
  @Getter
  private final String urlRequest;
  public Cache(String v , String url){
    value = v;
    timeRequestWasMade = System.currentTimeMillis() + timeToLive;
    urlRequest = url;
  }
  
  public Cache( long ttl, String v, String urlRequest ){
    value = v;
    timeRequestWasMade = System.currentTimeMillis() + timeToLive;
    this.urlRequest = urlRequest;
  }
}

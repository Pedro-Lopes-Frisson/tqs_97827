package pedrolopes.tqs.covid19trackingservice.models;

import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Cache")
public class Cache {
  private static final Logger log = LoggerFactory.getLogger(Cache.class);
  private static final long TIMETOLIVE = 30000;
  @Getter
  @Setter
  private long timeRequestWasMade;
  @Getter
  @Setter
  @Column(length = 9000)
  @NonNull
  private Serializable value;
  @Getter
  @Setter
  @NonNull
  @Column(unique = true)
  private String urlRequest;
  @Id
  @GeneratedValue
  Long id;
  
  public Cache( @NonNull String url, @NonNull Serializable value ) {
    this.value = value;
    timeRequestWasMade = System.currentTimeMillis() + TIMETOLIVE;
    urlRequest = url;
  }
  
  public Cache( long ttl, @NonNull String urlRequest, @NonNull Serializable value ) {
    this.value = value;
    timeRequestWasMade = System.currentTimeMillis() + ttl;
    this.urlRequest = urlRequest;
  }
}

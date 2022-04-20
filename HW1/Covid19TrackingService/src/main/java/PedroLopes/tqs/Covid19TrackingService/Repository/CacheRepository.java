package PedroLopes.tqs.Covid19TrackingService.Repository;

import PedroLopes.tqs.Covid19TrackingService.Models.Cache;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CacheRepository extends JpaRepository<Cache, Long> {
  public List<Cache> findBytimeRequestWasMadeLessThan( Long ttl);
  public Cache findByUrlRequest(String url);
  public void deleteByUrlRequest( String request);
}

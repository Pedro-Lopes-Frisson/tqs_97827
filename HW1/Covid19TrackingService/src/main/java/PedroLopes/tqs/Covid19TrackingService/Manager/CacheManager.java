package PedroLopes.tqs.Covid19TrackingService.Manager;

import PedroLopes.tqs.Covid19TrackingService.Models.Cache;
import PedroLopes.tqs.Covid19TrackingService.Repository.CacheRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CacheManager {
  @Autowired CacheRepository repository;
  
  @Getter
  private int numberOfRequests;
  @Getter
  private int numberHits;
  @Getter
  private int numberMisses;
  
  @Scheduled(fixedRate = 5000)
  public void manageCache() {
    repository.findBytimeRequestWasMadeLessThan( System.currentTimeMillis() );
  }
  
  public Object inCache( String requestUrl ) {
    this.numberOfRequests++;
    Cache inMem = repository.findByUrlRequest( requestUrl );
    if ( inMem.getTimeRequestWasMade() < System.currentTimeMillis() ) {
      repository.deleteByUrlRequest( requestUrl );
      return null;
    }
    if ( inMem == null ) {
      this.numberMisses++;
    }
    this.numberHits++;
    return inMem;
  }
  
  public Object saveCache(Cache c){
    return repository.save( c );
  }
  
  
}

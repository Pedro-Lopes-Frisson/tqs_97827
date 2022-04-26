package pedrolopes.tqs.covid19trackingservice.manager;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pedrolopes.tqs.covid19trackingservice.models.Cache;
import pedrolopes.tqs.covid19trackingservice.repository.CacheRepository;

import java.util.List;

@Component
public class CacheManager {
  private static final Logger log = LoggerFactory.getLogger( CacheManager.class );
  @Autowired CacheRepository repository;
  @Getter
  private int numberOfRequests = 0;
  @Getter
  private int numberHits = 0;
  @Getter
  private int numberMisses = 0;
  
  @Scheduled(initialDelay = 100L, fixedDelay = 4000L)
  public void manageCache() {
    List<Cache> cacheList = repository.findBytimeRequestWasMadeLessThan( System.currentTimeMillis() );
    cacheList.forEach( cache -> {
      log.info( "cache {}  was eliminated.", cache.getUrlRequest() );
      repository.delete( cache );
    } );
    
  }
  
  public Object inCache( String requestUrl ) {
    this.numberOfRequests++;
    Cache inMem = repository.findByUrlRequest( requestUrl );
    
    if ( inMem == null || inMem.getTimeRequestWasMade() < System.currentTimeMillis() ) {
      log.info( "Object was not on cache or it was already timed out therefore it was removed manually" );
      repository.deleteByUrlRequest( requestUrl );
      this.numberMisses++;
      return null;
    }
    log.info( "Object found in cache returning" );
    this.numberHits++;
    return inMem;
  }
  
  public Cache saveCache( Cache c ) {
    log.info( "caching Object for request: {} ", c.getUrlRequest() );
    return repository.save( c );
  }
  
  
}

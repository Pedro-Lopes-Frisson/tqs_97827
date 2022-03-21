package integration;

import connection.ISimpleHttpClient;
import geocoding.Address;
import geocoding.AddressResolver;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AddressResolverIT {
  
  private AddressResolver resolver;
  @Mock
  private ISimpleHttpClient geocoding;
  
  @BeforeEach
  public void init() {
    resolver = new AddressResolver( geocoding );
  }
  
  @Test
  public void whenGoodCoordidates_returnAddress() throws IOException, URISyntaxException, ParseException {
    //todo
    
    Optional<Address> result = resolver.findAddressForLocation( 40.640661, - 8.656688 );
    assertEquals( result, Optional.of( new Address( "Cais do Alboi", "Glória e Vera Cruz", "Centro", "3800-246",
      null ) ) );
    
  }
  
  @Test
  public void whenBadCoordidates_trhowBadArrayindex() throws IOException, URISyntaxException, ParseException {
    assertThrows( IndexOutOfBoundsException.class,
      () -> resolver.findAddressForLocation( - 200.000000, - 200.000000 ) );
    
  }
  
  @Test
  public void whenBadURI_throwNull() throws IOException, URISyntaxException, ParseException {
    assertThrows( NullPointerException.class,
      () -> geocoding.get( null ) );
  }
}

package PedroLopes.tqs;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsEqual.equalTo;

public class JsonPlaceHoldertypicodeTest {
  
  @BeforeEach public void setup() {
    RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
  }
  
  @Test public void testEndpointToTestListAllToDosIsAvailable() {
    get( "/todos" ).then().assertThat().statusCode( 200 ).log();
    
  }
  
  @Test public void testTodoNumber4Title() {
    given().pathParam( "todoNumber", 4 ).when().get( "/todos/{todoNumber}" ).then().contentType( ContentType.JSON )
           .assertThat().statusCode( 200 ).log().body().body( "title", equalTo( "et porro tempora" ) );
    
  }
  
  @Test public void whenTesttingAllTodosTestThatID198and199ArePresent() {
    
    List<Map<String, Object>> todos = get( "/todos" ).as( new TypeRef<List<Map<String, Object>>>() {} );
    
    assertThat( todos.size(), greaterThanOrEqualTo( 199 ) );
    assertThat( todos.get( 197 ).get( "id" ), IsEqual.<Object>equalTo( 198 ) );
    assertThat( todos.get( 198 ).get( "id" ), IsEqual.<Object>equalTo( 199 ) );
    assertThat( todos.stream().map( ( map ) -> {
      return map.get( "id" );
    } ).collect( Collectors.toList() ), hasItems( 198,199 ) );
  }
  
  
}

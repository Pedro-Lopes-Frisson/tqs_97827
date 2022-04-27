package pedrolopes.tqs.covid19trackingservice;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CacheSteps {
  
  
  final private WebDriver driver = new FirefoxDriver();
  
  @When("I go over the page {string}")
  public void iGoOverThePageLocalhostCache( String url ) {
    driver.get( url );
  }
  
  @Then("some statistics should appear like {string}, {string},{string}")
  public void someStatisticsShouldAppearLikeNumberOfRequestsNumberOfMissesNumberOfHits( String requests,
                                                                                        String hits,
                                                                                        String misses ) {
    
    WebElement requestsPTag =
      driver.findElement( By.cssSelector( "#root > div > div:nth-child(2) > div:nth-child(1) > p:nth-child(1)" ) );
    WebElement hitsPTag =
      driver.findElement( By.cssSelector( "#root > div > div:nth-child(2) > div:nth-child(2) > p:nth-child(1)" ) );
    WebElement missesPTag =
      driver.findElement( By.cssSelector( "#root > div > div:nth-child(2) > div:nth-child(3) > p:nth-child(1)" ) );
    System.out.println( requests );
    System.out.println( misses );
    System.out.println( hits );
    System.out.println( requestsPTag.getText() );
    System.out.println( missesPTag.getText() );
    System.out.println( hitsPTag.getText() );
    assertThat( requestsPTag.getText() ).isEqualTo( requests );
    assertThat( missesPTag.getText() ).isEqualTo( misses );
    assertThat( hitsPTag.getText() ).isEqualTo( hits );
  }
  
  @And("all of them should have the values {int}")
  public void allOfThemShouldHaveTheValues( int arg0 ) throws InterruptedException {
    
    Thread.sleep( 4000 );
    WebElement requests =
      driver.findElement( By.cssSelector( "#root > div > div:nth-child(2) > div:nth-child(1) > p:nth-child(2)" ) );
    WebElement misses =
      driver.findElement( By.cssSelector( "#root > div > div:nth-child(2) > div:nth-child(2) > p:nth-child(2)" ) );
    WebElement hits =
      driver.findElement( By.cssSelector( "#root > div > div:nth-child(2) > div:nth-child(3) > p:nth-child(2)" ) );
    
    
    assertThat( requests.getText() ).isEqualTo( arg0 + "" );
    assertThat( misses.getText() ).isEqualTo( arg0 + "" );
    assertThat( hits.getText() ).isEqualTo( arg0 + "" );
  }
  
  @And("Type in {string}")
  public void typeInAutauga( String s ) {
    WebElement input =
      driver.findElement(
        By.cssSelector( "#root > div > div:nth-child(2) > div:nth-child(1) > div > div > div > input" ) );
    input.sendKeys( s );
    
    
  }
  
  
  @And("there should be {int} requests and misses {int}, and hits {int}")
  public void thereShouldBeRequestsAndMissesAndHits( int arg0, int arg1, int arg2 ) {
    
    driver.get( "localhost:3000/cache" );
    
    final WebDriverWait wait = new WebDriverWait( driver, 2 );
    WebElement requests =
      driver.findElement( By.cssSelector( "#root > div > div:nth-child(2) > div:nth-child(1) > p:nth-child(2)" ) );
    WebElement misses =
      driver.findElement( By.cssSelector( "#root > div > div:nth-child(2) > div:nth-child(2) > p:nth-child(2)" ) );
    WebElement hits =
      driver.findElement( By.cssSelector( "#root > div > div:nth-child(2) > div:nth-child(3) > p:nth-child(2)" ) );
    
    wait.until( ExpectedConditions.visibilityOf( requests ) );
    assertThat( requests.getText() ).isEqualTo( arg0 + "" );
    assertThat( misses.getText() ).isEqualTo( arg1 + "" );
    assertThat( hits.getText() ).isEqualTo( arg2 + "");
  }
  
  @And("Click On Search Button")
  public void clickOnSearchButton() throws InterruptedException {
    WebElement button =
      driver.findElement( By.xpath( "//*[@id=\"root\"]/div/div[2]/div[1]/div/div/div/button" ) );
    
    button.click();
  }
  
  @And("Select Option Two Days Ago")
  public void selectOptionTwoDaysAgo() {
    
    WebElement selectElement =
      driver.findElement( By.cssSelector( "#root > div > div:nth-child(2) > div:nth-child(2) > select" ) );
    Select select = new Select( selectElement );
    select.selectByValue( "2" );
  }
  
  @And("Check That Confirmed cases is not zero in {string}")
  public void checkThatConfirmedCasesIsNotZeroInAutauga( String city ) throws InterruptedException {
    
    WebElement h1Cidades =
      driver.findElement( By.xpath( "//*[@id=\"root\"]/div/div[1]/h1" ) );
    assertThat( h1Cidades.getText() ).contains( city );
    
    
    final WebDriverWait wait = new WebDriverWait( driver, 10 );
    
    wait.until(
      ExpectedConditions.presenceOfElementLocated( By.xpath( "/html/body/div/div/div[3]/div[1]/div/div[1]" ) ) );
    
    WebElement confirmed =
      driver.findElement( By.cssSelector( "#confirmed > div.text-center.p-2.card-subtitle.h6" ) );
    
    assertThat( confirmed.getText() ).isNotEqualToIgnoringWhitespace( "" );
  }
  
  @And("Select Option Yesterday")
  public void selectOptionYesterday() {
    
    WebElement selectElement =
      driver.findElement( By.cssSelector( "#root > div > div:nth-child(2) > div:nth-child(2) > select" ) );
    Select select = new Select( selectElement );
    select.selectByValue( "1" );
  }
  
  
  @Then("I get to see {string} cases and {string} for {string}")
  public void iGetToSeeConfirmedCasesAndDeathsForAutauga(String cases, String deaths, String city) {
    
    final WebDriverWait wait = new WebDriverWait( driver, 10 );
    
    wait.until(
      ExpectedConditions.presenceOfElementLocated( By.cssSelector( "#confirmed > div:nth-child(1)")));
    
    WebElement confirmed =
      driver.findElement( By.cssSelector( "#confirmed > div:nth-child(1)"));
    
    WebElement deathsTitle =
      driver.findElement( By.cssSelector( "#deaths > div:nth-child(1)" ) );
    
    WebElement h1Cidades =
      driver.findElement( By.cssSelector( "#root > div > div:nth-child(1) > h1" ));
    assertThat( h1Cidades.getText() ).contains( city );
    
    assertThat( confirmed.getText() ).isEqualTo( cases );
    assertThat( deathsTitle.getText() ).isEqualTo( deaths );
  }
  
  @And("Close Browser")
  public void closeBrowser() {
    driver.quit();
  }
}

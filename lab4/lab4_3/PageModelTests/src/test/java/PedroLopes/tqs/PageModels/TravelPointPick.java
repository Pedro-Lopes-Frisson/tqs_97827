package PedroLopes.tqs.PageModels;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class TravelPointPick extends BasicPage {
  
  @FindBy(xpath = "/html/body/div[3]/form/select[1]")
  WebElement fromPort;// By.name( "fromPort" );
  
  @FindBy(xpath = "/html/body/div[3]/form/select[2]")
  WebElement toPort;// By.name( "toPort" );
  
  @FindBy(css = ".btn-primary")
  WebElement confirm;// By.cssSelector( ".btn-primary" );
  
  public TravelPointPick( WebDriver driver ) {
    super( driver );
  }
  
  public TravelPointPick( WebDriver driver, int timeoutTime ) {
    super( driver );
    setTimeoutSec( timeoutTime );
  }
  
  public void chooseFromPort( int i ) {
    Select drop = new Select( (WebElement) fromPort );
    drop.selectByIndex( i );
  }
  
  public void chooseToPort( int i ) {
    Select drop = new Select( (WebElement) toPort );
    drop.selectByIndex( i );
  }
  
  public void confirmFlight() {
    confirm.click();
  }
  
}
/*
 *
 * By findFlights = By.xpath( "/html/body/div[3]/form/div/input" );
 * By chooseFlight =
 * By.cssSelector( "body > div.container > table > tbody > tr:nth-child(4) > td:nth-child(2) > input" );
 * By inputName = By.id("inputName");
 * By address= By.id("address");
 * By city= By.id("city");
 * By state= By.id("state");
 * By zipCode= By.id("zipCode");
 * By creditCardNumber= By.id("creditCardNumber");
 * By nameOnCard = By.id("nameOnCard");
 * By rememberMeBtn = By.id("rememberMe");
 * By confirmPurchaceBtn = By.cssSelector(".btn-primary");
 * By otherBtn = By.cssSelector("pre");
 *
 *
 */
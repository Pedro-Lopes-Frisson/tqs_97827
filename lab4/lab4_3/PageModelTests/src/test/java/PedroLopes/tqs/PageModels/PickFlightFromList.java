package PedroLopes.tqs.PageModels;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PickFlightFromList extends BasicPage {
  
  
  @FindBy(xpath = "/html/body/div[2]/table/tbody/tr[1]/td[1]/input")
  WebElement chooseFlight;//By.cssSelector("body > div.container > table > tbody > tr:nth-child(4) > td:nth-child(2) > input");
  
  @FindBy(xpath = "/html/body/div[2]/table/tbody/tr[1]/td[6]")
  WebElement flightPrice;//By.cssSelector("body > div.container > table > tbody > tr:nth-child(4) > td:nth-child(2) >
  // input");
  
  @FindBy(xpath = "/html/body/div[2]/table/thead/tr/th[4]")
  WebElement departsCountry;
  @FindBy(xpath = "/html/body/div[2]/table/thead/tr/th[5]")
  WebElement arrivesCountry;
  
  public PickFlightFromList( WebDriver driver ) {
    super( driver );
  }
  public PickFlightFromList( WebDriver driver, int t ) {
    super( driver );
    setTimeoutSec( t );
  }
  
  public void chooseFlight(){
    chooseFlight.click();
  }
  
  public String getDepartCountry(){
    return this.departsCountry.getText();
  }
  public String getArrivesCountry(){
    return this.arrivesCountry.getText();
  }
  
  public String getFlightPrice(){
    return this.flightPrice.getText();
  }
}

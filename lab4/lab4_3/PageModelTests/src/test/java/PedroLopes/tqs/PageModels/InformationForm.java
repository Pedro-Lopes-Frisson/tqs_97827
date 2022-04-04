package PedroLopes.tqs.PageModels;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.logging.Level;

public class InformationForm extends BasicPage {
  public InformationForm( WebDriver driver ) {
    super( driver );
  }
  public InformationForm( WebDriver driver, int t ) {
    super( driver );
    setTimeoutSec( t );
  }

  public WebElement getInputName() {
    return inputName;
  }

  public WebElement getAddress() {
    return address;
  }

  public WebElement getCity() {
    return city;
  }

  public WebElement getState() {
    return state;
  }

  public WebElement getZipCode() {
    return zipCode;
  }

  public WebElement getCreditCardNumber() {
    return creditCardNumber;
  }

  public WebElement getNameOnCard() {
    return nameOnCard;
  }

  public WebElement getRememberMeBtn() {
    return rememberMeBtn;
  }

  public WebElement getConfirmPurchaceBtn() {
    return confirmPurchaceBtn;
  }

 @FindBy(id = "inputName")
 WebElement inputName;
 @FindBy(id = "address")
 WebElement address;
 @FindBy(id = "city")
 WebElement city;
 @FindBy(id = "state")
 WebElement state;
 @FindBy(id = "zipCode")
 WebElement zipCode;
 @FindBy(id = "creditCardNumber")
 WebElement creditCardNumber;
 @FindBy(id = "nameOnCard")
 WebElement nameOnCard;
 @FindBy(id = "rememberMe")
 WebElement rememberMeBtn;
 @FindBy(css= ".btn-primary")
 WebElement confirmPurchaceBtn;


  public void fillInputName( String inputName ) {
    this.inputName.sendKeys( inputName );
  }

  public void fillAddress( String address ) {
    this.address.sendKeys( address );
  }

  public void fillCity( String city ) {
    this.city.sendKeys(city);
  }

  public void fillState( String state ) {
    this.state.sendKeys(state);
  }

  public void fillZipCode( String zipCode ) {
    this.zipCode.sendKeys(zipCode);
  }

  public void fillCreditCardNumber( String creditCardNumber ) {
    this.creditCardNumber.sendKeys(creditCardNumber);
  }

  public void fillNameOnCard( String nameOnCard ) {
    this.nameOnCard.sendKeys(nameOnCard);
  }
  public String getText(WebElement e){
    return e.getAttribute( "value" );
  }
  public void setRememberMeBtn(){
    rememberMeBtn.click();
  }
  public void buy(){
    confirmPurchaceBtn.click();
  }
}

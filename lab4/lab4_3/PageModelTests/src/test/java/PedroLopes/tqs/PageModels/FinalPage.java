package PedroLopes.tqs.PageModels;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FinalPage extends BasicPage {
  
  public FinalPage( WebDriver driver ) {
    super( driver );
  }
  
  @FindBy(xpath = "/html/body/div[2]/div/pre") WebElement e;
  
  public String getTexte(){
    return e.getText();
  }
}

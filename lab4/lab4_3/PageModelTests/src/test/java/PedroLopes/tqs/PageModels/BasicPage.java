package PedroLopes.tqs.PageModels;


import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BasicPage {
  
  static final Logger log = Logger.getLogger(BasicPage.class.toString());
  
  WebDriver driver;
  WebDriverWait wait;
  int timeoutSec = 5; // wait timeout (5 seconds by default)
  
  public BasicPage(WebDriver driver) {
    this.driver = driver;
    wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSec));
    PageFactory.initElements(driver, this);
  }
  
  
  public void setTimeoutSec(int timeoutSec) {
    this.timeoutSec = timeoutSec;
  }
  
  public void visit(String url) {
    driver.get(url);
  }
  
  public WebElement find(By element) {
    return driver.findElement(element);
  }
  
  public void click(By element) {
    find(element).click();
  }
  
  public void type(By element, String text) {
    find(element).sendKeys(text);
  }
  
  public boolean isDisplayed(By locator) {
    try {
      wait.until( ExpectedConditions.visibilityOfElementLocated(locator));
    } catch (TimeoutException e) {
      //log.log( System.Logger.Level.ERROR,String.format("Timeout of {} wait for {}", timeoutSec, locator));
      log.log( Level.ALL, String.format("Timeout of %d wait for %s", timeoutSec, locator.toString()) );
      return false;
    }
    return true;
  }
  
}

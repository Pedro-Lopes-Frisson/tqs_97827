package PedroLopes.tqs;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;


public class HelloWorldChromeJupiterTest {
  
  
  static final Logger log = Logger.getLogger( "Ola" );
  private WebDriver driver;
    @BeforeAll
    static void setupClass() {
      WebDriverManager.chromedriver().setup();
    }
    @BeforeEach
    void setup() {
      driver = new FirefoxDriver();
    }
    
    @Test
    void test() {
      // Exercise
      String sutUrl = "https://bonigarcia.dev/selenium-webdriver-java/";
      driver.get(sutUrl);
      String title = driver.getTitle();
      log.log( Level.WARNING, "The title of " +  sutUrl + " title " + title );
      // Verify
      assertThat(title).isEqualTo("Hands-On Selenium WebDriver with Java");
    }
    
    @AfterEach
    void teardown() {
      driver.quit();
    }
    
}

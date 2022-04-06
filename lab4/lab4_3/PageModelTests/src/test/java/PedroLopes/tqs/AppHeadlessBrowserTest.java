package PedroLopes.tqs;


import io.github.bonigarcia.seljup.Arguments;
import PedroLopes.tqs.PageModels.FinalPage;
import PedroLopes.tqs.PageModels.InformationForm;
import PedroLopes.tqs.PageModels.PickFlightFromList;
import PedroLopes.tqs.PageModels.TravelPointPick;
import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Unit test for simple App.
 */
@ExtendWith(SeleniumJupiter.class)
public class AppHeadlessBrowserTest {
  @Test
  public void buyTicketHeadless(@Arguments("--headless") FirefoxDriver driver ) {

    driver.get( "https://blazedemo.com/" );
    TravelPointPick travelPointPick = new TravelPointPick( driver, 5 );
    travelPointPick.chooseFromPort( 1 );
    travelPointPick.chooseToPort( 2 );
    travelPointPick.confirmFlight();
    PickFlightFromList p = new PickFlightFromList( driver );
    assertThat( p.getDepartCountry() ).contains( "Philadelphia" );
    assertThat( p.getArrivesCountry() ).contains( "London" );
    String price = p.getFlightPrice();
    p.chooseFlight();

    InformationForm i = new InformationForm( driver, 10 );

    i.fillInputName( "Pedro" );
    try {
      TimeUnit.SECONDS.sleep( 1l );
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    assertThat( i.getText( i.getInputName() ) ).isEqualTo( "Pedro" );
    i.fillAddress( "asdfasdfas" );
    assertThat( i.getText( i.getAddress() ) ).isEqualTo( "asdfasdfas" );
    i.fillCity( "Europe" );
    assertThat( i.getText( i.getCity() ) ).isEqualTo( "Europe" );
    i.fillState( "Algarve" );
    assertThat( i.getText( i.getState() ) ).isEqualTo( "Algarve" );

    i.fillZipCode( "1234-123" );
    assertThat( i.getText( i.getZipCode() ) ).isEqualTo( "1234-123" );
    i.fillCreditCardNumber( "123" );
    assertThat( i.getText( i.getCreditCardNumber() ) ).isEqualTo( "123" );
    i.fillNameOnCard( "YOOOOOOOOOOOOOOOOOOOOOOOOOOOO" );
    assertThat( i.getText( i.getNameOnCard() ) ).isEqualTo( "YOOOOOOOOOOOOOOOOOOOOOOOOOOOO" );

    i.buy();
    try {
      TimeUnit.SECONDS.sleep( 1l );
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    assertThat( driver.getTitle()).isEqualTo( "BlazeDemo Confirmation");
  }
}

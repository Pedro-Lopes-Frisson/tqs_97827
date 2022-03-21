package com.PedroLopes.tqs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import static org.mockito.Mockito.*;

public class StockPortFolioTest {
  StockPortfolio stockPortfolio;
  
  IStockMarketService iStockMarketService;
  Stock s1;
  Stock s2;
  
  @BeforeEach
  public void before() {
    iStockMarketService = mock( IStockMarketService.class );
    stockPortfolio = new StockPortfolio( iStockMarketService );
    
    // filling StockPortFolio
    s1 = new Stock("EDP", 10);
    s2 = new Stock("Navigator", 5);
    
    stockPortfolio.addStock( s1 );
    stockPortfolio.addStock( s2 );
    
    //stubbing
    when( iStockMarketService.lookUpPrice( "EDP" ) ).thenReturn( 2.5 );
    when( iStockMarketService.lookUpPrice( "Navigator" ) ).thenReturn( 1.5 );
  }
  @AfterEach
  public void tearDown(){
    s1 = s2 = null;
    iStockMarketService = null;
    stockPortfolio = null;
  }
  
  @DisplayName( "Testing total value of a StockPortFolio" )
  @Test
  public void getTotaValueTest(){
    //assertEquals( 25 + 5 * 1.5 ,stockPortfolio.getTotalValue());
    assertThat(stockPortfolio.getTotalValue(), is(25 + 5 * 1.5));
    // test if the service was used
    verify( iStockMarketService ).lookUpPrice( "EDP" );
    verify( iStockMarketService ).lookUpPrice( "Navigator" );
  }
}

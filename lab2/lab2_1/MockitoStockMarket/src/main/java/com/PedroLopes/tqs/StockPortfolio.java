package com.PedroLopes.tqs;

import java.util.ArrayList;
import java.util.List;

public class StockPortfolio {
  
  List<Stock> stocks = new ArrayList<>();
  IStockMarketService iSocketMarket;
  
  public StockPortfolio( IStockMarketService iSocket ) {
    this.iSocketMarket = iSocket;
  }
  
  
  public void addStock( Stock stock ) {
    stocks.add( stock );
  }
  
  public double getTotalValue() {
    Double reduce =
      stocks.stream().map( stock -> stock.getQuantity() * iSocketMarket.lookUpPrice( stock.getLabel() ) ).reduce( 0.0,
      Double::sum );
    return reduce ;
  }
}

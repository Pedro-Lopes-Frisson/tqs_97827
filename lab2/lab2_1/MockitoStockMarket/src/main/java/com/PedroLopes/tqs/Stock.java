package com.PedroLopes.tqs;



public  class Stock{
  String label;
  Integer quantity;


  public Stock(String l, Integer i){
    this.label = l;
    this.quantity = i;
  }

  public String getLabel(){return this.label;}
  public Integer getQuantity(){return this.quantity;}
  public void getLabel(String s){this.label = s;}
  public void getQuantity(Integer q){this.quantity = q;}
}

package tqsdemo.CarService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Table(name = "car")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Car {
  @Id
  @GeneratedValue
  Long carID;
  @Column
  String maker;
  
  @Column
  String model;
  
  public Car(String m , String m1){
    this.maker = m;
    this.model = m1;
  }
  
  public Long getCarID() {
    return carID;
  }
  
  public void setCarID( Long carID ) {
    this.carID = carID;
  }
  
  public String getMaker() {
    return maker;
  }
  
  public void setMaker( String maker ) {
    this.maker = maker;
  }
  
  public String getModel() {
    return model;
  }
  
  public void setModel( String model ) {
    this.model = model;
  }
}

package retail.service.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

public class Product {

  @JsonProperty
  @NotEmpty
  private String id;

  @JsonProperty
  @NotEmpty
  private String name;

  @JsonProperty
  private String description;

  @JsonProperty
  @NotEmpty
  private String tag;

  @JsonProperty
  @NotEmpty
  private List<ProductPrice> pricePoints;

  public Product() {

  }

  public Product(String id, String name, String description, String tag, List<ProductPrice> pricePoints) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.tag = tag;
    this.pricePoints = pricePoints;
  }

  public String getId(){
    return this.id;
  }

  public String getName(){
    return this.name;
  }

  public String getDescription(){
    return this.description;
  }

  public String getTag(){
    return this.tag;
  }

  public List<ProductPrice> getPricePoints(){
    return this.pricePoints;
  }

  public void updatePrice(ProductPrice productPrice){
    this.getPricePoints().removeIf(x -> x.getCurrency().equals(productPrice.getCurrency()));
    this.getPricePoints().add(productPrice);
  }
}

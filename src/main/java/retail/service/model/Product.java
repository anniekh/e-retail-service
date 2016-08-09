package retail.service.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.HashMap;

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
  private HashMap<String, String> pricePoint;

  public Product() {

  }

  public Product(String id, String name, String description, String tag, HashMap<String, String> pricePoint) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.tag = tag;
    this.pricePoint = pricePoint;
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

  public HashMap<String, String> getPricePoint(){
    return this.pricePoint;
  }
}

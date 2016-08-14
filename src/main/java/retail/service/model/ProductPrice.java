package retail.service.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;


public class ProductPrice {

  @JsonProperty
  private String currency = "USD";

  @JsonProperty
  @NotEmpty
  private String price;

  public ProductPrice() {
  }

  public ProductPrice(String currency, String price) {
    this.currency = currency;
    this.price = price;
  }

  public String getCurrency(){
    return this.currency;
  }

  public String getprice(){
    return this.price;
  }
}

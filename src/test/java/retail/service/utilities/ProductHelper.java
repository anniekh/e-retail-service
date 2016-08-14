package retail.service.utilities;

import retail.service.model.Product;
import retail.service.model.ProductPrice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductHelper {

  public static Product createProduct() {
    List<ProductPrice> price = getProductPrices();
    HashMap<String, String> prices = new HashMap<>();
    prices.put("GBP", "10");
    return new Product("1", "compass", "a geometrical tool", "science", price);
  }

  public static List<ProductPrice> getProductPrices() {
    ProductPrice productPrice = new ProductPrice();
    List<ProductPrice> price = new ArrayList<>();
    price.add(productPrice);
    return price;
  }
}

package retail.service.model;

import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.json.*;
import org.springframework.boot.test.json.*;
import org.springframework.test.context.junit4.*;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@JsonTest
public class ProductJSONTest {

  private String productJSONBody = "{\n" +
          "\t\"id\": \"1\",\n" +
          "\t\"name\": \"compass\",\n" +
          "\t\"description\": \"a geometrical tool\",\n" +
          "\t\"tag\": \"science\",\n" +
          "\t\"pricePoints\": [{\n" +
          "\t\t\"currency\": \"GBP\",\n" +
          "\t\t\"price\": \"50\"\n" +
          "\t}]\n" +
          "}";

  @Autowired
  private JacksonTester<Product> productJson;


  @Test
  public void serializeProduct() throws Exception {
    Product product = createProduct();
    assertThat(this.productJson.write(product)).hasJsonPathStringValue("@.id");
    assertThat(this.productJson.write(product)).hasJsonPathStringValue("@.name");
    assertThat(this.productJson.write(product)).extractingJsonPathStringValue("@.id")
            .isEqualTo("1");
    assertThat(this.productJson.write(product)).extractingJsonPathStringValue("@.name")
            .isEqualTo("compass");
  }

  @Test
  public void deserializeProduct() throws Exception {
    assertThat(this.productJson.parse(productJSONBody)).isInstanceOf(Product.class);
    assertThat(this.productJson.parseObject(productJSONBody).getId()).isEqualTo("1");
  }

  private Product createProduct() {
    List<ProductPrice> prices = new ArrayList() ;
    prices.add(new ProductPrice("USD", "50"));
    return new Product("1", "compass", "a geometrical tool", "science", prices);
  }
}
package retail.service.model;

import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.json.*;
import org.springframework.boot.test.json.*;
import org.springframework.test.context.junit4.*;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@JsonTest
public class ProductJSONTest {

  private String productJSONBody = "{\"id\":\"1\",\"name\":\"compass\",\"description\":\"a geometrical tool\",\"tag\":\"science\",\"pricePoint\":{\"GBP\":\"10\"}};";


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
    HashMap<String, String> prices = new HashMap<>();
    prices.put("GBP", "10");
    return new Product("1", "compass", "a geometrical tool", "science", prices);
  }
}
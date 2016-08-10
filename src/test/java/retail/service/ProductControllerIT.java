package retail.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import retail.service.model.Product;

import java.io.IOException;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerIT {

  @Autowired
  private TestRestTemplate restTemplate;

  private ProductManager productManager;

  @Before
  public void setup() {
    productManager = ProductManager.getInstance();
    productManager.addProduct(createProduct());
  }

  @Test
  public void oKWhenProductExists() throws IOException {
    assertThat(this.restTemplate.getForEntity("/product/1", String.class, "sframework").getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  public void notFoundWhenProductDoesNotExist() throws IOException {
    assertThat(this.restTemplate.getForEntity("/product/1001", String.class, "sframework").getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

  private Product createProduct() {
    HashMap<String, String> prices = new HashMap<>();
    prices.put("GBP", "10");
    return new Product("1", "compass", "a geometrical tool", "science", prices);
  }



}
package retail.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
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

  @Test
  public void returnListOfExistingProducts() throws IOException {
    productManager.addProduct(new Product("2", "telescope", "an astronomical tool", "science", new HashMap<>()));
    assertThat(this.restTemplate.getForEntity("/products", String.class, "sframework").getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  public void returnOKWithEmptyIfNoProductExist() throws IOException {
    productManager.removeProductById("1");
    assertThat(this.restTemplate.getForEntity("/products", String.class, "sframework").getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  public void createProductIfProductIdDoesNotExists() throws IOException {
    String content = "{\n" +
            "\t\"id\": \"2\",\n" +
            "\t\"name\": \"telescope\",\n" +
            "\t\"description\": \"an astronomical tool\",\n" +
            "\t\"tag\": \"science\",\n" +
            "\t\"pricePoint\": {}\n" +
            "}";
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity = new HttpEntity<>(content, headers);
    assertThat(this.restTemplate.exchange("/product", HttpMethod.PUT, entity, String.class).getStatusCode()).isEqualTo(HttpStatus.CREATED);
  }

  @Test
  public void UpdateProductIfProductIdAlreadyExist() throws IOException {
    String content = "{\n" +
            "\t\"id\": \"1\",\n" +
            "\t\"name\": \"telescope\",\n" +
            "\t\"description\": \"an astronomical tool\",\n" +
            "\t\"tag\": \"science\",\n" +
            "\t\"pricePoint\": {}\n" +
            "}";
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity = new HttpEntity<>(content, headers);
    assertThat(this.restTemplate.exchange("/product", HttpMethod.PUT, entity, String.class).getStatusCode()).isNotEqualTo(HttpStatus.CREATED);
    assertThat(this.restTemplate.exchange("/product", HttpMethod.PUT, entity, String.class).getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  private Product createProduct() {
    HashMap<String, String> prices = new HashMap<>();
    prices.put("GBP", "10");
    return new Product("1", "compass", "a geometrical tool", "science", prices);
  }



}
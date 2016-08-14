package retail.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import retail.service.model.Product;
import retail.service.model.ProductPrice;
import retail.service.utilities.ProductHelper;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    productManager.addProduct(ProductHelper.createProduct());
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
  public void updatePriceDefaultCurrencyIsUSD() throws IOException {
    String content = "{\n" +
            "\t\"price\": \"500\"\n" +
            "}";
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity = new HttpEntity<>(content, headers);
    assertThat(this.restTemplate.exchange("/product/1/price", HttpMethod.POST, entity, String.class).getStatusCode()).isEqualTo(HttpStatus.OK);

    String updatedProduct = this.restTemplate.getForEntity("/product/1", String.class, "sframework").getBody();
    assertThat(updatedProduct.contains("USD"));
    assertThat(updatedProduct.contains("500"));
  }

  @Test
  public void updateCurrencyAndPriceIfProductIdExists() throws IOException {
    String content = "{\n" +
            "\t\"currency\": \"INR\",\n" +
            "\t\"price\": \"500\"\n" +
            "}";
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity = new HttpEntity<>(content, headers);
    assertThat(this.restTemplate.exchange("/product/1/price", HttpMethod.POST, entity, String.class).getStatusCode()).isEqualTo(HttpStatus.OK);

    String updatedProduct = this.restTemplate.getForEntity("/product/1", String.class, "sframework").getBody();
    assertThat(updatedProduct.contains("INR"));
    assertThat(updatedProduct.contains("500"));
  }

  @Test
  public void multipleCurrenciesAndPrices() throws IOException {
    String content = "{\n" +
            "\t\"currency\": \"AUD\",\n" +
            "\t\"price\": \"60\"\n" +
            "}";
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity = new HttpEntity<>(content, headers);
    assertThat(this.restTemplate.exchange("/product/1/price", HttpMethod.POST, entity, String.class).getStatusCode()).isEqualTo(HttpStatus.OK);

    String updatedProduct = this.restTemplate.getForEntity("/product/1", String.class, "sframework").getBody();
    assertThat(updatedProduct.contains("INR"));
    assertThat(updatedProduct.contains("AUD"));
    assertThat(updatedProduct.contains("500"));
    assertThat(updatedProduct.contains("60"));
  }


  @Test
  public void returnListOfExistingProducts() throws IOException {
    productManager.addProduct(new Product("2", "telescope", "an astronomical tool", "science", ProductHelper.getProductPrices()));
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
            "\t\"id\": \"3\",\n" +
            "\t\"name\": \"microscope\",\n" +
            "\t\"description\": \"a biology lab tool\",\n" +
            "\t\"tag\": \"science\",\n" +
            "\t\"pricePoints\": [{\n" +
            "\t\t\"currency\": \"GBP\",\n" +
            "\t\t\"price\": \"70\"\n" +
            "\t}]\n" +
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
            "\t\"name\": \"compass\",\n" +
            "\t\"description\": \"a geometrical tool\",\n" +
            "\t\"tag\": \"science\",\n" +
            "\t\"pricePoints\": [{\n" +
            "\t\t\"currency\": \"GBP\",\n" +
            "\t\t\"price\": \"50\"\n" +
            "\t}]\n" +
            "}";;
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity = new HttpEntity<>(content, headers);
    assertThat(this.restTemplate.exchange("/product", HttpMethod.PUT, entity, String.class).getStatusCode()).isNotEqualTo(HttpStatus.CREATED);
    assertThat(this.restTemplate.exchange("/product", HttpMethod.PUT, entity, String.class).getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  public void shouldReturnErrorIfJSONIsIllFormed() throws IOException {
    String invalidcontent = "{\n" +
            "\t\"id\": \"1\",\n" +
            "\t\"name\": \"compass\",\n" +
            "\t\"description\": \"a geometrical tool\",\n" +
            "\t\"tag\": \"science\",\n" +
            "\t\"pricePoints\": {\n" +
            "\t\t\"currency\": \"GBP\",\n" +
            "\t\t\"price\": \"50\"\n" +
            "\t}]\n" +
            "}";;
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity = new HttpEntity<>(invalidcontent, headers);
    assertThat(this.restTemplate.exchange("/product", HttpMethod.PUT, entity, String.class).getStatusCode()).isNotEqualTo(HttpStatus.CREATED);
    assertThat(this.restTemplate.exchange("/product", HttpMethod.PUT, entity, String.class).getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
  }

}
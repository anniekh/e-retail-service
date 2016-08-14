package retail.service;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import retail.service.model.Product;
import retail.service.utilities.ProductHelper;

import java.util.HashMap;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc
public class ProductControllerTest {

  @Autowired
  private MockMvc mockMvc;

  private ProductManager productManager;

  @Before
  public void setup() {
    productManager = ProductManager.getInstance();
  }

  @Test
  public void getProductById() throws Exception {
    ProductManager productManager = ProductManager.getInstance();
    productManager.addProduct(ProductHelper.createProduct());
    this.mockMvc.perform(get("/product/1").accept(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));

  }

  @Test
  public void setProductPriceById() throws Exception {
    ProductManager productManager = ProductManager.getInstance();
    productManager.addProduct(ProductHelper.createProduct());
    String content = "{\n" +
            "\t\"currency\": \"GBP\",\n" +
            "\t\"price\": \"50\"\n" +
            "}";
    this.mockMvc.perform(post("/product/1/price").accept(MediaType.APPLICATION_JSON_UTF8)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(content))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));

  }

  @Test
  public void products() throws Exception {
    this.mockMvc.perform(get("/products").accept(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));

  }

  @Test
  public void productResponseEntity() throws Exception {
    String content = "{\n" +
            "\t\"id\": \"4\",\n" +
            "\t\"name\": \"scissor\",\n" +
            "\t\"description\": \"an arts tool\",\n" +
            "\t\"tag\": \"arts\",\n" +
            "\t\"pricePoints\": [{\n" +
            "\t\t\"currency\": \"GBP\",\n" +
            "\t\t\"price\": \"5\"\n" +
            "\t}]\n" +
            "}";
    this.mockMvc.perform(put("/product").accept(MediaType.APPLICATION_JSON_UTF8)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(content))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
  }

}
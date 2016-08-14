package retail.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import retail.service.exceptions.ProductNotFoundException;
import retail.service.model.Product;

import java.util.ArrayList;

@RestController
public class ProductController {

  @RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
  public @ResponseBody Product getProductByName(@PathVariable String id) {
    if(ProductManager.getInstance().getProductById(id).isPresent())
      return ProductManager.getInstance().getProductById(id).get();
    else
      throw new ProductNotFoundException();
  }

  @RequestMapping(name = "/products", method = RequestMethod.GET)
  public @ResponseBody ArrayList<Product> products() {
    return ProductManager.getInstance().getAllProducts();
  }

  @RequestMapping(name = "/product", method = RequestMethod.PUT)
  public ResponseEntity<Product> productRequestEntity(@RequestBody Product product){
    HttpStatus httpStatus = HttpStatus.CREATED;
    if(ProductManager.getInstance().getProductById(product.getId()).isPresent()) {
      ProductManager.getInstance().updateProduct(product);
      httpStatus = HttpStatus.OK;
    }
    else {
      ProductManager.getInstance().addProduct(product);
    }
    return new ResponseEntity<>(product, httpStatus);
  }
}
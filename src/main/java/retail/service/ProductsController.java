package retail.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import retail.service.model.Product;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ProductsController {

  private final AtomicLong counter = new AtomicLong();

  @RequestMapping(name = "/product", method = RequestMethod.GET)
  public Product product(@RequestParam(value="name", defaultValue="product") String name,
                         @RequestParam(value="description", defaultValue="prod description") String description) {
    return new Product(String.valueOf(counter.incrementAndGet()), name, description, "", new HashMap<String, String>());
  }

  @RequestMapping(name = "/product", method = RequestMethod.POST)
  public ResponseEntity<Product> productRequestEntity(@RequestBody Product product){
    return new ResponseEntity<Product>(product, HttpStatus.CREATED);
  }
}
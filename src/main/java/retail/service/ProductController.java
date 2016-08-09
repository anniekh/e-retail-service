package retail.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import retail.service.exceptions.ProductNotFoundException;
import retail.service.model.Product;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

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

  @RequestMapping(name = "/product/", method = RequestMethod.PUT)
  public ResponseEntity<Product> productRequestEntity(@RequestBody Product product){
    HttpStatus httpStatus = HttpStatus.CREATED;
    if(ProductManager.getInstance().getProductById(product.getId()).isPresent()) {
      ProductManager.getInstance().updateProduct(product);
      httpStatus = HttpStatus.ACCEPTED;
    }
    else
      ProductManager.getInstance().addProduct(product);
    return new ResponseEntity<>(product, httpStatus);
  }



//  @RequestMapping(value = "/user/item/{loginName}", method = RequestMethod.GET)
//  public void getSourceDetails(@PathVariable String loginName) {
//    try {
//      System.out.println(loginName);
//      // it print like this  {john}
//    } catch (Exception e) {
//      LOG.error(e);
//    }
//  }
//
//  @RequestMapping(value="/owners/{ownerId}", method=RequestMethod.GET)
//  public String findOwner(@PathVariable String ownerId, Model model) {
//    Owner owner = ownerService.findOwner(ownerId);
//    model.addAttribute("owner", owner);
//    return "displayOwner";
//  }
}
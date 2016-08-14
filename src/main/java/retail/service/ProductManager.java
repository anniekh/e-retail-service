package retail.service;

import retail.service.exceptions.ProductNotFoundException;
import retail.service.model.Product;
import retail.service.model.ProductPrice;

import java.util.ArrayList;
import java.util.Optional;

public class ProductManager{

  private ArrayList<Product> products = new ArrayList<Product>();
  private static ProductManager instance = null;

  private ProductManager(){
  }

  /*
  Implements singleton pattern to persist and manages the products
   */
  public static synchronized ProductManager getInstance(){
    if(instance == null)
      instance = new ProductManager();
    return instance;
  }


  public void addProduct(final Product product) {
    products.add(product);
  }

  public void updateProduct(final Product product) {
    Optional<Product> existingProduct = getProductById(product.getId());
    if(!existingProduct.isPresent())
      throw new ProductNotFoundException();
    products.remove(existingProduct);
    products.add(product);
  }

  void removeProductById(String id){
    Optional<Product> existingProduct = getProductById(id);
    if(!existingProduct.isPresent())
      throw new ProductNotFoundException();
    products.remove(existingProduct);
  }

  public void setProductPriceByProductId(String id, ProductPrice productPrice){
    Optional<Product> existingProduct = getProductById(id);
    existingProduct.ifPresent(x -> x.updatePrice(productPrice));
  }

  public Optional<Product> getProductById(final String id) {
   return products.stream().filter(x -> x.getId().equals(id)).findFirst();
  }

  public ArrayList<Product> getAllProducts(){
    return this.products;
  }
}

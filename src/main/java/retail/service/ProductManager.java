package retail.service;

import retail.service.model.Product;

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
    Product existingProduct = new Product();
   if(products.stream().filter(x -> x.getId().equals(product.getId())).findFirst().isPresent())
     existingProduct = products.stream().filter(x -> x.getId().equals(product.getId())).findFirst().get();
    products.remove(existingProduct);
    products.add(product);
  }

  public Optional<Product> getProductById(final String id) {
   return products.stream().filter(x -> x.getId().equals(id)).findFirst();
  }

  public ArrayList<Product> getAllProducts(){
    return this.products;
  }
}

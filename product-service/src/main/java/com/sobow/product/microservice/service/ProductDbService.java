package com.sobow.product.microservice.service;

import com.sobow.product.microservice.model.Product;
import com.sobow.product.microservice.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductDbService
{
  private final ProductRepository productRepository;
  
  public List<Product> findAll()
  {
    return productRepository.findAll();
  }
  
  public Product save(final Product product)
  {
    return productRepository.save(product);
  }
  
  public Optional<Product> findByCreditIdAndProductNameAndValue(Long creditId, String productName, Integer value)
  {
    return productRepository.findByCreditIdAndProductNameAndValue(creditId, productName, value);
  }
  
  public void deleteByCreditId(final Long creditId)
  {
    productRepository.deleteByCreditId(creditId);
  }
}

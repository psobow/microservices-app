package com.sobow.credit.microservice.service;

import com.sobow.credit.microservice.client.ProductClient;
import com.sobow.credit.microservice.dto.ProductDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService
{
  private final ProductClient productClient;
  
  public void postProduct(ProductDto productDto)
  {
    productClient.postProduct(productDto);
  }
  
  public List<ProductDto> getProducts()
  {
    return productClient.getProducts();
  }
  
  public void deleteProductByCreditId(final Long creditId)
  {
    productClient.deleteProductByCreditId(creditId);
  }
}

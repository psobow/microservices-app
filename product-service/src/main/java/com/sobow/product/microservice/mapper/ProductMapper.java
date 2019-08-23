package com.sobow.product.microservice.mapper;

import com.sobow.product.microservice.dto.ProductDto;
import com.sobow.product.microservice.model.Product;
import com.sobow.product.microservice.service.ProductDbService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductMapper
{
  private final ProductDbService dbService;
  
  public Product mapToProduct(final ProductDto productDto)
  {
    // Check if object exists in DB and set up it's ID
    // If object does not exist in DB set up it's ID to 0L
    
    Product product = dbService.findByCreditIdAndProductNameAndValue(productDto.getCreditId(),
                                                                     productDto.getProductName(),
                                                                     productDto.getValue()).orElse(null);
    long id = product != null ? product.getId() : 0L;
    return new Product(id, productDto.getCreditId(), productDto.getProductName(), productDto.getValue());
  }
  
  public ProductDto mapToProductDto(final Product product)
  {
    return new ProductDto(product.getCreditId(), product.getProductName(), product.getValue());
  }
  
  public List<ProductDto> mapToProductDtos(final List<Product> products)
  {
    return products.stream().map(this::mapToProductDto).collect(Collectors.toList());
  }
}

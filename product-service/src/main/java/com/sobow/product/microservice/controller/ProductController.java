package com.sobow.product.microservice.controller;

import com.sobow.product.microservice.dto.ProductDto;
import com.sobow.product.microservice.mapper.ProductMapper;
import com.sobow.product.microservice.service.ProductDbService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/products")
@RequiredArgsConstructor
public class ProductController
{
  private final ProductMapper productMapper;
  
  private final ProductDbService dbService;
  
  @GetMapping
  public List<ProductDto> getProducts()
  {
    return productMapper.mapToProductDtos(dbService.findAll());
  }
  
  @PostMapping
  public void createProduct(@RequestBody final ProductDto productDto)
  {
    dbService.save(productMapper.mapToProduct(productDto));
  }
}

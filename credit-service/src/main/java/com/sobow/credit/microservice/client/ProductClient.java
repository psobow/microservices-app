package com.sobow.credit.microservice.client;

import com.google.gson.Gson;
import com.sobow.credit.microservice.dto.ProductDto;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductClient
{
  private final RestTemplate restTemplate;
  private final Gson gson = new Gson();
  private final URI productMicroServiceURL = UriComponentsBuilder.fromHttpUrl("http://localhost:8082/v1/products")
                                                                 .build()
                                                                 .encode()
                                                                 .toUri();
  
  public void postProduct(ProductDto productDto)
  {
    HttpEntity<String> requestBody = new HttpEntity<>(gson.toJson(productDto));
    restTemplate.postForObject(productMicroServiceURL, requestBody, Object.class);
  }
  
  public List<ProductDto> getProducts()
  {
    try
    {
      ProductDto[] boardResponse = restTemplate.getForObject(productMicroServiceURL, ProductDto[].class);
      return Arrays.asList(Optional.ofNullable(boardResponse).orElse(new ProductDto[0]));
    }
    catch (RestClientException e)
    {
      log.error(e.getMessage(), e);
      return new ArrayList<>();
    }
  }
}

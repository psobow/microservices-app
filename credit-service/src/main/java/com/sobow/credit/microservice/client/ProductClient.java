package com.sobow.credit.microservice.client;

import com.google.gson.Gson;
import com.sobow.credit.microservice.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductClient
{
  private final RestTemplate restTemplate;
  private final Gson gson = new Gson();
  private final String productServicePort = "8082";
  @Value("${product.service.host}")
  private String productServiceHost;
  
  
  public void postProduct(final ProductDto productDto)
  {
    URI productMicroServiceURL = UriComponentsBuilder.fromHttpUrl(
        "http://" + productServiceHost + ":" + productServicePort + "/v1/products").build().encode().toUri();
    
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> requestBody = new HttpEntity<>(gson.toJson(productDto), headers);
    restTemplate.postForObject(productMicroServiceURL, requestBody, Object.class);
  }
  
  public List<ProductDto> getProducts()
  {
    URI productMicroServiceURL = UriComponentsBuilder.fromHttpUrl(
        "http://" + productServiceHost + ":" + productServicePort + "/v1/products").build().encode().toUri();
    
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
  
  public void deleteProductByCreditId(final Long creditId)
  {
    URI productMicroServiceURL = UriComponentsBuilder.fromHttpUrl(
        "http://" + productServiceHost + ":" + productServicePort + "/v1/products").build().encode().toUri();
    
    String entityUrl = productMicroServiceURL + "/" + creditId.toString();
    restTemplate.delete(entityUrl);
  }
}

package com.sobow.credit.microservice.client;

import com.google.gson.Gson;
import com.sobow.credit.microservice.dto.CustomerDto;
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
public class CustomerClient
{
  private final RestTemplate restTemplate;
  private final Gson gson = new Gson();
  private final String customerServicePort = "8081";
  @Value("${customer.service.host}")
  private String customerServiceHost;
  
  public void postCustomer(final CustomerDto customerDto)
  {
    URI customerMicroServiceURL = UriComponentsBuilder.fromHttpUrl(
        "http://" + customerServiceHost + ":" + customerServicePort + "/v1/customers").build().encode().toUri();
    
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> requestBody = new HttpEntity<>(gson.toJson(customerDto), headers);
    restTemplate.postForObject(customerMicroServiceURL, requestBody, Object.class);
  }
  
  public List<CustomerDto> getCustomers()
  {
    URI customerMicroServiceURL = UriComponentsBuilder.fromHttpUrl(
        "http://" + customerServiceHost + ":" + customerServicePort + "/v1/customers").build().encode().toUri();
    
    try
    {
      CustomerDto[] boardResponse = restTemplate.getForObject(customerMicroServiceURL, CustomerDto[].class);
      return Arrays.asList(Optional.ofNullable(boardResponse).orElse(new CustomerDto[0]));
    }
    catch (RestClientException e)
    {
      log.error(e.getMessage(), e);
      return new ArrayList<>();
    }
  }
  
  public void deleteCustomerByCreditId(final Long creditId)
  {
    URI customerMicroServiceURL = UriComponentsBuilder.fromHttpUrl(
        "http://" + customerServiceHost + ":" + customerServicePort + "/v1/customers").build().encode().toUri();
    
    String entityUrl = customerMicroServiceURL + "/" + creditId.toString();
    restTemplate.delete(entityUrl);
  }
}

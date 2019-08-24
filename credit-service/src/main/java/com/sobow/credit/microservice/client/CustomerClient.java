package com.sobow.credit.microservice.client;

import com.google.gson.Gson;
import com.sobow.credit.microservice.dto.CustomerDto;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomerClient
{
  private final RestTemplate restTemplate;
  private final Gson gson = new Gson();
  private final URI customerMicroServiceURL = UriComponentsBuilder.fromHttpUrl("http://localhost:8081/v1/customers")
                                                                  .build()
                                                                  .encode()
                                                                  .toUri();
  
  public void postCustomer(final CustomerDto customerDto)
  {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> requestBody = new HttpEntity<>(gson.toJson(customerDto), headers);
    restTemplate.postForObject(customerMicroServiceURL, requestBody, Object.class);
  }
  
  public List<CustomerDto> getCustomers()
  {
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
    String entityUrl = customerMicroServiceURL + "/" + creditId.toString();
    restTemplate.delete(entityUrl);
  }
}

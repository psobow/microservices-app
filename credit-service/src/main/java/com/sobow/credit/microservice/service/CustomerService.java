package com.sobow.credit.microservice.service;

import com.sobow.credit.microservice.client.CustomerClient;
import com.sobow.credit.microservice.dto.CustomerDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService
{
  private final CustomerClient customerClient;
  
  public void postCustomer(CustomerDto customerDto)
  {
    customerClient.postCustomer(customerDto);
  }
  
  public List<CustomerDto> getCustomers()
  {
    return customerClient.getCustomers();
  }
}

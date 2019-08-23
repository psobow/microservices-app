package com.sobow.customer.microservice.controller;

import com.sobow.customer.microservice.dto.CustomerDto;
import com.sobow.customer.microservice.mapper.CustomerMapper;
import com.sobow.customer.microservice.service.CustomerDbService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/customers")
@RequiredArgsConstructor
public class CustomerController
{
  private final CustomerMapper customerMapper;
  
  private final CustomerDbService dbService;
  
  @GetMapping
  public List<CustomerDto> getCustomers()
  {
    return customerMapper.mapToCustomerDtos(dbService.findAll());
  }
  
  @PostMapping
  public void createCustomer(@RequestBody final CustomerDto customerDto)
  {
    dbService.save(customerMapper.mapToCustomer(customerDto));
  }
}

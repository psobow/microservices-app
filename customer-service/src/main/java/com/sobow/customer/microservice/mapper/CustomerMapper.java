package com.sobow.customer.microservice.mapper;

import com.sobow.customer.microservice.dto.CustomerDto;
import com.sobow.customer.microservice.model.Customer;
import com.sobow.customer.microservice.service.CustomerDbService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerMapper
{
  private final CustomerDbService dbService;
  
  public Customer mapToCustomer(final CustomerDto customerDto)
  {
    // Check if object exists in DB and set up it's ID
    // If object does not exist in DB set up it's ID to 0L
    
    Customer customer = dbService.findByCreditIdAndFirstNameAndPeselAndSurname(customerDto.getCreditId(),
                                                                               customerDto.getFirstName(),
                                                                               customerDto.getPesel(),
                                                                               customerDto.getSurname()).orElse(null);
    long id = customer != null ? customer.getId() : 0L;
    return new Customer(id,
                        customerDto.getCreditId(),
                        customerDto.getFirstName(),
                        customerDto.getPesel(),
                        customer.getSurname());
  }
  
  public CustomerDto mapToCustomerDto(final Customer customer)
  {
    return new CustomerDto(customer.getCreditId(), customer.getFirstName(), customer.getPesel(), customer.getSurname());
  }
  
  public List<CustomerDto> mapToCustomerDtos(final List<Customer> customers)
  {
    return customers.stream().map(this::mapToCustomerDto).collect(Collectors.toList());
  }
}

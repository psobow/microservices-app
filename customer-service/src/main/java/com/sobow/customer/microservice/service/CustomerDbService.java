package com.sobow.customer.microservice.service;

import com.sobow.customer.microservice.model.Customer;
import com.sobow.customer.microservice.repository.CustomerRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerDbService
{
  private final CustomerRepository customerRepository;
  
  public List<Customer> findAll()
  {
    return customerRepository.findAll();
  }
  
  public Customer save(final Customer customer)
  {
    return customerRepository.save(customer);
  }
  
  public Optional<Customer> findByCreditIdAndFirstNameAndPeselAndSurname(Long creditId,
                                                                         String firstName,
                                                                         String pesel,
                                                                         String surname)
  {
    return customerRepository.findByCreditIdAndFirstNameAndPeselAndSurname(creditId, firstName, pesel, surname);
  }
  
  public void deleteByCreditId(final Long creditId)
  {
    customerRepository.deleteByCreditId(creditId);
  }
}

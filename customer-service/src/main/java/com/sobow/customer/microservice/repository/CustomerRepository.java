package com.sobow.customer.microservice.repository;

import com.sobow.customer.microservice.model.Customer;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface CustomerRepository extends CrudRepository<Customer, Long>
{
  @Override
  List<Customer> findAll();
  
  @Override
  @SuppressWarnings("unchecked")
  Customer save(final Customer customer);
  
  Optional<Customer> findByCreditIdAndFirstNameAndPeselAndSurname(Long creditId,
                                                                  String firstName,
                                                                  String pesel,
                                                                  String surname);
}

package com.sobow.credit.microservice.repository;

import com.sobow.credit.microservice.model.Credit;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface CreditRepository extends CrudRepository<Credit, Long>
{
  @Override
  List<Credit> findAll();
  
  @Override
  @SuppressWarnings("unchecked")
  Credit save(final Credit credit);
  
}

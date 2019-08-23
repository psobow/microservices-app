package com.sobow.credit.microservice.service;

import com.sobow.credit.microservice.model.Credit;
import com.sobow.credit.microservice.repository.CreditRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreditDbService
{
  private final CreditRepository creditRepository;
  
  public List<Credit> findAll()
  {
    return creditRepository.findAll();
  }
  
  public Credit save(final Credit credit)
  {
    return creditRepository.save(credit);
  }
  
  public Optional<Credit> findByCreditName(final String creditName)
  {
    return creditRepository.findByCreditName(creditName);
  }
  
  // only for tests purpouse
  void deleteAll()
  {
    creditRepository.deleteAll();
  }
  
}

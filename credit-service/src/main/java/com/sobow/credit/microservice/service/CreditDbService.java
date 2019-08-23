package com.sobow.credit.microservice.service;

import com.sobow.credit.microservice.model.Credit;
import com.sobow.credit.microservice.repository.CreditRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreditDbService
{
  private final CreditRepository creditRepository;
  
  public List<Credit> findAllCredits()
  {
    return creditRepository.findAll();
  }
  
  public Credit saveCredit(final Credit credit)
  {
    return creditRepository.save(credit);
  }
  
  public void deleteAll()
  {
    creditRepository.deleteAll();
  }
  
}

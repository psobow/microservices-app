package com.sobow.credit.microservice.mapper;

import com.sobow.credit.microservice.dto.CreditDto;
import com.sobow.credit.microservice.model.Credit;
import com.sobow.credit.microservice.service.CreditDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreditMapper
{
  private final CreditDbService dbService;
  
  public Credit mapToCredit(final CreditDto creditDto)
  {
    // Check if object exists in DB and set up it's ID
    // If object does not exist in DB set up it's ID to 0L
  
    Credit credit = dbService.findByCreditName(creditDto.getCreditName()).orElse(null);
    long id = credit != null ? credit.getId() : 0L;
    return new Credit(id, creditDto.getCreditName());
  }
  
  public CreditDto mapToCreditDto(final Credit credit)
  {
    return new CreditDto(credit.getCreditName());
  }
  
}

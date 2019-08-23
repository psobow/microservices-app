package com.sobow.credit.microservice.mapper;

import com.sobow.credit.microservice.dto.CreditDto;
import com.sobow.credit.microservice.model.Credit;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreditMapper
{
  public Credit mapToCredit(final CreditDto creditDto)
  {
    return new Credit(creditDto.getId(), creditDto.getCreditName());
  }
  
  public CreditDto mapToCreditDto(final Credit credit)
  {
    return new CreditDto(credit.getId(), credit.getCreditName());
  }
  
  public List<CreditDto> mapToCreditsDto(final List<Credit> credits)
  {
    return credits.stream().map(this::mapToCreditDto).collect(Collectors.toList());
  }
  
}

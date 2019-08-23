package com.sobow.credit.microservice.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreditDataDto
{
  @Valid
  @NotNull
  private CustomerDto customerDto;
  
  @Valid
  @NotNull
  private ProductDto productDto;
  
  @Valid
  @NotNull
  private CreditDto creditDto;
}

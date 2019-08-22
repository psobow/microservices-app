package com.sobow.credit.microservice.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProductDto
{
  @Min(value = 0, message = "ID cannot be negative number")
  private long id;
  
  @Min(value = 0, message = "ID cannot be negative number")
  private long creditId;
  
  @NotEmpty
  private String productName;
  
  @NotEmpty
  private Integer value;
}

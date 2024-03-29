package com.sobow.credit.microservice.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDto
{
  @Min(value = 0, message = "ID cannot be negative number")
  private long creditId;
  
  @NotEmpty
  private String productName;
  
  @NotNull
  private Integer value;
}

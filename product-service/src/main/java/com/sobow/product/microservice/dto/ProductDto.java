package com.sobow.product.microservice.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProductDto
{
  @Min(value = 0, message = "ID cannot be negative number")
  @NotNull
  private Long creditId;
  
  @NotEmpty
  private String productName;
  
  @NotNull
  private Integer value;
}

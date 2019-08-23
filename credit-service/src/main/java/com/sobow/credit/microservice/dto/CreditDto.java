package com.sobow.credit.microservice.dto;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreditDto
{
  @NotEmpty(message = "Credit name cannot be null or empty")
  @Size(min = 1, max = 200, message = "Credit name must be between 1 and 200 characters")
  private String creditName;
}
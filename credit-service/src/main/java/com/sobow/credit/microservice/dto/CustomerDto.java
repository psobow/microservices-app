package com.sobow.credit.microservice.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerDto
{
  @Min(value = 0, message = "ID cannot be negative number")
  private long creditId;
  
  @NotEmpty(message = "First name cannot be null or empty")
  @Pattern(regexp = "[A-Za-z]+", message = "First name can contain only letters")
  private String firstName;
  
  @NotEmpty(message = "PESEL cannot be null or empty")
  @Pattern(regexp = "[\\d]{11}", message = "PESEL must contain only 11 digits")
  private String pesel;
  
  @NotEmpty(message = "Surname cannot be null or empty")
  @Pattern(regexp = "[A-Za-z]+", message = "Surname can contain only letters")
  private String surname;
}


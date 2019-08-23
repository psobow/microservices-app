package com.sobow.customer.microservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Customer
{
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(unique = true)
  private long id;
  
  @Min(value = 0, message = "ID cannot be negative number")
  @NotNull
  private Long creditId;
  
  @NotEmpty(message = "First name cannot be null or empty")
  @Pattern(regexp = "^[^0-9]+$", message = "First name cannot contain digits")
  private String firstName;
  
  @NotEmpty(message = "PESEL cannot be null or empty")
  @Pattern(regexp = "[\\d]{11}", message = "PESEL must contain only 11 digits")
  private String pesel;
  
  @NotEmpty(message = "Surname cannot be null or empty")
  @Pattern(regexp = "^[^0-9]+$", message = "Surname cannot contain digits")
  private String surname;
}

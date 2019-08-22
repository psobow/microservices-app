package com.sobow.credit.microservice.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Credit
{
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  
  @NotEmpty(message = "Credit name cannot be null or empty")
  @Size(min = 1, max = 200, message = "Credit name must be between 1 and 200 characters")
  private String creditName;
}

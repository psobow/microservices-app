package com.sobow.product.microservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Product
{
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(unique = true)
  private long id;
  
  @Min(value = 0, message = "ID cannot be negative number")
  @NotNull
  private Long creditId;
  
  @NotEmpty
  private String productName;
  
  @NotNull
  private Integer value;
}

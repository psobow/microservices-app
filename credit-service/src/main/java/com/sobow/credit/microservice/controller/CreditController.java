package com.sobow.credit.microservice.controller;

import com.sobow.credit.microservice.dto.CreditDataDto;
import com.sobow.credit.microservice.dto.CreditDto;
import com.sobow.credit.microservice.dto.CustomerDto;
import com.sobow.credit.microservice.dto.ProductDto;
import com.sobow.credit.microservice.mapper.CreditMapper;
import com.sobow.credit.microservice.model.Credit;
import com.sobow.credit.microservice.service.CreditDbService;
import com.sobow.credit.microservice.service.CustomerService;
import com.sobow.credit.microservice.service.ProductService;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/credits")
@CrossOrigin("*")
@RequiredArgsConstructor
public class CreditController
{
  private final CreditMapper creditMapper;
  
  private final CreditDbService dbService;
  private final ProductService productService;
  private final CustomerService customerService;
  
  @GetMapping
  public List<CreditDataDto> getCredits()
  {
    List<Credit> credits = dbService.findAll();
    List<ProductDto> productDtos = productService.getProducts();
    List<CustomerDto> customerDtos = customerService.getCustomers();
  
    return compoundData(credits, productDtos, customerDtos);
  }
  
  @PostMapping
  public Long CreateCredit(@Valid @RequestBody final CreditDataDto creditDataDto)
  {
    Credit credit = dbService.save(creditMapper.mapToCredit(creditDataDto.getCreditDto()));
    
    ProductDto productDto = creditDataDto.getProductDto();
    productDto.setCreditId(credit.getId());
    productService.postProduct(productDto);
  
    CustomerDto customerDto = creditDataDto.getCustomerDto();
    customerDto.setCreditId(credit.getId());
    customerService.postCustomer(customerDto);
    
    return credit.getId();
  }
  
  @DeleteMapping("/{id}")
  public void deleteCredit(@PathVariable("id") final Long creditId)
  {
    dbService.deleteById(creditId);
    productService.deleteProductByCreditId(creditId);
    customerService.deleteCustomerByCreditId(creditId);
  }
  
  private List<CreditDataDto> compoundData(List<Credit> credits,
                                           List<ProductDto> productDtos,
                                           List<CustomerDto> customerDtos)
  {
    List<CreditDataDto> result = new ArrayList<>();
    int quantityOfDataToCompound = productDtos.size();
  
    for (int i = 0; i < quantityOfDataToCompound; i++)
    {
      long creditId = credits.get(i).getId();
      ProductDto productDto = productDtos.stream()
                                         .filter(productDto1 -> productDto1.getCreditId() == creditId)
                                         .findFirst()
                                         .orElseThrow(NoSuchElementException::new);
      CustomerDto customerDto = customerDtos.stream()
                                            .filter(customerDto1 -> customerDto1.getCreditId() == creditId)
                                            .findFirst()
                                            .orElseThrow(NoSuchElementException::new);
      CreditDto creditDto = creditMapper.mapToCreditDto(credits.get(i));
      
      CreditDataDto data = new CreditDataDto(customerDto, productDto, creditDto);
      
      result.add(data);
    }
    return result;
  }
  
}

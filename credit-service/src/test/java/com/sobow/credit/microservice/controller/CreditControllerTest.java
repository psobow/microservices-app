package com.sobow.credit.microservice.controller;

import com.google.gson.Gson;
import com.sobow.credit.microservice.dto.CreditDataDto;
import com.sobow.credit.microservice.dto.CreditDto;
import com.sobow.credit.microservice.dto.CustomerDto;
import com.sobow.credit.microservice.dto.ProductDto;
import com.sobow.credit.microservice.mapper.CreditMapper;
import com.sobow.credit.microservice.model.Credit;
import com.sobow.credit.microservice.service.CreditDbService;
import com.sobow.credit.microservice.service.CustomerService;
import com.sobow.credit.microservice.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CreditController.class)
public class CreditControllerTest
{
  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private CreditDbService dbService;
  @MockBean
  private ProductService productService;
  @MockBean
  private CustomerService customerService;
  @MockBean
  private CreditMapper creditMapper;
  
  private final String CREDIT_ENDPOINT = "/v1/credits";
  
  @Test
  public void shouldFetchEmptyList() throws Exception
  {
    // Given
    List<Credit> credits = new ArrayList<>();
    List<ProductDto> productDtos = new ArrayList<>();
    List<CustomerDto> customerDtos = new ArrayList<>();
    
    when(dbService.findAll()).thenReturn(credits);
    when(productService.getProducts()).thenReturn(productDtos);
    when(customerService.getCustomers()).thenReturn(customerDtos);
    
    // When & Then
    mockMvc.perform(get(CREDIT_ENDPOINT).contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$", hasSize(0)));
  }
  
  @Test
  public void shouldFetchCreditDataList() throws Exception
  {
    // Given
    List<Credit> credits = new ArrayList<>();
    credits.add(new Credit(1L, "creditName"));
    
    List<ProductDto> productDtos = new ArrayList<>();
    productDtos.add(new ProductDto(1L, "productName", 20));
    
    List<CustomerDto> customerDtos = new ArrayList<>();
    customerDtos.add(new CustomerDto(1L, "customerName", "12345678901", "customerSurname"));
    
    when(dbService.findAll()).thenReturn(credits);
    when(productService.getProducts()).thenReturn(productDtos);
    when(customerService.getCustomers()).thenReturn(customerDtos);
    
    when(creditMapper.mapToCreditDto(ArgumentMatchers.any())).thenReturn(new CreditDto("creditName"));
    
    // When & Then
    mockMvc.perform(get(CREDIT_ENDPOINT).contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$", hasSize(1)))
    
           .andExpect(jsonPath("$[0].customerDto.firstName", is("customerName")))
           .andExpect(jsonPath("$[0].customerDto.surname", is("customerSurname")))
           .andExpect(jsonPath("$[0].customerDto.pesel", is("12345678901")))
    
           .andExpect(jsonPath("$[0].productDto.productName", is("productName")))
           .andExpect(jsonPath("$[0].productDto.value", is(20)))
    
           .andExpect(jsonPath("$[0].creditDto.creditName", is("creditName")));
    
  }
  
  @Test
  public void shouldCreateCredit() throws Exception
  {
    // Given
    CustomerDto customerDto = new CustomerDto(0L, "firstName", "12345678901", "surname");
    ProductDto productDto = new ProductDto(0L, "productName", 20);
    CreditDto creditDto = new CreditDto("creditName");
    CreditDataDto creditDataDto = new CreditDataDto(customerDto, productDto, creditDto);
    
    when(creditMapper.mapToCredit(ArgumentMatchers.any())).thenReturn(new Credit(0L, "creditName"));
    when(dbService.save(ArgumentMatchers.any())).thenReturn(new Credit(1L, "creditName"));
    
    Gson gson = new Gson();
    String jsonContent = gson.toJson(creditDataDto);
    
    // When & Then
    mockMvc.perform(post(CREDIT_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent));
    
    verify(dbService, times(1)).save(ArgumentMatchers.any());
  }
  
  @Test
  public void shouldNotCreateCredit_InvalidInput_NullCreditName() throws Exception
  {
    // Given
    CustomerDto customerDto = new CustomerDto(0L, "firstName", "12345678901", "surname");
    ProductDto productDto = new ProductDto(0L, "productName", 20);
    CreditDto creditDto = new CreditDto(null);
    CreditDataDto creditDataDto = new CreditDataDto(customerDto, productDto, creditDto);
    
    when(creditMapper.mapToCredit(ArgumentMatchers.any())).thenReturn(new Credit(0L, "creditName"));
    when(dbService.save(ArgumentMatchers.any())).thenReturn(new Credit(1L, "creditName"));
    
    Gson gson = new Gson();
    String jsonContent = gson.toJson(creditDataDto);
    
    // When & Then
    mockMvc.perform(post(CREDIT_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent));
    
    verify(dbService, times(0)).save(ArgumentMatchers.any());
  }
  
  @Test
  public void shouldNotCreateCredit_InvalidInput_EmptyValue() throws Exception
  {
    // Given
    CustomerDto customerDto = new CustomerDto(0L, "firstName", "12345678901", "surname");
    ProductDto productDto = new ProductDto(0L, "productName", null);
    CreditDto creditDto = new CreditDto("creditName");
    CreditDataDto creditDataDto = new CreditDataDto(customerDto, productDto, creditDto);
    
    when(creditMapper.mapToCredit(ArgumentMatchers.any())).thenReturn(new Credit(0L, "creditName"));
    when(dbService.save(ArgumentMatchers.any())).thenReturn(new Credit(1L, "creditName"));
    
    Gson gson = new Gson();
    String jsonContent = gson.toJson(creditDataDto);
    
    // When & Then
    mockMvc.perform(post(CREDIT_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent));
    
    verify(dbService, times(0)).save(ArgumentMatchers.any());
  }
  
  @Test
  public void shouldNotCreateCredit_InvalidInput_EmptyProductName() throws Exception
  {
    // Given
    CustomerDto customerDto = new CustomerDto(0L, "firstName", "12345678901", "surname");
    ProductDto productDto = new ProductDto(0L, "", 20);
    CreditDto creditDto = new CreditDto("creditName");
    CreditDataDto creditDataDto = new CreditDataDto(customerDto, productDto, creditDto);
    
    when(creditMapper.mapToCredit(ArgumentMatchers.any())).thenReturn(new Credit(0L, "creditName"));
    when(dbService.save(ArgumentMatchers.any())).thenReturn(new Credit(1L, "creditName"));
    
    Gson gson = new Gson();
    String jsonContent = gson.toJson(creditDataDto);
    
    // When & Then
    mockMvc.perform(post(CREDIT_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent));
    
    verify(dbService, times(0)).save(ArgumentMatchers.any());
  }
  
  @Test
  public void shouldNotCreateCredit_InvalidInput_InvalidSurname() throws Exception
  {
    // Given
    CustomerDto customerDto = new CustomerDto(0L, "firstName", "12345678901", "su123321)(&%$rname");
    ProductDto productDto = new ProductDto(0L, "productName", 20);
    CreditDto creditDto = new CreditDto("creditName");
    CreditDataDto creditDataDto = new CreditDataDto(customerDto, productDto, creditDto);
    
    when(creditMapper.mapToCredit(ArgumentMatchers.any())).thenReturn(new Credit(0L, "creditName"));
    when(dbService.save(ArgumentMatchers.any())).thenReturn(new Credit(1L, "creditName"));
    
    Gson gson = new Gson();
    String jsonContent = gson.toJson(creditDataDto);
    
    // When & Then
    mockMvc.perform(post(CREDIT_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent));
    
    verify(dbService, times(0)).save(ArgumentMatchers.any());
  }
  
  @Test
  public void shouldNotCreateCredit_InvalidInput_InvalidFirstName() throws Exception
  {
    // Given
    CustomerDto customerDto = new CustomerDto(0L, "firstName19712368125*%&^$&%^7", "12345678901", "surname");
    ProductDto productDto = new ProductDto(0L, "productName", 20);
    CreditDto creditDto = new CreditDto("creditName");
    CreditDataDto creditDataDto = new CreditDataDto(customerDto, productDto, creditDto);
    
    when(creditMapper.mapToCredit(ArgumentMatchers.any())).thenReturn(new Credit(0L, "creditName"));
    when(dbService.save(ArgumentMatchers.any())).thenReturn(new Credit(1L, "creditName"));
    
    Gson gson = new Gson();
    String jsonContent = gson.toJson(creditDataDto);
    
    // When & Then
    mockMvc.perform(post(CREDIT_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent));
    
    verify(dbService, times(0)).save(ArgumentMatchers.any());
  }
  
  @Test
  public void shouldNotCreateCredit_InvalidInput_InvalidPesel() throws Exception
  {
    // Given
    CustomerDto customerDto = new CustomerDto(0L, "firstName", "123", "surname");
    ProductDto productDto = new ProductDto(0L, "productName", 20);
    CreditDto creditDto = new CreditDto("creditName");
    CreditDataDto creditDataDto = new CreditDataDto(customerDto, productDto, creditDto);
    
    when(creditMapper.mapToCredit(ArgumentMatchers.any())).thenReturn(new Credit(0L, "creditName"));
    when(dbService.save(ArgumentMatchers.any())).thenReturn(new Credit(1L, "creditName"));
    
    Gson gson = new Gson();
    String jsonContent = gson.toJson(creditDataDto);
    
    // When & Then
    mockMvc.perform(post(CREDIT_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent));
    
    verify(dbService, times(0)).save(ArgumentMatchers.any());
  }
}
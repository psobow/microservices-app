package com.sobow.credit.microservice.service;

import com.sobow.credit.microservice.model.Credit;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CreditDbServiceTest
{
  @Autowired
  CreditDbService dbService;
  
  @Test
  public void databaseTest()
  {
    List<Credit> credits = dbService.findAll();
    
    System.out.println("\nCredits size: " + credits.size() + '\n');
  }
  
  @Test
  public void clearDb()
  {
    dbService.deleteAll();
  
    List<Credit> credits = dbService.findAll();
    
    System.out.println("\nCredits size after delete all: " + credits.size() + '\n');
  }
}
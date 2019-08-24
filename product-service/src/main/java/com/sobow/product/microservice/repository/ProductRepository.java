package com.sobow.product.microservice.repository;

import com.sobow.product.microservice.model.Product;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface ProductRepository extends CrudRepository<Product, Long>
{
  @Override
  List<Product> findAll();
  
  @Override
  @SuppressWarnings("unchecked")
  Product save(final Product product);
  
  Optional<Product> findByCreditIdAndProductNameAndValue(Long creditId, String productName, Integer value);
  
  void deleteByCreditId(final Long creditId);
}

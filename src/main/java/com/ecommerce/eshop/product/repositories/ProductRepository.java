package com.ecommerce.eshop.product.repositories;

import com.ecommerce.eshop.product.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

     List<Product> findAllByCategory_Name(String category_name);
     List<Product> findAllByCategory_Id(Long category_id);
     List<Product> findAllByisPromo(boolean promo);
     List<Product> findAllByPriceNotNullOrderByPriceAsc();
     List<Product> findAllByPriceNotNullAndCategory_Name(String category_name);

     List<Product> findAllByNameContains(@NotEmpty String name);
}

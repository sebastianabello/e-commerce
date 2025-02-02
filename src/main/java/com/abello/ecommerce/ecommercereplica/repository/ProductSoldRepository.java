package com.abello.ecommerce.ecommercereplica.repository;

import com.abello.ecommerce.ecommercereplica.model.ProductSold;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSoldRepository extends JpaRepository<ProductSold,Long> {
    @Query("SELECT p FROM ProductSold p WHERE p.barCode=:barCode ")
    Page<ProductSold> findProductsSoldByBarCode(@Param("barCode") String barcode, Pageable pageable);
}

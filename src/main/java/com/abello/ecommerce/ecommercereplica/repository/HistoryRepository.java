package com.abello.ecommerce.ecommercereplica.repository;


import com.abello.ecommerce.ecommercereplica.model.History;
import com.abello.ecommerce.ecommercereplica.model.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends JpaRepository<History,Long> {
    @Query("SELECT h.sales FROM History h WHERE h.id= :historyId")
    Page<Sale> findHistorySales(@Param("historyId")Long id, Pageable pageable);
    
}

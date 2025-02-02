package com.abello.ecommerce.ecommercereplica.repository;

import com.abello.ecommerce.ecommercereplica.model.Customer;
import com.abello.ecommerce.ecommercereplica.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token,Long> {
    @Query("SELECT t FROM Token t inner join Customer c on t.customer.id= c.id WHERE c.id= :customerId and (t.expired=false or t.revoked=false)")
    List<Token> findAllValidTokenByCustomer(@Param("customerId")Long id);
    @Query("SELECT t.customer FROM Token t WHERE t.token= :token ")
    Customer getCustomerByToken(@Param("token")String token);
    Optional<Token> findTokenByToken(String token);
}

package com.erich.dev.prod.respository;

import com.erich.dev.prod.entity.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAll(int page, int size);

    List<Product> findAllByIdInOrderById(Set<Long> ids);
}

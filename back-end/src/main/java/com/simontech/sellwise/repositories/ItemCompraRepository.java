package com.simontech.sellwise.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.simontech.sellwise.domain.ItemCompra;

public interface ItemCompraRepository extends JpaRepository<ItemCompra, Integer> {
    
}

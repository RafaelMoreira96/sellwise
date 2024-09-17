package com.simontech.sellwise.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.simontech.sellwise.domain.Compra;

public interface CompraRepository extends JpaRepository<Compra, Integer> {
    
}

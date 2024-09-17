package com.simontech.sellwise.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.simontech.sellwise.domain.Venda;

public interface VendaRepository extends JpaRepository<Venda, Integer> {
    
}

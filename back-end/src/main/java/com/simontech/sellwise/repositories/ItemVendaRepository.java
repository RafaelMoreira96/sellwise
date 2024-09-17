package com.simontech.sellwise.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.simontech.sellwise.domain.ItemVenda;

public interface ItemVendaRepository extends JpaRepository<ItemVenda, Integer> {
    
}

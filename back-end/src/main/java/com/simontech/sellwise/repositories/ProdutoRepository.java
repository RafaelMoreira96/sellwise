package com.simontech.sellwise.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.simontech.sellwise.domain.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
    Optional<Produto> findByCodBarras(String codBarras);
}

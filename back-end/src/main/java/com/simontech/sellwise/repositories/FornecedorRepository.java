package com.simontech.sellwise.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.simontech.sellwise.domain.Fornecedor;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, Integer> {
    Optional<Fornecedor> findByCnpj(String cnpj);
    Optional<Fornecedor> findByNomeFantasia(String nomeFantasia);
}

package com.simontech.sellwise.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.simontech.sellwise.domain.Fornecedor;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, Integer> {
    Fornecedor findByCnpj(String cnpj);
    Fornecedor findByNomeFantasia(String nomeFantasia);
}

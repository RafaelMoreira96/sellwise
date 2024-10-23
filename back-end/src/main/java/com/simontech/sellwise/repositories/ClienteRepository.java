package com.simontech.sellwise.repositories;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.simontech.sellwise.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    Optional<Cliente> findByCpf(String cpf);

    @Query("SELECT c FROM Cliente c ORDER BY c.idPessoa DESC")
    List<Cliente> findLastCliente();
}

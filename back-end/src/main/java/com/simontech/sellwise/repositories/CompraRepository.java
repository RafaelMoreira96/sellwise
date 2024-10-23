package com.simontech.sellwise.repositories;

import java.util.List;
import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.simontech.sellwise.domain.Compra;

public interface CompraRepository extends JpaRepository<Compra, Integer> {
    @Query("SELECT v FROM Compra v WHERE v.dataCompra BETWEEN :dataInicial AND :dataFinal AND v.status = 2")    
    List<Compra> findByDataCompraBetween(@Param("dataInicial") LocalDate dataInicial, @Param("dataFinal") LocalDate dataFinal);

    @Query("SELECT v FROM Compra v WHERE v.dataCompra BETWEEN :dataInicial AND :dataFinal AND v.status = 1")
    List<Compra> findCancelComprasByDate(@Param("dataInicial") LocalDate dataInicial, @Param("dataFinal") LocalDate dataFinal);


}

package com.simontech.sellwise.domain;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Hidden
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "item_compra")
public class ItemCompra {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idItemCompra;
    private Integer idProduto;
    private String descricao;
    private String codBarras;
    private double precoCompra;
    private double quantidade;
}

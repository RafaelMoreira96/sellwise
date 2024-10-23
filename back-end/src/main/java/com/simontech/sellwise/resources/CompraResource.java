package com.simontech.sellwise.resources;

import java.util.List;
import java.util.stream.Collectors;
import java.net.URI;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simontech.sellwise.domain.Compra;
import com.simontech.sellwise.domain.dtos.CompraDto;
import com.simontech.sellwise.services.CompraService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Compra", description = "Operações CRUD relacionadas às compras")
@RequestMapping("/compra")
public class CompraResource {
    
    @Autowired
    private CompraService service;

    @Operation(summary = "Busca compra por ID", description = "Retorna uma compra a partir do ID.")
    @GetMapping(value = "/{id}")
    public ResponseEntity<CompraDto> findById(@PathVariable Integer id) {
        Compra compraObject = service.findById(id);
        return ResponseEntity.ok().body(new CompraDto(compraObject));
    }

    @Operation(summary = "Lista todas as compras", description = "Retorna uma lista com todas as compras cadastradas.")
    @GetMapping
    public ResponseEntity<List<CompraDto>> findAll() {
        List<Compra> listCompras = service.findAll();
        List<CompraDto> list = listCompras.stream().map(obj -> new CompraDto(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(list);
    }

    @Operation(summary = "Cadastra uma compra", description = "Cadastra uma nova compra.")
    @PostMapping
    public ResponseEntity<CompraDto> create(@RequestBody CompraDto compraDto) {
        Compra compra = service.create(compraDto);
        URI uri = URI.create("/compra/" + compra.getIdCompra());
        return ResponseEntity.created(uri).body(new CompraDto(compra));
    }

    @Operation(summary = "Cancela uma compra", description = "Muda o status de FINALIZADO para CANCELADO.")
    @PutMapping(value = "/{id}")
    public ResponseEntity<CompraDto> cancelar(@PathVariable Integer id) {
        service.cancelCompra(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Dashboard de compras", description = "Retorna informações sobre as compras.")
    @GetMapping(value = "/dashboard-compra-info")
    public ResponseEntity<Map<String, Object>> dashboardComprasInfo(){
        Map<String, Object> dashboardComprasInfo = service.dashboardComprasInformation();
        return ResponseEntity.ok().body(dashboardComprasInfo);
    }
}

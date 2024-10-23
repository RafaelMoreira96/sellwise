package com.simontech.sellwise.resources;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.net.URI;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.simontech.sellwise.domain.Venda;
import com.simontech.sellwise.domain.dtos.VendaDto;
import com.simontech.sellwise.services.VendaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Venda", description = "Operações CRUD relacionadas às vendas")
@RequestMapping("/venda")
public class VendaResource {
    @Autowired
    private VendaService service;

    @Operation(summary = "Busca venda por ID", description = "Retorna uma venda a partir do ID.")
    @GetMapping(value = "/{id}")
    public ResponseEntity<VendaDto> findById(@PathVariable Integer id) {
        Venda vendaObject = service.findById(id);
        return ResponseEntity.ok().body(new VendaDto(vendaObject));
    }

    @Operation(summary = "Lista todas as vendas", description = "Retorna uma lista com todas as vendas cadastradas.")
    @GetMapping
    public ResponseEntity<List<VendaDto>> findAll() {
        List<Venda> listVendas = service.findAll();
        List<VendaDto> list = listVendas.stream().map(obj -> new VendaDto(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(list);
    }

    @Operation(summary = "Cadastra uma venda", description = "Cadastra uma nova venda.")
    @PostMapping
    public ResponseEntity<VendaDto> create(@RequestBody VendaDto vendaDto) {
        Venda venda = service.create(vendaDto);
        URI uri = URI.create("/venda/" + venda.getIdVenda());
        return ResponseEntity.created(uri).body(new VendaDto(venda));
    }

    @Operation(summary = "Cancela uma venda", description = "Muda o status de FINALIZADO para CANCELADO.")
    @PutMapping(value = "/{id}")
    public ResponseEntity<VendaDto> cancelar(@PathVariable Integer id) {
        service.cancelVenda(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Pesquisa venda por período", description = "Efetua uma busca a partir de uma data inicial e uma data final")
    @GetMapping("/searchBetweenDates")
    public ResponseEntity<List<VendaDto>> findVendasBetweenDate(
            @RequestParam("dataInicial") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate firstDate,
            @RequestParam("dataFinal") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate secondDate) {
        List<Venda> listVendas = service.findByDate(firstDate, secondDate);
        List<VendaDto> list = listVendas.stream().map(obj -> new VendaDto(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(list);
    }

    @Operation(summary = "Dashboard de Informações sobre Vendas", description = "Retorna informações sobre quantidade e valor somado de vendas do dia.")
    @GetMapping("/dashboard-venda-info")
    public ResponseEntity<Map<String, Object>> dashboardVendasInformation() {
        Map<String, Object> dashboardVendasInfo = service.dashboardVendasInformation();
        return ResponseEntity.ok(dashboardVendasInfo);
    }

    @Operation(summary = "Dashboard de Informações sobre Vendas", description = "Retorna informações sobre as cinco últimas vendas.")
    @GetMapping("/five-last-vendas")
    public ResponseEntity<Map<String, Object>> findFiveLastVendas() {
        Map<String, Object> dashboardVendasInfo = service.findFiveLastVendas();
        return ResponseEntity.ok(dashboardVendasInfo);
    }
}

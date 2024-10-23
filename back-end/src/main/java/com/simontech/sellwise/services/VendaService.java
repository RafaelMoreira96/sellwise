package com.simontech.sellwise.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simontech.sellwise.domain.*;
import com.simontech.sellwise.domain.dtos.VendaDto;
import com.simontech.sellwise.domain.enums.StatusVenda;
import com.simontech.sellwise.repositories.*;
import com.simontech.sellwise.services.exceptions.ObjectNotFoundException;

import jakarta.validation.Valid;

@Service
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;
    @Autowired
    private ItemVendaRepository itemVendaRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private FuncionarioRepository funcionarioRepository;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    public Venda findById(Integer id) {
        return vendaRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Venda não encontrada! ID: " + id));
    }

    public List<Venda> findByDate(LocalDate firstDate, LocalDate secondDate) {
        List<Venda> vendas = vendaRepository.findByDataVendaBetween(firstDate, secondDate);
        if (vendas.isEmpty()) {
            throw new ObjectNotFoundException("Nenhuma venda encontrada entre " + firstDate + " e " + secondDate);
        }
        return vendas;
    }

    public List<Venda> findAll() {
        return vendaRepository.findAll().stream()
                .filter(venda -> venda.getStatus().equals(StatusVenda.FINALIZADO) 
                               || venda.getStatus().equals(StatusVenda.ANDAMENTO))
                .collect(Collectors.toList());
    }

    public Venda create(@Valid VendaDto vendaDto) {
        Cliente cliente = clienteRepository.findById(vendaDto.getClienteId())
                .orElseThrow(() -> new ObjectNotFoundException("Cliente não encontrado!"));

        Funcionario funcionario = funcionarioRepository.findById(vendaDto.getFuncionarioId())
                .orElseThrow(() -> new ObjectNotFoundException("Funcionario não encontrado!"));

        FormaPagamento formaPagamento = formaPagamentoRepository.findById(vendaDto.getFormaPagamentoId())
                .orElseThrow(() -> new ObjectNotFoundException("Forma de pagamento não encontrada!"));

        List<ItemVenda> itensVenda = processarItensVenda(vendaDto.getItensVenda());

        Venda venda = new Venda();
        venda.setDataVenda(LocalDate.now());
        venda.setCliente(cliente);
        venda.setFuncionario(funcionario);
        venda.setFormaPagamento(formaPagamento);
        venda.setItensVenda(itensVenda);
        venda.setValorVenda(calcularValorVenda(itensVenda));
        venda.setNumeroVenda(UUID.randomUUID().toString());
        venda.setStatus(StatusVenda.FINALIZADO);

        itemVendaRepository.saveAll(itensVenda);
        return vendaRepository.save(venda);
    }

    public void cancelVenda(Integer id) {
        Venda venda = findById(id);
        venda.getItensVenda().forEach(itemVenda -> atualizarEstoque(itemVenda.getIdProduto(), itemVenda.getQuantidade()));
        venda.setStatus(StatusVenda.CANCELADO);
        vendaRepository.save(venda);
    }

    // Funções adicionais
    public Map<String, Object> dashboardVendasInformation() {
        LocalDate todayDate = LocalDate.now();
        List<Venda> vendas = vendaRepository.findByDataVendaBetween(todayDate, todayDate);

        Map<String, Object> dashboardInfo = new HashMap<>();
        dashboardInfo.put("quantidadeVendas", vendas.size());
        dashboardInfo.put("valorDasVendas", vendas.stream().mapToDouble(Venda::getValorVenda).sum());

        return dashboardInfo;
    }

    public Map<String, Object> findFiveLastVendas() {
        List<Venda> vendas = vendaRepository.findFiveLastVendas();

        int limit = Math.min(vendas.size(), 5);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        List<Map<String, Object>> lastVendasList = new ArrayList<>();

        for (int i = 0; i < limit; i++) {
            Venda venda = vendas.get(i);
            Map<String, Object> vendaMap = new HashMap<>();
            vendaMap.put("idVenda", venda.getIdVenda());
            vendaMap.put("numeroVenda", venda.getNumeroVenda());
            vendaMap.put("dataVenda", venda.getDataVenda().format(formatter));
            vendaMap.put("valorVenda", venda.getValorVenda());
            vendaMap.put("status", venda.getStatus().toString());

            lastVendasList.add(vendaMap);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("lastVendas", lastVendasList);

        return result;
    }

    // Métodos auxiliares
    private List<ItemVenda> processarItensVenda(List<ItemVenda> itensVendaDto) {
        List<ItemVenda> itensVenda = new ArrayList<>();

        for (ItemVenda itemVendaDto : itensVendaDto) {
            Produto produto = produtoRepository.findById(itemVendaDto.getIdProduto())
                    .orElseThrow(() -> new ObjectNotFoundException("Produto não encontrado! ID: " + itemVendaDto.getIdProduto()));

            atualizarProdutoComEstoque(itemVendaDto, produto);
            itensVenda.add(itemVendaDto);
        }

        return itensVenda;
    }

    private void atualizarProdutoComEstoque(ItemVenda itemVenda, Produto produto) {
        itemVenda.setDescricao(produto.getDescricao());
        itemVenda.setCodBarras(produto.getCodBarras());

        double novaQuantidadeEstoque = produto.getQteEstoque() - itemVenda.getQuantidade();
        produto.setQteEstoque(novaQuantidadeEstoque);

        produtoRepository.save(produto);
    }

    private void atualizarEstoque(Integer produtoId, double quantidade) {
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new ObjectNotFoundException("Produto não encontrado! ID: " + produtoId));

        double novaQuantidadeEstoque = produto.getQteEstoque() + quantidade;
        produto.setQteEstoque(novaQuantidadeEstoque);

        produtoRepository.save(produto);
    }

    private double calcularValorVenda(List<ItemVenda> itensVenda) {
        return itensVenda.stream()
                .mapToDouble(item -> item.getQuantidade() * item.getPrecoVendido())
                .sum();
    }
}

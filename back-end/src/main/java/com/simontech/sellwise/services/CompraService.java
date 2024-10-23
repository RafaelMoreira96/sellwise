package com.simontech.sellwise.services;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simontech.sellwise.domain.*;
import com.simontech.sellwise.domain.dtos.CompraDto;
import com.simontech.sellwise.domain.enums.StatusCompra;
import com.simontech.sellwise.repositories.*;
import com.simontech.sellwise.services.exceptions.ObjectNotFoundException;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class CompraService {

    @Autowired
    private CompraRepository compraRepository;
    @Autowired
    private ItemCompraRepository itemCompraRepository;
    @Autowired
    private FornecedorRepository fornecedorRepository;
    @Autowired
    private FuncionarioRepository funcionarioRepository;
    @Autowired
    private ProdutoRepository produtoRepository;

    public Compra findById(Integer id) {
        return compraRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Compra não encontrada! ID: " + id));
    }

    public List<Compra> findByDate(LocalDate firstDate, LocalDate secondDate) {
        List<Compra> compras = compraRepository.findByDataCompraBetween(firstDate, secondDate);
        if (compras.isEmpty()) {
            throw new ObjectNotFoundException("Nenhuma compra encontrada entre " + firstDate + " e " + secondDate);
        }
        return compras;
    }

    public List<Compra> findAll() {
        return compraRepository.findAll().stream()
                .filter(compra -> compra.getStatus().equals(StatusCompra.FINALIZADO) 
                               || compra.getStatus().equals(StatusCompra.ANDAMENTO))
                .collect(Collectors.toList());
    }

    @Transactional
    public Compra create(@Valid CompraDto compraDto) {
        Fornecedor fornecedor = fornecedorRepository.findById(compraDto.getFornecedorId())
                .orElseThrow(() -> new ObjectNotFoundException("Fornecedor não encontrado!"));

        Funcionario funcionario = funcionarioRepository.findById(compraDto.getFuncionarioId())
                .orElseThrow(() -> new ObjectNotFoundException("Funcionario não encontrado!"));

        List<ItemCompra> itensCompra = processarItensCompra(compraDto.getItensCompra());

        Compra compra = new Compra();
        compra.setDataCompra(LocalDate.now());
        compra.setFornecedor(fornecedor);
        compra.setFuncionario(funcionario);
        compra.setItensCompra(itensCompra);
        compra.setValorTotal(calcularValorTotal(itensCompra));
        compra.setNumeroCompra(UUID.randomUUID().toString());
        compra.setStatus(StatusCompra.FINALIZADO);

        itemCompraRepository.saveAll(itensCompra);
        return compraRepository.save(compra);
    }

    @Transactional
    public void cancelCompra(Integer id) {
        Compra compra = findById(id);
        for (ItemCompra itemCompra : compra.getItensCompra()) {
            atualizarEstoque(itemCompra.getIdProduto(), -itemCompra.getQuantidade());
        }
        compra.setStatus(StatusCompra.DEVOLUCAO);
        compraRepository.save(compra);
    }

    public Map<String, Object> dashboardComprasInformation() {
        LocalDate todayDate = LocalDate.now();
        List<Compra> compras = compraRepository.findByDataCompraBetween(todayDate, todayDate);

        Map<String, Object> dashboardInfo = new HashMap<>();
        dashboardInfo.put("quantidadeCompras", compras.size());
        dashboardInfo.put("totalValorCompras", compras.stream()
                .mapToDouble(Compra::getValorTotal).sum());

        return dashboardInfo;
    }

    private List<ItemCompra> processarItensCompra(List<ItemCompra> itensCompraDto) {
        List<ItemCompra> itensCompra = new ArrayList<>();

        for (ItemCompra itemCompraDto : itensCompraDto) {
            Produto produto = produtoRepository.findById(itemCompraDto.getIdProduto())
                    .orElseThrow(() -> new ObjectNotFoundException("Produto não encontrado! ID: " + itemCompraDto.getIdProduto()));

            atualizarProdutoComEstoque(itemCompraDto, produto);
            itensCompra.add(itemCompraDto);
        }

        return itensCompra;
    }

    private void atualizarProdutoComEstoque(ItemCompra itemCompra, Produto produto) {
        itemCompra.setDescricao(produto.getDescricao());
        itemCompra.setCodBarras(produto.getCodBarras());

        double novaQuantidadeEstoque = produto.getQteEstoque() + itemCompra.getQuantidade();
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

    private double calcularValorTotal(List<ItemCompra> itensCompra) {
        return itensCompra.stream()
                .mapToDouble(item -> item.getQuantidade() * item.getPrecoCompra())
                .sum();
    }
}

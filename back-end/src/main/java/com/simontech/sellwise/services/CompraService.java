package com.simontech.sellwise.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simontech.sellwise.domain.Compra;
import com.simontech.sellwise.domain.Fornecedor;
import com.simontech.sellwise.domain.Funcionario;
import com.simontech.sellwise.domain.ItemCompra;
import com.simontech.sellwise.domain.Produto;
import com.simontech.sellwise.domain.dtos.CompraDto;
import com.simontech.sellwise.domain.enums.StatusCompra;
import com.simontech.sellwise.repositories.CompraRepository;
import com.simontech.sellwise.repositories.FornecedorRepository;
import com.simontech.sellwise.repositories.FuncionarioRepository;
import com.simontech.sellwise.repositories.ItemCompraRepository;
import com.simontech.sellwise.repositories.ProdutoRepository;
import com.simontech.sellwise.services.exceptions.ObjectNotFoundException;

import jakarta.validation.Valid;

@Service
public class CompraService {

    @Autowired
    private CompraRepository repository;
    @Autowired
    private ItemCompraRepository itemCompraRepository;
    @Autowired
    private FornecedorRepository fornecedorRepository;
    @Autowired
    private FuncionarioRepository funcionarioRepository;
    @Autowired
    private ProdutoRepository produtoRepository;

    public Compra findById(Integer id) {
        Optional<Compra> compra = repository.findById(id);
        return compra.orElseThrow(() -> new ObjectNotFoundException("Compra não encontrada! ID: " + id));
    }

    public List<Compra> findByDate(LocalDate firstDate, LocalDate secondDate) {
        List<Compra> compras = repository.findByDataCompraBetween(firstDate, secondDate);
        if (compras.isEmpty()) {
            throw new ObjectNotFoundException("Nenhuma compra encontrada entre " + firstDate + " e " + secondDate);
        }
        return compras;
    }

    public List<Compra> findAll() {
        List<Compra> listDB = repository.findAll();
        List<Compra> list = new ArrayList<>();
        for (Compra compra : listDB) {
            if (compra.getStatus().equals(StatusCompra.FINALIZADO) || compra.getStatus().equals(StatusCompra.ANDAMENTO)) {
                list.add(compra);
            }
        }
        return list;
    }

    public Compra create(@Valid CompraDto compraDto) {
        Optional<Fornecedor> fornecedorOptional = fornecedorRepository.findById(compraDto.getFornecedorId());
        Fornecedor fornecedorDB = fornecedorOptional.orElseThrow(() -> new ObjectNotFoundException("Fornecedor não encontrado!"));

        Optional<Funcionario> funcionario = funcionarioRepository.findById(compraDto.getFuncionarioId());
        Funcionario funcionarioDB = funcionario.orElseThrow(() -> new ObjectNotFoundException("Funcionario não encontrado!"));

        List<ItemCompra> listTemp = compraDto.getItens();
        List<ItemCompra> list = new ArrayList<>();
        double valorTotal = 0;

        for (ItemCompra itemCompra : listTemp) {
            Produto produto = new Produto();
            Optional<Produto> produtoTemporario = produtoRepository.findById(itemCompra.getIdProduto());

            if (!produtoTemporario.isPresent()) {
                throw new ObjectNotFoundException("Produto não encontrado! ID: " + itemCompra.getIdProduto());
            }

            itemCompra.setDescricao(produtoTemporario.get().getDescricao());
            itemCompra.setCodBarras(produtoTemporario.get().getCodBarras());
            list.add(itemCompra);

            double quantidadeEstoque = produtoTemporario.get().getQteEstoque() + itemCompra.getQuantidade();
            produtoTemporario.get().setQteEstoque(quantidadeEstoque);

            produto.setIdProduto(produtoTemporario.get().getIdProduto());
            produto.setCodBarras(produtoTemporario.get().getCodBarras());
            produto.setDescricao(produtoTemporario.get().getDescricao());
            produto.setPrecoAtacado(produtoTemporario.get().getPrecoAtacado());
            produto.setPrecoVarejo(produtoTemporario.get().getPrecoVarejo());
            produto.setQteEstoque(quantidadeEstoque);
            produto.setQteMax(produtoTemporario.get().getQteMax());
            produto.setQteMin(produtoTemporario.get().getQteMin());

            produtoRepository.save(produto);
        }

        itemCompraRepository.saveAll(list);

        Compra compra = new Compra();
        compra.setDataCompra(LocalDate.now());
        compra.setFornecedor(fornecedorDB);
        compra.setFuncionario(funcionarioDB);
        compra.setItens(list);

        for (ItemCompra itemCompra : list) {
            valorTotal += (itemCompra.getQuantidade() * itemCompra.getPrecoCompra());
        }

        compra.setValorTotal(valorTotal);
        compra.setNumeroCompra(UUID.randomUUID().toString());
        compra.setStatus(StatusCompra.FINALIZADO);
        return repository.save(compra);
    }

    public void cancelCompra(Integer id) {
        Compra compra = findById(id);
        Produto produto = new Produto();
        for (ItemCompra itemCompra : compra.getItens()) {
            Optional<Produto> objTemp = produtoRepository.findById(itemCompra.getIdProduto());
            double quantidadeEstoque = objTemp.get().getQteEstoque() - itemCompra.getQuantidade();

            produto.setIdProduto(objTemp.get().getIdProduto());
            produto.setCodBarras(objTemp.get().getCodBarras());
            produto.setDescricao(objTemp.get().getDescricao());
            produto.setPrecoAtacado(objTemp.get().getPrecoAtacado());
            produto.setPrecoVarejo(objTemp.get().getPrecoVarejo());
            produto.setQteEstoque(quantidadeEstoque);
            produto.setQteMax(objTemp.get().getQteMax());
            produto.setQteMin(objTemp.get().getQteMin());

            produtoRepository.save(produto);
        }
        compra.setStatus(StatusCompra.DEVOLUCAO);
        repository.save(compra);
    }

    // funções adicionais
    public Map<String, Object> dashboardComprasInformation(){
        LocalDate todayDate = LocalDate.now();
        List<Compra> compras = repository.findByDataCompraBetween(todayDate, todayDate);
        
        Map<String, Object> dashboardComprasInfo = new HashMap<>();
        dashboardComprasInfo.put("quantidadeCompras", compras.size());
        dashboardComprasInfo.put("totalValorCompras", compras.stream().mapToDouble(Compra::getValorTotal).sum());

        return dashboardComprasInfo;
    }
}

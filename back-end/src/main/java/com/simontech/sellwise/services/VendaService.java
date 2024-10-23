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

import com.simontech.sellwise.domain.Cliente;
import com.simontech.sellwise.domain.FormaPagamento;
import com.simontech.sellwise.domain.Funcionario;
import com.simontech.sellwise.domain.ItemVenda;
import com.simontech.sellwise.domain.Produto;
import com.simontech.sellwise.domain.Venda;
import com.simontech.sellwise.domain.dtos.VendaDto;
import com.simontech.sellwise.domain.enums.StatusVenda;
import com.simontech.sellwise.repositories.ClienteRepository;
import com.simontech.sellwise.repositories.FormaPagamentoRepository;
import com.simontech.sellwise.repositories.FuncionarioRepository;
import com.simontech.sellwise.repositories.ItemVendaRepository;
import com.simontech.sellwise.repositories.ProdutoRepository;
import com.simontech.sellwise.repositories.VendaRepository;
import com.simontech.sellwise.services.exceptions.ObjectNotFoundException;

import jakarta.validation.Valid;

@Service
public class VendaService {
    @Autowired
    private VendaRepository repository;
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
        Optional<Venda> o = repository.findById(id);
        return o.orElseThrow(() -> new ObjectNotFoundException("Venda não encontrada! ID: " + id));
    }

    public List<Venda> findByDate(LocalDate firstDate, LocalDate secondDate) {
        List<Venda> vendas = repository.findByDataVendaBetween(firstDate, secondDate);
        if (vendas.isEmpty()) {
            throw new ObjectNotFoundException("Nenhuma venda encontrada entre " + firstDate + " e " + secondDate);
        }
        return vendas;
    }

    public List<Venda> findAll() {
        List<Venda> listDB = repository.findAll();
        List<Venda> list = new ArrayList<>();
        for (Venda venda : listDB) {
            if (venda.getStatus().equals(StatusVenda.FINALIZADO) || venda.getStatus().equals(StatusVenda.ANDAMENTO)) {
                list.add(venda);
            }
        }
        return list;
    }

    public Venda create(@Valid VendaDto vendaDto) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(vendaDto.getClienteId());
        Cliente clienteDB = clienteOptional.orElseThrow(() -> new ObjectNotFoundException("Cliente não encontrado!"));

        Optional<Funcionario> funcionario = funcionarioRepository.findById(vendaDto.getFuncionarioId());
        Funcionario funcionarioDB = funcionario
                .orElseThrow(() -> new ObjectNotFoundException("Funcionario não encontrado!"));

        Optional<FormaPagamento> formaPagamento = formaPagamentoRepository.findById(vendaDto.getFormaPagamentoId());
        FormaPagamento formaPagamentoDB = formaPagamento
                .orElseThrow(() -> new ObjectNotFoundException("Forma de pagamento não encontrada!"));

        List<ItemVenda> listTemp = vendaDto.getItens();
        List<ItemVenda> list = new ArrayList<>();
        double valorVenda = 0;

        for (ItemVenda itemVenda : listTemp) {
            Produto produto = new Produto();
            Optional<Produto> produtoTemporario = produtoRepository.findById(itemVenda.getIdProduto());

            if (!produtoTemporario.isPresent()) {
                throw new ObjectNotFoundException("Produto não encontrado! ID: " + itemVenda.getIdProduto());
            }

            itemVenda.setDescricao(produtoTemporario.get().getDescricao());
            itemVenda.setCodBarras(produtoTemporario.get().getCodBarras());
            list.add(itemVenda);

            double quantidadeEstoque = produtoTemporario.get().getQteEstoque() - itemVenda.getQuantidade();
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

        itemVendaRepository.saveAll(list);

        Venda venda = new Venda();
        venda.setDataVenda(LocalDate.now());
        venda.setCliente(clienteDB);
        venda.setFuncionario(funcionarioDB);
        venda.setFormaPagamento(formaPagamentoDB);
        venda.setItensVenda(list);

        for (ItemVenda itemVenda : list) {
            valorVenda += (itemVenda.getQuantidade() * itemVenda.getPrecoVendido());
        }

        venda.setValorVenda(valorVenda);
        venda.setNumeroVenda(UUID.randomUUID().toString());
        venda.setStatus(StatusVenda.FINALIZADO);
        return repository.save(venda);
    }

    public void cancelVenda(Integer id) {
        Venda venda = findById(id);
        Produto produto = new Produto();
        for (ItemVenda itemVenda : venda.getItensVenda()) {
            Optional<Produto> objTemp = produtoRepository.findById(itemVenda.getIdProduto());
            double quantidadeEstoque = objTemp.get().getQteEstoque() + itemVenda.getQuantidade();

            produto.setIdProduto(objTemp.get().getIdProduto());
            produto.setCodBarras(objTemp.get().getCodBarras());
            produto.setDescricao(objTemp.get().getDescricao());
            produto.setPrecoAtacado(objTemp.get().getPrecoAtacado());
            produto.setPrecoVarejo(objTemp.get().getPrecoVarejo());
            produto.setQteEstoque(quantidadeEstoque);
            produto.setQteMax(objTemp.get().getQteMax());
            produto.setQteMin(objTemp.get().getQteMin());

            produtoRepository.save(produto);
            itemVenda.getQuantidade();
        }
        venda.setStatus(StatusVenda.CANCELADO);
        repository.save(venda);
    }

    // funções adicionais
    public Map<String, Object> dashboardVendasInformation() {
        LocalDate todayDate = LocalDate.now();
        List<Venda> vendas = repository.findByDataVendaBetween(todayDate, todayDate);

        Map<String, Object> dashboardVendasInfo = new HashMap<String, Object>();
        dashboardVendasInfo.put("quantidadeVendas", vendas.size());
        dashboardVendasInfo.put("valorDasVendas", vendas.stream().mapToDouble(v -> v.getValorVenda()).sum());//

        return dashboardVendasInfo;
    }

    public Map<String, Object> findFiveLastVendas() {
        List<Venda> vendas = repository.findFiveLastVendas();

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

}

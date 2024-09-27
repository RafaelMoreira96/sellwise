package com.simontech.sellwise.services;

import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simontech.sellwise.domain.Produto;
import com.simontech.sellwise.domain.dtos.ProdutoDto;
import com.simontech.sellwise.repositories.ProdutoRepository;
import com.simontech.sellwise.services.exceptions.DataIntegrityViolationException;
import com.simontech.sellwise.services.exceptions.ObjectNotFoundException;

import jakarta.validation.Valid;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository repository;

    public Produto findById(Integer id) {
        Optional<Produto> optionalProduto = repository.findById(id);
        return optionalProduto.orElseThrow(() -> new ObjectNotFoundException("Produto não encontrado! ID: " + id));
    }

    public List<Produto> findAll() {
        List<Produto> listProdutosDB = repository.findAll();
        List<Produto> listActiveProdutos = new ArrayList<>();
        for(Produto produto: listProdutosDB){
            if(produto.isStatus() == true){
                listActiveProdutos.add(produto);
            }
        }

        return listActiveProdutos;
    }

    public Produto create(@Valid ProdutoDto produtoDto){
        checkBarCode(produtoDto.getCodBarras());

        Produto produto = new Produto();
        produto.setIdProduto(null);
        produto.setDescricao(produtoDto.getDescricao());
        produto.setCodBarras(produtoDto.getCodBarras());
        produto.setPrecoAtacado(produtoDto.getPrecoAtacado());
        produto.setPrecoVarejo(produtoDto.getPrecoVarejo());
        produto.setQteEstoque(produtoDto.getQteEstoque());
        produto.setQteMin(produtoDto.getQteMin());
        produto.setQteMax(produtoDto.getQteMax());
        produto.setStatus(true);

        return repository.save(produto);
    }

    public Produto update(Integer idProduto, @Valid ProdutoDto produtoDto){
        Produto produtoDB = findById(idProduto);

        if(!produtoDB.getCodBarras().equals(produtoDto.getCodBarras())){
            checkBarCode(produtoDto.getCodBarras());
        }

        produtoDB.setCodBarras(produtoDto.getCodBarras());
        produtoDB.setDescricao(produtoDto.getDescricao());
        produtoDB.setPrecoAtacado(produtoDto.getPrecoAtacado());
        produtoDB.setPrecoVarejo(produtoDto.getPrecoVarejo());
        produtoDB.setQteEstoque(produtoDto.getQteEstoque());
        produtoDB.setQteMin(produtoDto.getQteMin());
        produtoDB.setQteMax(produtoDto.getQteMax());

        return repository.save(produtoDB);
    }

    public void delete(Integer idProduto){
        Produto produto = findById(idProduto);
        produto.setStatus(false);
        repository.save(produto);
    }

    // Funções auxiliares
    private void checkBarCode(String barcode){
        Optional<Produto> optionalProduto = repository.findByCodBarras(barcode);
        if(optionalProduto.isPresent()){
            throw new DataIntegrityViolationException("Código de barras já cadastrado.");
        }
    }
}
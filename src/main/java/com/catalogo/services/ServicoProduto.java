package com.catalogo.services;

import com.catalogo.Infra.exceptions.DatabaseException;
import com.catalogo.Infra.exceptions.ResourceNotFoundException;
import com.catalogo.dto.ProdutoDTO;
import com.catalogo.entities.Produto;
import com.catalogo.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;


@Service
public class ServicoProduto {

    @Autowired
    private ProdutoRepository repository;

    @Transactional(readOnly = true)
    public Page<ProdutoDTO> findAllPage(PageRequest pageRequest) {

        return repository.findAll(pageRequest)
                .map(ProdutoDTO::new);
    }

    @Transactional(readOnly = true)
    public ProdutoDTO buscarPorId(Long id) {
        Optional<Produto> ct = repository.findById(id);
        Produto produto = ct.orElseThrow(() -> new ResourceNotFoundException("Item não encontrado"));

        return new ProdutoDTO(produto, produto.getCategories());
    }

//    @Transactional
//    public Produto cadastrarProduto(ProdutoDTO dto) {
//        Produto Produto = new Produto(dto);
//
//        return repository.save(Produto);
//    }

    @Transactional
    public void deletarProduto(Long id) {
        try {
            Produto Produto = repository.findById(id).get();
            repository.delete(Produto);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Item do id: " + id + " não foi encontrado");
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Violação de integridade");
        } catch (NoSuchElementException e) {
            throw new DatabaseException("Violação de integridade");
        }
    }

//    @Transactional
//    public ProdutoDTO editarProduto(Long id, ProdutoDTO dto) {
//        try {
//            Produto Produto = repository.findById(id).get();
//            Produto.setNome(dto.getNome());
//
//            return new ProdutoDTO(Produto);
//        } catch (EntityNotFoundException e) {
//            throw new ResourceNotFoundException("Item do id: " + id + " não foi encontrado");
//        }
//    }

}

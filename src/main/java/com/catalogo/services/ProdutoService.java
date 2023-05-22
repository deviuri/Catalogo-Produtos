package com.catalogo.services;

import com.catalogo.Infra.exceptions.DatabaseException;
import com.catalogo.Infra.exceptions.ResourceNotFoundException;
import com.catalogo.dto.CategoryDTO;
import com.catalogo.dto.ProdutoDTO;
import com.catalogo.entities.Category;
import com.catalogo.entities.Produto;
import com.catalogo.repository.CategoryRepository;
import com.catalogo.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
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
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public Page<ProdutoDTO> findAllPage(PageRequest pageRequest) {

        return repository.findAll(pageRequest)
                .map(ProdutoDTO::new);
    }

    @Transactional(readOnly = true)
    public ProdutoDTO buscarPorId(Long id) {
        Optional<Produto> ct = repository.findById(id);
        Produto produto = ct.orElseThrow(() -> new ResourceNotFoundException("Item não encontrado"));

        return new ProdutoDTO(produto, produto.getCategorias());
    }

    @Transactional
    public ProdutoDTO cadastrarProduto(ProdutoDTO dto) {
        Produto entity = new Produto();
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new ProdutoDTO(entity, entity.getCategorias());
    }

    @Transactional
    public void deletarProduto(Long id) {
        try {
            Produto produto = repository.findById(id).get();
            repository.delete(produto);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Produto do id: " + id + " não foi encontrado");
        } catch (DataIntegrityViolationException | NoSuchElementException e) {
            throw new DatabaseException("Produto não existe em nosso Banco de Dados");
        }
    }

    @Transactional
    public ProdutoDTO editarProduto(Long id, ProdutoDTO dto) {
        try {
            Produto entity = repository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return new ProdutoDTO(entity, entity.getCategorias());
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Produto do id: " + id + ", não foi encontrado");
        }
    }

    private void copyDtoToEntity(ProdutoDTO dto, Produto entity) {
        entity.setNome(dto.getNome());
        entity.setDescricao(dto.getDescricao());
        entity.setPreco(dto.getPreco());
        entity.setData(dto.getData());
        entity.setImgUrl(dto.getImgUrl());

        entity.getCategorias().clear();
        for (CategoryDTO catDto : dto.getCategorias()) {
            Category category = categoryRepository.getReferenceById(catDto.getId());
            entity.getCategorias().add(category);
        }
    }
}

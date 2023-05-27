package com.catalogo.servicos;

import com.catalogo.Infra.exceptions.DatabaseException;
import com.catalogo.dto.CategoriaDTO;
import com.catalogo.entities.Categoria;
import com.catalogo.repository.CategoryRepository;
import com.catalogo.Infra.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.NoSuchElementException;
import java.util.Optional;


@Service
public class CategoryServico {

    @Autowired
    private CategoryRepository repository;

    @Transactional(readOnly = true)
    public Page<CategoriaDTO> findAllPage(Pageable paginacao) {

        return repository.findAll(paginacao)
                .map(CategoriaDTO::new);
    }

    @Transactional(readOnly = true)
    public CategoriaDTO buscarPorId(Long id) {
        Optional<Categoria> ct = repository.findById(id);
        Categoria category = ct.orElseThrow(() -> new ResourceNotFoundException("Item não encontrado"));

        return new CategoriaDTO(category);
    }

    @Transactional
    public CategoriaDTO cadastrarCategory(CategoriaDTO dto) {
        Categoria category = new Categoria();
        category.setNome(dto.getNome());
        category = repository.save(category);

        return new CategoriaDTO(category);
    }

    @Transactional
    public void deletarCategory(Long id) {
        try {
            Categoria category = repository.findById(id).get();
            repository.delete(category);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Item do id: " + id + " não foi encontrado");
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Violação de integridade");
        } catch (NoSuchElementException e) {
            throw new DatabaseException("Violação de integridade");
        }
    }

    @Transactional
    public CategoriaDTO editarCategory(Long id, CategoriaDTO dto) {
        try {
            Categoria category = repository.findById(id).get();
            category.setNome(dto.getNome());

            return new CategoriaDTO(category);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Item do id: " + id + " não foi encontrado");
        }
    }

}

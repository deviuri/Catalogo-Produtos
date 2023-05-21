package com.catalogo.services;

import com.catalogo.Infra.exceptions.DatabaseException;
import com.catalogo.dto.CategoryDTO;
import com.catalogo.entities.Category;
import com.catalogo.repository.CategoryRepository;
import com.catalogo.Infra.exceptions.ResourceNotFoundException;
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
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Transactional(readOnly = true)
    public Page<CategoryDTO> findAllPage(PageRequest pageRequest) {

        return repository.findAll(pageRequest)
                .map(CategoryDTO::new);
    }

    @Transactional(readOnly = true)
    public CategoryDTO buscarPorId(Long id) {
        Optional<Category> ct = repository.findById(id);
        Category category = ct.orElseThrow(() -> new ResourceNotFoundException("Item não encontrado"));

        return new CategoryDTO(category);
    }

    @Transactional
    public Category cadastrarCategory(CategoryDTO dto) {
        Category category = new Category(dto);

        return repository.save(category);
    }

    @Transactional
    public void deletarCategory(Long id) {
        try {
            Category category = repository.findById(id).get();
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
    public CategoryDTO editarCategory(Long id, CategoryDTO dto) {
        try {
            Category category = repository.findById(id).get();
            category.setNome(dto.getNome());

            return new CategoryDTO(category);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Item do id: " + id + " não foi encontrado");
        }
    }

}

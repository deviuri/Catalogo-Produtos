package com.catalogo.services;

import com.catalogo.dto.CategoryDTO;
import com.catalogo.entities.Category;
import com.catalogo.repository.CategoryRepository;
import com.catalogo.Infra.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;
    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll(){
        return repository
                .findAll()
                .stream()
                .map(CategoryDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoryDTO buscarPorId(Long id){
        Optional<Category> ct = repository.findById(id);
        Category category = ct.orElseThrow(() -> new EntityNotFoundException("Item não encontrado"));

        return new CategoryDTO(category);
    }

    @Transactional
    public Category cadastrarCategory(CategoryDTO dto){
        Category category = new Category(dto);

        return repository.save(category);
    }

    @Transactional
    public void deletarCategory(Long id){
        Category category = repository.findById(id).get();

        repository.delete(category);
    }

    @Transactional
    public CategoryDTO editarCategory(Long id, CategoryDTO dto){
        Category category = repository.findById(id).get();
        category.setNome(dto.getNome());

        return new CategoryDTO(category);
    }


}

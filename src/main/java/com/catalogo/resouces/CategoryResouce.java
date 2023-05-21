package com.catalogo.resouces;

import com.catalogo.dto.CategoryDTO;
import com.catalogo.entities.Category;
import com.catalogo.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.LineNumberReader;
import java.util.List;


@RestController
@RequestMapping("/categories")
public class CategoryResouce {

    @Autowired
    private CategoryService service;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> buscar(){

        var list = service.findAll();

        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> buscarPorId(@PathVariable Long id){
        var category = service.buscarPorId(id);

        return ResponseEntity.ok().body(category);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<CategoryDTO> cadastrarCategoria(@RequestBody @Valid CategoryDTO dto, UriComponentsBuilder builder){

        var category = service.cadastrarCategory(dto);

        var uri = builder.path("/categories/{id}")
                .buildAndExpand(category.getId()).toUri();

        return ResponseEntity.created(uri).body(new CategoryDTO(category));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletarCategoria(@PathVariable Long id){
        service.deletarCategory(id);

        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<CategoryDTO> deletarCategoria(@PathVariable Long id, @RequestBody @Valid CategoryDTO dto){
        var category = service.editarCategory(id, dto);

        return ResponseEntity.ok().body(category);
    }
}

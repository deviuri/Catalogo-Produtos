package com.catalogo.resouces;

import com.catalogo.dto.CategoryDTO;
import com.catalogo.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping("/categories")
public class CategoryResouce {

    @Autowired
    private CategoryService service;

    @GetMapping
    public ResponseEntity<Page<CategoryDTO>> buscar(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "10") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction
    ) {

        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);

        Page<CategoryDTO> list = service.findAllPage(pageRequest);

        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> buscarPorId(@PathVariable Long id) {
        CategoryDTO category = service.buscarPorId(id);

        return ResponseEntity.ok().body(category);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<CategoryDTO> cadastrarCategoria(@RequestBody @Valid CategoryDTO dto, UriComponentsBuilder builder) {

        dto = service.cadastrarCategory(dto);

        URI uri = builder.path("/categories/{id}")
                .buildAndExpand(dto.getId()).toUri();

        return ResponseEntity.created(uri).body(dto);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deletarCategoria(@PathVariable Long id) {
        service.deletarCategory(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<CategoryDTO> deletarCategoria(@PathVariable Long id, @RequestBody @Valid CategoryDTO dto) {
        CategoryDTO category = service.editarCategory(id, dto);

        return ResponseEntity.ok().body(category);
    }
}

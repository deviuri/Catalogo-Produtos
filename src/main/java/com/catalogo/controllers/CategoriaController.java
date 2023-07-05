package com.catalogo.controllers;

import com.catalogo.dto.CategoriaDTO;
import com.catalogo.servicos.CategoryServico;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;


@RestController
@RequestMapping("/categorias")
@CrossOrigin(origins = "http://localhost:3000")
public class CategoriaController {

    @Autowired
    private CategoryServico service;

    @GetMapping
    public ResponseEntity<Page<CategoriaDTO>> buscar(Pageable paginacao) {
        Page<CategoriaDTO> list = service.findAllPage(paginacao);

        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> buscarPorId(@PathVariable Long id) {
        CategoriaDTO category = service.buscarPorId(id);

        return ResponseEntity.ok().body(category);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<CategoriaDTO> cadastrarCategoria(@RequestBody @Valid CategoriaDTO dto, UriComponentsBuilder builder) {

        dto = service.cadastrarCategory(dto);

        URI uri = builder.path("/categorias/{id}")
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
    public ResponseEntity<CategoriaDTO> deletarCategoria(@PathVariable Long id, @RequestBody @Valid CategoriaDTO dto) {
        CategoriaDTO category = service.editarCategory(id, dto);

        return ResponseEntity.ok().body(category);
    }
}

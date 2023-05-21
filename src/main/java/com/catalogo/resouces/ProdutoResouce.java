package com.catalogo.resouces;

import com.catalogo.dto.ProdutoDTO;

import com.catalogo.services.ServicoProduto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("/produtos")
public class ProdutoResouce {

    @Autowired
    private ServicoProduto service;

    @GetMapping
    public ResponseEntity<Page<ProdutoDTO>> buscar(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "10") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction
    ) {

        var pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);

        var list = service.findAllPage(pageRequest);

        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> buscarPorId(@PathVariable Long id) {
        var Produto = service.buscarPorId(id);

        return ResponseEntity.ok().body(Produto);
    }

//    @PostMapping
//    @Transactional
//    public ResponseEntity<ProdutoDTO> cadastrarCategoria(@RequestBody @Valid ProdutoDTO dto, UriComponentsBuilder builder) {
//
//        var Produto = service.cadastrarProduto(dto);
//
//        var uri = builder.path("/categories/{id}")
//                .buildAndExpand(Produto.getId()).toUri();
//
//        return ResponseEntity.created(uri).body(new ProdutoDTO(Produto));
//    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deletarCategoria(@PathVariable Long id) {
        service.deletarProduto(id);

        return ResponseEntity.noContent().build();
    }

//    @PutMapping("/{id}")
//    @Transactional
//    public ResponseEntity<ProdutoDTO> deletarCategoria(@PathVariable Long id, @RequestBody @Valid ProdutoDTO dto) {
//        var Produto = service.editarProduto(id, dto);
//
//        return ResponseEntity.ok().body(Produto);
//    }
}

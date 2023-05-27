package com.catalogo.controllers;

import com.catalogo.dto.ProdutoDTO;
import com.catalogo.servicos.ProdutoServico;
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
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoServico service;

    @GetMapping
    public ResponseEntity<Page<ProdutoDTO>> buscar(Pageable paginacao) {
        Page<ProdutoDTO> list = service.findAllPage(paginacao);

        return ResponseEntity.ok().body(list);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> buscarPorId(@PathVariable Long id) {
        ProdutoDTO Produto = service.buscarPorId(id);

        return ResponseEntity.ok().body(Produto);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ProdutoDTO> cadastrarProduto(@RequestBody ProdutoDTO produtoDTO, UriComponentsBuilder uriBuilder) {
        produtoDTO = service.cadastrarProduto(produtoDTO);
        URI uri = uriBuilder.path("/produtos/{id}")
                .buildAndExpand(produtoDTO.getId()).toUri();

        return ResponseEntity.created(uri).body(produtoDTO);
    }
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ProdutoDTO> editarCategoria(@PathVariable Long id, @RequestBody @Valid ProdutoDTO dto) {
        dto = service.editarProduto(id, dto);

        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deletarCategoria(@PathVariable Long id) {
        service.deletarProduto(id);

        return ResponseEntity.noContent().build();
    }
}

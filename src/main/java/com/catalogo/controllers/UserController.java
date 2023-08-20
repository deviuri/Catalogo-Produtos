package com.catalogo.controllers;

import com.catalogo.dto.UserDTO;
import com.catalogo.dto.UserInsertDTO;
import com.catalogo.servicos.UserServico;
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
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserServico service;

    @GetMapping
    public ResponseEntity<Page<UserDTO>> paginar(Pageable paginacao) {
        Page<UserDTO> list = service.findAllPage(paginacao);

        return ResponseEntity.ok().body(list);
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> buscarPorId(@PathVariable Long id) {
        UserDTO User = service.buscarPorId(id);

        return ResponseEntity.ok().body(User);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<UserDTO> cadastrarUser(@RequestBody UserInsertDTO user, UriComponentsBuilder uriBuilder) {
        UserDTO userDTO = service.cadastrarUser(user);
        URI uri = uriBuilder.path("/Users/{id}")
                .buildAndExpand(userDTO.getId()).toUri();

        return ResponseEntity.created(uri).body(userDTO);
    }
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<UserDTO> editarCategoria(@PathVariable Long id, @RequestBody @Valid UserDTO dto) {
        dto = service.atualizarUser(id, dto);

        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deletarCategoria(@PathVariable Long id) {
        service.deletarUser(id);

        return ResponseEntity.noContent().build();
    }
}

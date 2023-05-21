package com.catalogo.resouces;

import com.catalogo.dto.CategoryDTO;
import com.catalogo.entities.Category;
import com.catalogo.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories")
public class CategoryResouce {

    @Autowired
    private CategoryService service;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> buscar(){

        List<CategoryDTO> list = service.findAll();

        return ResponseEntity.ok().body(list);
    }
}

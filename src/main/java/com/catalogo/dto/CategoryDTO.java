package com.catalogo.dto;

import com.catalogo.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    private Long id;
    private String nome;

    public CategoryDTO(Category entity){
        this.id = entity.getId();
        this.nome = entity.getNome();
    }
}

package com.catalogo.dto;


import com.catalogo.entities.Category;
import com.catalogo.entities.Produto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {

    private Long id;
    private String nome;
    private String descricao;
    private double preco;
    private String img_url;
    private Instant data;

    private List<CategoryDTO> categories = new ArrayList<>();

    public ProdutoDTO(Produto entity) {
        this.id = entity.getId();
        this.nome = entity.getNome();
        this.descricao = entity.getDescricao();
        this.preco = entity.getPreco();
        this.img_url = entity.getImg_url();
        this.data = entity.getData();
    }
    public ProdutoDTO(Produto entity, Set<Category> categories) {
        this(entity);
        categories.forEach(category -> this.categories.add(new CategoryDTO(category)));
    }


}

package com.catalogo.dto;


import com.catalogo.entities.Category;
import com.catalogo.entities.Produto;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

public class ProdutoDTO {

    private Long id;
    private String nome;
    private String descricao;
    private double preco;
    private String imgUrl;
    private Instant data;

    private Set<CategoryDTO> categorias = new HashSet<>();

    public ProdutoDTO() {
    }

    public ProdutoDTO(Long id, String nome, String descricao, Double preco, String imgUrl, Instant data) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.imgUrl = imgUrl;
        this.data = data;
    }

    public ProdutoDTO(Produto entity) {
        this.id = entity.getId();
        this.nome = entity.getNome();
        this.descricao = entity.getDescricao();
        this.preco = entity.getPreco();
        this.imgUrl = entity.getImgUrl();
        this.data = entity.getData();
    }

    public ProdutoDTO(Produto entity, Set<Category> categorias) {
        this(entity);
        categorias.forEach(categoria -> this.categorias.add(new CategoryDTO(categoria)));
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Instant getData() {
        return data;
    }

    public void setData(Instant data) {
        this.data = data;
    }

    public Set<CategoryDTO> getCategorias() {
        return categorias;
    }
}

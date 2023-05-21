package com.catalogo.entities;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tb_product")
public class Produto {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @Column(columnDefinition = "TEXT")
    private String descricao;
    private double preco;
    private String img_url;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant data;

    @ManyToMany
    @JoinTable(
      name = "tb_product_category",
      joinColumns = @JoinColumn(name = "product_id"),
      inverseJoinColumns = @JoinColumn(name = "category_id"))
    Set<Category> categories = new HashSet<>();

    public Produto() {
    }

    public Produto(Long id, String nome, String descricao, double price, String img_url, Instant data) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = price;
        this.img_url = img_url;
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public double getPrice() {
        return preco;
    }

    public void setPrice(double price) {
        this.preco = price;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public Instant getData() {
        return data;
    }

    public void setData(Instant data) {
        this.data = data;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Produto produto)) return false;
        return Objects.equals(id, produto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

package com.catalogo.entities;

import com.catalogo.dto.CategoryDTO;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "tb_category")
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    public Category(){
    }


    public Category(Long id, String nome){
        this.id = id;
        this.nome = nome;
    }

    public Category(CategoryDTO dto) {
        this.nome = dto.getNome();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category category)) return false;
        return Objects.equals(id, category.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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


}

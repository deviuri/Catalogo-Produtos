package com.catalogo.entities;

import com.catalogo.dto.CategoryDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "tb_category")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant created_At;

    public Category(CategoryDTO dto) {
        this.nome = dto.getNome();
    }

}

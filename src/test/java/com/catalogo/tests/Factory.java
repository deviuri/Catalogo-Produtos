package com.catalogo.tests;

import com.catalogo.dto.ProdutoDTO;
import com.catalogo.entities.Categoria;
import com.catalogo.entities.Produto;

import java.time.Instant;

public class Factory {
    public static Produto criarProduto(){
        Produto produto = new Produto(1L, "Phone", "Good Phone", 800.0, "https://img.com/img.png", Instant.now());
        produto.getCategorias().add(new Categoria(2L, "Eletronics"));
        return produto;
    }

    public static ProdutoDTO criarProdutoDTO(){
        Produto produto = criarProduto();
        return new ProdutoDTO(produto, produto.getCategorias());
    }
}

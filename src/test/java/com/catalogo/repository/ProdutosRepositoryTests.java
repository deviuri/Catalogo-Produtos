package com.catalogo.repository;

import com.catalogo.entities.Produto;
import com.catalogo.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;

@DataJpaTest
public class ProdutosRepositoryTests {

    long idTest;
    long IdInexistente;

    @Autowired
    private ProdutosRepository repository;

    @BeforeEach
    void setUp() throws Exception{
        idTest = 1L;
        IdInexistente = 1000L;
    }
    @Test
    public void DeveRetornarNaoVazioQuandoIdExistirViceVersa(){
        Optional<Produto> result = repository.findById(25L);

        Assertions.assertTrue(result.isPresent());
    }

    @Test
    public void DeveSalvarQuandoIdForNulo(){
        Produto produto = Factory.criarProduto();
        produto.setId(null);

        produto = repository.save(produto);
        Assertions.assertNotNull(produto.getId());
    }

    @Test
    public void DeveApagarSeProdutoQuandoIdExistir(){

        repository.deleteById(idTest);

        Optional<Produto> produtoTest = repository.findById(idTest);

        Assertions.assertFalse(produtoTest.isPresent());
    }

    @Test
    public void DeveLancarSeIdNaoExistirEmptyResultDataAccessException(){

        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
            repository.deleteById(IdInexistente);
        });
    }
}

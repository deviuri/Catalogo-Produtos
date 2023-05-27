package com.catalogo.servicos;

import com.catalogo.repository.ProdutosRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class ProdutoServicoTests {

    @InjectMocks
    private ProdutoServico servico;
    @Mock
    private ProdutosRepository repository;

    private long IdExiste;
    private long Naoexiste;
    @BeforeEach
    void setUp() throws Exception{
        IdExiste = 1L;
        Naoexiste = 1000L;

        Mockito.doNothing().when(repository).deleteById(IdExiste);

        Mockito.doNothing().doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(Naoexiste);
    }

    @Test
    public void NaoDeveFazerNadaQuandoIdExistir() throws RuntimeException{
        Assertions.assertDoesNotThrow(() -> {
            servico.deletarProduto(IdExiste);
        });

        Mockito.verify(repository, Mockito.times(1)).deleteById(IdExiste);
    }
}

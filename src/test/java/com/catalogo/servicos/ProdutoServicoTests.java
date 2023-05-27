package com.catalogo.servicos;

import com.catalogo.Infra.exceptions.DatabaseException;
import com.catalogo.Infra.exceptions.ResourceNotFoundException;
import com.catalogo.repository.ProdutosRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class ProdutoServicoTests {

    @InjectMocks
    private ProdutoServico servico;
    @Mock
    private ProdutosRepository repository;

    private long IdExiste;
    private long IdNaoexiste;
    private long dependeId;
    @BeforeEach
    void setUp() throws Exception{
        IdExiste = 1L;
        IdNaoexiste = 1000L;
        dependeId = 4L;

        doNothing().when(repository).deleteById(IdExiste);
        
        doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(IdNaoexiste);

        doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependeId);
    }

    @Test
    public void DeveLancarDatabaseExceptionQuandoNaoExistirId(){

        Assertions.assertThrows(DatabaseException.class, () -> {
            servico.deletarProduto(dependeId);
        });

        verify(repository, times(1)).deleteById(dependeId);
    }

    @Test
    public void DeveLancarResouceNotFoundExceptionQuandoNaoExistirId(){

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            servico.deletarProduto(IdNaoexiste);
        });

        verify(repository, times(1)).deleteById(IdNaoexiste);
    }
    @Test
    public void NaoDeveFazerNadaQuandoIdExistir(){

        Assertions.assertDoesNotThrow(() -> {
            servico.deletarProduto(IdExiste);
        });

        verify(repository, times(1)).deleteById(IdExiste);
    }
}

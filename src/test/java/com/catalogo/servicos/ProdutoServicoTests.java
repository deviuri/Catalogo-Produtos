package com.catalogo.servicos;

import com.catalogo.Infra.exceptions.DatabaseException;
import com.catalogo.Infra.exceptions.ResourceNotFoundException;
import com.catalogo.dto.ProdutoDTO;
import com.catalogo.entities.Produto;
import com.catalogo.repository.ProdutosRepository;
import com.catalogo.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

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
    private PageImpl<Produto> page;
    private Produto produto;

    @BeforeEach
    void setUp() throws Exception{
        IdExiste = 1L;
        IdNaoexiste = 1000L;
        dependeId = 4L;

        produto = Factory.criarProduto();
        List<Produto> produtos = new java.util.ArrayList<>();
        produtos.add((produto));

        page = new PageImpl<>(produtos);

        when(repository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);

        when(repository.save(ArgumentMatchers.any())).thenReturn(produtos);

        when(repository.findById(IdExiste)).thenReturn(Optional.of(produto));

        when(repository.findById(IdNaoexiste)).thenReturn(Optional.empty());

        doNothing().when(repository).deleteById(IdExiste);
        doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(IdNaoexiste);
        doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependeId);
    }
    @Test
    public void findAllDeveRetornaUmaPagina(){
        Pageable pageable = PageRequest.of(0,10);

        Page<ProdutoDTO> resultado = servico.findAllPage(pageable);
        
        Assertions.assertNotNull(resultado);
        verify(repository, times(1)).findAll(pageable);
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

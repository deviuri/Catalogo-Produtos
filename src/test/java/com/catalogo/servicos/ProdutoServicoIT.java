package com.catalogo.servicos;

import com.catalogo.Infra.exceptions.ResourceNotFoundException;
import com.catalogo.dto.ProdutoDTO;
import com.catalogo.repository.ProdutosRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Transactional
public class ProdutoServicoIT {

    @Autowired
    private ProdutoServico servico;

    @Autowired
    private ProdutosRepository repository;

    private Long id;
    private Long naoExisteID;
    private Long qtdeProdutos;

    @BeforeEach
    void setUp(){
        id = 1L;
        naoExisteID = 1000L;
        qtdeProdutos = 25L;
    }

    @Test
    public void DeveDeletarProdutoQuandoIdExistir(){

        servico.deletarProduto(id);

        Assertions.assertEquals(qtdeProdutos - 1, repository.count());
    }

    @Test
    public void DeveLancarRosourceNotFoundQuandoIdProdutoNaoExistir(){
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            servico.deletarProduto(naoExisteID);
        });
    }

    @Test
    public void findAllPageDeveRetornarPaginaQuandoPagina0Tamanho10(){
        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<ProdutoDTO> result = servico.findAllPage(pageRequest);

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(0, result.getNumber());
        Assertions.assertEquals(10, result.getSize());
        Assertions.assertEquals(qtdeProdutos, result.getTotalElements());
    }

    @Test
    public void findAllPageDeveRetornarVazioQuandoPaginaNaoExistir(){
        PageRequest pageRequest = PageRequest.of(50, 10);

        Page<ProdutoDTO> result = servico.findAllPage(pageRequest);

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void findAllPageDeveRetornarOrdernaQuandoOrdernarPorNome(){
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("nome"));

        Page<ProdutoDTO> result = servico.findAllPage(pageRequest);

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals("Macbook Pro",result.getContent().get(0).getNome());
        Assertions.assertEquals("PC Gamer",result.getContent().get(1).getNome());
        Assertions.assertEquals("PC Gamer Alfa",result.getContent().get(2).getNome());

    }

}

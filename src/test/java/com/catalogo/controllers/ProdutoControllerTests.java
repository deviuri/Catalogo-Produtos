package com.catalogo.controllers;

import com.catalogo.dto.ProdutoDTO;
import com.catalogo.servicos.ProdutoServico;
import com.catalogo.tests.Factory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(ProdutoController.class)
public class ProdutoControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProdutoServico servico;
    private ProdutoDTO produtoDTO;
    private PageImpl<ProdutoDTO> page;

    @BeforeEach
    void setUp(){
        produtoDTO = Factory.criarProdutoDTO();
        List<ProdutoDTO> produtoDTOS = new ArrayList<>();
        produtoDTOS.add(produtoDTO);
        page = new PageImpl<>(produtoDTOS);

        when(servico.findAllPage(any())).thenReturn(page);

    }

    @Test
    public void findAllDeveRetornarUmPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/produtos"))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

}

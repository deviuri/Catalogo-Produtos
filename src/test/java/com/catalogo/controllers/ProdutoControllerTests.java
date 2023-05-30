package com.catalogo.controllers;

import com.catalogo.Infra.exceptions.DatabaseException;
import com.catalogo.Infra.exceptions.ResourceNotFoundException;
import com.catalogo.dto.ProdutoDTO;
import com.catalogo.servicos.ProdutoServico;
import com.catalogo.tests.Factory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProdutoController.class)
public class ProdutoControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProdutoServico servico;
    private ProdutoDTO produtoDTO;
    private PageImpl<ProdutoDTO> page;

    private Long id;
    private Long NaoID;
    private Long DependeID;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        id = 1L;
        NaoID = 2L;
        DependeID = 3L;

        produtoDTO = Factory.criarProdutoDTO();
        List<ProdutoDTO> produtoDTOS = new ArrayList<>();
        produtoDTOS.add(produtoDTO);
        page = new PageImpl<>(produtoDTOS);

        when(servico.findAllPage(any())).thenReturn(page);

        when(servico.buscarPorId(id)).thenReturn(produtoDTO);
        when(servico.buscarPorId(NaoID)).thenThrow(ResourceNotFoundException.class);

        when(servico.atualizarProduto(eq(id), any())).thenReturn(produtoDTO);
        when(servico.atualizarProduto(eq(NaoID), any())).thenThrow(ResourceNotFoundException.class);

        when(servico.cadastrarProduto(any())).thenReturn(produtoDTO);

        doNothing().when(servico).deletarProduto(id);
        doThrow(ResourceNotFoundException.class).when(servico).deletarProduto(NaoID);
        doThrow(DatabaseException.class).when(servico).deletarProduto(DependeID);
    }
    @Test
    public void DeveRetornarNotfoundQuandoIdNaoExistir() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/produtos/{id}", NaoID))
                .andExpect(status().isNotFound());
    }

    @Test
    public void DeveRetornaNocontentQuandoExistir() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/produtos/{id}", id))
                .andExpect(status().isNoContent());
    }

    @Test
    public void DeveRetornaCreatedQuando() throws Exception {
        String jsonbody = objectMapper.writeValueAsString(produtoDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/produtos")
                        .content(jsonbody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void AtualizarDeveRetornarProdutoDTOQuandoIdExistir() throws Exception {
        String jsonbody = objectMapper.writeValueAsString(produtoDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/produtos/{id}", id)
                        .content(jsonbody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nome").exists());

    }

    @Test
    public void AtualizarDeveLancarResourceNotFoundQuandoIdExistir() throws Exception {
        String jsonbody = objectMapper.writeValueAsString(produtoDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/produtos/{id}", NaoID)
                        .content(jsonbody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    public void findAllDeveRetornarUmPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/produtos")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void DeveRetornarUmProdutoQuandoIdExistir() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/produtos/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nome").exists())
                .andExpect(jsonPath("$.descricao").exists());


    }

    @Test
    public void DeveRetornarUmNotFoundQuandoIdNaoExistir() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/produtos/{id}", NaoID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}

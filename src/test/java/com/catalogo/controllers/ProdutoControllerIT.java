package com.catalogo.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProdutoControllerIT {

    @Autowired
    private MockMvc mockMvc;

    private Long id;
    private Long naoExisteID;
    private Long qtdeProdutos;

    @BeforeEach
    void setUp() {
        id = 1L;
        naoExisteID = 1000L;
        qtdeProdutos = 25L;
    }

    @Test
    public void findAllDeveRetornarPageOrdenadaPorNome() throws Exception {


        mockMvc.perform(MockMvcRequestBuilders.get("/produtos?page=0&size=10&sort=nome,asc").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(qtdeProdutos))
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.content[0].nome").value("Macbook Pro"))
                .andExpect(jsonPath("$.content[1].nome").value("PC Gamer"))
                .andExpect(jsonPath("$.content[2].nome").value("PC Gamer Alfa"))
                .andExpect(jsonPath("$.content[3].nome").value("PC Gamer Boo"));
    }
}

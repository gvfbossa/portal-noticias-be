package br.com.bws.portalnoticias.web.controller;

import br.com.bws.portalnoticias.application.service.NoticiaService;
import br.com.bws.portalnoticias.domain.entity.Noticia;
import br.com.bws.portalnoticias.domain.model.Category;
import br.com.bws.portalnoticias.domain.model.Type;
import br.com.bws.portalnoticias.web.dto.PagedResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class NoticiaControllerTest {

    private MockMvc mockMvc;
    private NoticiaService noticiaService;
    private NoticiaController noticiaController;

    private Noticia noticia;

    @BeforeEach
    void setup() {
        noticiaService = Mockito.mock(NoticiaService.class);
        noticiaController = new NoticiaController(noticiaService);
        mockMvc = MockMvcBuilders.standaloneSetup(noticiaController).build();

        noticia = new Noticia();
        noticia.setId(1L);
        noticia.setHeadline("Título Notícia");
    }

    @Test
    void deveCadastrarNoticia() throws Exception {
        Mockito.when(noticiaService.cadastrarNoticia(any(), any(), anyString(), anyString(), anyString(), any(), anyString()))
                .thenReturn(noticia);

        MockMultipartFile mockFile = new MockMultipartFile(
                "image",
                "teste.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "conteudo do arquivo".getBytes()
        );

        mockMvc.perform(multipart("/api/noticias/cadastro")
                        .file(mockFile)
                        .param("type", "HIGHLIGHT")
                        .param("category", "GERAL")
                        .param("headline", "Título Notícia")
                        .param("subtitle", "Sub Notícia")
                        .param("summary", "Resumo Notícia")
                        .param("fullText", "Texto Completo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.headline").value("Título Notícia"));
    }

    @Test
    void deveAtualizarNoticiaSemArquivo() throws Exception {
        Mockito.when(noticiaService.atualizarNoticia(eq(1L), any(), any(), anyString(), anyString(), anyString(), any(), anyString()))
                .thenReturn(noticia);

        mockMvc.perform(put("/api/noticias/cadastro/1")
                        .param("headline", "Título Atualizado")
                        .param("subtitle", "Sub Atualizado")
                        .param("summary", "Resumo Atualizado")
                        .param("fullText", "Texto Atualizado"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.headline").value("Título Notícia"));
    }

    @Test
    void deveDeletarNoticia() throws Exception {
        Mockito.doNothing().when(noticiaService).deletarNoticia(1L);

        mockMvc.perform(delete("/api/noticias/cadastro/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deveListarNoticias() throws Exception {
        Page<Noticia> page = new PageImpl<>(List.of(noticia));
        Mockito.when(noticiaService.getNoticias(0, 10)).thenReturn(page);

        mockMvc.perform(get("/api/noticias/todas")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].headline").value("Título Notícia"));
    }
}

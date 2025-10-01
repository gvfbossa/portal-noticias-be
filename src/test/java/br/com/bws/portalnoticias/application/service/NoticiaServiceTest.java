package br.com.bws.portalnoticias.application.service;

import br.com.bws.portalnoticias.application.exception.NoticiaNaoEncontradaException;
import br.com.bws.portalnoticias.domain.entity.Noticia;
import br.com.bws.portalnoticias.domain.model.Category;
import br.com.bws.portalnoticias.domain.model.Type;
import br.com.bws.portalnoticias.domain.repository.NoticiaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NoticiaServiceTest {

    private NoticiaRepository noticiaRepository;
    private CloudinaryService cloudinaryService;
    private NoticiaService noticiaService;

    @BeforeEach
    void setup() {
        noticiaRepository = Mockito.mock(NoticiaRepository.class);
        cloudinaryService = Mockito.mock(CloudinaryService.class);
        noticiaService = new NoticiaService(noticiaRepository, cloudinaryService);
    }

    @Test
    void deveCadastrarNoticiaComImagem() throws IOException {
        MockMultipartFile file = new MockMultipartFile("image", "teste.jpg", "image/jpeg", "conteudo".getBytes());
        when(cloudinaryService.uploadImage(file)).thenReturn("url-da-imagem");
        when(noticiaRepository.save(any(Noticia.class))).thenAnswer(i -> i.getArgument(0));

        Noticia noticia = noticiaService.cadastrarNoticia(Type.HIGHLIGHT, Category.GERAL,
                "Título", "Sub", "Resumo", file, "Texto completo");

        assertNotNull(noticia);
        assertEquals("Título", noticia.getHeadline());
        assertEquals("url-da-imagem", noticia.getImagePath());
        verify(noticiaRepository).save(any(Noticia.class));
    }

    @Test
    void deveCadastrarNoticiaSemImagem() throws IOException {
        when(noticiaRepository.save(any(Noticia.class))).thenAnswer(i -> i.getArgument(0));

        Noticia noticia = noticiaService.cadastrarNoticia(Type.HIGHLIGHT, Category.GERAL,
                "Título", "Sub", "Resumo", null, "Texto completo");

        assertNotNull(noticia);
        assertEquals("Título", noticia.getHeadline());
        assertNull(noticia.getImagePath());
        verify(noticiaRepository).save(any(Noticia.class));
    }

    @Test
    void deveAtualizarNoticiaExistente() throws IOException {
        Noticia existing = new Noticia();
        existing.setId(1L);
        existing.setHeadline("Old Title");
        when(noticiaRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(cloudinaryService.uploadImage(any())).thenReturn("nova-url");
        when(noticiaRepository.save(any(Noticia.class))).thenAnswer(i -> i.getArgument(0));

        MockMultipartFile file = new MockMultipartFile("image", "novo.jpg", "image/jpeg", "conteudo".getBytes());
        Noticia updated = noticiaService.atualizarNoticia(1L, Type.HIGHLIGHT, Category.GERAL,
                "Novo Título", null, null, file, null);

        assertEquals("Novo Título", updated.getHeadline());
        assertEquals("nova-url", updated.getImagePath());
    }

    @Test
    void atualizarNoticiaNaoExistenteDeveLancarExcecao() {
        when(noticiaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NoticiaNaoEncontradaException.class, () ->
                noticiaService.atualizarNoticia(99L, null, null, null, null, null, null, null)
        );
    }

    @Test
    void deveDeletarNoticiaExistente() {
        Noticia existing = new Noticia();
        existing.setId(1L);
        when(noticiaRepository.findById(1L)).thenReturn(Optional.of(existing));

        noticiaService.deletarNoticia(1L);

        verify(noticiaRepository).delete(existing);
    }

    @Test
    void deletarNoticiaInexistenteNaoFazNada() {
        when(noticiaRepository.findById(99L)).thenReturn(Optional.empty());

        noticiaService.deletarNoticia(99L);

        verify(noticiaRepository, never()).delete(any());
    }

    @Test
    void deveRetornarNoticiasPaginadas() {
        Noticia n1 = new Noticia();
        n1.setHeadline("Notícia 1");
        Noticia n2 = new Noticia();
        n2.setHeadline("Notícia 2");

        Page<Noticia> page = new PageImpl<>(Arrays.asList(n1, n2));
        when(noticiaRepository.findAll(any(Pageable.class))).thenReturn(page);

        Page<Noticia> result = noticiaService.getNoticias(0, 10);

        assertEquals(2, result.getContent().size());
        assertEquals("Notícia 1", result.getContent().getFirst().getHeadline());
    }
}

package br.com.bws.portalnoticias.application.service;

import br.com.bws.portalnoticias.domain.entity.Anuncio;
import br.com.bws.portalnoticias.domain.model.AdPosition;
import br.com.bws.portalnoticias.domain.repository.AnuncioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AnuncioServiceTest {

    private AnuncioRepository anuncioRepository;
    private CloudinaryService cloudinaryService;
    private AnuncioService anuncioService;

    @BeforeEach
    void setup() {
        anuncioRepository = Mockito.mock(AnuncioRepository.class);
        cloudinaryService = Mockito.mock(CloudinaryService.class);
        anuncioService = new AnuncioService(anuncioRepository, cloudinaryService);
    }

    @Test
    void deveCadastrarAnuncioComImagem() throws IOException {
        MockMultipartFile file = new MockMultipartFile("imagem", "banner.jpg", "image/jpeg", "conteudo".getBytes());
        when(cloudinaryService.uploadImage(file)).thenReturn("url-da-imagem");
        when(anuncioRepository.save(any(Anuncio.class))).thenAnswer(i -> i.getArgument(0));

        Anuncio anuncio = anuncioService.cadastrarAnuncio("https://exemplo.com", file, AdPosition.MAIN_TOP, LocalDate.now().plusDays(10));

        assertNotNull(anuncio);
        assertEquals("url-da-imagem", anuncio.getImagem());
        assertEquals("https://exemplo.com", anuncio.getUrl());
        assertEquals(AdPosition.MAIN_TOP, anuncio.getPosition());
        verify(anuncioRepository).save(any(Anuncio.class));
    }

    @Test
    void deveRetornarTodosAnunciosNaoExpirados() {
        Anuncio a1 = new Anuncio();
        Anuncio a2 = new Anuncio();
        List<Anuncio> lista = Arrays.asList(a1, a2);

        when(anuncioRepository.findByDataExpiracaoGreaterThanEqual(LocalDate.now())).thenReturn(lista);

        List<Anuncio> result = anuncioService.getAll();

        assertEquals(2, result.size());
        verify(anuncioRepository).findByDataExpiracaoGreaterThanEqual(any(LocalDate.class));
    }

    @Test
    void deveRetornarAnuncioPorId() {
        Anuncio anuncio = new Anuncio();
        when(anuncioRepository.findById(1L)).thenReturn(Optional.of(anuncio));

        Optional<Anuncio> result = anuncioService.getById(1L);

        assertTrue(result.isPresent());
        verify(anuncioRepository).findById(1L);
    }

    @Test
    void deleteExpiredDeveChamarRepository() {
        anuncioService.deleteExpired();
        verify(anuncioRepository).deleteByDataExpiracaoBefore(any(LocalDate.class));
    }

    @Test
    void existsByIdDeveRetornarTrue() {
        when(anuncioRepository.existsById(1L)).thenReturn(true);
        assertTrue(anuncioService.existsById(1L));
        verify(anuncioRepository).existsById(1L);
    }

    @Test
    void deleteByIdDeveChamarRepository() {
        anuncioService.deleteById(1L);
        verify(anuncioRepository).deleteById(1L);
    }
}

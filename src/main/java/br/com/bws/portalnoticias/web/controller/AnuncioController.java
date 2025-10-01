package br.com.bws.portalnoticias.web.controller;

import br.com.bws.portalnoticias.domain.model.AdPosition;
import br.com.bws.portalnoticias.domain.entity.Anuncio;
import br.com.bws.portalnoticias.application.service.AnuncioService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/anuncios")
public class AnuncioController {

    private final AnuncioService anuncioService;

    public AnuncioController(AnuncioService anuncioService) {
        this.anuncioService = anuncioService;
    }

    @GetMapping
    public ResponseEntity<?> listarTodos() {
        return ResponseEntity.ok(anuncioService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return anuncioService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/add-positions")
    public ResponseEntity<?> getAdPositions() {
        return ResponseEntity.ok(List.of(AdPosition.values()));
    }

    @PostMapping(value = "/cadastro", consumes = "multipart/form-data")
    public ResponseEntity<?> criar(
            @RequestParam("url") String url,
            @RequestParam("imagem") MultipartFile imagem,
            @RequestParam("position") AdPosition position,
            @RequestParam("dataExpiracao") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataExpiracao
    ) {
        try {
            Anuncio anuncio = anuncioService.cadastrarAnuncio(url, imagem, position, dataExpiracao);
            return ResponseEntity.status(HttpStatus.CREATED).body(anuncio);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao fazer upload da imagem.");
        }
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (anuncioService.existsById(id)) {
            anuncioService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}

package br.com.bws.portalnoticias.web.controller;

import br.com.bws.portalnoticias.domain.entity.Noticia;
import br.com.bws.portalnoticias.web.dto.PagedResponse;
import br.com.bws.portalnoticias.application.service.NoticiaService;
import br.com.bws.portalnoticias.domain.model.Category;
import br.com.bws.portalnoticias.domain.model.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/noticias")
public class NoticiaController {

    private final NoticiaService noticiaService;

    public NoticiaController(NoticiaService noticiaService) {
        this.noticiaService = noticiaService;
    }

    @PostMapping("/cadastro")
    public ResponseEntity<Noticia> cadastrarNoticia(
            @RequestParam String type,
            @RequestParam String category,
            @RequestParam String headline,
            @RequestParam String subtitle,
            @RequestParam String summary,
            @RequestParam(required = false) MultipartFile image,
            @RequestParam String fullText) throws IOException {

        Noticia noticia = noticiaService.cadastrarNoticia(Type.valueOf(type.toUpperCase()), Category.valueOf(category.toUpperCase()), headline, subtitle,
                summary, image, fullText);

        return ResponseEntity.ok(noticia);
    }

    @PutMapping("/cadastro/{id}")
    public ResponseEntity<Noticia> atualizarNoticia(
            @PathVariable Long id,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String headline,
            @RequestParam(required = false) String subtitle,
            @RequestParam(required = false) String summary,
            @RequestParam(required = false) MultipartFile image,
            @RequestParam(required = false) String fullText) throws IOException {

        Noticia noticia = noticiaService.atualizarNoticia(id,
                type != null ? Type.valueOf(type.toUpperCase()) : null,
                category != null ? Category.valueOf(category.toUpperCase()) : null,
                headline, subtitle, summary, image, fullText);

        return ResponseEntity.ok(noticia);
    }

    @DeleteMapping("/cadastro/{id}")
    public ResponseEntity<Void> deletarNoticia(@PathVariable Long id) {
        noticiaService.deletarNoticia(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/todas")
    public PagedResponse<Noticia> getNoticias(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<Noticia> noticiasPage = noticiaService.getNoticias(page, size) ;
        return new PagedResponse<>(noticiasPage);
    }

}

package br.com.bws.portalnoticias.application.service;

import br.com.bws.portalnoticias.application.exception.NoticiaNaoEncontradaException;
import br.com.bws.portalnoticias.domain.model.Category;
import br.com.bws.portalnoticias.domain.entity.Noticia;
import br.com.bws.portalnoticias.domain.model.Type;
import br.com.bws.portalnoticias.domain.repository.NoticiaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class NoticiaService {

    private final NoticiaRepository noticiaRepository;
    private final CloudinaryService cloudinaryService;

    public NoticiaService(NoticiaRepository noticiaRepository, CloudinaryService cloudinaryService) {
        this.noticiaRepository = noticiaRepository;
        this.cloudinaryService = cloudinaryService;
    }

    public Optional<Noticia> getNoticiaById(Long id) {
        return noticiaRepository.findById(id);
    }

    public Noticia cadastrarNoticia(Type type, Category category, String headline, String subtitle,
                                    String summary, MultipartFile image, String fullText) throws IOException {

        String imageUrl = null;

        if (image != null && !image.isEmpty()) {
            try {
                imageUrl = cloudinaryService.uploadImage(image);
            } catch (Exception ignored) {
            }
        }

        Noticia noticia = new Noticia();
        noticia.setType(type);
        noticia.setCategory(category);
        noticia.setHeadline(headline);
        noticia.setSubtitle(subtitle);
        noticia.setSummary(summary);
        if (imageUrl != null)
            noticia.setImagePath(imageUrl);
        noticia.setDate(LocalDate.now());
        noticia.setFullText(fullText);

        return noticiaRepository.save(noticia);
    }

    public Page<Noticia> getNoticias(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "date"));
        return noticiaRepository.findAll(pageable);
    }

    public Noticia atualizarNoticia(Long id, Type type, Category category, String headline, String subtitle,
                                    String summary, MultipartFile image, String fullText) throws IOException {
        Optional<Noticia> optionalNoticia = noticiaRepository.findById(id);

        if (optionalNoticia.isPresent()) {
            Noticia noticia = optionalNoticia.get();

            if (type != null) noticia.setType(type);
            if (category != null) noticia.setCategory(category);
            if (headline != null) noticia.setHeadline(headline);
            if (subtitle != null) noticia.setSubtitle(subtitle);
            if (summary != null) noticia.setSummary(summary);
            if (image != null) noticia.setImagePath(cloudinaryService.uploadImage(image));
            if (fullText != null) noticia.setFullText(fullText);

            return noticiaRepository.save(noticia);
        } else {
            throw new NoticiaNaoEncontradaException("Notícia não encontrada com o ID: " + id);
        }
    }

    public void deletarNoticia(Long id) {
        Optional<Noticia> noticia = noticiaRepository.findById(id);
        noticia.ifPresent(noticiaRepository::delete);
    }
}

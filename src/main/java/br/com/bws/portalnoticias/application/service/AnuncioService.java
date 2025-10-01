package br.com.bws.portalnoticias.application.service;

import br.com.bws.portalnoticias.domain.model.AdPosition;
import br.com.bws.portalnoticias.domain.entity.Anuncio;
import br.com.bws.portalnoticias.domain.repository.AnuncioRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AnuncioService {

    private final AnuncioRepository anuncioRepository;
    private final CloudinaryService cloudinaryService;

    public AnuncioService(AnuncioRepository anuncioRepository, CloudinaryService cloudinaryService) {
        this.anuncioRepository = anuncioRepository;
        this.cloudinaryService = cloudinaryService;
    }

    public List<Anuncio> getAll() {
        return anuncioRepository.findByDataExpiracaoGreaterThanEqual(LocalDate.now());
    }

    public Optional<Anuncio> getById(Long id) {
        return anuncioRepository.findById(id);
    }

    public Anuncio cadastrarAnuncio(String url, MultipartFile imagem, AdPosition position, LocalDate dataExpiracao) throws IOException {
        String imageUrl = null;

        if (imagem != null && !imagem.isEmpty()) {
            imageUrl = cloudinaryService.uploadImage(imagem);
        }

        Anuncio anuncio = new Anuncio();
        anuncio.setImagem(imageUrl);
        anuncio.setUrl(url);
        anuncio.setPosition(position);
        anuncio.setDataExpiracao(dataExpiracao);

        return anuncioRepository.save(anuncio);
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteExpired() {
        anuncioRepository.deleteByDataExpiracaoBefore(LocalDate.now());
    }

    public boolean existsById(Long id) {
        return anuncioRepository.existsById(id);
    }

    public void deleteById(Long id) {
        anuncioRepository.deleteById(id);
    }
}
